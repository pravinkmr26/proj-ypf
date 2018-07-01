/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.deletedPack;

import com.pravin.models.Images;
import com.pravin.models.Users;
import com.pravin.dao.DBUserDAO;
import com.pravin.dao.ImagesDao;
import com.pravin.services.UtilServices;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author Pravinkumar
 */
@Controller
public class ControllerMain {

    @Autowired
    UtilServices ud;
    @Autowired
    DBUserDAO dao;
    @Autowired
    ImagesDao img;

    
    @RequestMapping("/uploadPost")
    public String gotoPostUploader(){
        return "UploadPost";
    }
    
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String page(Model model) {
        model.addAttribute("Attrib", "<p><i>hello</i></p>pravin");
        return "Login";
    }
    @RequestMapping("/TestModules")
    public String ShowModule(){
        return "TestModules";
    }
    @RequestMapping("/save")
    public String RequestBody(@ModelAttribute("info") Users user, ModelMap map) {
        //dao.saveUser(user);
        return "Test";
    }

    @RequestMapping("/test")
    public String showTest() {
        return "Test";
    }

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
    }*/
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

    @RequestMapping("/gotoImages")
    public String redirectImages() {
        return "ImageLoad";
    }

    @RequestMapping("/profile")
    public String showProfile(HttpServletRequest req, HttpServletResponse res) {
        String id = "user";

        String t = "";
        HttpSession ss = req.getSession(true);
        if (id.equals("user14")) {
            ss.setAttribute("userId", "user14");
            t = "14";
        } else {
            ss.setAttribute("userId", "user15");
            t = "15";
        }
        return "UserHome";
    }

    @RequestMapping("/blog")
    public String loadBlog(Model map) {

        return "Blog";
    }
}
