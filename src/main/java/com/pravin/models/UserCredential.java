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
public class UserCredential {

    private String userId;
    private String password;
    private int seqQuestId;
    private String seqQuesAns;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSeqQuestId() {
        return seqQuestId;
    }

    public void setSeqQuestId(int seqQuestId) {
        this.seqQuestId = seqQuestId;
    }

    public String getSeqQuesAns() {
        return seqQuesAns;
    }

    public void setSeqQuesAns(String seqQuesAns) {
        this.seqQuesAns = seqQuesAns;
    }

}
