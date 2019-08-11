package org.ddemidiuk.example.images.endpoint;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;

import java.util.SortedMap;
import java.util.TreeMap;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Test for {@code HealthCheckEndpoint}.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(HealthCheckEndpoint.class)
public class HealthCheckEndpointTest {
    @MockBean
    private HealthCheckRegistry healthCheckRegistry;

    @Autowired
    private MockMvc mvc;


    @Test
    public void testStatus() throws Exception {

        mockHealthCheckResult();
        mvc.perform(get("/api/healthcheck").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fad", is(true)));
    }

    private void mockHealthCheckResult() {
        final SortedMap<String, HealthCheck.Result> status = new TreeMap<>();
        status.put("fad", HealthCheck.Result.healthy());
        when(healthCheckRegistry.runHealthChecks()).thenReturn(status);
    }
}
