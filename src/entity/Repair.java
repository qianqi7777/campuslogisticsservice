package src.entity;

import java.sql.Timestamp;

public class Repair {
    private int repairID;
    private String content;
    private Timestamp submitTime;
    private String status;
    private String submitterID;
    private String handlerID;

    public Repair() {}

    public int getRepairID() { return repairID; }
    public void setRepairID(int repairID) { this.repairID = repairID; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Timestamp getSubmitTime() { return submitTime; }
    public void setSubmitTime(Timestamp submitTime) { this.submitTime = submitTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSubmitterID() { return submitterID; }
    public void setSubmitterID(String submitterID) { this.submitterID = submitterID; }

    public String getHandlerID() { return handlerID; }
    public void setHandlerID(String handlerID) { this.handlerID = handlerID; }
}
