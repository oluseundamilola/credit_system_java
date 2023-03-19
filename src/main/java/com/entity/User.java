/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author OLUSEUN DAMILOLA
 */
@Entity
@NamedQuery(name = User.FIND_USER_BY_ACCOUNTNUMBER, query = "select user from User user where user.accountNumber = :accountNumber ")
@NamedQuery(name = User.FIND_USER_BY_ID, query = "select user from User user where user.id = :id")
@NamedQuery(name = User.COUNT_USERS_BY_ACCOUNTNUMBER, query = "select count(user) from User user where user.accountNumber = :accountNumber")
@Table(name = "User_Table")
public class User {

    public static final String FIND_USER_BY_ACCOUNTNUMBER = "User.findByAccountNumber";
    public static final String FIND_USER_BY_ID = "User.findUserById";
    public static final String COUNT_USERS_BY_ACCOUNTNUMBER = "User.countByAccountNumber";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull(message = "Please fill out fullName field")
    private String fullName;

    @NotNull(message = "Please provide a given balance")
    private Long balance;

    private Long accountNumber;

    @NotNull(message = "Password must be set")
    private String password;

    private String salt;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

//    public String getAccountNumber_String() {
//        return accountNumber_String;
//    }
//
//    public void setAccountNumber_String(String accountNumber_String) {
//        this.accountNumber_String = accountNumber_String;
//    }
    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long Balance) {
        this.balance = Balance;
    }

}
