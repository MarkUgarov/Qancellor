/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.qancellor.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author mugarov
 */
public class ManListener implements ActionListener{
    
    public static final String UPDATE_TEXT = "Update";
    public static final String UPDATE_COMMAND = "update";
    
    public static final String CANCEL_ALL_TEXT = "Cancel all";
    public static final String CANCEL_ALL_COMMAND = "cancelAll";
    
    private final StatusWatcher watcher;
    
    public ManListener(StatusWatcher watcher){
        this.watcher = watcher;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.watcher.runs()){
            System.out.println("Could not perform "+e.getActionCommand()+" because watcher is currently updating. Please try again.");
            return;
        }
        
        if(e.getActionCommand().equals(ManListener.CANCEL_ALL_COMMAND)){
            this.watcher.cancelAll();
        }
        else if(e.getActionCommand().endsWith(ManListener.UPDATE_COMMAND)){
            this.watcher.runCycle();
        }
        else{
            System.err.println("Unknown command in "+ManListener.class.getName());
        }
        
    }
    
}
