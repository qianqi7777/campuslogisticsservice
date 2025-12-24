package src.entity;

import java.sql.Date;

/**
 * Reservation 实体类
 * 对应数据库中的 Reservation 表，用于表示场馆预约信息
 * 字段说明：
 *  - resID: 预约 ID（主键）
 *  - venueID: 关联的场馆 ID
 *  - reserverID: 预约者学号
 *  - resTime: 预约日期
 *  - duration: 预约时长（单位可为小时等）
 *  - auditStatus: 审核状态（待审核/通过/拒绝）
 */
public class Reservation {
    private int resID;
    private int venueID;
    private String reserverID;
    private Date resTime;
    private int duration;
    private String auditStatus;

    public Reservation() {}

    // getter / setter
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
