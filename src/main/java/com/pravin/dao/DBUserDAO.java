/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.dao;

import com.pravin.models.UserCredential;
import com.pravin.models.Users;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Pravinkumar
 */
@Service
public class DBUserDAO {

    String query;
    JdbcTemplate jdbc;

    @Autowired
    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    private static final Logger LOGGER = Logger.getLogger(DBUserDAO.class);

    /**
     *
     * @param email
     * @return
     * @throws DataAccessException
     */
    public boolean checkIsUserAvailable(String email) throws DataAccessException {
        try {
            return jdbc.queryForObject("select userId from TBL_UserCredentials where email = ?", new Object[]{email}, String.class) == null;
        } catch (DataAccessException ex) {
            return true;
        } catch (Exception ex) {
            return true;
        }

    }
    public boolean setPassword(String password,String userId){
        try{
            query = "update TBL_UserCredentials set password=?,verified='yes' where userId = ?";
            jdbc.update(query,new Object[]{password,userId});
            return true;
        }
        catch(DataAccessException ex){
            LOGGER.debug("Error @DBUserDao.setPassword()"+ex.getMessage());
        }
        catch(Exception ex){
            LOGGER.debug("Error @DBUserDao.setPassword()"+ex.getMessage());
        }
        return false;
    }
    public String authenticateUser(String userId, String password) {
        String id = "";

        try {
            id = jdbc.queryForObject("select userId from TBL_UserCredentials where email = ? and password = ? and verified = 'yes'", new Object[]{userId, password}, String.class
            );
            return id;

        } catch (DataAccessException obDAEx) {
            try {
                id = jdbc.queryForObject("select userId from TBL_UserCredentials where mobile = ? and password = ? and verified = 'yes'", new Object[]{userId, password}, String.class
                );
                return id;
            } catch (DataAccessException ex) {
                return null;
            }
        }

    }

    public Users updateUser() {
        query = "select * from TBL_userpersonalinfo";

        Users usr = jdbc.queryForObject(query, new RowMapper<Users>() {
            @Override
            public Users mapRow(ResultSet rs, int rn) throws SQLException {
                Users ob = new Users();
                ob.setDob(Date.valueOf(rs.getString("dob")));
                ob.setGender(rs.getString("gender"));
                ob.setName(rs.getString("name"));
                return ob;
            }
        });
        return usr;
    }

    public Users returnValues(String userId) {

        query = "select name,mobile from TBL_userpersonalinfo";
        return jdbc.queryForObject(query, new RowMapper<Users>() {
            @Override
            public Users mapRow(ResultSet set, int r) throws SQLException {

                Users usr = new Users();
                usr.setName(set.getString(1));
                usr.setMobile(set.getString(2));
                return usr;
            }
        });
    }

    public boolean saveUser(Users user) {
        try {
            
            query = "insert into TBL_UserCredentials(userId,email,mobile,aadharNo,verified) values(?,?,?,?,?)";
            jdbc.update(query, new Object[]{user.getUserId(), user.getEmail(), user.getMobile(), user.getAadhar(), "No"});
            query = "insert into TBL_UserPersonalInfo (name,gender,dob,userId) values(?,?,?,?)";
            jdbc.update(query, new Object[]{user.getName(), user.getGender(), user.getDob(), user.getUserId()});
        } catch (Exception ex) {
            LOGGER.debug("Error in inserting the user @DBUserDao.saveUser(User,UserCredentials): " + ex.getMessage());
            //----------------------------------------------------------Roll back code with UserId
            rollback(user.getUserId());
            return false;
        }
        return true;
    }

    public boolean verifyUser(UserCredential userCred) {
        try {
            query = "update TBL_UserCredentials set password=?, secQstn=? , answer=? ,verified='Yes' where userId=?";
            jdbc.update(query, new Object[]{userCred.getPassword(), userCred.getSeqQuestId(), userCred.getSeqQuesAns(), userCred.getUserId()});
            return true;
        } catch (Exception ex) {
            LOGGER.debug("Error @DBUserDao.varifyUser " + ex.getMessage());
        }
        return false;
    }

    public boolean rollback(String user) {
        try {
             try {
                query = "delete from TBL_UserPersonalInfo where userId = ?";
                jdbc.update(query, new Object[]{user});
            } catch (DataAccessException ex) {
                LOGGER.debug(("Error @DBUserDao.rollback()" + ex.getMessage()));
            }
            
            try {
                query = "delete from TBL_UserCredentials where userId = ?";
                jdbc.update(query, new Object[]{user});
            } catch (DataAccessException ex) {
                LOGGER.debug(("Error @DBUserDao.rollback()" + ex.getMessage()));
            }
           
        } catch (Exception ex) {
            LOGGER.debug(("Error @DBUserDao.rollback()" + ex.getMessage()));
        }
        return true;
    }

    public String
            getUserName(String userId) throws DataAccessException, Exception {
        try {
            return jdbc.queryForObject("select name from TBL_UserPersonalInfo where userId = ?", new Object[]{userId}, String.class
            );
        } catch (DataAccessException ex) {
            LOGGER.debug("Error @DBUseerDao.getUserName() " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            LOGGER.debug("Error @DBUseerDao.getUserName() " + ex.getMessage());
            throw ex;
        }
    }
}
