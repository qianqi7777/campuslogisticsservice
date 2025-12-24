package src.dao;

import src.entity.LostItem;
import src.util.DBUtil;
import java.sql.*;

/**
 * LostItemDAO：失物招领相关的数据库访问对象
 * 提供失物信息的插入等操作
 */
public class LostItemDAO {
    /**
     * 插入一条失物招领记录
     * @param lostItem 要插入的 LostItem 对象
     */
    public void insertLostItem(LostItem lostItem) {
        // TODO
        // 实现说明：
        // 1. SQL: INSERT INTO LostItem (ItemName, Place, PublishTime, Status, PublisherID) VALUES (?, ?, ?, ?, ?)
        //    - PublishTime 可使用 CURRENT_TIMESTAMP 或由 Java 端传入
        // 2. 获取连接并创建 PreparedStatement，设置参数
        // 3. 执行更新，并在需要时通过 getGeneratedKeys 获取 ItemID
        // 4. 关闭资源并处理异常
    }
}
