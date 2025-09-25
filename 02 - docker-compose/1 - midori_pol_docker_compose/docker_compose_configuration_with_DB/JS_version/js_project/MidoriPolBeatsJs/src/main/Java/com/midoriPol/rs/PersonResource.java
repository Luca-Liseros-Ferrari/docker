package com.midoriPol.rs;
import com.midoriPol.business.BeatsManager;
import com.midoriPol.model.Cart;
import com.midoriPol.model.Product;
import com.midoriPol.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/persons")
public class PersonResource {
    private EntityManagerFactory emf;


    public PersonResource() {

        emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPerson(Person person) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // Creazione del carrello
        Cart cart = new Cart();
        em.persist(cart);
        // Associazione tra persona e carrello
        person.setCart(cart);
        cart.setPerson(person);
        // Creazione del prodotto di prova
        Product product = new Product();
        product.setName(product.getName());
        product.setCategory(product.getCategory());
        // Aggiunta del prodotto al carrello della persona
        //cart.addProduct(product);
       // product.setCart(cart);
        // product.setPerson(person);
        em.persist(person);
        em.persist(product);
        em.getTransaction().commit();
        em.close();
        return Response.ok().build();
    }


}