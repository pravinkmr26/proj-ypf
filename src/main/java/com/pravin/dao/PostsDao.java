/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.dao;

import com.pravin.models.Posts;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pravinkumar
 */
@Service
public class PostsDao {
    @Autowired JdbcTemplate jdbc;
    @Autowired ImagesDao viaImagesDao;
    String query;
    private static final Logger LOGGER = Logger.getLogger(PostsDao.class);
    public String savePost(Posts ob){
       query="insert into TBL_Posts(title,postId,hasImages,time,userId) values(?,?,?,?,?)";
       try{
           jdbc.update(query, ob.getTitle(),ob.getPostId(),ob.getHasImage(),ob.getTime(),ob.getUserId());
       }catch(DataAccessException ex){
           return "Error"+ex.getMessage();
       }
       return "ok";
    }
    public List<Posts> getPosts(String userId, int select){
        query = "select top "+select+" * from TBL_Posts where userId=? order by [time] DESC";
        try{
            //List<Posts> ob = jdbc.queryForList(query,new Object[]{userId},Posts.class);
            
            return jdbc.query(query, new Object[]{userId}, new RowMapper<Posts>(){
            @Override
            public Posts mapRow(ResultSet rs, int rowNum) throws SQLException{
                Posts object = new Posts();
                object.setHasImage(rs.getInt("hasImages"));
                object.setPostId(rs.getString("postId"));
                object.setTime(rs.getDate("time"));
                object.setTitle(rs.getString("title"));
                object.setUserId(rs.getString("userId"));
                return object;
            }
        });
        }catch(DataAccessException ex){
            LOGGER.error("Error at getting Post List @getPosts() overloaded " + ex);
        }catch(Exception genEx){
            LOGGER.error("Error at getting Post List @getPosts() overloaded ,General exception here "+genEx);
        }
        return null;
    }
    public List<Posts> getPosts(String userId){
        
        query = "select * from TBL_Posts where userId = ?  Order by [time] ASC";
        try{
            //List<Posts> ob = jdbc.queryForList(query,new Object[]{userId},Posts.class);
            
            return jdbc.query(query, new Object[]{userId}, new RowMapper<Posts>(){
            @Override
            public Posts mapRow(ResultSet rs, int rowNum) throws SQLException{
                Posts object = new Posts();
                object.setHasImage(rs.getInt("hasImages"));
                object.setPostId(rs.getString("postId"));
                object.setTime(rs.getDate("time"));
                object.setTitle(rs.getString("title"));
                object.setUserId(rs.getString("userId"));
                return object;
            }
        });
        }catch(DataAccessException ex){
            LOGGER.error("Error at getting Post List @getPosts() " + ex);
        }catch(Exception genEx){
            LOGGER.error("Error at getting Post List @getPosts() ,General exception here "+genEx);
        }
        return null;
    }
    
    public Posts getPost(String postId){
        query = "select * from TBL_Posts where postId=?";
        return jdbc.queryForObject(query,new Object[]{postId}, new RowMapper<Posts>(){
            @Override
            public Posts mapRow(ResultSet rs ,int rn){
                Posts ob = new Posts();
                //-------------------------------code has to written
                
                return ob;
            }
        });
    }
    public int delPost(String postId){
        query = "delete from TBL_Posts where postId=?";
        jdbc.update(query,new Object[]{postId});
        return 1;
    }
    public int verifyPost(String postId){
        int x=0 ;
        query = "select hasImages from TBL_Posts where postId = ?";
        try{
         x = jdbc.queryForObject(query,new Object[]{postId},Integer.class);
        }catch(DataAccessException ex){
            throw ex;
        }
        return x;
    }
    public boolean setImageId(List<Posts> list){
        for (Posts ob : list) {
            String[] imageId = viaImagesDao.getImageIdArray(ob.getPostId()).toArray(new String[0]);
            ob.setImageId(imageId);
        }
        return false;
    }
    
}
