package org.ddemidiuk.example.images.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageInUrlDto {
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("url")
    private String url;
    @JsonProperty("rewrite")
    private boolean rewrite;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRewrite() {
        return rewrite;
    }

    public void setRewrite(boolean rewrite) {
        this.rewrite = rewrite;
    }
}
