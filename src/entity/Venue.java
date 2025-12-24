package src.entity;

/**
 * Venue 实体类
 * 对应数据库中的 Venue 表，用于表示体育场馆或教室等可预约场地的信息
 * 字段说明：
 *  - venueID: 场馆 ID（主键）
 *  - venueName: 场馆名称
 *  - capacity: 容量
 *  - location: 位置描述
 *  - isAvailable: 是否可用（是/否）
 */
public class Venue {
    private int venueID;
    private String venueName;
    private int capacity;
    private String location;
    private String isAvailable;

    public Venue() {}

    // getter / setter
    public int getVenueID() { return venueID; }
    public void setVenueID(int venueID) { this.venueID = venueID; }

    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getIsAvailable() { return isAvailable; }
    public void setIsAvailable(String isAvailable) { this.isAvailable = isAvailable; }
}
