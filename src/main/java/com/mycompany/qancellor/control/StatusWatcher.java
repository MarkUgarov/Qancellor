/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.qancellor.control;

import com.mycompany.qancellor.model.Entry;
import com.mycompany.qancellor.model.EntryParser;
import com.mycompany.qancellor.view.Frame;
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
        System.out.println("Running Watcher Cycle");
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
        // for testing
        if(status == null || status.isEmpty()){
                //    job-ID  prior   name       user         state submit/start at     queue                          slots ja-task-ID 
                //-----------------------------------------------------------------------------------------------------------------
                //5112539 1.05000 data0_Newb mugarov      r     05/16/2016 16:56:44 all.q@fuc01008.CeBiTec.Uni-Bie     8 
            StringBuilder testBuilder = new StringBuilder();
            testBuilder.append("job-ID  prior   name       user         state submit/start at     queue                          slots ja-task-ID ");
            testBuilder.append("\n");
            testBuilder.append("-----------------------------------------------------------------------------------------------------------------");
            testBuilder.append("\n");
            testBuilder.append("5112539 1.05000 data0_Newb mugarov      r     05/16/2016 16:56:44 all.q@fuc01008.CeBiTec.Uni-Bie     8 ");
            status = testBuilder.toString();
        }
        
        // not for testing
        this.entries = EntryParser.toEntryList(status);
        
        // for testing
        if(this.entries.isEmpty()){
            Entry entry1 = new Entry("TestID 1","TestPriority 1","TestName 1","TestUser 1 ","TestState 1","TestDate 1", "TestTime 1", "TestQueue 1", "TestSlots 1","");
            this.entries.add(entry1);
            Entry entry2 = new Entry("TestID 2","TestPriority 2","TestName 2","TestUser 2","TestState 2","TestDate 2", "TestTime 2", "TestQueue 2", "TestSlots 2","");
            this.entries.add(entry2);
        }
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
