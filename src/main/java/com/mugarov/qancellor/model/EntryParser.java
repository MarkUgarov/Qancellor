/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mugarov.qancellor.model;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author mugarov
 */
public abstract class EntryParser {
    //    job-ID  prior   name       user         state submit/start at     queue                          slots ja-task-ID 
//-----------------------------------------------------------------------------------------------------------------
//5112539 1.05000 data0_Newb mugarov      r     05/16/2016 16:56:44 all.q@fuc01008.CeBiTec.Uni-Bie     8        

    public static ArrayList<Entry> simpleToEntryList(String status){
        ArrayList<Entry> entries = new ArrayList<>();
        if(status == null || status.length() == 0){
            System.out.println("Status is empty");
            return entries;
        }
        
        
        String lineSeparator = System.getProperty("line.separator");
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
    

    public static ArrayList<Entry> xmlToEntryList(String status){
        ArrayList<Entry> entries = new ArrayList<>();
        Document doc = null;
        
        try {
            doc= EntryParser.getDocument(status);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(EntryParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EntryParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(EntryParser.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        if(doc == null){
            System.err.println("Could not been read.");
            return entries;
        }
        
        NodeList important = doc.getFirstChild().getChildNodes();
        if(important.getLength() == 0){
            System.out.println("Empty");
        }
        for(int i =0; i<important.getLength(); i++){
//            System.out.println("i ="+i);
            entries.addAll(EntryParser.nodeListToEntries(important.item(i).getChildNodes()));
        }
        return entries;
    }
    
    public static Document getDocument(String status) throws ParserConfigurationException, IOException, SAXException{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        javax.xml.parsers.DocumentBuilder db;
        db= dbf.newDocumentBuilder(); 
        return db.parse(new InputSource(new ByteArrayInputStream(status.getBytes("utf-8"))));
    }
    
    /**
     * 
     * @param list
     * @return a (potentially empty) list of entries
     */
    public static ArrayList<Entry> nodeListToEntries(NodeList list){
        ArrayList<Entry> ret = new ArrayList<>();
        Entry current;
        for(int j = 0; j<list.getLength(); j++){
//            System.out.println("j ="+j);
            current = EntryParser.nodeToEntry(list.item(j));
            if(current != null && !current.isEmpty()){
                ret.add(current);
            }
        }
        return ret;
    }
    
    /**
     * 
     * @param node
     * @return null if node does not contain information
     */
    public static Entry nodeToEntry(Node node){
//        System.out.println(node.getNodeName()+":"+node.getTextContent());
        String essential = node.getTextContent();
//        System.out.println(essential);
        String lineSeparator = System.getProperty("line.separator");
        String whiteSpace = "\\s+";
        
        String[] lines = essential.split(lineSeparator);
        ArrayList<String> nonEmpty = new ArrayList<>();
        String current;
        for(int i = 1; i<lines.length;i++){
            current = lines[i].trim();
            current = current.replaceAll(whiteSpace, "");
            nonEmpty.add(current);

        }
        if(nonEmpty.isEmpty()){
            return null;
        }
        
        String[] newlines = new String[nonEmpty.size()];
        return new Entry(nonEmpty.toArray(newlines), true);
    }
}
