/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs4280;

/**
 *
 * @author jsvollmer2
 */
public class PurchaseRec {

    private String SID, PID, RID, UID;
    private String time, seat, price, gainedpoints, paymethod, actor, status;

    public PurchaseRec(String PID, String SID, String RID, String UID, String time, String seat, String price,
            String gainedpoints, String paymethod, String actor, String status) {
        setSid(SID);
        setPid(PID);
        setRid(RID);
        setUid(UID);
        setTime(time);
        setSeat(seat);
        setPrice(price);
        setGainedpoints(gainedpoints);
        setPaymethod(paymethod);
        setActor(actor);
        setStatus(status);
    }

    public PurchaseRec() {
    }

    public String getRid() {
        return RID;
    }

    private void setRid(String RID) {
        this.RID = RID;
    }

    public String getUid() {
        return UID;
    }

    private void setUid(String UID) {
        this.UID = UID;
    }

    public String getSeat() {
        return seat;
    }

    private void setSeat(String seat) {
        this.seat = seat;
    }

    public String getPrice() {
        return price;
    }

    private void setPrice(String price) {
        this.price = price;
    }

    public String getGainedpoints() {
        return gainedpoints;
    }

    private void setGainedpoints(String gainedpoints) {
        this.gainedpoints = gainedpoints;
    }

    public String getPaymethod() {
        return paymethod;
    }

    private void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getActor() {
        return actor;
    }

    private void setActor(String actor) {
        this.actor = actor;
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    public String getSid() {
        return (SID);
    }

    private void setSid(String SID) {
        this.SID = SID;
    }

    public String getPID() {
        return PID;
    }

    private void setPid(String PID) {
        this.PID = PID;
    }

    public String getTime() {
        return time;
    }

    private void setTime(String time) {
        this.time = time;
    }

}
