package com.sachindra.futsalbook.response;

public class ImageUploadResponse {
    String filename;

    public ImageUploadResponse(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
