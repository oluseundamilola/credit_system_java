/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rest;

import com.service.QueryService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author OLUSEUN DAMILOLA
 */
@Path("history")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HistoryRest {
    
    @Inject
    private QueryService queryService;
    
    @GET
    @Path("debit")
    public Response getUserHistoryDebit(){
        return Response.ok(queryService.getAllHistoryBySender()).build();
    }
    
    @GET
    @Path("credit")
    public Response getUserHistoryCredit(){
        return Response.ok( queryService.getAllHistoryByReceiver() ).build();
    }
    
    @GET
    @Path("all")
    public Response getAllHistory(){
        return Response.ok(queryService.getAllHistory()).build();
    }
    
}
