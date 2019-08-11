package org.ddemidiuk.example.images.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageInBase64Dto {
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("imageInBase64")
    private String imageInBase64;
    @JsonProperty("rewrite")
    private boolean rewrite;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getImageInBase64() {
        return imageInBase64;
    }

    public void setImageInBase64(String imageInBase64) {
        this.imageInBase64 = imageInBase64;
    }

    public boolean isRewrite() {
        return rewrite;
    }

    public void setRewrite(boolean rewrite) {
        this.rewrite = rewrite;
    }
}
