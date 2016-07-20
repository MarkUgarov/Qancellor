/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.qancellor.view;

import com.mugarov.qancellor.model.Entry;
import com.mugarov.qancellor.view.tabletools.ButtonEditor;
import com.mugarov.qancellor.view.tabletools.ButtonRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mugarov
 */
public class TablePanel extends JPanel {
    
    private final String CANCEL = "Cancel";
    
    private final Object[] header;
    private final DefaultTableModel tableModel;
    private ArrayList<Entry> entries;
    private final JScrollPane scroll;
    private JTable table;
    private Object[][] values;
    
    private boolean enabled;
    private ActionListener cancelListener;
    
   public TablePanel(){
        this.setBackground(Color.red);
        this.setLayout(new BorderLayout());
        this.scroll = new JScrollPane();
        this.add(this.scroll, BorderLayout.CENTER);
        this.tableModel = new DefaultTableModel();
        
        this.header = new String[Entry.SIZE+1];
        for(int i = 0; i<Entry.SIZE; i++){
            this.header[i] = Entry.HEADLINE[i];
        }
        this.header[Entry.SIZE]=this.CANCEL;
        
        
    }
    
    
    public void setValues(ArrayList<Entry> entries, ActionListener cancelListener){
        this.entries = new ArrayList<>(entries);
        this.cancelListener = cancelListener;
        boolean add = (this.table == null);
        if(entries != null){
            this.values = new Object[entries.size()][Entry.SIZE+1];
            int index = 0;
            
            for(Entry entry: entries){
                System.arraycopy(entry.asArray(), 0, values[index], 0, Entry.SIZE);
                values[index][Entry.SIZE] = this.CANCEL;
                index++;
            }
        }
        
        this.tableModel.setDataVector(this.values, this.header);
        if(add){
            this.table = new JTable(this.tableModel){
                @Override
                public boolean isCellEditable(int row, int col) {
                    return (enabled && col==Entry.SIZE);
                }
            };
            this.table.getColumn(this.CANCEL).setCellRenderer(new ButtonRenderer());
            this.table.getColumn(this.CANCEL).setCellEditor(new ButtonEditor(cancelListener));

            
            this.scroll.add(this.table);
        }
        
        this.scroll.setViewportView(this.table);
        
        this.scroll.setBackground(Color.BLUE);
        this.setBackground(Color.GREEN);
        this.updateUI();
    }
    
    private void resetValues(){
        this.setValues(this.entries, this.cancelListener);
        this.tableModel.fireTableDataChanged();
    }
    
    public void addLine(Entry entry){
        if(entry == null){
            System.out.println("Entry is null");
            return;
        }
        else{
            this.entries.add(entry);
            if(this.tableModel != null){
                this.tableModel.addRow(entry.asArray());
            }
            
        }
        this.tableModel.fireTableDataChanged();
//        this.printContent();
    }
    
    public void deleteLine(String id){
        if(this.table != null){
            int index = 0;
            while (index<this.entries.size()){
                if(this.entries.get(index).getID().equals(id)){
                    this.deleteLine(index);
                }
                else{
                    index++;
                }
            }
        }
        
    }
    
    
    public ArrayList<Entry> getContent(){   
        return this.entries;
    }

    public void deleteLine(int index) {
//        System.out.println("Removing: "+this.entries.get(index).getID());
        if(index<this.entries.size()){
            this.entries.remove(index);
        }
        try{
            if(index<this.tableModel.getRowCount()){
                    this.tableModel.removeRow(index);
                    this.tableModel.fireTableDataChanged();
                }
            }
        catch(IndexOutOfBoundsException e){
            this.resetValues();
        }
//        this.printContent();
    }
    
    public void printContent(){
        System.out.println("Content is now ");
        if(this.entries == null){
            System.out.println("\tempty");
            return;
        }
        for(Entry entry:this.entries){
            System.out.println("\t"+entry.getID());
        }
    }
    
    @Override
    public void updateUI(){
        if(this.table != null){
            this.setPreferredSize(this.table.getPreferredSize());
        }
        super.updateUI();
    }
    
    public void setButtonsEnabled(boolean enabled){
        this.enabled = enabled;
    }
    
}
