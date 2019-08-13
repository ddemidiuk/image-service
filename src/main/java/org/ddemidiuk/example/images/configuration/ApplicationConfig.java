package org.ddemidiuk.example.images.configuration;

import com.codahale.metrics.InstrumentedExecutorService;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.readytalk.metrics.StatsDReporter;
import org.ddemidiuk.example.images.configuration.properties.ApplicationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Configuration of basic beans.
 */
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {

    private static final int BYTES_IN_KILOBYTE = 1024;
    private int executorServiceThreadNumber;

    @Bean
    MultipartConfigElement multipartConfigElement(ApplicationProperties properties) {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.of(properties.getImageMaxSizeKb(), DataUnit.KILOBYTES));
        factory.setMaxRequestSize(DataSize.of(properties.getImageMaxSizeKb() * 3, DataUnit.KILOBYTES));
        return factory.createMultipartConfig();
    }

/*    @Bean
    MultipartResolver multipartResolver(ApplicationProperties properties) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(properties.getImageMaxSizeKb() * BYTES_IN_KILOBYTE);
        multipartResolver.setMaxInMemorySize(properties.getImageMaxSizeKb() * BYTES_IN_KILOBYTE * 3);
        multipartResolver.setDefaultEncoding("utf-8");
        return multipartResolver;
    }*/

    @Bean
    public RestTemplate restTemplate(ApplicationProperties properties) {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(properties.getUrlConnectTimeoutMillis());
        httpRequestFactory.setConnectTimeout(properties.getUrlConnectTimeoutMillis());
        httpRequestFactory.setReadTimeout(properties.getUrlReadTimeoutMillis());

        return new RestTemplate(httpRequestFactory);
    }

    /**
     * {@link ExecutorService} for performing fad/rad notifications tasks.
     *
     * @return new {@link ExecutorService}
     */
    @Bean("client-executor")
    public ExecutorService clientExecutor(MetricRegistry metricRegistry) {
        return new InstrumentedExecutorService(
                Executors.newFixedThreadPool(executorServiceThreadNumber),
                metricRegistry,
                "client-executor");
    }

    /**
     * {@link ExecutorService} for performing async router tasks.
     *
     * @return new {@link ExecutorService}
     */
    @Bean("notification-org.ddemidiuk.example.images.service-executor")
    public ExecutorService notificationServiceExecutor(MetricRegistry metricRegistry) {
        return new InstrumentedExecutorService(
                Executors.newFixedThreadPool(executorServiceThreadNumber),
                metricRegistry,
                "notification-org.ddemidiuk.example.images.service-executor");
    }

    /**
     * Metric collector for obtaining app metrics.
     *
     * @param statsdSettings statsd settings
     * @return new {@link MetricRegistry}
     */
    @Bean
    public MetricRegistry createMetricsCollector(StatsDReporterProperties statsdSettings) {
        MetricRegistry registry = new MetricRegistry();
        if (statsdSettings.isEnable()) {
            StatsDReporter.forRegistry(registry)
                    .prefixedWith(statsdSettings.getPrefix())
                    .build(statsdSettings.getHost(), statsdSettings.getPort())
                    .start(statsdSettings.getPeriodSec(), TimeUnit.SECONDS);
        }

        return registry;
    }

    /**
     * Create health check collector.
     *
     * @return health check collector.
     */
    @Bean
    public HealthCheckRegistry healthCheckRegistry() {
        return new HealthCheckRegistry();
    }

    public int getExecutorServiceThreadNumber() {
        return executorServiceThreadNumber;
    }

    public void setExecutorServiceThreadNumber(int executorServiceThreadNumber) {
        this.executorServiceThreadNumber = executorServiceThreadNumber;
    }

}
