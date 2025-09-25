package com.midoriPol.rs;

import com.midoriPol.business.BeatsManager;
import com.midoriPol.business.CarrelloManager;
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
    public Response viewCart (@QueryParam("userId") String userId) {
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
            CartDto cartDTO = CarrelloManager.mapCartToDTO(cart);
            return ResourceUtility.ok(cartDTO);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return ResourceUtility.internalServerError
                    ("Si è verificato un errore durante il recupero del carrello.");
        } finally {
            em.close();
        }
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
        em.getTransaction().begin();
        // person code
        Person person = null;
        String userId = product.getUserId();
        if (userId != null) {
            person = em.find(Person.class, userId);
        }
        if (person == null) {
            person = new Person();
            person.setId(userId);
            em.merge(person);
        }
        Cart cart = person.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setPerson(person);
            person.setCart(cart);
            em.persist(cart);
        }
        // +1 se esiste
        CartProduct existingProduct = em.createNamedQuery("quantityPlusOne", CartProduct.class)
                .setParameter("productId", product.getId())
                .setParameter("cartId", cart.getId())
                .getResultStream()
                .findFirst()
                .orElse(null);

        CartProduct exclusiveProduct =
                em.createNamedQuery("avoidPlusIfExclusive", CartProduct.class)
                .setParameter("exclusive", CategoriaProdotto.EXCLUSIVE)
                .setParameter("cartId", cart.getId())
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (exclusiveProduct != null) {
            System.out.println("exclusive già nel carrello");
        }

        if (existingProduct != null) {
            if (product.getCategory().equals(CategoriaProdotto.EXCLUSIVE)) {
                existingProduct.setQuantity(1);
            } else {
                existingProduct.setQuantity(existingProduct.getQuantity() + 1);
            }
            em.merge(existingProduct);
        } else {
            CartProduct nuovoProdotto = new CartProduct();
            nuovoProdotto.setName(product.getName());
            nuovoProdotto.setQuantity(1);
            nuovoProdotto.setCategory(product.getCategory());
            nuovoProdotto.setPrice(product.getPrice());
            Long productId = product.getId();
            Product productEntity = em.find(Product.class, productId);
            nuovoProdotto.setProduct(productEntity);
            nuovoProdotto.associo(nuovoProdotto.getId(), cart, person);
            cart.addProduct(nuovoProdotto);
            em.persist(nuovoProdotto);
        }
        em.getTransaction().commit();
        em.close();
        return ResourceUtility.ok("prodotto aggiunto");
    }

  @POST
  @Path("/delete")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response deleteAllProducts(CartDto cart) {
    EntityManager em = emf.createEntityManager();
    em.getTransaction().begin();
    // Recupero il carrello
    Person personCart = em.find(Person.class, cart.getUserId());
    System.out.println(personCart);
    if (personCart == null) {
      em.getTransaction().rollback();
      em.close();
      return ResourceUtility.notFound("Cart non trovato");
    } else {
      // Rimuovo il carrello
      em.remove(personCart);
      em.getTransaction().commit();
      em.close();
      return Response.ok().build();
    }
  }
}


