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
    /** 预约 ID（主键） */
    private int resID;
    /** 关联的场馆 ID */
    private int venueID;
    /** 预约者学号 */
    private String reserverID;
    /** 预约日期 */
    private Date resTime;
    /** 预约时长 */
    private int duration;
    /** 审核状态 */
    private String auditStatus;

    public Reservation() {}

    // getter / setter
    /**
     * 获取预约 ID
     * @return 预约 ID
     */
    public int getResID() { return resID; }
    /**
     * 设置预约 ID
     * @param resID 预约 ID
     */
    public void setResID(int resID) { this.resID = resID; }

    /**
     * 获取场馆 ID
     * @return 场馆 ID
     */
    public int getVenueID() { return venueID; }
    /**
     * 设置场馆 ID
     * @param venueID 场馆 ID
     */
    public void setVenueID(int venueID) { this.venueID = venueID; }

    /**
     * 获取预约者 ID
     * @return 预约者 ID
     */
    public String getReserverID() { return reserverID; }
    /**
     * 设置预约者 ID
     * @param reserverID 预约者 ID
     */
    public void setReserverID(String reserverID) { this.reserverID = reserverID; }

    /**
     * 获取预约时间
     * @return 预约时间
     */
    public Date getResTime() { return resTime; }
    /**
     * 设置预约时间
     * @param resTime 预约时间
     */
    public void setResTime(Date resTime) { this.resTime = resTime; }

    /**
     * 获取预约时长
     * @return 预约时长
     */
    public int getDuration() { return duration; }
    /**
     * 设置预约时长
     * @param duration 预约时长
     */
    public void setDuration(int duration) { this.duration = duration; }

    /**
     * 获取审核状态
     * @return 审核状态
     */
    public String getAuditStatus() { return auditStatus; }
    /**
     * 设置审核状态
     * @param auditStatus 审核状态
     */
    public void setAuditStatus(String auditStatus) { this.auditStatus = auditStatus; }
}
