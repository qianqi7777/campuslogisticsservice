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
    /** 物品 ID（主键） */
    private int itemID;
    /** 物品名称 */
    private String itemName;
    /** 丢失或捡到的地点 */
    private String place;
    /** 发布时间 */
    private Timestamp publishTime;
    /** 状态 */
    private String status;
    /** 发布人学号 */
    private String publisherID;

    public LostItem() {}

    // getter / setter
    /**
     * 获取物品 ID
     * @return 物品 ID
     */
    public int getItemID() { return itemID; }
    /**
     * 设置物品 ID
     * @param itemID 物品 ID
     */
    public void setItemID(int itemID) { this.itemID = itemID; }

    /**
     * 获取物品名称
     * @return 物品名称
     */
    public String getItemName() { return itemName; }
    /**
     * 设置物品名称
     * @param itemName 物品名称
     */
    public void setItemName(String itemName) { this.itemName = itemName; }

    /**
     * 获取地点
     * @return 地点
     */
    public String getPlace() { return place; }
    /**
     * 设置地点
     * @param place 地点
     */
    public void setPlace(String place) { this.place = place; }

    /**
     * 获取发布时间
     * @return 发布时间
     */
    public Timestamp getPublishTime() { return publishTime; }
    /**
     * 设置发布时间
     * @param publishTime 发布时间
     */
    public void setPublishTime(Timestamp publishTime) { this.publishTime = publishTime; }

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
     * 获取发布人 ID
     * @return 发布人 ID
     */
    public String getPublisherID() { return publisherID; }
    /**
     * 设置发布人 ID
     * @param publisherID 发布人 ID
     */
    public void setPublisherID(String publisherID) { this.publisherID = publisherID; }
}
