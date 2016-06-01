/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.qancellor.view;

import com.mugarov.qancellor.control.ManListener;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author mugarov
 */
public class ButtonPanel extends JPanel{
    
    private final JButton update;
    private final JButton cancelAll;
    
    public ButtonPanel(){
        this.update = new JButton(ManListener.UPDATE_TEXT);
        this.update.setActionCommand(ManListener.UPDATE_COMMAND);
        this.cancelAll = new JButton(ManListener.CANCEL_ALL_TEXT);
        this.cancelAll.setActionCommand(ManListener.CANCEL_ALL_COMMAND);
        
        this.setLayout(new GridLayout(0,1));
        this.add(this.update);
        this.add(this.cancelAll);
    }
    
    
    public void init(ActionListener listener){
        this.update.addActionListener(listener);
        this.cancelAll.addActionListener(listener);
    }
    
    /**
     * Sets the buttons enabled or disabled.
     * @param enabled
     */
    public void setButtonsEnabled(boolean enabled){
        this.update.setEnabled(enabled);
        this.cancelAll.setEnabled(enabled);
    }
    
}
