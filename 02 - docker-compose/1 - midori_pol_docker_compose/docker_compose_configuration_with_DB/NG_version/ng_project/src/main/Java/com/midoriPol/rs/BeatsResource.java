package com.midoriPol.rs;

import com.midoriPol.business.BeatsManager;
import com.midoriPol.model.Product;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/beats")
public class BeatsResource {
    @GET
    public Response getAll() {
        List<Product> beats = BeatsManager.getAll();
        return ResourceUtility.ok(beats);
    }
    @GET
    @Path("/{id}")
    public Response getById (@PathParam("id") Integer id) {
        Product beat = BeatsManager.getById(id);
        return ResourceUtility.ok(beat);
    }
}

