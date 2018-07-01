/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.services;

import com.pravin.dao.DBUserDAO;
import com.pravin.dao.ImagesDao;
import com.pravin.dao.PostsDao;
import com.pravin.models.Image;
import com.pravin.models.Images;
import com.pravin.models.Mail;
import com.pravin.models.MultiMail;
import com.pravin.models.Posts;
import com.pravin.models.UserCredential;
import com.pravin.models.Users;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author Pravinkumar
 */
@SessionAttributes("userId")
@Service
public class UtilServices {

    //--------------------------------------------------------|||||||-----------------
    @Autowired
    JdbcTemplate jdbc;
    @Autowired
    ImagesDao img;
    @Autowired
    PostsDao posts;
    @Autowired
    DBUserDAO viaUserDao;
    @Autowired MongoServices viaMongoServices;

    //----------------------------------------------global variables section end;
    String query;
    private static final Logger LOGGER = Logger.getLogger(UtilServices.class);

    //----------------------Methods----------------------
    public Posts getPostDetailsFromReq(MultipartHttpServletRequest req) {
        Posts ob = new Posts();
        ob.setPostId(query);
        return ob;
    }
    public Image parseImage(MultipartHttpServletRequest req,String imageDomName) throws IOException{
        if (req.getFile(imageDomName).getBytes().length > 0) {
            MultipartFile file = req.getFile(imageDomName);
            Image ob = new Image();
            ob.setContentType(file.getContentType());
            ob.setFileName(file.getOriginalFilename());
            ob.setImage(file.getBytes());
            ob.setImageId("IMG" + nameItWithInTen(RandomStringUtils.randomAlphanumeric(15))+nameItWithInFive(RandomStringUtils.randomAlphabetic(5)));

            return ob;
        }
        return null;
    }
    public Images getFileFromReq(MultipartHttpServletRequest req, String postId) throws IOException {
        MultipartFile file;
        long x;
        if (( x = req.getFile("image").getBytes().length) > 0 && postId != null) {
            file = req.getFile("image");
            Images ob = new Images();
            ob.setContentType(file.getContentType());
            ob.setPostId(postId);
            ob.setFileName(file.getOriginalFilename());
            ob.setImage(file.getBytes());
            ob.setImageId("IMG" + nameItWithInTen(RandomStringUtils.randomAlphanumeric(15))+nameItWithInFive(RandomStringUtils.randomAlphabetic(5)));

            return ob;
        }
        return null;
    }

    public List<Images> getFilesFromReq(MultipartHttpServletRequest req, int len, String postId) throws IOException {
        List<MultipartFile> list;
        Images ob;
        List<Images> listt = new ArrayList();
        try {
            list = req.getFiles("image");

            for (MultipartFile file : list) {
                try {
                    ob = new Images();
                    ob.setContentType(file.getContentType());
                    ob.setFileName(file.getOriginalFilename());
                    ob.setImage(file.getBytes());
                    ob.setPostId(postId);
                    int x = file.getOriginalFilename().length();
                    if (x > 4) {
                        x = 4;
                    }
                    String xx = ob.getFileName().substring(0, x);
                    ob.setImageId("IMG" + xx + Calendar.getInstance().get(Calendar.SECOND) + req.getLocalName() + Calendar.getInstance().get(Calendar.MILLISECOND));
                    listt.add(ob);
                } catch (Exception ex) {
                    throw ex;
                }

            }
        } catch (Exception ex) {
        }
        return listt;
    }

    public Images downloadImage(String imageId) {
        query = "select * from TBL_Images where imageId = ? ";
        return jdbc.queryForObject(query, new Object[]{imageId}, new RowMapper<Images>() {
            @Override
            public Images mapRow(ResultSet rs, int rowNum) throws SQLException {
                Images x = new Images();
                x.setContentType(rs.getString("contentType"));
                x.setFileName(rs.getString("fileName"));
                x.setImage(rs.getBytes("image"));
                x.setImageId(rs.getString("imageId"));
                return x;
            }
        });
    }

    public Posts preparePost(MultipartHttpServletRequest mreq, int hasImage, String userId) {
        //--------------------------------|||| Declaration part
        String postId = "";
        Posts ob;
        //-------------------------------------|||| Code part
        
        postId = "POST_" + RandomStringUtils.randomAlphabetic(10) + Calendar.getInstance().get(Calendar.MILLISECOND);
        ob = new Posts();
        ob.setHasImage(hasImage);
        ob.setPostId(postId);
        ob.setTitle(mreq.getParameter("caption"));
        ob.setUserId(userId);
        ob.setTime(Calendar.getInstance().getTime());
        return ob;
    }

    public String nameItWithInTen(String text) {
        if (text.length() >= 9) {
            text = text.substring(0, 8);
        }
        return text;
    }

    public String nameItWithInFive(String text) {
        if (text.length() >= 6) {
            text = text.substring(0, 4);
        }
        return text;
    }

    public String nameItWithInFifteen(String text) {
        if (text.length() > 15) {
            text = text.substring(0, 13);
        }
        return text;
    }

    public String nameItWithInTwenty(String text) {
        if (text.length() > 20) {
            text = text.substring(0, 17);
        }
        return text;
    }

    public boolean verifyPost(int hasImage, String postId) {

        int hI = posts.verifyPost(postId);

        if (hI == hasImage) {
            int count = img.verifyImage(postId);
            if (count == hasImage) {
                return true;
            }
        } else {
            return false;
        }

        return false;
    }

    public UserCredential parseUserDetails(HttpServletRequest req) {
        try {
            UserCredential userCred = new UserCredential();
            String password = req.getParameter("password");
            password = Arrays.toString(Base64.getDecoder().decode(password));
            userCred.setPassword(password);
            userCred.setSeqQuesAns(req.getParameter("secQuesAns"));
            userCred.setSeqQuestId(Integer.parseInt(req.getParameter("secQuesId")));
            userCred.setUserId(req.getParameter("userId"));
            return userCred;
        } catch (Exception ex) {
            LOGGER.debug(("Error @UtilServices.parseUserDetails() : " + ex.getMessage()));
            return null;
        }
    }

    public Users parseUser(HttpServletRequest req) {
        Users user = new Users();
        user.setDob(Calendar.getInstance().getTime());
        user.setAadhar(req.getParameter("aadhaar"));
        user.setEmail(req.getParameter("emailId"));
        user.setGender(req.getParameter("gender"));
        user.setMobile(req.getParameter("mobile"));
        user.setName(req.getParameter("name"));
        user.setUserId(generateUserId(user.getName()));//-----------------------------------------------User id parse method call;
        return user;
    }

    private String generateUserId(String userName) {
        String random = nameItWithInTwenty(nameItWithInFifteen(nameItWithInFive(userName) + nameItWithInFive("" + Math.round(Math.random()) + RandomStringUtils.randomAlphabetic(5)) + "" + Calendar.getInstance().getTimeInMillis()) + "" + Calendar.getInstance().get(Calendar.MILLISECOND));
        return random;
    }

    public String generateOTP() {
        return RandomStringUtils.randomAlphanumeric(6);
    }

    public boolean sendEmail(Mail mail) {
        String fromAddress = "pravinkmr2619@gmail.com";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "localhost");
        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddress));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail.getToAddress()));
            message.setSubject(mail.getSubject());
            message.setText(mail.getDescription());
            Transport.send(message);
            return true;
        } catch (MessagingException ex) {
            LOGGER.debug(("Error @UtilServices.sendEmail() : " + ex.getMessage()));
        } catch (Exception ex) {

            LOGGER.debug(("Error @UtilServices.sendEmail() : " + ex.getMessage()));
            return true;
        }
        return false;
    }

    public boolean sendEmail(MultiMail mail)    {
        String fromAddress = "pravinkmr2619@gmail.com";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "localhost");
        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddress));
            for (Address address : mail.getToAddress()) {
                message.addRecipient(Message.RecipientType.TO, address);
            }
            message.setSubject(mail.getSubject());
            message.setText(mail.getDescription());
            Transport.send(message);
        } catch (MessagingException ex) {
            LOGGER.debug(("Error @UtilServices.sendEmail() : " + ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.debug(("Error @UtilServices.sendEmail() : " + ex.getMessage()));
        }
        return false;
    }

    public Mail prepareMail(Users user, String otp) throws UnknownHostException {
        String _subject = "user Registration verification from YouthPoliticalForum.";
        String _text = "Welcome to Youth Political Forum";
        String _desc = "Dear " + user.getName();
        _desc += "your OTP is : " + otp + " , Use this to verify<br>";
        _desc += "or just click the following link to verify :  https://" + viaMongoServices.getCurrentUrl()  + "/verify/" + user.getUserId() + "/" + otp;
        return new Mail(user.getEmail(), _subject, _desc,_text);
    }
}
