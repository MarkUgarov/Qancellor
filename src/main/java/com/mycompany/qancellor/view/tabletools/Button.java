/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.qancellor.view.tabletools;

import javax.swing.JButton;

/**
 *
 * @author mugarov
 */
public class Button extends JButton{
    private String ID;
    
    public Button(){
        super();
        this.ID = null;
    }
    
    public String getID(){
        return this.ID;
    }
    
    public void setID(String id){
        this.ID = id;
        this.setActionCommand(this.ID);
    }
    
    
}
