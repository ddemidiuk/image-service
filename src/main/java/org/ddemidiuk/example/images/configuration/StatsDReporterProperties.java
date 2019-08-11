package org.ddemidiuk.example.images.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Settings for metrics sender to statsd server.
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "statsd")
public class StatsDReporterProperties {
    private String host;
    private int port;
    private String prefix;
    private int periodSec;
    private boolean enable;

    public int getPeriodSec() {
        return periodSec;
    }

    public void setPeriodSec(int periodSec) {
        this.periodSec = periodSec;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
