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
@NamedQuery(name = ReceiverHistory.FIND_RECEIVER_HISTORY_BY_RECEIVER, query = "select h from ReceiverHistory h where h.receiverUser.accountNumber = :accountNumber")
public class ReceiverHistory extends AbstractHistory{
    
    public static final String FIND_RECEIVER_HISTORY_BY_RECEIVER = "ReceiverHistory.findBySender";
    public static final String GET_ALL_HISTORY_RECEIVER = "ReceiverHistory.getAllHistoryItems";
    
    private String sender;
    
    @ManyToOne
    @JoinColumn(name = "receiver_user_id")
    @NotNull
    private User receiverUser;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public User getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(User receiverUser) {
        this.receiverUser = receiverUser;
    }
    
    
}
