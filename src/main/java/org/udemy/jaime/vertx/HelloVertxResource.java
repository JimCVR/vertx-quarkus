package org.udemy.jaime.vertx;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.HttpResponse;
import io.vertx.mutiny.ext.web.client.WebClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.udemy.jaime.Users;

import java.net.URI;
import java.util.List;

@Path("/vertx")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class HelloVertxResource {
    private static final Logger LOG = LoggerFactory.getLogger(HelloVertxResource.class);
    private final Vertx vertx;

    @Inject
    public HelloVertxResource(Vertx vertx){
        this.vertx = vertx;
    }

    @GET
    public Uni<JsonArray> get() {
        LOG.info("Get all ids...");
        JsonArray item = new JsonArray();
        item.add(new JsonObject().put("id", 1));
        item.add(new JsonObject().put("id", 2));
        return Uni.createFrom().item(item);
    }

    @GET
    @Path("/users")
    public Uni<JsonArray> getFromUsers() {
        WebClient client = WebClient.create(vertx,
                new WebClientOptions()
                        .setDefaultHost("localhost")
                        .setDefaultPort(8080));
        return client
                .get("/users")
                .send()
                .onItem()
                .transform(HttpResponse::bodyAsJsonArray);
    }
}
