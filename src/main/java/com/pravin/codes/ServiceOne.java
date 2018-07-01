/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.codes;

import com.pravin.Interfaces.IServiceOne;
import com.mongodb.BasicDBObject;
import com.pravin.dao.DBUserDAO;
import com.pravin.dao.ImagesDao;
import com.pravin.dao.MongoUtilDao;
import com.pravin.dao.PostsDao;
import com.pravin.dao.PostsMDao;
import com.pravin.models.Comment;
import com.pravin.models.Image;
import com.pravin.models.Images;
import com.pravin.models.Posts;
import com.pravin.models.Reply;
import com.pravin.models.UserCredential;
import com.pravin.models.Users;
import com.pravin.services.Mailer;
import com.pravin.services.MongoServices;
import com.pravin.services.UtilServices;
import java.net.UnknownHostException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 *
 * @author Pravinkumar
 */
@Service
public class ServiceOne implements IServiceOne {

    //-----------------------------------------------------------------------------------------Autowired objects
    @Autowired
    PostsMDao viaMongoPost;
    @Autowired
    PostsDao viaJdbcPost;
    @Autowired
    UtilServices viaUtilServices;
    @Autowired
    ImagesDao viaImgDao;
    @Autowired
    MongoServices viaMongoServices;
    @Autowired
    DBUserDAO viaUserDao;
    @Autowired
    MongoUtilDao viaMongoUtilDao;
    @Autowired
    Mailer viaMailer;
    //--------------------------------------------------------------------------------------------Objects used.
    HttpSession session;
    private static final Logger LOGGER = Logger.getLogger(ServiceOne.class);

    @Override
    public boolean savePost(HttpServletRequest req, HttpServletResponse res) {
        int hasImage = 0;
        String postId = "";
        Boolean rollback = false;
        Images image;
        List<Images> list;
        Posts post;
        MultipartHttpServletRequest mreq;
        //parseMultipartReq;
        mreq = (MultipartHttpServletRequest) req;
        session = req.getSession();             //-----session management( dont use true here)lastly
        try {
            hasImage = mreq.getFiles("image").size();
        } catch (Exception ex) {
            hasImage = 0;
            LOGGER.error("Error at getting files" + ex);
        }
        String userId = session.getAttribute("YPFUID").toString();
        if (null != (post = viaUtilServices.preparePost(mreq, hasImage, userId))) {
            try {
                viaJdbcPost.savePost(post);

                postId = post.getPostId();
                if (hasImage != 0) {
                    if (hasImage <= 1) {
                        LOGGER.info("trying to get file from request gonna execute getFileFromReq()");
                        if (null != (image = viaUtilServices.getFileFromReq(mreq, postId))) {
                            try {
                                LOGGER.info("executing code block to save image");
                                viaImgDao.saveImage(image);
                                //viaMongoPost.savePost(image);

                            } catch (Exception ex) {
                                LOGGER.error("error saving image," + ex);
                                rollback = true;
                            }
                        } else {
                            LOGGER.error(("ErrorAtGEtImage,Error getting image from req"));
                        }
                    } else {
                        try {
                            LOGGER.info("getting the image list from the multipartReq");
                            list = viaUtilServices.getFilesFromReq(mreq, hasImage, postId);
                            try {
                                String error = viaImgDao.saveImage(list);
                                LOGGER.debug("got information in saving the image list" + error);
                            } catch (Exception ex) {
                                LOGGER.error("Error saving the image List " + ex);
                                rollback = true;
                            }
                        } catch (Exception ex) {
                            LOGGER.error("Error getting the image list" + ex);
                        }
                    }
                }
            } catch (Exception ex) {
                LOGGER.error("error saving the post" + ex);
                return false;
            }
            if (rollback) {
                LOGGER.info("Roll back is in action");
                postId = post.getPostId();
                viaJdbcPost.delPost(postId);
                viaImgDao.delPost(postId);
                return false;
            } else {
                if (true == viaUtilServices.verifyPost(hasImage, postId)) {
                    LOGGER.info("Post saved successfully");
                } else {
                    LOGGER.error("Rol back is in action action");
                    viaJdbcPost.delPost(postId);
                    viaImgDao.delPost(postId);
                    return false;
                }
            }
        }
        LOGGER.info("everything is okay at saving post, and returning to client");
        return true;
    }

    @Override
    public String saveUser(HttpServletRequest req) {
        try {
            String otp;
            Users user = viaUtilServices.parseUser(req);
            if (viaUserDao.checkIsUserAvailable(user.getEmail())) {
                if (viaUserDao.saveUser(user)) {
                    if ((otp = viaMongoUtilDao.setOTP(user.getUserId())) != null) {

                        if (viaMailer.sendEmail(viaUtilServices.prepareMail(user, otp))) {
                            return user.getUserId();
                        } else if (viaMailer.sendEmail(viaUtilServices.prepareMail(user, otp))) {
                            return user.getUserId();
                        } else {
                            viaUserDao.rollback(user.getUserId());
                            return "YPFERR_NETWORK_SENDING_EMAIL";
                        }
                    } else {
                        viaUserDao.rollback(user.getUserId());
                        return "YPFERR_OTP_ERROR";
                    }
                } else {
                    viaUserDao.rollback(user.getUserId());
                    return "YPFERR_TRY_AGAIN";
                }//--------------------------------------------write roll back code..
            } else {
                return "YPFCERR_ALREADY_USER";
            }
        } catch (DataAccessException ex) {
            LOGGER.debug("Error @UserDao.checkUserAvailable" + ex.getMessage());
            return null;
        } catch (UnknownHostException ex) {
            LOGGER.debug("HOst Exception @ServiceOne.saveUser().prepareMail() " + ex.getMessage());
            //--------------------------------------------------write roll back code 
            return null;
        } catch (Exception ex) {
            LOGGER.debug(("Error @ServiceOne.saveUser " + ex.getMessage()));
            //--------------------------------------------------write roll back code 
            return null;
        }

    }

    @Override
    public boolean verifyOTP(String userId, String otp) {
        return viaMongoUtilDao.verifyOTP(otp, userId);
    }

    @Override
    public boolean verifyUser(HttpServletRequest req) {

        UserCredential userCred;
        if ((userCred = viaUtilServices.parseUserDetails(req)) != null) {
            viaUserDao.verifyUser(userCred);

        }
        return false;
    }

    @Override
    public boolean saveBlogPost(HttpServletRequest req) {
        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
        if (mreq.getParameterNames().hasMoreElements()) {
            session = req.getSession();
            String userId = session.getAttribute("YPFUID").toString();
            try {
                int hasImage = mreq.getFiles("images").size();
                Posts post = viaUtilServices.preparePost(mreq, hasImage, userId);
                if (post.getHasImage() > 0) {
                    try {
                        post.setImageId(viaMongoServices.saveImages(mreq, post.getPostId()).toArray(new String[0]));
                    } catch (Exception ex) {
                        LOGGER.debug("Error @MongoSErvices.SaveBLogPost " + ex.getMessage());
                        return false;
                    }
                }
                viaMongoPost.savePost(post);
                return true;
            } catch (Exception ex) {
                LOGGER.debug("Error @Services.saveBlogPost() " + ex.getMessage());
            }
        }
        return false;
    }

    @Override
    public List<Posts> showPostForProfile(String userId) {
        try {
            List<Posts> list = viaJdbcPost.getPosts(userId, 3);
            return list;
        } catch (Exception ex) {
            LOGGER.error("Error at getting TOPThree " + ex.getMessage());
        }
        return null;
    }

    @Override
    public BasicDBObject getPostWithComments(String postId) {
        BasicDBObject post = viaMongoPost.getPost(postId);
        return post;
    }

    @Override
    public List<Comment> getComments(String postId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String setComments(HttpServletRequest req) {
        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) req;
        try {
            session = req.getSession();
            mreq.setAttribute("userId", session.getAttribute("YPFUID").toString());
            Comment comment = viaMongoServices.prepareComment(mreq);
            String postId = mreq.getParameter("postId");
            viaMongoPost.setComment(comment, postId);
            return comment.getCommentId();
        } catch (Exception ex) {
            LOGGER.debug("Error @serviceOne.setComment() " + ex.getMessage());
            return null;
        }
    }

    @Override
    public String setDP(HttpServletRequest req) {
        try{
        session = req.getSession();
        String userId = session.getAttribute("YPFUID").toString();
        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)req;
        Image dp = viaUtilServices.parseImage(mreq,"dp");
        viaMongoServices.setDP(userId,dp);
        return "ok";
        }catch(Exception ex){
            return "ERR";
        }
    }

    @Override
    public String setReply(HttpServletRequest req) {

        try {
            req.setAttribute("userId", session.getAttribute("YPFUID").toString());
            Reply reply = viaMongoServices.prepareReply(req);
            String postId = req.getParameter("postId");
            String commentId = req.getParameter("commentId");

            viaMongoPost.saveReply(reply, postId, commentId);
            return reply.getReplyId();
        } catch (Exception ex) {
            LOGGER.debug("Error @serviceOne.setComment() " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<BasicDBObject> getTrendingPosts(HttpServletRequest req) {
        try {

            List<BasicDBObject> obj;
            obj = viaMongoPost.getListOfTrendingPosts();
            return obj;
        } catch (Exception ex) {
            LOGGER.debug("Error @ServiceOne.getTrendingPosts() " + ex.getMessage());
            return null;
        }
    }
    
    
    @Override
    public List<BasicDBObject> getHomeContent(){
        return viaMongoPost.getHomeContents();
    }
    @Override
    public List<Posts> loadPostsForProfile(HttpServletRequest req) {
        String userId = req.getAttribute("userId").toString();
        List<Posts> ob = viaJdbcPost.getPosts(userId, 3);
        viaJdbcPost.setImageId(ob);
        return ob;
    }

    @Override
    public List<BasicDBObject> refreshReplies(HttpServletRequest req) {
        try {
            String postId = req.getAttribute("postId").toString();
            String commentId = req.getAttribute("commentId").toString();
            return viaMongoPost.refreshReplys(postId, commentId);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<BasicDBObject> refreshComments(HttpServletRequest req) {
        try {
            String postId = req.getAttribute("postId").toString();
                return viaMongoPost.refreshComments(postId);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<BasicDBObject> getUserContributions(HttpServletRequest req) {
        try {
            session = req.getSession();
            String userId = session.getAttribute("YPFUID").toString();
            return viaMongoServices.getUserContributions(userId);
        } catch (Exception ex) {
            LOGGER.debug("Error @SErviceOne.getUserContibutions() " + ex.getMessage());
        }
        return null;
    }

    @Override
    public BasicDBObject doLikeComment(HttpServletRequest req) {
        try {
            return viaMongoServices.doLikeComment(req.getAttribute("postId").toString(), req.getAttribute("commentId").toString(), req.getSession().getAttribute("YPFUID").toString());
        } catch (Exception ex) {
            LOGGER.debug("Error @ServiceOne.doLikeComment() " + ex.getMessage());
        }
        return null;
    }

    @Override
    public String getUserName(String userId) {
        try {
            if ((userId = viaUserDao.getUserName(userId)) != null) {
                return userId;
            } else {
                return "YPFINVALIDUSERNAMERROr";
            }
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean setUpUser(HttpServletRequest req) {
        return viaUserDao.setPassword(req.getParameter("password"), req.getParameter("userId"));
    }

    @Override
    public Image showDpByUserId(String userId) {
        Object ob = viaMongoServices.getDP(userId);
        Image img = (Image)ob;
        return img;
    }
    @Override
    public void setCurrentUrl(HttpServletRequest req){
        if(req.getParameter("password").equals("YPFADMINSETCURRENTUrL")){
            viaMongoServices.setCurrentUrl(req.getParameter("url"));
        }
    }
            
}
