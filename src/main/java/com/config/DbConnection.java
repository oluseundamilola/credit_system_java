/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author OLUSEUN DAMILOLA
 */
public class DbConnection {
    
    private static Connection conn = null;
    
    public static Connection getConnection(){
        if(conn != null){
            return conn;
        }
        
        try{
            String dbUrl = "jdbc:mysql://localhost/credit";
            String username = "root";
            String password = "olamide333";
            conn = DriverManager.getConnection(dbUrl, username, password);
            System.out.println("Connected to Database");
            
        }catch(SQLException ex){
            System.out.println("Error connecting to database: " + ex.getMessage());
        }
        
        return conn;
    }
    
    
}
