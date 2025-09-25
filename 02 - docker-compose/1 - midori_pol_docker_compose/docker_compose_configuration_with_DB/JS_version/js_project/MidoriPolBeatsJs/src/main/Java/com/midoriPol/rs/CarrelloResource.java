package com.midoriPol.rs;

import com.midoriPol.business.BeatsManager;
import com.midoriPol.model.*;
import dto.CartDto;
import dto.CartProductDto;
import dto.ProductDto;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/carrello")
public class CarrelloResource {

    private EntityManagerFactory emf;

    public CarrelloResource() {
        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    @GET
    @Path("/viewCart")
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewCart (@QueryParam("userId") Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Person person = em.find(Person.class, userId);
            if (person == null) {
                return ResourceUtility.notFound("Utente non trovato");
            }
            Cart cart = person.getCart();
            if (cart == null) {
                return ResourceUtility.notFound("Carrello non trovato per questo utente");
            }

            // Inizializza esplicitamente la collezione CartProducts
            Hibernate.initialize(cart.getCartProducts());

            em.getTransaction().commit();

            CartDto cartDTO = mapCartToDTO(cart);
            return ResourceUtility.ok(cartDTO);

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return ResourceUtility.internalServerError("Si è verificato un errore durante il recupero del carrello.");
        } finally {
            em.close();
        }
    }

    // metodo per rendere visibile il carrello all'utente
    private CartDto mapCartToDTO(Cart cart) {
        CartDto cartDTO = new CartDto();
        cartDTO.setId(cart.getId());

        List<CartProductDto> cartProductDTOs = new ArrayList<>();

        for (CartProduct cartProduct : cart.getCartProducts()) {
            CartProductDto cartProductDTO = new CartProductDto();
            cartProductDTO.setId(cartProduct.getId());
            cartProductDTO.setName(cartProduct.getName());
            cartProductDTO.setCategory(cartProduct.getCategory());
            cartProductDTO.setQuantity(cartProduct.getQuantity());

            cartProductDTOs.add(cartProductDTO);

        }

        cartDTO.setCartProducts(cartProductDTOs);

        return cartDTO;
    }

    @POST
    @Path("/less")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response lessProductToCart (ProductDto product) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // Recupero la persona
        Person person = em.find(Person.class, product.getUserId());
        if (person == null) {
            return ResourceUtility.notFound("Persona non trovata");
        }
        // Recupero il carrello
        Cart cart = person.getCart();
        if (cart == null) {
            return ResourceUtility.notFound("carrello non trovato");
        }
        // Recupero il prodotto nel carrello
        CartProduct existingProduct = em.createNamedQuery("quantityMinusOne", CartProduct.class)
                .setParameter("productId", product.getId())
                .setParameter("cartId", cart.getId())
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (existingProduct != null) {
            // Decremento la quantità solo se è maggiore di 1
            if (existingProduct.getQuantity() > 1) {
                existingProduct.setQuantity(existingProduct.getQuantity() - 1);
                em.merge(existingProduct);
                em.getTransaction().commit();
                em.close();
                return Response.ok().build();
            } else {
                // Rimuovo il prodotto se la quantità è 1
                em.remove(existingProduct);
                em.getTransaction().commit();
                em.close();
                return Response.ok().build();
            }
        } else {
            em.getTransaction().commit();
            em.close();
            return ResourceUtility.notFound("prodotto non trovato nel carrello");
        }
    }
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProductToCart(ProductDto product) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Cerca la persona con l'ID specificato
            Person person = em.find(Person.class, product.getUserId());

            // Se la persona non esiste, crea una nuova persona
            if (person == null) {
                person = new Person();
                person.setId(product.getUserId());
                em.persist(person); // Usa persist per una nuova entità
            }

            // Ottieni il carrello della persona o crea un nuovo carrello se non esiste
            Cart cart = person.getCart();
            if (cart == null) {
                cart = new Cart();
                cart.setPerson(person);
                person.setCart(cart);
                em.persist(cart); // Persisti il nuovo carrello
            }

            // Cerca se esiste già il prodotto nel carrello
            CartProduct existingProduct = em.createNamedQuery("quantityPlusOne", CartProduct.class)
                    .setParameter("productId", product.getId())
                    .setParameter("cartId", cart.getId())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (existingProduct != null) {
                // Aggiorna la quantità del prodotto esistente
                existingProduct.setQuantity(existingProduct.getQuantity() + 1);
                em.merge(existingProduct); // Usa merge per entità esistenti
            } else {
                // Crea un nuovo prodotto nel carrello
                CartProduct nuovoProdotto = new CartProduct();
                nuovoProdotto.setName(product.getName());
                nuovoProdotto.setQuantity(1);
                nuovoProdotto.setCategory(product.getCategory());
                Product productEntity = em.find(Product.class, product.getId());
                nuovoProdotto.setProduct(productEntity);
                cart.addProduct(nuovoProdotto); // Aggiungi il prodotto al carrello
                em.persist(nuovoProdotto); // Persisti il nuovo prodotto
            }

            em.getTransaction().commit(); // Committa la transazione
            return Response.ok("Prodotto aggiunto").build();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Rollback in caso di errore
            }
            return ResourceUtility.internalServerError("Si è verificato un errore durante l'aggiunta del prodotto al carrello.");
        } finally {
            em.close();
        }
    }
}


