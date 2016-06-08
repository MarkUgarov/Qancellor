/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.qancellor.control;

import com.mugarov.qancellor.model.Entry;
import com.mugarov.qancellor.model.EntryParser;
import com.mugarov.qancellor.view.Frame;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 *
 * @author mugarov
 */
public class StatusWatcher extends TimerTask{
    
    private ArrayList<Entry> entries;
    private final Frame frame;
    private final Executer executer;
    
    private boolean runs;
    
    public StatusWatcher(Frame frame){
        this.frame = frame;
        this.frame.initButtonPanel(new ManListener(this));
        this.entries = new ArrayList<>();
        this.executer = new Executer();
        this.runs = false;
    }
    
    public void runCycle(){
//        System.out.println("Running Watcher Cycle");
        this.runs = true;
        this.frame.setButtonsEnabled(false);
        this.parse();
        this.adjust();
        this.frame.updateUIs();
        this.frame.setButtonsEnabled(true);
        this.runs = false;
    }
    
    private void adjust(){
        if(this.frame.getContent() == null){
            this.frame.initTablePanel(entries, new CancelListener(this));
            return;
        }
//        System.out.println("START check");
//        this.frame.printTableContent();
        for(Entry parsed: this.entries){
            if(!this.frame.getContent().contains(parsed)){
                this.frame.addValue(parsed);
//                System.out.println("\tAdding value "+parsed.getID());
            }
            else{
//                System.out.println("\tDo not add "+parsed.getID());
            }
        }
        int index = 0;
        while(index < this.frame.getContent().size()){
//            System.out.println("\tContent on index "+index+" is "+this.frame.getContent().get(index).getID());
            if(!this.entries.contains(this.frame.getContent().get(index))){
//                System.out.println("\tRemoving "+this.frame.getContent().get(index));
                this.frame.removeValue(index);
            }
            else{
                index++;
            }
        }
        this.frame.updateUIs();
//        System.out.println("END check");
//        this.frame.printTableContent();
    }
    
    private boolean isEqual(Entry parsed, Entry old){
        for(int i =0 ; i<parsed.asArray().length && i<old.asArray().length; i++){
            if(!parsed.asArray()[i].equals(old.asArray()[i])){
                return false;
            }
        }
        return true;
    }
    
    
    private void parse(){
        String status = this.executer.getStatusReport();
        this.entries = EntryParser.toEntryList(status);
    }
    
    public boolean cancel(String id){
        if(this.executer.cancel(id)){
            this.frame.removeValue(id);
            return true;
        } 
        return false;
    }
    
    public boolean cancelAll(){
        boolean ret = true;
        for(Entry entry:this.entries){
            ret = ret && this.cancel(entry.getID());
        }
        return ret;
    }

    @Override
    public void run() {
        if(!this.runs){
            this.runCycle();
        }
    }
    
    public boolean runs(){
        return this.runs;
    }
}
