/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.qancellor.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author mugarov
 */
public class CancelListener implements ActionListener{
    
    private final StatusWatcher watcher;
    
    public CancelListener(StatusWatcher watcher){
        this.watcher = watcher;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.watcher.runs()){
            System.out.println("Automated check is running. Try to cancel later.");
            return;
        }
//        System.out.println("Performed: Event "+e.getActionCommand());
        this.watcher.cancel(e.getActionCommand());

        
    }
    
}
