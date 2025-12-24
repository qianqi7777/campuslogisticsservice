package src.entity;

import java.sql.Timestamp;

/**
 * LostItem 实体类
 * 对应数据库中的 LostItem 表，用于表示失物招领的记录
 * 字段说明：
 *  - itemID: 物品 ID（主键，自增）
 *  - itemName: 物品名称
 *  - place: 丢失或捡到的地点
 *  - publishTime: 发布时间
 *  - status: 状态（如 未认领/已认领）
 *  - publisherID: 发布人学号
 */
public class LostItem {
    private int itemID;
    private String itemName;
    private String place;
    private Timestamp publishTime;
    private String status;
    private String publisherID;

    public LostItem() {}

    // getter / setter
    public int getItemID() { return itemID; }
    public void setItemID(int itemID) { this.itemID = itemID; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public Timestamp getPublishTime() { return publishTime; }
    public void setPublishTime(Timestamp publishTime) { this.publishTime = publishTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPublisherID() { return publisherID; }
    public void setPublisherID(String publisherID) { this.publisherID = publisherID; }
}
