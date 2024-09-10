package de.az82.demo;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.jms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RandomDieConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomDieConsumer.class);

    @Inject
    ConnectionFactory connectionFactory;

    private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

    private volatile String lastPrice;

    public String getLastPrice() {
        return lastPrice;
    }

    void onStart(@Observes StartupEvent ev) {
        scheduler.submit(() -> {
            try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
                JMSConsumer consumer = context.createConsumer(context.createQueue("workQueue"));
                while (true) {
                    Message message = consumer.receive();
                    if (message == null) return;
                    LOGGER.info("Received die cast: {}", message.getBody(String.class));
                }
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        });
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduler.shutdown();
    }
}
