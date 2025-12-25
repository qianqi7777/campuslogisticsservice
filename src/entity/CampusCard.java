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
    /** 卡号（主键） */
    private String cardID;
    /** 关联用户ID */
    private String userID;
    /** 用户类型 */
    private String userType;
    /** 卡内余额 */
    private BigDecimal balance;
    /** 卡状态 */
    private String status;
    /** 发卡时间 */
    private Timestamp createTime;

    public CampusCard() {}

    // 以下为标准的 getter/setter，用于在 DAO/Service 中传递数据
    /**
     * 获取卡号
     * @return 卡号
     */
    public String getCardID() { return cardID; }
    /**
     * 设置卡号
     * @param cardID 卡号
     */
    public void setCardID(String cardID) { this.cardID = cardID; }

    /**
     * 获取用户 ID
     * @return 用户 ID
     */
    public String getUserID() { return userID; }
    /**
     * 设置用户 ID
     * @param userID 用户 ID
     */
    public void setUserID(String userID) { this.userID = userID; }

    /**
     * 获取用户类型
     * @return 用户类型
     */
    public String getUserType() { return userType; }
    /**
     * 设置用户类型
     * @param userType 用户类型
     */
    public void setUserType(String userType) { this.userType = userType; }

    /**
     * 获取余额
     * @return 余额
     */
    public BigDecimal getBalance() { return balance; }
    /**
     * 设置余额
     * @param balance 余额
     */
    public void setBalance(BigDecimal balance) { this.balance = balance; }

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
     * 获取发卡时间
     * @return 发卡时间
     */
    public Timestamp getCreateTime() { return createTime; }
    /**
     * 设置发卡时间
     * @param createTime 发卡时间
     */
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }
}
