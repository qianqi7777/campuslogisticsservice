package src.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * CampusCard 实体类
 * 对应数据库中的 CampusCard 表，用于表示校园卡的基础信息
 * 字段说明：
 *  - cardID: 卡号（主键）
 *  - userID: 关联用户ID（学号或工号）
 *  - userType: 用户类型（student 或 staff）
 *  - balance: 卡内余额
 *  - status: 卡状态（例如：正常、挂失、停用）
 *  - createTime: 发卡时间
 */
public class CampusCard {
    private String cardID;
    private String userID;
    private String userType;
    private BigDecimal balance;
    private String status;
    private Timestamp createTime;

    public CampusCard() {}

    // 以下为标准的 getter/setter，用于在 DAO/Service 中传递数据
    public String getCardID() { return cardID; }
    public void setCardID(String cardID) { this.cardID = cardID; }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }
}
