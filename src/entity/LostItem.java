package src.entity;

import java.sql.Timestamp;

public class LostItem {
    private int itemID;
    private String itemName;
    private String place;
    private Timestamp publishTime;
    private String status;
    private String publisherID;

    public LostItem() {}

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
