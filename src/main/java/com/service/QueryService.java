/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service;

import com.entity.History;
import com.entity.ReceiverHistory;
import com.entity.SenderHistory;
import com.entity.User;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author OLUSEUN DAMILOLA
 */
public class QueryService {

    @PersistenceContext
    EntityManager entityManager;
    
    @Inject
    SecurityUtil securityUtil;
    
    @Inject
    MySession mySession;

    public User findUserByAccountNumber(Long accountNumber) {
        try {
            return entityManager.createNamedQuery(User.FIND_USER_BY_ACCOUNTNUMBER, User.class)
                    .setParameter("accountNumber", accountNumber).getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            return null;
        }
    }
    
    public History findHistoryById(Long id){
        try{
            return entityManager.createNamedQuery(History.FIND_HISTORY_BY_ID, History.class)
                    .setParameter("id", id).getSingleResult();
        }catch(NonUniqueResultException | NoResultException e){
            return null;
        }
    }
    
    public List<History> findAllHistoryByUser(){
        return entityManager.createNamedQuery(History.FIND_HISTORY_BY_USER, History.class)
                .setParameter("accountNumber", mySession.getAccountNNumber() ).getResultList();
        
    }
    
    public List<SenderHistory> getAllHistory(){
        return entityManager.createNamedQuery(SenderHistory.GET_ALL_HISTORY, SenderHistory.class).getResultList();
    }
    
    public List<SenderHistory> getAllHistoryBySender(){
        return entityManager.createNamedQuery(SenderHistory.FIND_SENDER_HISTORY_BY_SENDER, SenderHistory.class)
                .setParameter("accountNumber", mySession.getAccountNNumber()).getResultList();
    }
    
    public List<ReceiverHistory> getAllHistoryByReceiver(){
        return entityManager.createNamedQuery(ReceiverHistory.FIND_RECEIVER_HISTORY_BY_RECEIVER, ReceiverHistory.class)
                .setParameter("accountNumber", mySession.getAccountNNumber()).getResultList();
    }
    

    public boolean authenticateUser(Long accountNumber, String plainTextPassword) {
        User user = findUserByAccountNumber(accountNumber);
        if( user != null ){
            return securityUtil.passwordsMatch(user.getPassword(), user.getSalt(), plainTextPassword);
        }
        return false;

    }

}
