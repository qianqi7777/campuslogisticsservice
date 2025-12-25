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
    /** 场馆 ID（主键） */
    private int venueID;
    /** 场馆名称 */
    private String venueName;
    /** 容量 */
    private int capacity;
    /** 位置描述 */
    private String location;
    /** 是否可用（是/否） */
    private String isAvailable;

    public Venue() {}

    // getter / setter
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
     * 获取场馆名称
     * @return 场馆名称
     */
    public String getVenueName() { return venueName; }
    /**
     * 设置场馆名称
     * @param venueName 场馆名称
     */
    public void setVenueName(String venueName) { this.venueName = venueName; }

    /**
     * 获取容量
     * @return 容量
     */
    public int getCapacity() { return capacity; }
    /**
     * 设置容量
     * @param capacity 容量
     */
    public void setCapacity(int capacity) { this.capacity = capacity; }

    /**
     * 获取位置
     * @return 位置
     */
    public String getLocation() { return location; }
    /**
     * 设置位置
     * @param location 位置
     */
    public void setLocation(String location) { this.location = location; }

    /**
     * 获取是否可用状态
     * @return 是否可用
     */
    public String getIsAvailable() { return isAvailable; }
    /**
     * 设置是否可用状态
     * @param isAvailable 是否可用
     */
    public void setIsAvailable(String isAvailable) { this.isAvailable = isAvailable; }
}
