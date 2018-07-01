/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deletedPack;

import com.mongodb.BasicDBObject;
import com.pravin.Interfaces.IServiceOne;
import com.pravin.dao.ImagesDao;
import com.pravin.dao.PostsDao;
import com.pravin.dao.PostsMDao;
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
public class Ajax{
    @Autowired(required = true)
    UtilServices fu;
    @Autowired
    IServiceOne viaServiceOne;
    @Autowired
    ImagesDao imgDao;
    @Autowired
    PostsDao postDao;
    @Autowired
    PostsMDao postMongo;
    
    //----------------------------------------------------------------------------------------------decleared objects
    HttpSession session;
    
    private static final Logger LOGGER = Logger.getLogger(Ajax.class);
    //    @RequestMapping(value = "/savePost/{userId}", method = RequestMethod.POST)
    //public String savePost(@PathVariable("userId") String userId, HttpServletRequest req, HttpServletResponse res,Model map){
    @RequestMapping(value = "/savePost", method = RequestMethod.POST)
    public @ResponseBody
    String savePost(HttpServletRequest req, HttpServletResponse res, Model map) throws IOException {
        //code is in construction;
        
        viaServiceOne.savePost(req, res);
        
        return "Ok";
    }
    @RequestMapping(value = "/saveBlogPost", method = RequestMethod.POST)
    public @ResponseBody
    String saveBlogPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        session = req.getSession();
        session.setAttribute("userId", "user12");
        if(viaServiceOne.saveBlogPost(req))
            return "ok";
        return null;
    }
    @RequestMapping("/showImage/{imageId}")
    public void showImage(@PathVariable("imageId") String imageId, HttpServletRequest req, HttpServletResponse res, Model map) {
        Images dimg = fu.downloadImage(imageId);
        res.setContentType(dimg.getContentType());
        res.setContentLength(dimg.getImage().length);
        res.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        try {
            FileCopyUtils.copy(dimg.getImage(), res.getOutputStream());
        } catch (IOException ex) {
            map.addAttribute("error", ex);
        }
    }
    @RequestMapping("/loadPost")
    public @ResponseBody List<Posts> TestLoadPost(){
        
        return postDao.getPosts("user15");
    }
    @RequestMapping("/loadImage")
    public @ResponseBody Images tsting()
    {
        Images ob = postMongo.getImage("");
        return ob;
    }
    @RequestMapping("/loadPosts")
    public void loadPost(@PathVariable("postId")String postId,Model map){
        
    }
    @RequestMapping("/getMongoPost")
    public @ResponseBody BasicDBObject getMongoPost(){    
        return viaServiceOne.getPostWithComments("POST_22Pravinku593");
    }
    @RequestMapping("/TestMongo")
    public @ResponseBody String MongoTest(){
        //this  is just temporary code here!
        return "hi";
    }
}
