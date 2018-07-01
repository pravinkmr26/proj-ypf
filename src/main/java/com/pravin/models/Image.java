/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.models;

/**
 *
 * @author Pravinkumar
 */
public class Image {
    private String imageId;
    private byte[] image;
    private String fileName;
    private String contentType;

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public String getImageId() {
        return imageId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
