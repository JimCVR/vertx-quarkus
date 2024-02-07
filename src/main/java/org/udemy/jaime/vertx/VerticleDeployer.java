package org.udemy.jaime.vertx;

import io.quarkus.runtime.StartupEvent;
import io.vertx.core.AbstractVerticle;
import io.vertx.mutiny.core.Vertx;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;

@ApplicationScoped
public class VerticleDeployer {
    public void init(@Observes StartupEvent startupEvent, Vertx vertx, Instance<AbstractVerticle> verticles){
        verticles.forEach(v ->
                vertx.deployVerticle(v).await().indefinitely());
    }
}
