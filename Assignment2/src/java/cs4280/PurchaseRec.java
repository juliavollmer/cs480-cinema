/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs4280;

/**
 *
 * @author yanfewang3
 */
public class PurchaseRec {
    
    private int SID, PID;
    private String date;
    private String status; //refunded, rejected, waiting, success 
    
    public int getPID(){
        return PID;
    }
    public int getSID(){
        return SID;
    }
    public String getDate(){
        return date;
    }
    public String getStatus(){
        return status;
    }
    
}
