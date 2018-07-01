/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.pravin.models.Comment;
import com.pravin.models.Images;
import com.pravin.models.Posts;
import com.pravin.models.Reply;
import com.pravin.services.MongoServices;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pravin
 */
@Service
public class PostsMDao {

    //--------------------------------------------------------------------------------------auto wired beans
    @Autowired
    MongoTemplate viaMongo;
    @Autowired
    MongoServices viaService;

    //----------------------------------------------------------------------------------------global objects
    Query query;

    private static final Logger LOGGER = Logger.getLogger(PostsMDao.class);
    //-----------------------------------------------------------------------------------------------------------methods;

   
    public Images getImage(String imageId) {
        query = new Query();
        query.addCriteria(Criteria.where("imageId").is(imageId));
        return viaMongo.findOne(query, Images.class, "Images");
    }
    
    public boolean setImage(List<Images> list){
        viaMongo.insert(list,"Images");
        return true;
    }
    
    public boolean setImage(Images img){
        viaMongo.insert(img,"Images");
        return true;
    }

    public List<Comment> getCommentsFromDBObect(List<Comment> list) {
        Iterator iter = list.iterator();
        List<Comment> commentList = new ArrayList();
        while (iter.hasNext()) {
            BasicDBObject ob = (BasicDBObject) iter.next();
            Comment comment = viaMongo.getConverter().read(Comment.class, ob);
            commentList.add(comment);
        }
        return commentList;
    }
    public List<BasicDBObject> getListOfTrendingPosts(){
        query = new  Query();
        query.fields().include("postId");
        query.fields().include("title");
        query.fields().include("time");
        query.limit(20);
        return viaMongo.find(query,BasicDBObject.class,"Posts");
    }
    public List<BasicDBObject> getHomeContents(){
        query = new  Query();
        query.fields().exclude("_id");
        query.limit(20);
        return viaMongo.find(query,BasicDBObject.class,"Posts");
    }
        public BasicDBObject getPost(String PostId) {
        query = new Query();
        query.addCriteria(Criteria.where("postId").is(PostId));
        query.with(new Sort(Sort.Direction.ASC, "time"));
        try {
            BasicDBObject object = viaMongo.findOne(query, BasicDBObject.class, "Posts");
//            Posts post = viaMongo.getConverter().read(Posts.class, object);
                

            return object;
        } catch (Exception ex) {
            LOGGER.error("Error at getMongoPost" + ex.getMessage());
        }
        return null;
    }
/*
    private long getSequence(String postId) {
        String qry = "{postId:'" + postId + "'},{sequnce,_id:0}";
        BasicQuery query = new BasicQuery(qry);
        return viaMongo.count(query, Long.class, "Posts");
        //return viaMongo.findOne(query,Integer.class,"Posts");//query, Integer.class,"Post");
    }
*/
    public boolean savePost(Posts post) {
        try {
            viaMongo.insert(post, "Posts");
        } catch (Exception ex) {
            LOGGER.error("Error at saving the mongo db post"+ex);
            return false;
        }
        return true;
    }
   
    
    public boolean saveThumnail(Images img) {
        viaMongo.insert(img, "Posts");
        return true;
    }
    public boolean saveReply(Reply reply, String postId, String CommentId){
        query = new Query();
        query.addCriteria(Criteria.where("postId").is(postId).andOperator(Criteria.where("comments.commentId").is(CommentId)));
        Update update = new Update();
        BasicDBObject ob = new  BasicDBObject();
        viaMongo.getConverter().write(reply, ob);
        update.addToSet("comments.$.replys",ob);
        update.inc("comments.$.hasResponse",1);
        viaMongo.updateFirst(query, update,"Posts");
        return true;
    }
    public boolean setComment(Comment comment, String postId) {
        query = new Query();
        query.addCriteria(Criteria.where("postId").is(postId));
        Update update = new Update();
        update.push("comments", comment);
        update.inc("hasResponse",1);
        viaMongo.updateFirst(query, update, "Posts");
        return true;
    }
    public List<BasicDBObject> refreshReplys(String postId,String commentId){
        query = new Query();
        query.addCriteria(Criteria.where("postId").is(postId).andOperator(Criteria.where("comments.commentId").is(commentId)));
        query.fields().include("comments.$.replys");
        return viaMongo.find(query, BasicDBObject.class,"Posts");
    }
    public List<BasicDBObject> refreshComments(String postId){
        query = new Query();
        query.addCriteria(Criteria.where("postId").is(postId));
        query.fields().include("comments");
        return viaMongo.find(query, BasicDBObject.class,"Posts");
    }
    
    

}
/*
DBObject ob = new BasicDBObject();
        DBObject ob2 = new BasicDBObject();
        BasicQuery query = new BasicQuery("{Name : 'hello' }");
        ob.put("Name",x);
        viaMongo.insert(ob,"Posts");
        
        JsonTest tst = viaMongo.findOne(query,JsonTest.class,"Posts");
        query = new BasicQuery("{postId :'POST_31DESKTOP-105' }");
        Images img = viaMongo.findOne( query, Images.class,"Posts");
        LOGGER.info("Successfully Ladied the image to the object");




 public boolean updatePost() {
        String qry = "{postId:" + "'POST_22Pravinku593'}";
        BasicQuery query = new BasicQuery(qry);
        Update update = new Update();
        Comment comment = viaService.prepareComment();
        update.push("comment", comment);
        update.set("fileName", "myName");
        viaMongo.updateFirst(query, update, "Posts");
        return true;
    }

 */
