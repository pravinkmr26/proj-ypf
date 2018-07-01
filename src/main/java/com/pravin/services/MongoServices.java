/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.services;

import com.mongodb.BasicDBObject;
import com.pravin.dao.PostsMDao;
import com.pravin.models.Comment;
import com.pravin.models.Image;
import com.pravin.models.Images;
import com.pravin.models.Reply;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author Pravinkumar
 */
@Service
public class MongoServices {

    //-----------------------------------------------------------------------------AutoWired objects
    @Autowired MongoTemplate viaMongo;
    @Autowired PostsMDao viaMongoPost;
    @Autowired UtilServices viaUtilServices;

    //----------------------------------------------------------------------------Global objects
    Query query;
    private static final Logger LOGGER = Logger.getLogger(MongoServices.class);

    //-------------------------------------------------------------------------------methods
    public Reply prepareReply(HttpServletRequest req){
        Reply reply = new Reply();
        try{
        reply.setReply(req.getParameter("comment"));
        reply.setReplyId("REP_"+RandomStringUtils.randomAlphabetic(8)+RandomStringUtils.randomAlphabetic(3));
        reply.setTime(new Date());
        reply.setUserId(req.getAttribute("userId").toString());
        }catch(Exception ex){
            LOGGER.debug("Error @MongosErvices.prepareREply() "+ex.getMessage());
            return null;
        }
        return reply;
    }
    
    
    
    public Comment prepareComment(MultipartHttpServletRequest mreq) {
        Comment comment = new Comment();
        try {
            comment.setComment(mreq.getParameter("comment"));
            int size = mreq.getFiles("images").size();
            if(size == 1 && mreq.getFiles("images").iterator().next().getBytes().length == 0){size = 0;}
            comment.setHasImage(size);
            comment.setTime(new Date());
            comment.setUserId(mreq.getAttribute("userId").toString());
            comment.setCommentId(prepareANameForPost(viaUtilServices.nameItWithInTen(mreq.getLocalName() + "" + mreq.getLocalAddr())));
            if (comment.getHasImage() > 0) {
                try {
                    List<String> list = saveImages(mreq, comment.getCommentId());
                    comment.setImageId(list.toArray(new String[0]));
                } catch (Exception ex) {
                    LOGGER.error("Error @MongoSErvices.prepareComment " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            LOGGER.error("error in preparing comment " + ex);
        }
        return comment;
    }

    public String prepareANameForPost(String name) {
        return viaUtilServices.nameItWithInTwenty("CMT_" + RandomStringUtils.randomAlphabetic(5) + RandomStringUtils.randomAlphabetic(5) + name + Calendar.getInstance().get(Calendar.MILLISECOND));
    }

    public List<String> saveImages(MultipartHttpServletRequest mreq, String postId) {
        List<String> imageId = new ArrayList<>();
        try {
            List<Images> images = parseImages(mreq.getFiles("images"));
            for (Images img : images) {
                img.setPostId(postId);
                imageId.add(img.getImageId());
            }
            viaMongoPost.setImage(images);
        } catch (Exception ex) {
            LOGGER.error("Error at parsing files from multipart req @ MongoSErvices.saveImages() " + ex.getMessage());
            return null;
        }
        return imageId;
    }

    private List<Images> parseImages(List<MultipartFile> files) {
        List<Images> list = new ArrayList<>();
        Images img;
        for (MultipartFile file : files) {
            try {
                img = new Images();
                img.setContentType(file.getContentType());
                img.setFileName(file.getOriginalFilename());
                img.setImage(file.getBytes());
                img.setImageId("IMG" + RandomStringUtils.randomAlphabetic(10) + "" + RandomStringUtils.randomAlphabetic(5));

                list.add(img);

            } catch (IOException ex) {
                LOGGER.error("Error foreach loop @MongoSErvices.parseimages() " + ex.getMessage());
                return null;
            }
             catch (Exception ex) {
                LOGGER.error("Error foreach loop @MongoSErvices.parseimages() " + ex.getMessage());
                return null;
            }
        }
        return list;
    }
    public List<BasicDBObject> getUserContributions(String userId){
        try{
        query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.fields().include("title").include("postId").include("time").exclude("_id");
        return viaMongo.find(query,BasicDBObject.class,"Posts");
        }
        catch(Exception ex){
            LOGGER.debug("Error @MongoServices.getUserContributions ");
        }
        return null;
    }
    public BasicDBObject doLikeComment(String postId, String commentId,String userId){
        query = new Query();
        try{
        query.addCriteria(Criteria.where("postId").is(postId).andOperator(Criteria.where("comments.commentId").is(commentId)));
        query.fields().include("comments.gotLikes").exclude("_id");
        query.fields().elemMatch("gotLikes",Criteria.where("comments.commentId").is(commentId));
        Update update = new Update();
        update.inc("comments.$.gotLikes",1);
        return viaMongo.findAndModify(query, update,BasicDBObject.class ,"Posts");
        //viaMongo.updateFirst(query, update,"Posts");
        }catch(Exception ex){
            LOGGER.debug("Error @MongoServices.doLikeComment() "+ex.getMessage());
        }
        return null;
    }
    public boolean setDP(String userId,Image Img){
        query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        Update update = new Update();
        update.set("dp", Img);
        viaMongo.updateFirst(query, update,"Util");
        return true;
    }
    public Object getDP(String userId){
        //---------------------------------------------------------- Code is in construction
        try{
        query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.fields().include("dp").exclude("_id");
        BasicDBObject ob =  viaMongo.findOne(query,BasicDBObject.class,"Util");
        
        ob  = (BasicDBObject)ob.get("dp");
        
        return viaMongo.getConverter().read(Object.class,ob);
     }catch(Exception ex){
            return null;
        }
    }
    public String getCurrentUrl(){
        query = new Query();
        query.addCriteria(Criteria.where("_id").is("5aca3d07bcbdaada3a7da67d"));
        query.fields().exclude("_id");
        return viaMongo.findOne(query, BasicDBObject.class, "appUtil").getString("url");
    }
    public void setCurrentUrl(String url){
        query = new Query();
        query.addCriteria(Criteria.where("_id").is("5aca3d07bcbdaada3a7da67d"));
        Update update = new Update();
        update.set("url", url);
        viaMongo.updateFirst(query, update, "appUtil");
    }
}



//        BasicDBObject dbob = viaMongo.findOne(query,BasicDBObject.class,"Util");
//        dbob = (BasicDBObject)dbob.get("dp");
//        Image ob;
//        ob  = viaMongo.getConverter().read(Image.class, dbob);
//        return ob;
   