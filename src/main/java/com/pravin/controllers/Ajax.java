/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.controllers;

import com.mongodb.BasicDBObject;
import com.pravin.Interfaces.IServiceLogin;
import com.pravin.Interfaces.IServiceOne;
import com.pravin.dao.ImagesDao;
import com.pravin.dao.PostsDao;
import com.pravin.dao.PostsMDao;
import com.pravin.models.Image;
import com.pravin.models.Images;
import com.pravin.models.Posts;
import com.pravin.services.UtilServices;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Pravinkumar
 */
@Controller
public class Ajax {

    @Autowired UtilServices viaUtilServices;
    @Autowired
    IServiceOne viaServiceOne;
    @Autowired
    IServiceLogin viaServiceLogin;
    @Autowired
    ImagesDao imgDao;
    @Autowired
    PostsDao postDao;
    @Autowired
    PostsMDao postMongo;

    //----------------------------------------------------------------------------------------------decleared objects
    HttpSession session;
    private static String result = "ok";
    private static final Logger LOGGER = Logger.getLogger(Ajax.class);

    //--------------------------------------------------------------------------------------------------Methods    
    @RequestMapping(value = "/registerUserDetails", method = RequestMethod.POST)
    public @ResponseBody
    Object saveUserDetails(HttpServletRequest req, HttpServletResponse res) {
        result = viaServiceOne.saveUser(req);
        if (result.contains("YPFERR")) {
            return "errorPage";
        }
        return new Object[]{result};
    }

    @RequestMapping(value = "/savePost")
    public @ResponseBody
    String savePost(HttpServletRequest req, HttpServletResponse res, Model map) throws IOException {
        //code is in construction;
        if (viaServiceLogin.processLogin(req)) {
            if (viaServiceOne.savePost(req, res)) {
                return "Ok";
            }
        }
        return null;
    }

    @RequestMapping(value = "/saveBlogPost", method = RequestMethod.POST)
    public @ResponseBody
    String saveBlogPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        if (viaServiceLogin.processLogin(req)) {
            if (viaServiceOne.saveBlogPost(req)) {
                return result;
            }
        }
        return null;
    }

    @RequestMapping("/saveComment")
    public @ResponseBody
    String savePost(HttpServletRequest req, HttpServletResponse res) {
        if (viaServiceLogin.processLogin(req)) {
            return viaServiceOne.setComments(req);
        }
        return null;
    }

    @RequestMapping("/saveReply")
    public @ResponseBody
    String saveReply(HttpServletRequest req, HttpServletResponse res) {
        if (viaServiceLogin.processLogin(req)) {
            result = viaServiceOne.setReply(req);
            return result;
        }
        return null;

    }

    @RequestMapping("/showImage/{imageId}")
    public void showImage(@PathVariable("imageId") String imageId, HttpServletRequest req, HttpServletResponse res, Model map) {
        if (viaServiceLogin.processLogin(req)) {
            Images dimg = viaUtilServices.downloadImage(imageId);
            res.setContentType(dimg.getContentType());
            res.setContentLength(dimg.getImage().length);
            res.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            try {
                FileCopyUtils.copy(dimg.getImage(), res.getOutputStream());
            } catch (IOException ex) {
                map.addAttribute("error", ex);
            }
        }
    }

    @RequestMapping("/loadPost/{userId}")
    public @ResponseBody
    List<Posts> loadPostForProfile(@PathVariable("userId") String userId, HttpServletRequest req) {
        if (viaServiceLogin.processLogin(req)) {
            req.setAttribute("userId", userId);
            return viaServiceOne.loadPostsForProfile(req);
        }
        return null;
    }

    @RequestMapping("/getSource/{imageId}")
    public @ResponseBody
    void viewingSource(@PathVariable("imageId") String imageId, HttpServletRequest req, HttpServletResponse res) {
        if (viaServiceLogin.processLogin(req)) {
            Images dimg = postMongo.getImage(imageId);
            res.setContentType(dimg.getContentType());
            res.setContentLength(dimg.getImage().length);
            res.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            try {
                FileCopyUtils.copy(dimg.getImage(), res.getOutputStream());
            } catch (IOException ex) {
                LOGGER.debug("error @Ajax .viewingSource() " + ex.getMessage());
            }
        }

    }

    @RequestMapping("/getImage/{imageId}")
    public @ResponseBody
    Images tsting(@PathVariable("imageId") String imageId) {
        Images ob = postMongo.getImage(imageId);
        return ob;
    }

    @RequestMapping("/getDP/{userId}")
    public void showDP(HttpServletRequest req, HttpServletResponse res, @PathVariable("userId") String userId) {

        if (viaServiceLogin.processLogin(req)) {
            try {
                Image dp = viaServiceOne.showDpByUserId(userId);
                res.setContentType(dp.getContentType());
                //res.setContentLength(dp.getImage().length);
                res.setContentLength(dp.getImage().length);
                res.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
                try {
                    FileCopyUtils.copy(dp.getImage(), res.getOutputStream());
                } catch (IOException ex) {
                    LOGGER.debug("Error");
                }
            } catch (Exception ex) {
                LOGGER.debug(("Error"));
            }
        }
    }
    @RequestMapping("/getHomeContent")
    public @ResponseBody List<BasicDBObject> getHomeContents(){
        return viaServiceOne.getHomeContent();
    }
    @RequestMapping("/setDP")
    public @ResponseBody 
    String setDP(HttpServletRequest req, HttpServletResponse res) {
        if (viaServiceLogin.processLogin(req)) {
            return viaServiceOne.setDP(req);
        } else {
            return "YPFSESSIONERR";
        }
    }

    @RequestMapping("/getPost/{postId}")
    public @ResponseBody
    BasicDBObject loadBlogPost(HttpServletRequest req, @PathVariable("postId") String postId) {
        if (viaServiceLogin.processLogin(req)) {
            try {
                return viaServiceOne.getPostWithComments(postId);
            } catch (Exception ex) {
                LOGGER.debug("Error @Ajax.loadBlogPost() " + ex.getMessage());
            }
        }
        return null;
    }

    @RequestMapping("/getTrendingPosts")
    public @ResponseBody
    List<BasicDBObject> getTrendingPosts(HttpServletRequest req) {
        return viaServiceOne.getTrendingPosts(req);
    }

    @RequestMapping("/getReplies/{postId}/{commentId}")
    public @ResponseBody
    List<BasicDBObject> refreshReplies(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId, HttpServletRequest req, HttpServletResponse res) {
        if (viaServiceLogin.processLogin(req)) {
            req.setAttribute("postId", postId);
            req.setAttribute("commentId", commentId);
            return viaServiceOne.refreshReplies(req);
        }
        return null;
    }

    @RequestMapping("/getComments/{postId}")
    public @ResponseBody
    List<BasicDBObject> refreshComments(@PathVariable("postId") String postId, HttpServletRequest req, HttpServletResponse res) {
        if (viaServiceLogin.processLogin(req)) {
            req.setAttribute("postId", postId);
            return viaServiceOne.refreshComments(req);
        }
        return null;
    }

    @RequestMapping("/contributions")
    public @ResponseBody
    List<BasicDBObject> getContributions(HttpServletRequest req) {
        if (viaServiceLogin.processLogin(req)) {
            viaServiceOne.getUserContributions(req);
        }
        return null;
    }

    @RequestMapping("/doLikeComment/{postId}/{commentId}")
    public @ResponseBody
    BasicDBObject doLikeComment(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId, HttpServletRequest req, HttpServletResponse res) {
        if (viaServiceLogin.processLogin(req)) {
            req.setAttribute("postId", postId);
            req.setAttribute("commentId", commentId);
            return viaServiceOne.doLikeComment(req);
        }
        return null;
    }
    @RequestMapping("/getUserName/{userId}")
    public @ResponseBody Object returnUserName(@PathVariable("userId")String userId){
        return new Object[]{viaServiceOne.getUserName(userId)};
    }
        

}
