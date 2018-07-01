/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.Interfaces;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Pravinkumar
 */
public interface IServiceLogin {

    boolean processLogin(HttpServletRequest req);

    boolean validateUserAndLogin(HttpServletRequest req);
    
}
