/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.models;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Pravinkumar
 */

@Document(collection="posts")
public class Posts {
    private String userId;
    private String postId;
    
    private String title;
    
    private Date time;
    
    private int hasImage;
    private String imageId[];
    
    private int hasResponse;

    
    public int getHasImage() {
        return hasImage;
    }

    public void setHasImage(int hasImage) {
        this.hasImage = hasImage;
    }

    public int getHasResponse() {
        return hasResponse;
    }

    public void setHasResponse(int hasResponse) {
        this.hasResponse = hasResponse;
    }

    public String[] getImageId() {
        return imageId;
    }

    public void setImageId(String[] imageId) {
        this.imageId = imageId;
    }

    public String getPostId() {
        return postId;
    }
    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
