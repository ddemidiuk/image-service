package org.ddemidiuk.example.images.endpoint.dto;

public class ImageResponseDto {
    private String imageFileName;
    private boolean isSuccessfullySaved;

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public boolean isSuccessfullySaved() {
        return isSuccessfullySaved;
    }

    public void setSuccessfullySaved(boolean successfullySaved) {
        isSuccessfullySaved = successfullySaved;
    }
}
