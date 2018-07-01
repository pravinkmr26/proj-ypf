/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.models;

import java.util.Date;
/**
 *
 * @author Pravinkumar
 */

public class Comment{
    private String userId;
    private String comment;
    private String commentId;
    private Date time;
    private int hasImage;
    private int hasResponse;
    private int gotLikes;
    private String[] imageId;

    public Comment() {
        
    }

    public Comment(String userId, String comment, Date time, int hasImage, int hasResponse, int gotLikes, String[] imageId) {
        this.userId = userId;
        this.comment = comment;
        this.time = time;
        this.hasImage = hasImage;
        this.hasResponse = hasResponse;
        this.gotLikes = gotLikes;
        this.imageId = imageId;
    }
    
    
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

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

    public int getGotLikes() {
        return gotLikes;
    }

    public void setGotLikes(int gotLikes) {
        this.gotLikes = gotLikes;
    }

    public String[] getImageId() {
        return imageId;
    }

    public void setImageId(String[] imageId) {
        this.imageId = imageId;
    }

}
