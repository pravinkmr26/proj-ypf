/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.dao;

import com.pravin.models.Images;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pravinkumar
 */
@Service
public class ImagesDao {

    String query;
    JdbcTemplate jdbc;

    @Autowired
    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public String saveImage(Images ob) {
        try {
            query = "insert into TBL_Images(imageId,image,fileName,contentType,postId) values(?,?,?,?,?)";
            jdbc.update(query, ob.getImageId(), ob.getImage(), ob.getFileName(), ob.getContentType(), ob.getPostId());
        } catch (DataAccessException ex) {
            return "Image_update_notOk_ErrorWithDataAccessException: " + ex;
        } catch (Exception exx) {
            return "NotOk_ErrorWithGeneralException: " + exx;
        }
        
        return "ok";
    }
    public List<String> getImageIdArray(String postId){
        try{
        query = "select imageId from TBL_Images where postId = ?";
        return jdbc.queryForList(query,String.class,new Object[]{postId});
        }catch(DataAccessException ex){
            return null;
        }
    }

    public String saveImage(List<Images> list) {
        for (Images ob : list) {
            try {
                query = "insert into TBL_Images(imageId,image,fileName,contentType,postId) values(?,?,?,?,?)";
                jdbc.update(query, ob.getImageId(), ob.getImage(), ob.getFileName(), ob.getContentType(), ob.getPostId());
            } catch (DataAccessException ex) {
                return "ListUpdate_not_Ok WithDataAccessException: " + ex;
            } catch (Exception exx) {
                return "NotOk_ErrorWithGeneralException: " + exx;
            }
        }

        return "Ok";
    }

    public int delPost(String postId) {
        query = "delete from TBL_Images where postId=?";
        jdbc.update(query, new Object[]{postId});
        return 1;
    }
    public int verifyImage(String postId){
        query = "select count(*) from TBL_Images where postId = ?";
        int x = jdbc.queryForObject(query,new Object[]{postId},Integer.class);
        return x;
    }
}
