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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mugarov
 */
public class Executer {
    private final String home = System.getProperty("user.home");
    private final String lineSeparator = System.getProperty("line.separator");
    private ProcessBuilder statusBuilder;
    private ProcessBuilder cancelBuilder;
    private Process statusProcess;
    private Process cancelProcess;
    
    public Executer(){
        
    }
    
    public String getStatusReport(){
        this.statusBuilder = new ProcessBuilder(CommandPool.GET_QSTAT);
        try {
            this.statusProcess = this.statusBuilder.start();
        } catch (IOException ex) {
            Logger.getLogger(Executer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
//            System.out.println(this.statusBuilder.command());
            this.statusProcess.waitFor();
        } catch (InterruptedException ex) {
            Logger.getLogger(Executer.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(statusProcess.getInputStream()));
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
        String del[] = new String[CommandPool.DELETE_PROCESS.length+1];
        System.arraycopy(CommandPool.DELETE_PROCESS, 0, del, 0, CommandPool.DELETE_PROCESS.length);
        del[del.length-1] = id;
//        System.out.println("Trying to delete "+id+" with command "+Arrays.deepToString(del));
        this.cancelBuilder = new ProcessBuilder(del);
        try {
//            System.out.println(this.cancelBuilder.command());
            this.cancelProcess = this.cancelBuilder.start();
        } catch (IOException ex) {
            Logger.getLogger(Executer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        try {
            this.cancelProcess.waitFor();
        } catch (InterruptedException ex) {
            Logger.getLogger(Executer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    
}
