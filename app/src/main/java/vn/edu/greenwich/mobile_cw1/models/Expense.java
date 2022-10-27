package vn.edu.greenwich.mobile_cw1.models;

import java.io.Serializable;

public class Expense implements Serializable {
    protected long _id;
    protected String _typeAmount;
    protected String _amount;
    protected String _date;
    protected String _time;
    protected String _type;
    protected long _tripId;

    public Expense() {
        _id = -1;
        _typeAmount = null;
        _amount = null;
        _date = null;
        _time = null;
        _type = null;
        _tripId = -1;
    }

    public Expense(long id, String typeAmount, String amount, String date, String time, String type, long tripId) {
        _id = id;
        _typeAmount = typeAmount;
        _amount = amount;
        _date = date;
        _time = time;
        _type = type;
        _tripId = tripId;
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public String getContent() {
        return _typeAmount;
    }

    public void setContent(String typeAmount) {
        _typeAmount = typeAmount;
    }

    public String getAmount() {
        return _amount;
    }

    public void setAmount(String amount) { _amount = amount; }

    public String getDate() {
        return _date;
    }

    public void setDate(String date) {
        _date = date;
    }

    public String getTime() {
        return _time;
    }

    public void setTime(String time) {
        _time = time;
    }

    public String getType() {
        return _type;
    }

    public void setType(String type) {
        _type = type;
    }

    public long getTripId() {
        return _tripId;
    }

    public void setTripId(long tripId) {
        _tripId = tripId;
    }

    public boolean isEmpty() {
        if (-1 == _id && null == _typeAmount && null == _date && null == _time && null == _type && -1 == _tripId)
            return true;

        return false;
    }

    @Override
    public String toString() {
        return "[" + _type + "][" + _date + "] " + _typeAmount;
    }
}