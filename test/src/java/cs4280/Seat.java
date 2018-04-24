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
public class Seat {

    private String seid;
    private String row_id, seat_id, value;

    public Seat(String seid, String value) {
        setSeid(seid);
        setValue(value);
    }

    public Seat() {
    }

    public String getSeid() {
        return (seid);
    }

    private void setSeid(String seid) {
        this.seid = seid;
    }

    public String getRow() {
        return (row_id);
    }

    private void setRow(String row_id) {
        this.row_id = row_id;

    }

    private void setSeatid(String seat_id) {
        this.seat_id = seat_id;
    }

    public String getSeatid() {
        return (seat_id);
    }

    public String getValue() {
        return (value);
    }

    private void setValue(String value) {
        this.value = value;

    }
}
