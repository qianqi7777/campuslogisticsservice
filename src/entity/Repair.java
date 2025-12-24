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
    private int repairID;
    private String content;
    private Timestamp submitTime;
    private String status;
    private String submitterID;
    private String handlerID;

    public Repair() {}

    // getter / setter
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
