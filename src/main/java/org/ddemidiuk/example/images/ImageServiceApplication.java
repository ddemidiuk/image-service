package org.ddemidiuk.example.images;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

/**
 * Find-a-Doc application.
 */
@SpringBootApplication
public class ImageServiceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageServiceApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ImageServiceApplication.class, args);
    }

    @EventListener
    public void handleContextRefreshEvent(ContextRefreshedEvent ctxRefreshedEvent) {
        LOGGER.info("Running under java version: {}", System.getProperty("java.version"));
    }
}
