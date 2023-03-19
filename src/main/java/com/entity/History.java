package com.entity;

import java.time.LocalDate;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author OLUSEUN DAMILOLA
 */
@Entity
@NamedQuery(name = History.FIND_HISTORY_BY_USER, query = "select h from History h where h.user.accountNumber = :accountNumber")
@NamedQuery(name = History.FIND_ALL_HISTORY, query = "select h from History h")
@NamedQuery(name = History.FIND_HISTORY_BY_ID, query = "select h from History h where h.id = :id")
public class History {
    
    public static final String FIND_HISTORY_BY_USER = "History.FindHistoryBYUser";
    public static final String FIND_ALL_HISTORY = "History,findAll";
    public static final String FIND_HISTORY_BY_ID = "History.findById";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    private Long amount;

    @NotNull
    private boolean status;

    @NotNull
    private String type;
    

    private String beneficiary;
    
    private String sender;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user; //Many histroy transaction belongs to one User

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    
    

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
