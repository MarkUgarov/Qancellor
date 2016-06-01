/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.qancellor.control;

import com.mugarov.qancellor.CommandPool;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mugarov
 */
public class Executer {
    private final String home = System.getProperty("user.home");
    private final String lineSeparator = System.getProperty("line.separator");
    private ProcessBuilder processBuilder;
    private Process process;
    
    public Executer(){
        
    }
    
    public String getStatusReport(){
        this.processBuilder = new ProcessBuilder(CommandPool.GET_QSTAT);
        try {
            this.process = this.processBuilder.start();
        } catch (IOException ex) {
            Logger.getLogger(Executer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.process.waitFor();
        } catch (InterruptedException ex) {
            Logger.getLogger(Executer.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line = null;
        try {
            while ( (line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(this.lineSeparator);
            }
        } catch (IOException ex) {
            Logger.getLogger(Executer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return builder.toString();
    }
    
    public boolean cancel(String id){
        this.processBuilder = new ProcessBuilder(CommandPool.DELETE_PROCESS, id);
        try {
            this.process = this.processBuilder.start();
        } catch (IOException ex) {
            Logger.getLogger(Executer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        try {
            this.process.waitFor();
        } catch (InterruptedException ex) {
            Logger.getLogger(Executer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    
}
