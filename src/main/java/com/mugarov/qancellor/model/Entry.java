/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.qancellor.model;

import java.util.Objects;
import java.util.Vector;

/**
 *
 * @author mugarov
 */
public class Entry {
    
    private final String id;
    private final String prior;
    private final String name;
    private final String user;
    private final String state;
    private final String startDate;
    private final String startTime;
    private final String queue;
    private final String slots;
    private final String jaTaskID;
    
    public static final String[] HEADLINE =  new String[]{"ID", "Priority", "Name", "User","State", "Start Date", "Start Time", "Queue", "Slots", "ja-taks-ID"};
    public static final int ID_ROW = 0;
    public static final int SIZE = 10;
    
    public Entry(String id, String priority, String name, String user, String state, String startDate, String startTime, String queue, String slots, String jaTaskID){
        this.id = id;
        this.prior = priority;
        this.name = name;
        this.user = user;
        this.state = state;
        this.startDate = startDate;
        this.startTime = startTime;
        this.queue = queue;
        this.slots = slots;
        this.jaTaskID = jaTaskID;
    }
    
    public Entry(String[] entryArray){
        if(entryArray == null){
            System.err.println("Constructing Entry with null-parameters");
        }
//        else if(entryArray.length != Entry.SIZE){
//            System.err.println("Entry receiving Array with non-matching size: "+entryArray.length +" !="+Entry.SIZE);
//        }
        this.id = this.getCheckedEntry(entryArray, 0);
        this.prior = this.getCheckedEntry(entryArray, 1);
        this.name = this.getCheckedEntry(entryArray, 2);
        this.user = this.getCheckedEntry(entryArray, 3);
        this.state = this.getCheckedEntry(entryArray, 4);
        this.startDate = this.getCheckedEntry(entryArray, 5);
        this.startTime = this.getCheckedEntry(entryArray, 6);
        this.queue = this.getCheckedEntry(entryArray, 7);
        this.slots = this.getCheckedEntry(entryArray, 8);
        this.jaTaskID = this.getCheckedEntry(entryArray, 9);
    }
    
    public Entry(String[] entryArray, boolean splitTime){
        if(splitTime){
            this.id = this.getCheckedEntry(entryArray, 0);
            this.prior = this.getCheckedEntry(entryArray, 1);
            this.name = this.getCheckedEntry(entryArray, 2);
            this.user = this.getCheckedEntry(entryArray, 3);
            this.state = this.getCheckedEntry(entryArray, 4);
            String[] splitTimeArray = this.splitTime(this.getCheckedEntry(entryArray, 5));
            this.startDate = this.getCheckedEntry(splitTimeArray,0);
            this.startTime = this.getCheckedEntry(splitTimeArray,1);
            this.queue = this.getCheckedEntry(entryArray, 6);
            this.slots = this.getCheckedEntry(entryArray, 7);
            this.jaTaskID = this.getCheckedEntry(entryArray, 8);
        }
        else{
            this.id = this.getCheckedEntry(entryArray, 0);
            this.prior = this.getCheckedEntry(entryArray, 1);
            this.name = this.getCheckedEntry(entryArray, 2);
            this.user = this.getCheckedEntry(entryArray, 3);
            this.state = this.getCheckedEntry(entryArray, 4);
            this.startDate = this.getCheckedEntry(entryArray, 5);
            this.startTime = this.getCheckedEntry(entryArray, 6);
            this.queue = this.getCheckedEntry(entryArray, 7);
            this.slots = this.getCheckedEntry(entryArray, 8);
            this.jaTaskID = this.getCheckedEntry(entryArray, 9);
        }

        
    }

    public Object[] asArray() {
        return new String[]{this.id, this.prior, this.name, this.user, this.state, this.startDate, this.startTime, this.queue, this.slots, this.jaTaskID};
    }
    
    public Object[][] asStartArray(){
        return new String[][]{{this.id, this.prior, this.name, this.user, this.state, this.startDate, this.startTime, this.queue, this.slots, this.jaTaskID}};
    }
    
    public String getID(){
        return this.id;
    }
    
    private String[] splitTime(String dateTime){
        return dateTime.split("T");
    }
    
    private String getCheckedEntry(String[] array, int index){
        String ret = "";
        if(array != null && index < array.length && array[index] != null){
            ret = array[index].trim();
        }
        return ret;
    }
    
    @Override
    public boolean equals(Object object)
    {
        boolean sameSame = false;

        if (object != null && object instanceof Entry)
        {
            sameSame = this.id.equals(((Entry) object).getID());
            sameSame =  sameSame && this.prior.equals(((Entry) object).getPriority());
            sameSame =  sameSame && this.state.equals(((Entry) object).getState());
        }

        return sameSame;
    }

    private String getPriority() {
        return this.prior;
    }
    
    private String getState(){
        return this.state;
    }
    
    public boolean isEmpty(){
        boolean empty = true;
        for(Object s:this.asArray()){
            if(s!= null && !s.equals("")){
                empty = false;
            }
        }
        return empty;
        
    }
    
}
