package vn.edu.greenwich.mobile_cw1.models;

import java.io.Serializable;

public class Trip implements Serializable {
    protected long _id;
    protected String _name;
    protected String _startDate;
    protected String _departure;
    protected String _destination;
    protected String _riskAssessment;
    protected String _desc;
    protected String _participants;
//    protected int _owner;

    public Trip() {
        _id = -1;
        _name = null;
        _startDate = null;
        _departure = null;
        _destination = null;
        _riskAssessment = null;
        _desc = null;
        _participants = null;
//        _owner = -1;
    }

    public Trip(long id, String name, String startDate, String departure, String destination, String riskAssessment, String desc, String participants) {
        _id = id;
        _name = name;
        _startDate = startDate;
        _departure = departure;
        _destination = destination;
        _riskAssessment = riskAssessment;
        _desc = desc;
        _participants = participants;
//        _owner = owner;
    }

    public long getId() { return _id; }
    public void setId(long id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }

    public String getStartDate() {
        return _startDate;
    }
    public void setStartDate(String startDate) {
        _startDate = startDate;
    }

    public String getDeparture() {
        return _departure;
    }
    public void setDeparture(String departure) {_departure = departure;}

    public String getDestination() {
        return _destination;
    }
    public void setDestination(String destination) {_destination = destination;}

    public String getRiskAssessment() {
        return _riskAssessment;
    }
    public void setRiskAssessment(String riskAssessment) {_riskAssessment = riskAssessment;}

    public String getDesc() {
        return _desc;
    }
    public void setDesc(String desc) {_desc = desc;}

    public String getParticipants() {
        return _participants;
    }
    public void setParticipants(String participants) {_participants = participants;}

    public boolean isEmpty() {
        if (-1 == _id && null == _name && null == _startDate)
            return true;

        return false;
    }

    @Override
    public String toString() {
        return "[" + _startDate + "] " + _name;
    }
}