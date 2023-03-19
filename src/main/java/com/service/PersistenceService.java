package com.service;

import com.config.DbConnection;
import com.entity.History;
import com.entity.ReceiverHistory;
import com.entity.SenderHistory;
import com.entity.User;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Map;
import java.util.Random;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author OLUSEUN DAMILOLA
 */

@DataSourceDefinition(
        name = "java:app/credit/MyDS",
        className = "com.mysql.cj.jdbc.Driver",
        databaseName = "credit-system",
        serverName = "localhost",
        portNumber = 3306,
        user = "root",
        password = "olamide333",
        url = "jdbc:mysql://localhost:3306/credit-system"
        
)




@Stateless
public class PersistenceService {

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    SecurityUtil securityUtil;

    @Inject
    private MySession mySession;

    @Inject
    private QueryService queryService;
    
    @Inject
    private History history;
    

    //save a beneficiary into dataStore
    public User saveNewUser(User user) {
        Map<String, String> credentialMap = securityUtil.hashPassword(user.getPassword());
        if (user.getId() == null) {
            user.setAccountNumber(generateAccountNumber());
            user.setPassword(credentialMap.get("hashedPassword"));
            user.setSalt(credentialMap.get("salt"));
            entityManager.persist(user);
        }

        return user;
    }

    public User updateUserBalance(User user) {
        entityManager.merge(user);
        return user;
    }

    public History updateHistory(History history) {
        entityManager.merge(history);
        return history;
    }
    
    public SenderHistory saveSenderHistory( SenderHistory senderHistory){
        User sender = queryService.findUserByAccountNumber(mySession.getAccountNNumber());
        User beneficiary = queryService.findUserByAccountNumber(mySession.getBeneficiary());
        String BeneficiaryUserName = beneficiary.getFullName();
        
        senderHistory.setDate(LocalDate.now());
        senderHistory.setAmount(mySession.getUserAmount());
        senderHistory.setBenefitiary(BeneficiaryUserName);
        senderHistory.setSenderUser(sender);
        
        if(senderHistory.getId() == null){
            
            entityManager.persist(senderHistory);
            return senderHistory;
            
        }
        return null;
    }
    
    public ReceiverHistory saveReceiverHistory(ReceiverHistory receiverHistory){
        User sender = queryService.findUserByAccountNumber(mySession.getAccountNNumber());
        String senderName = sender.getFullName();
        User beneficiary = queryService.findUserByAccountNumber(mySession.getBeneficiary());
        
        receiverHistory.setDate(LocalDate.now());
        receiverHistory.setAmount(mySession.getUserAmount());
        receiverHistory.setSender(senderName);
        receiverHistory.setReceiverUser(beneficiary);
        
        if(receiverHistory.getId() == null){
            entityManager.persist(receiverHistory);
            return receiverHistory;
        }
        return null;
    }

    public History saveHistory(int condition) {
        User loggedInUser = queryService.findUserByAccountNumber(mySession.getAccountNNumber());
        User beneficiary = queryService.findUserByAccountNumber(mySession.getBeneficiary());
        String BeneficiaryUserName = beneficiary.getFullName();
        User sender = queryService.findUserByAccountNumber(mySession.getAccountNNumber());
        String senderName = sender.getFullName();

        if (condition == 0) {
            history.setDate(LocalDate.now());
            history.setAmount(mySession.getUserAmount());
            history.setBeneficiary(BeneficiaryUserName);
            history.setSender(null);
            if (mySession.getFormerBalance() > mySession.getNewBalance()) {
                history.setType("Debit");
            } else {
                history.setType("Credit");
            }
            if (mySession.isSuccess()) {
                history.setStatus(true);
            }

            history.setUser(loggedInUser);
            entityManager.persist(history);

        }

        if (condition == 1) {
            history.setId(history.getId() + 1);
            history.setDate(LocalDate.now());
            history.setAmount(mySession.getUserAmount());
            history.setBeneficiary(null);
            history.setSender(senderName);
            if (mySession.getFormerBalance() > mySession.getNewBalance()) {
                history.setType("Debit");
            } else {
                history.setType("Credit");
            }
            if (mySession.isSuccess()) {
                history.setStatus(true);
            }
            history.setUser(beneficiary);
            entityManager.persist(history);

        }

        return history;
    }

//    public History saveBeneficiaryHistory(History history){
//        User beneficiary = queryService.findUserByAccountNumber(mySession.getBeneficiary());
//        User sender = queryService.findUserByAccountNumber(mySession.getAccountNNumber());
//        String senderName = sender.getFullName();
//
//            history.setDate(LocalDate.now());
//            history.setAmount(mySession.getUserAmount());
//            history.setBeneficiary(null);
//            history.setSender(senderName);
//            if (mySession.getFormerBalance() > mySession.getNewBalance()) {
//                history.setType("Debit");
//            } else {
//                history.setType("Credit");
//            }
//            if (mySession.isSuccess()) {
//                history.setStatus(true);
//            }
//            history.setUser(beneficiary);
////            entityManager.persist(history);
//
//        
//        return history;
//        
//    }
//    public void saveAllHistory(History history){
//        History userHistory = saveHistory(history);
//        History beneficiaryHistory = saveBeneficiaryHistory(history);
//        entityManager.persist(userHistory);
//        entityManager.persist(beneficiaryHistory);
//    }
    //Code to generate AccountNumber
    public static Long generateAccountNumber() {
        Random rand = new Random();
        long upperBound = 10000000000L; // 10^10
        long lowerBound = 1000000000L; // 10^9
        long accoutNumberGenerated = (long) rand.nextInt((int) (upperBound - lowerBound)) + lowerBound;
        return accoutNumberGenerated;
    }

}
