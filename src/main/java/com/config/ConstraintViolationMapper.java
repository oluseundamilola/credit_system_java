/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.config;

import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author OLUSEUN DAMILOLA
 */
@Provider
public class ConstraintViolationMapper implements ExceptionMapper<ConstraintViolationException>{

     @Override
    public Response toResponse(ConstraintViolationException e) {
        
        JsonObjectBuilder errorBuilder = Json.createObjectBuilder().add("Error", "There was an Error in data sent");
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        
        for(ConstraintViolation constraintViolations : e.getConstraintViolations()){
            String property = constraintViolations.getPropertyPath().toString().split("\\.")[2];
            String message = constraintViolations.getMessage();
            
            objectBuilder.add(property, message);
            
//            myViolationMap.put(property, message);
        }
        errorBuilder.add("Violated Fields", objectBuilder.build());
     
        return Response.status(Response.Status.PRECONDITION_FAILED).entity(errorBuilder.build()).build();
    }
    
}
