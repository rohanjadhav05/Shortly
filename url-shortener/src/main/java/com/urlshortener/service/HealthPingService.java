package com.urlshortener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Periodically pings the external health endpoint to keep it warm.
 */
@Service
public class HealthPingService {

    private static final Logger logger = LoggerFactory.getLogger(HealthPingService.class);

    private static final String HEALTH_URL = "https://shortly-oe7u.onrender.com/health";

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Calls the health endpoint every 5 minutes while the application is running.
     */
    @Scheduled(fixedRate = 300_000)
    public void pingHealthEndpoint() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(HEALTH_URL, String.class);
            logger.debug("Health ping to {} responded with status {} and body: {}", HEALTH_URL,
                    response.getStatusCodeValue(), response.getBody());
        } catch (Exception ex) {
            logger.warn("Failed to ping health endpoint {}: {}", HEALTH_URL, ex.getMessage());
        }
    }
}

