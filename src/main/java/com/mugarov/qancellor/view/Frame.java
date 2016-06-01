/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.qancellor.view;

import com.mugarov.qancellor.model.Entry;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author mugarov
 */
public class Frame extends JFrame{
    
    private final TablePanel tablePanel;
    private final ButtonPanel buttonPanel;
    private final GlassPane watermark;
    
    private boolean sizeSet;
    private final int minInitHeight = 200;
    private final int minInitWidth = 200;
    
    public Frame(WindowListener listener, boolean visible){
        super();
        this.sizeSet = false;
        this.setBackground(Color.BLACK);
        if(listener == null){
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        else{
            this.addWindowListener(listener);
        }
        this.setTitle("Qancellor");
        this.setLayout(new BorderLayout());
        this.tablePanel = new TablePanel();
        this.add(this.tablePanel, BorderLayout.CENTER);
        
        this.buttonPanel = new ButtonPanel();
        this.add(this.buttonPanel, BorderLayout.SOUTH);
        
        this.watermark = new GlassPane(this.getGlassPane());
        this.setGlassPane(this.watermark);
        
        this.setVisible(visible);
    }
    
    public Frame(){
        this(null, true);
    }
    
    public void initTablePanel(ArrayList<Entry> entries, ActionListener cancelListener){
        this.tablePanel.setValues(entries, cancelListener);
    }
    
    public void initButtonPanel(ActionListener manListener){
        this.buttonPanel.init(manListener);
    }
    
    public void addValue(Entry entry){
        this.tablePanel.addLine(entry);
    }
    
    public void removeValue(String id){
        this.tablePanel.deleteLine(id);
    }
    
    public void removeValue(int index){
        this.tablePanel.deleteLine(index);
    }
    
    public ArrayList<Entry> getContent(){
        return this.tablePanel.getContent();
    }

    public void updateUIs(){
        this.tablePanel.updateUI();
        this.buttonPanel.updateUI();
        this.watermark.updateUI();
        if(!sizeSet){
            int width = (int) Math.max(this.minInitWidth, this.tablePanel.getPreferredSize().getWidth()*1.5);
            int height = (int) Math.max(this.minInitHeight, this.tablePanel.getPreferredSize().getHeight()*1.2);
            this.setPreferredSize(new Dimension(width, height));
            this.setSize(this.getPreferredSize());
            this.sizeSet = true;
        }
    }
    
    public void printTableContent(){
        this.tablePanel.printContent();
    }
    
    public void setButtonsEnabled(boolean enabled){
        this.buttonPanel.setButtonsEnabled(enabled);
        this.tablePanel.setButtonsEnabled(enabled);
//        this.watermark.setVisibility(!enabled);
        this.tablePanel.updateUI();
        this.buttonPanel.updateUI();
        this.watermark.updateUI();
    }
}
