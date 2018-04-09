/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs4280;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 *
 * @author yanfewang3
 */
public class User {
    private int uid;
    private ArrayList<PurchaseRec> record = new ArrayList<>();
    private double point = -1;
    private String name, actor;
    
    public String getName(){
        return name;
    }
    
    public void setName(String n){
        name = n;
    }
    public int getUID(){
        return uid;
    }
    
    public void setUID(int id){
        uid = id;
    }
    
    public String getActor(){
        return actor;
    }
    
    public void setActor(String a){
        actor = a;
    }
    public ArrayList<PurchaseRec> getRecord(){
        return record;
    }
    
    public void setRecord(String r){
        PurchaseRec rec = new PurchaseRec();
        
        record.add(rec);
    }
    
    public double getPoint(){
        return point;
    }
    
    public void setPoint(double p){
        point += p;
    }
}
