/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 

package com.deletedPack;

import com.pravin.dao.DBUserDAO;
import com.pravin.dao.PostsMDao;
import com.pravin.services.UtilServices;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
*/
/**
 *
 * @author Pravinkumar

@Controller
public class Login {
    @Autowired
    PostsMDao viaMongo;
    @Autowired
    UtilServices util;
    @Autowired
    DBUserDAO user;
     
    
    @RequestMapping("/url")
    public String page(Model model) {
        model.addAttribute("attribute", "value");
        return "view.name";
    }
   
    @RequestMapping("/signIn")
    public String processLogin(HttpServletRequest req){
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        if(user.authenticateUser(userId, password)){
            HttpSession session = req.getSession();
            session.setAttribute("YPFUITMPVAL",userId);
            return "UserHome";
        }
        return "UserHome";
    }
} */