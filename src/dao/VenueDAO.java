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
        return null;
    }

    /**
     * 查询所有可用（isAvailable = '是'）的场馆
     * @return 可用场馆列表
     */
    public List<Venue> selectAvailableVenues() {
        // TODO
        return new ArrayList<>();
    }

    /**
     * 更新场馆是否可用状态（例如管理员审核后设置）
     * @param venueId 场馆 ID
     * @param isAvailable "是" 或 "否"
     */
    public void updateIsAvailable(int venueId, String isAvailable) {
        // TODO
    }
}
