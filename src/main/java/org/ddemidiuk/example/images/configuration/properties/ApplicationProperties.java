package org.ddemidiuk.example.images.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "images")
public class ApplicationProperties {
    private boolean useFileSystemStorage;
    private String filesystemStorePath;
    private int urlConnectTimeoutMillis;
    private int urlReadTimeoutMillis;
    private int imageMaxSizeKb;
    private int imageBufferMaxSizeMb;

    public boolean isUseFileSystemStorage() {
        return useFileSystemStorage;
    }

    public void setUseFileSystemStorage(boolean useFileSystemStorage) {
        this.useFileSystemStorage = useFileSystemStorage;
    }

    public String getFilesystemStorePath() {
        return filesystemStorePath;
    }

    public void setFilesystemStorePath(String filesystemStorePath) {
        this.filesystemStorePath = filesystemStorePath;
    }

    public int getUrlConnectTimeoutMillis() {
        return urlConnectTimeoutMillis;
    }

    public void setUrlConnectTimeoutMillis(int urlConnectTimeoutMillis) {
        this.urlConnectTimeoutMillis = urlConnectTimeoutMillis;
    }

    public int getUrlReadTimeoutMillis() {
        return urlReadTimeoutMillis;
    }

    public void setUrlReadTimeoutMillis(int urlReadTimeoutMillis) {
        this.urlReadTimeoutMillis = urlReadTimeoutMillis;
    }

    public int getImageMaxSizeKb() {
        return imageMaxSizeKb;
    }

    public void setImageMaxSizeKb(int imageMaxSizeKb) {
        this.imageMaxSizeKb = imageMaxSizeKb;
    }

    public int getImageBufferMaxSizeMb() {
        return imageBufferMaxSizeMb;
    }

    public void setImageBufferMaxSizeMb(int imageBufferMaxSizeMb) {
        this.imageBufferMaxSizeMb = imageBufferMaxSizeMb;
    }
}