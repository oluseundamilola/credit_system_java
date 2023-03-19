/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author OLUSEUN DAMILOLA
 */
@SessionScoped
public class MySession implements Serializable{
    private Long accountNNumber;
    private Long beneficiary;
    
    private Long userAmount;
    private Long formerBalance;
    private Long newBalance;
    private boolean success;
    private Long historyId;

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }
    
    

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    
    

    public Long getFormerBalance() {
        return formerBalance;
    }

    public void setFormerBalance(Long formerBalance) {
        this.formerBalance = formerBalance;
    }

    public Long getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(Long newBalance) {
        this.newBalance = newBalance;
    }
    
    

    public Long getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(Long userAmount) {
        this.userAmount = userAmount;
    }
    
    
    
    

    public Long getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Long beneficiary) {
        this.beneficiary = beneficiary;
    }
    public Long getAccountNNumber() {
        return accountNNumber;
    }

    public void setAccountNNumber(Long accountNNumber) {
        this.accountNNumber = accountNNumber;
    }
    
}
