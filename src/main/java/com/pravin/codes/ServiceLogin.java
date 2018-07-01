/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.codes;

import com.pravin.Interfaces.IServiceLogin;
import com.pravin.dao.DBUserDAO;
import com.pravin.dao.MongoUtilDao;
import com.pravin.services.MongoServices;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Pravinkumard
 */
@Service
public class ServiceLogin implements IServiceLogin {
    @Autowired DBUserDAO viaUser;
    @Autowired MongoServices viaMongoServices;
    @Autowired MongoUtilDao viaMongoUtilDao;
    
    @Override
    public boolean validateUserAndLogin(HttpServletRequest req){
        String userId = com.sun.jersey.core.util.Base64.base64Decode(req.getParameter("userId"));
        String password=com.sun.jersey.core.util.Base64.base64Decode(req.getParameter("password"));
        
        String id= "";
        if((id = viaUser.authenticateUser(userId, password))!= null){
            HttpSession session = req.getSession();
            String sessionId = RandomStringUtils.randomAlphanumeric(10);
            viaMongoUtilDao.createSesssion(id,sessionId);
            session.setAttribute("YPFUIDTMPVAL",sessionId);
            session.setAttribute("YPFUID",id);
            return true;
        }
        return false;
    }
    @Override
    public boolean processLogin(HttpServletRequest req){
        HttpSession session = req.getSession();
        String sessionId="";
        String userId = "";
        try{
        
        sessionId = session.getAttribute("YPFUIDTMPVAL").toString();
        userId = session.getAttribute("YPFUID").toString();
        }catch(Exception ex){
            return false;
        }
        return  viaMongoUtilDao.verifySesssion(userId, sessionId);
    }
    
}
