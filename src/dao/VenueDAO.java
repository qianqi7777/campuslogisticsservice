package src.dao;

import src.entity.Venue;
import src.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * VenueDAO：场馆（Venue）相关的数据库访问对象
 * 提供场馆查询和状态更新操作
 */
public class VenueDAO {
    /**
     * 根据场馆ID查询场馆信息
     * @param venueId 场馆 ID
     * @return Venue 对象或 null
     */
    public Venue selectById(int venueId) {
        // TODO
        // 实现说明：
        // 1. SQL: SELECT VenueID, VenueName, Capacity, Location, IsAvailable FROM Venue WHERE VenueID = ?
        // 2. 获取连接，使用 PreparedStatement 设置参数并执行查询
        // 3. 如果存在结果，将其映射为 Venue 并返回
        // 4. 关闭资源并处理 SQLException
        return null;
    }

    /**
     * 查询所有可用（isAvailable = '是'）的场馆
     * @return 可用场馆列表
     */
    public List<Venue> selectAvailableVenues() {
        // TODO
        // 实现说明：
        // 1. SQL: SELECT VenueID, VenueName, Capacity, Location, IsAvailable FROM Venue WHERE IsAvailable = '是' ORDER BY VenueName
        // 2. 执行查询，遍历 ResultSet，映射为 Venue 并加入返回列表
        // 3. 关闭资源并返回列表（若无记录返回空列表）
        return new ArrayList<>();
    }

    /**
     * 更新场馆是否可用状态（例如管理员审核后设置）
     * @param venueId 场馆 ID
     * @param isAvailable "是" 或 "否"
     */
    public void updateIsAvailable(int venueId, String isAvailable) {
        // TODO
        // 实现说明：
        // 1. SQL: UPDATE Venue SET IsAvailable = ? WHERE VenueID = ?
        // 2. 使用 PreparedStatement 设置参数并执行更新
        // 3. 处理异常并关闭资源
    }
}
