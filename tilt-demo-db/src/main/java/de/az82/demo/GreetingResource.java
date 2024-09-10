package de.az82.demo;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        List<Greeting> greetings = Greeting.listAll();
        return greetings.get(ThreadLocalRandom.current().nextInt(greetings.size())).text;
    }
}
