package src.entity;

import java.sql.Date;

public class Reservation {
    private int resID;
    private int venueID;
    private String reserverID;
    private Date resTime;
    private int duration;
    private String auditStatus;

    public Reservation() {}

    public int getResID() { return resID; }
    public void setResID(int resID) { this.resID = resID; }

    public int getVenueID() { return venueID; }
    public void setVenueID(int venueID) { this.venueID = venueID; }

    public String getReserverID() { return reserverID; }
    public void setReserverID(String reserverID) { this.reserverID = reserverID; }

    public Date getResTime() { return resTime; }
    public void setResTime(Date resTime) { this.resTime = resTime; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getAuditStatus() { return auditStatus; }
    public void setAuditStatus(String auditStatus) { this.auditStatus = auditStatus; }
}
