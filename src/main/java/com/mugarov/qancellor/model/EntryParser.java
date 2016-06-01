/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.qancellor.model;

import java.util.ArrayList;

/**
 *
 * @author mugarov
 */
public abstract class EntryParser {
    //    job-ID  prior   name       user         state submit/start at     queue                          slots ja-task-ID 
//-----------------------------------------------------------------------------------------------------------------
//5112539 1.05000 data0_Newb mugarov      r     05/16/2016 16:56:44 all.q@fuc01008.CeBiTec.Uni-Bie     8        

    public static ArrayList<Entry> toEntryList(String status){
        ArrayList<Entry> entries = new ArrayList<>();
        if(status == null || status.length() == 0){
            System.out.println("Status is empty");
            return entries;
        }
        
        
        String lineSeparator = "\n";
        String whiteSpace = "\\s+";
        int startLine = 2;
        
        String[] lines = status.split(lineSeparator);
        
        if(lines.length<=startLine){
            System.out.println("Status does not have enough lines.");
            return entries;
        }
        
        for(int i = startLine; i<lines.length; i++){
            entries.add(new Entry(lines[i].split(whiteSpace)));
        }

        return entries;
    }
}
