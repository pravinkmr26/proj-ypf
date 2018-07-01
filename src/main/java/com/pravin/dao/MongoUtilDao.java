/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.dao;

import com.mongodb.BasicDBObject;
import com.pravin.services.MongoServices;
import com.pravin.services.UtilServices;
import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pravinkumar
 */
@Service
public class MongoUtilDao {

    @Autowired MongoTemplate viaMongo;
    @Autowired UtilServices viaUtilServices;
    @Autowired MongoServices viaMongoServies;
    //--------------------------------------------------declared Objects;
    Query query;
    Update update;
    
    private static final Log4JLogger LOGGER = new Log4JLogger();
    
    //-----------------------------------------------------------------------------------Methods
    public String setOTP(String userId) {
        query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        update = new Update();
        try {
            String otp = viaUtilServices.generateOTP();
            if (viaMongo.count(query, "Util") > 0) {
                update.push("OTP",otp);
                viaMongo.updateFirst(query, update,"Util");
                return otp;
            }
            BasicDBObject ob = new BasicDBObject();
            ob.append("userId",userId);
            ob.append("OTP", otp);
            viaMongo.insert(ob,"Util");
            return otp;
        }catch(Exception ex){
            LOGGER.debug("Error @MongoUtilDao.setOTP "+ex.getMessage());
        }
        return null;
    }

    public boolean verifyOTP(String OTP, String userId) {
        query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId).and("OTP").is(OTP));
        if(viaMongo.count(query,"Util")>0){
            return true;
        }
        return false;
    }
    public boolean createSesssion(String userId,String session){
        query = new Query();
        update = new Update();
        query.addCriteria(Criteria.where("userId").is(userId));
        try {
            if (viaMongo.count(query, "Util") > 0) {
                update.set("session",session);
                viaMongo.updateFirst(query, update, "Util");
            }
            return true;
        }catch(Exception ex){
            LOGGER.debug("Error @MongoUtilDao.createSession() "+ex.getMessage());
            return false;
        }
    }
    public boolean verifySesssion(String userId,String session){
        query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId).andOperator(Criteria.where("session").is(session)));
        try {
            return viaMongo.count(query, "Util") > 0;
        }catch(Exception ex){
            LOGGER.debug("Error @MongoUtilDao.verifySession() "+ex.getMessage());
            return false;
        }
    }
}
