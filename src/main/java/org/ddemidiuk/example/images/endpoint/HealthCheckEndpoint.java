package org.ddemidiuk.example.images.endpoint;

import com.codahale.metrics.health.HealthCheckRegistry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.ddemidiuk.example.images.endpoint.dto.HealthCheckMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Endpoint to check availability of integration endpoints.
 */
@Api(value = "Availability checker.", description = "Check availability of integration points.")
@RestController
@RequestMapping("/api/healthcheck")
public class HealthCheckEndpoint {

    private final HealthCheckRegistry healthCheckRegistry;

    @Autowired
    public HealthCheckEndpoint(HealthCheckRegistry healthCheckRegistry) {
        this.healthCheckRegistry = healthCheckRegistry;
    }

    /**
     * Checks availability of services.
     * @return  statuses of services.
     */
    @ApiOperation(value = "Check availability of services.", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> status() {
        Map<String, Object> healthcheckMap = new HashMap<>();

        collectHealthStatuses()
                .forEach(h -> healthcheckMap.put(h.getName(), h.isHealth()));

        return healthcheckMap;
    }

    private Stream<HealthCheckMessage> collectHealthStatuses() {
        return healthCheckRegistry.runHealthChecks().entrySet().stream()
                .map(e -> new HealthCheckMessage(e.getKey(), e.getValue().getMessage(), e.getValue().isHealthy()));
    }
}
