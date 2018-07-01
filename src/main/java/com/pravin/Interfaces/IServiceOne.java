/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.Interfaces;

import com.mongodb.BasicDBObject;
import com.pravin.models.Comment;
import com.pravin.models.Image;
import com.pravin.models.Posts;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pravinkumar
 */
public interface IServiceOne {

    List<Comment> getComments(String postId);

    BasicDBObject getPostWithComments(String postId);

    boolean savePost(HttpServletRequest req, HttpServletResponse res);
    
    boolean saveBlogPost(HttpServletRequest req);

    List<Posts> showPostForProfile(String userId);
    
    String setComments(HttpServletRequest mreq);
    
    String setReply(HttpServletRequest mreq);
    
    String saveUser(HttpServletRequest req);
    
    boolean verifyUser(HttpServletRequest req);
    
    boolean verifyOTP(String userId, String otp);
    
    List<BasicDBObject> getTrendingPosts(HttpServletRequest req);      
    
    List<BasicDBObject> getHomeContent();      
    
    List<Posts> loadPostsForProfile(HttpServletRequest req);
    
    List<BasicDBObject> refreshReplies(HttpServletRequest req);
     
    List<BasicDBObject> refreshComments(HttpServletRequest req);
    
    List<BasicDBObject> getUserContributions(HttpServletRequest req);
    
    BasicDBObject doLikeComment(HttpServletRequest req);
    
    String getUserName(String userId);
    
    String setDP(HttpServletRequest req);
    
    Image showDpByUserId(String userId);
    
    boolean setUpUser(HttpServletRequest req);
    
    void setCurrentUrl(HttpServletRequest req);
}
