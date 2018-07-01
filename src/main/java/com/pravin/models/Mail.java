/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.models;

/**
 *
 * @author Pravinkumar
 */
public class Mail {
    private String toAddress;
    private String subject;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    private String description;

    public Mail() {
    }

    public Mail(String toAddress, String subject, String description,String text) {
        this.toAddress = toAddress;
        this.subject = subject;
        this.description = description;
        this.text = text;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
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
}
