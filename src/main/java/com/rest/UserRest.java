/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest;

import com.config.SecureRest;
import com.config.SecurityFilter;
import com.entity.History;
import com.entity.ReceiverHistory;
import com.entity.SenderHistory;
import com.entity.User;
import com.service.MySession;
import com.service.PersistenceService;
import com.service.QueryService;
import com.service.SecurityUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.math.BigDecimal;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author OLUSEUN DAMILOLA
 */
//

@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserRest {
    
    @Inject
    private PersistenceService persistenceService;
    
    @Inject
    private QueryService queryService;
    
    @Inject
    private SecurityUtil securityUtil;
    
    @Context
    private UriInfo uriInfo;
    
    @Inject
    private MySession mySession;
    
    @Inject
    private SenderHistory senderHistory;
    
    @Inject
    private ReceiverHistory receiverHistory;
    
    @PersistenceContext
    EntityManager entityManager;
    
    
    
    
    //Path to create a new user
    @Path("new")
    @POST
    public Response createUser(@Valid User user ){
        persistenceService.saveNewUser(user);
        return Response.ok(user).build();
    }
    
    @SecureRest
    //Path to find a user by accountNumber
    @Path("find/{accountNumber}")
    @GET
    public Response findBeneficiary( @PathParam("accountNumber") Long accountNumber ){
        User beneficiary = queryService.findUserByAccountNumber(accountNumber);
        User currentUser = queryService.findUserByAccountNumber(mySession.getAccountNNumber());
        
        if(currentUser.getAccountNumber().equals(accountNumber)){
            JsonObject message = Json.createObjectBuilder().add("Error", "You cannot search yourself as a Beneficiart").build();
            return Response.ok(message).build();
        }
        mySession.setBeneficiary(beneficiary.getAccountNumber());
        JsonObject beneficiaryMessage = Json.createObjectBuilder().add("Beneficiary Selected", beneficiary.getFullName())
                .add("Account NUmber", beneficiary.getAccountNumber()).build();
        return Response.ok( beneficiaryMessage ).build();
    }
    
    @POST
    @Path("send")
    public Response sendCredit(@QueryParam("amount") Long amount){
        User beneficiary = queryService.findUserByAccountNumber(mySession.getBeneficiary());
        User currentUser = queryService.findUserByAccountNumber(mySession.getAccountNNumber());
        Long oldBalance = currentUser.getBalance();
        if(beneficiary != null && currentUser != null){
            if(currentUser.getBalance() > amount){
                long newCurrentUserBalance = currentUser.getBalance() - amount;
                long newBeneficiaryBalance = beneficiary.getBalance() + amount;
                currentUser.setBalance(newCurrentUserBalance);
                beneficiary.setBalance(newBeneficiaryBalance);
                
                persistenceService.updateUserBalance(currentUser);
                persistenceService.updateUserBalance(beneficiary);
                
                JsonObject successMessage = Json.createObjectBuilder().add("Success", "You have sent " +amount+ " credits to " +beneficiary.getFullName()).build();

                mySession.setUserAmount(amount);
                mySession.setFormerBalance(oldBalance);
                mySession.setNewBalance(newCurrentUserBalance);
                mySession.setSuccess(true);
                
                persistenceService.saveSenderHistory(senderHistory);
                persistenceService.saveReceiverHistory(receiverHistory);
                
                
                
                
                return Response.ok(successMessage).build();
            }
            else{
                //Print insuffient credits
                JsonObject insuffientMessage = Json.createObjectBuilder().add("Message", "Insuffient credits").build();
                return Response.ok(insuffientMessage).build();
                //dd
            }
        }
        return null;  
    }
    
    
    @GET
    @Path("checkBalance")
    public Response checkBalance(){
        Long userAccountNumber = mySession.getAccountNNumber();
        User user = queryService.findUserByAccountNumber(userAccountNumber);
        JsonObject balanceMessage = Json.createObjectBuilder().add("Your Balance is:", +user.getBalance()+ " credits").build();
        return Response.ok(balanceMessage).build();
        
    }
    
    
    
    
    
    
    
    
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@NotNull(message = "Account Number must be set") @FormParam("accountNumber") Long accountNumber,
                            @NotNull(message = "Password must be set") @FormParam("password") String password){
        
        User current_user = queryService.findUserByAccountNumber(accountNumber);
        if (!securityUtil.authenticateUser(accountNumber, password)) {
            throw new SecurityException("Account Number or Password invalid");
        }
        
        String token = getToken(accountNumber.toString());
        mySession.setAccountNNumber(accountNumber);
        
        User LoginUser = queryService.findUserByAccountNumber(mySession.getAccountNNumber());
        
        JsonObjectBuilder loginMessage = Json.createObjectBuilder().add("Success", "Welcome" + LoginUser.getFullName());
        
        return Response.ok().header(AUTHORIZATION, SecurityFilter.BEARER + " " + token).build();
        
    }
    
    
    
     private String getToken(String accountNumber) {
        Key key = securityUtil.generateKey(accountNumber);

        String token = Jwts.builder().setSubject(accountNumber).setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date()).setExpiration(securityUtil.toDate(LocalDateTime.now().plusMinutes(15)))
                .signWith(SignatureAlgorithm.HS512, key).setAudience(uriInfo.getBaseUri().toString())
                .compact();

//                logger.log(Level.INFO, "Generated token is {0}", token);
        return token;
    }
    
}
