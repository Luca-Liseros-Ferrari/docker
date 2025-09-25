package com.midoriPol.rs;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import dto.CartDto;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("/stripe")
public class StripeResource {
  private static String apiKey() {
    String skKey = System.getenv("sk_key");
    System.out.println
            ("Retrieved key: " + (skKey != null ? skKey : "No skKey found"));
    return skKey;
  }

  private static final Logger LOGGER = Logger.getLogger(StripeResource.class.getName());

  @POST
  @Path("/confirm")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response stripeRedirect(CartDto cartDto) {
    System.out.println("stripe ok");
    LOGGER.info("Received request to /stripe/confirm with cart data: " + cartDto);

    Stripe.apiKey = apiKey();

    try {
      LOGGER.info("Building line items...");
      SessionCreateParams.LineItem[] lineItems = cartDto.getCartProducts().stream()
        .map(cartProduct -> {
          LOGGER.info("Price from cartProduct: " + cartProduct.getPrice());
          LOGGER.info("Processing product: " + cartProduct.getName()
            + " with quantity: " + cartProduct.getQuantity());
          return SessionCreateParams.LineItem.builder()
            .setQuantity(cartProduct.getQuantity().longValue())
            .setPriceData(
              SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("eur")
                .setUnitAmount(cartProduct.getPrice() * 100)
                .setProductData(
                  SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName(cartProduct.getName() + " - " + cartProduct.getCategory())
                    .build())
                .build())
            .build();
        }).toArray(SessionCreateParams.LineItem[]::new);

      LOGGER.info("Line items built: " + Arrays.stream(lineItems)
              .map(SessionCreateParams.LineItem::toString)
              .collect(Collectors.joining(", ")));

      SessionCreateParams params = SessionCreateParams.builder()
        .setSuccessUrl("http://localhost:4200/success")
        .setCancelUrl("http://localhost:4200/cancel")
        .addAllLineItem(Arrays.asList(lineItems))
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .build();

      LOGGER.info("Creating Stripe session with params: " + params);
      Session session = Session.create(params);
      LOGGER.info("Created Stripe session, ID: " + session.getId());
      return Response.ok(session.getUrl()).build();
    } catch (Exception e) {
      LOGGER.severe("Error creating Stripe session: " + e.getMessage());
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error creating Stripe session").build();
    }
  }
}
