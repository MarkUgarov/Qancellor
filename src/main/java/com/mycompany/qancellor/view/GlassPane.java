/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.qancellor.view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;

/**
 *
 * @author mugarov
 */
public class GlassPane extends JPanel{
    
    public GlassPane(Component component){
        this.setSize(component.getSize());
        this.setBackground(new Color(125,125,125,125));
    }
    
    public void setVisibility(boolean visible){
        this.setVisible(visible);
    }
    
}
