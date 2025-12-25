package src.entity;

import java.sql.Timestamp;

/**
 * Repair 实体类
 * 对应数据库中的 Repair 表，用于表示维修申请单
 * 字段说明：
 *  - repairID: 维修单 ID（主键）
 *  - content: 维修内容描述
 *  - submitTime: 提交时间
 *  - status: 当前状态（待处理/处理中/已完成）
 *  - submitterID: 提交人学号
 *  - handlerID: 处理人工号
 */
public class Repair {
    /** 维修单 ID（主键） */
    private int repairID;
    /** 维修内容描述 */
    private String content;
    /** 提交时间 */
    private Timestamp submitTime;
    /** 当前状态 */
    private String status;
    /** 提交人学号 */
    private String submitterID;
    /** 处理人工号 */
    private String handlerID;

    public Repair() {}

    // getter / setter
    /**
     * 获取维修单 ID
     * @return 维修单 ID
     */
    public int getRepairID() { return repairID; }
    /**
     * 设置维修单 ID
     * @param repairID 维修单 ID
     */
    public void setRepairID(int repairID) { this.repairID = repairID; }

    /**
     * 获取维修内容
     * @return 维修内容
     */
    public String getContent() { return content; }
    /**
     * 设置维修内容
     * @param content 维修内容
     */
    public void setContent(String content) { this.content = content; }

    /**
     * 获取提交时间
     * @return 提交时间
     */
    public Timestamp getSubmitTime() { return submitTime; }
    /**
     * 设置提交时间
     * @param submitTime 提交时间
     */
    public void setSubmitTime(Timestamp submitTime) { this.submitTime = submitTime; }

    /**
     * 获取状态
     * @return 状态
     */
    public String getStatus() { return status; }
    /**
     * 设置状态
     * @param status 状态
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * 获取提交人 ID
     * @return 提交人 ID
     */
    public String getSubmitterID() { return submitterID; }
    /**
     * 设置提交人 ID
     * @param submitterID 提交人 ID
     */
    public void setSubmitterID(String submitterID) { this.submitterID = submitterID; }

    /**
     * 获取处理人 ID
     * @return 处理人 ID
     */
    public String getHandlerID() { return handlerID; }
    /**
     * 设置处理人 ID
     * @param handlerID 处理人 ID
     */
    public void setHandlerID(String handlerID) { this.handlerID = handlerID; }
}
