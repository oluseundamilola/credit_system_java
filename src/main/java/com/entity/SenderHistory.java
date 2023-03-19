/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 *
 * @author OLUSEUN DAMILOLA
 */
@Entity
@NamedQuery(name = SenderHistory.FIND_SENDER_HISTORY_BY_SENDER, query = "select h from SenderHistory h where h.senderUser.accountNumber = :accountNumber")
@NamedQuery(name = SenderHistory.GET_ALL_HISTORY, query = "select h from SenderHistory h")
public class SenderHistory extends AbstractHistory{
    
    public static final String FIND_SENDER_HISTORY_BY_SENDER = "SendHistory.findBySender";
    public static final String GET_ALL_HISTORY = "SenderHistory.getAllHistoryItems";
    
    private String benefitiary;
    
    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    @NotNull
    private User senderUser;

    public String getBenefitiary() {
        return benefitiary;
    }

    public void setBenefitiary(String benefitiary) {
        this.benefitiary = benefitiary;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }
    
    
    
}
