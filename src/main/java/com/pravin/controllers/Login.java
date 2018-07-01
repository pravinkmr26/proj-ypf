/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.controllers;

import com.pravin.Interfaces.IServiceLogin;
import com.pravin.Interfaces.IServiceOne;
import com.pravin.dao.DBUserDAO;
import com.pravin.dao.PostsMDao;
import com.pravin.services.UtilServices;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Pravinkumar
 */
@Controller
public class Login {

    @Autowired
    PostsMDao viaMongo;
    @Autowired
    UtilServices util;
    @Autowired
    DBUserDAO user;
    @Autowired
    IServiceLogin viaServiceLogin;
    @Autowired
    IServiceOne viaServiceOne;

    @RequestMapping("/signIn")
    public @ResponseBody
    String ValidateLogin(HttpServletRequest req) {
        if (viaServiceLogin.validateUserAndLogin(req)) {
            return "ok";
        }
        return null;
    }

    @RequestMapping("/profile")
    public String processLogin(HttpServletRequest req, HttpServletResponse res, ModelMap map) {
        if (viaServiceLogin.processLogin(req)) {
            String userName = "";
            String userId = req.getSession().getAttribute("YPFUID").toString();
            map.put("userId", userId);
            if ((userName = viaServiceOne.getUserName(userId)) != null) {
                map.put("userName", userName);
                return "UserProfile";
            }
        }
        return "errorPage";
    }

    @RequestMapping("/verify/{userId}/{otp}")
    public String verifyRegistration(@PathVariable("userId") String userId, @PathVariable("otp") String otp, ModelMap map) {
        try {
            if (viaServiceOne.verifyOTP(userId, otp)) {
                map.put("userId", userId);
                String userName = "";
                if ((userName = viaServiceOne.getUserName(userId)) != null) {
                    map.put("userName", userName);
                    return "NewUserREg";
                }
            }
        } catch (Exception ex) {

        }
        return "errorPage";
    }

    @RequestMapping("/verify/{userId}")
    public String verifyUserShowOTP(@PathVariable("userId") String userId, ModelMap map) {
        return "ConfirmOTP";

    }

    @RequestMapping("/gotoUser/{userId}")
    public String showUserProfile(HttpServletRequest req, ModelMap map, @PathVariable("userId") String userId) {
        map.put("userId", userId);
        String userName = "";
        if ((userName = viaServiceOne.getUserName(userId)) != null) {
            map.put("userName", userName);
            return "UserProfile";
        }
        return "errorPage";
    }

    @RequestMapping("/setPassword")
    public String setPassword(HttpServletRequest req, HttpServletResponse res, ModelMap map) {
        map.put("result", "<h2>Registration failed, Please try again later!..</h2>");
        if (viaServiceOne.setUpUser(req)) {
            map.put("result", "<h2>Registration succesfull!..</h2>");
        }
        return "ResultPage";
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest req,HttpServletResponse res){
        req.getSession().invalidate();
        return "Login";
    }
}
