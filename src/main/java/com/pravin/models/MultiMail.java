/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.models;

import java.util.List;
import javax.mail.Address;

/**
 *
 * @author Pravinkumar
 */
public class MultiMail {
    private String subject;
    private String description;
    private List<Address> toAddress;

    public MultiMail(String subject, String description, List<Address> toAddress) {
        this.subject = subject;
        this.description = description;
        this.toAddress = toAddress;
    }

    public MultiMail() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Address> getToAddress() {
        return toAddress;
    }

    public void setToAddress(List<Address> toAddress) {
        this.toAddress = toAddress;
    }
    
}
