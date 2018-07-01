/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.controllers;

import com.pravin.Interfaces.IServiceLogin;
import com.pravin.codes.ServiceOne;
import com.pravin.dao.DBUserDAO;
import com.pravin.dao.ImagesDao;
import com.pravin.services.UtilServices;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Pravinkumar
 */
@Controller
public class Pages {

    @Autowired
    UtilServices ud;
    @Autowired
    DBUserDAO dao;
    @Autowired
    ImagesDao img;
    @Autowired
    IServiceLogin viaServiceLogin;
    @Autowired
    ServiceOne viaServiceOne;

    @RequestMapping("/register")
    public String showRegister() {
        return "Register";
    }
    

    @RequestMapping("/uploadPost")
    public String gotoPostUploader() {
        return "UploadPost";
    }

    @RequestMapping("/forum")
    public String gotoForum() {
        return "Forum";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String page(HttpServletRequest req, ModelMap map) {
        if (viaServiceLogin.processLogin(req)) {
            map.put("userId", req.getSession().getAttribute("YPFUID").toString());
            return "UserProfile";
        }
        return "Login";
    }

    @RequestMapping("/gotoImages")
    public String redirectImages() {
        return "ImageLoad";
    }
    @RequestMapping("/setCurrentURLByAdminPravin")
    public String showSEtURL(){
        return "SEtURL";
    }
    @RequestMapping("/setURL")
    public String setURLofCurrentDomain(HttpServletRequest req,HttpServletResponse res){
        viaServiceOne.setCurrentUrl(req);
        return "Login";
    }
    @RequestMapping("/errorPage")
    public String page404(HttpServletRequest req,HttpServletResponse res){
        return "errorPage";
    }

}

/*

   

    /*    @RequestMapping("/showImage")
    public void shohImage(HttpServletRequest req, HttpServletResponse res,Model map, Images img) throws IOException{
        Images dimg = ud.downloadImage("image23", "kir");
        res.setContentType("image/jpeg");
        res.setContentLength(dimg.getImage().length);
        res.setHeader(HttpHeaders.CACHE_CONTROL,"no-cache");
        //res.setHeader("Content-Disposition","attachment;filename=\""+dimg.getFileName()+"\"");
        try{
            FileCopyUtils.copy(dimg.getImage(),res.getOutputStream());
        }
        catch(IOException ex)
        {
            map.addAttribute("error",ex);
        }
    }

//    @RequestMapping("/blog")
//    public String loadBlog(Model map) {
//
//        return "Blog";
//    }
    @RequestMapping("/saveImage")
    public String saveImage(HttpServletRequest req, HttpServletResponse res, Model map) throws ServletRequestBindingException, IOException {
        MultipartHttpServletRequest mreq;
        mreq = (MultipartHttpServletRequest) req;
        MultipartFile file = mreq.getFile("image");
        Images ob = new Images();
        ob.setImage(file.getBytes());
        ob.setContentType(file.getContentType());
        ob.setFileName(file.getOriginalFilename());
        //ob.setImageId(caption+""+Calendar.getInstance().get(Calendar.SECOND));
        ob.setImageId("image234");
        String x = img.saveImage(ob);
        map.addAttribute("value", x);
        return "UserHome";
    }


 */
