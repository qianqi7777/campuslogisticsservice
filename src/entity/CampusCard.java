package src.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CampusCard {
    private String cardID;
    private String userID;
    private String userType;
    private BigDecimal balance;
    private String status;
    private Timestamp createTime;

    public CampusCard() {}

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
