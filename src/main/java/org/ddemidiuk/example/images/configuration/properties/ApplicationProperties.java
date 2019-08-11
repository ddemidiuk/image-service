package org.ddemidiuk.example.images.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "images")
public class ApplicationProperties {
    private String storePath;
    private int urlConnectTimeout;
    private int urlReadTimeout;
    private int imageMaxSize;

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public int getUrlConnectTimeout() {
        return urlConnectTimeout;
    }

    public void setUrlConnectTimeout(int urlConnectTimeout) {
        this.urlConnectTimeout = urlConnectTimeout;
    }

    public int getUrlReadTimeout() {
        return urlReadTimeout;
    }

    public void setUrlReadTimeout(int urlReadTimeout) {
        this.urlReadTimeout = urlReadTimeout;
    }

    public int getImageMaxSize() {
        return imageMaxSize;
    }

    public void setImageMaxSize(int imageMaxSize) {
        this.imageMaxSize = imageMaxSize;
    }
}
