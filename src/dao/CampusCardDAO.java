package src.dao;

import src.entity.CampusCard;
import src.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CampusCardDAO：校园卡相关的数据库访问对象（DAO）
 * 负责对 CampusCard 表进行增删改查操作（数据访问层）
 */
public class CampusCardDAO {
    /**
     * 根据用户ID和用户类型查询校园卡信息
     * @param userId 关联的用户ID（例如学号或工号）
     * @param userType 用户类型（例如 "student" 或 "staff"）
     * @return CampusCard 若存在则返回对应 CampusCard 对象，否则返回 null
     */
    public CampusCard selectByUserIdAndType(String userId, String userType) {
        // TODO
        // 实现说明（注释）：
        // 1. 准备 SQL：SELECT * FROM CampusCard WHERE UserID = ? AND UserType = ?
        // 2. 获取数据库连接：Connection conn = DBUtil.getConnection()
        // 3. 使用 PreparedStatement 设置参数 userId, userType
        // 4. 执行查询 pstmt.executeQuery()
        // 5. 如果存在记录，构建并返回 CampusCard 实体（映射各列到实体字段）
        // 6. 关闭 ResultSet/PreparedStatement/Connection（或使用 DBUtil.close）
        // 7. 在 catch 块中处理 SQLException 并返回 null 或抛出运行期异常
        return null;
    }

    /**
     * 根据用户ID和类型更新校园卡状态（例如挂失/恢复）
     * @param userId 关联用户ID
     * @param userType 用户类型
     * @param status 要设置的状态字符串
     */
    public void updateStatus(String userId, String userType, String status) {
        // TODO
        // 实现说明（注释）：
        // 1. SQL: UPDATE CampusCard SET Status = ? WHERE UserID = ? AND UserType = ?
        // 2. 获取连接并使用 PreparedStatement 设置参数(status, userId, userType)
        // 3. 执行更新 pstmt.executeUpdate()
        // 4. 处理返回的更新计数（可选），用于判断是否成功更新
        // 5. 关闭资源并在异常情况下记录/抛出异常
    }

    /**
     * 根据卡号查询校园卡信息
     * @param cardId 校园卡卡号
     * @return CampusCard 对象或 null
     */
    public CampusCard selectByCardId(String cardId) {
        // TODO
        // 实现说明：
        // 1. SQL: SELECT * FROM CampusCard WHERE CardID = ?
        // 2. 获取连接、创建 PreparedStatement，设置 cardId
        // 3. 执行查询并映射结果到 CampusCard
        // 4. 关闭资源并返回 CampusCard 或 null
        return null;
    }

    /**
     * 根据卡号更新状态（用于恢复或挂失）
     * @param cardId 卡号
     * @param status 新状态
     */
    public void updateStatusByCardId(String cardId, String status) {
        // TODO
        // 实现说明：
        // 1. SQL: UPDATE CampusCard SET Status = ? WHERE CardID = ?
        // 2. 获取连接、设置参数并执行更新
        // 3. 处理异常并关闭资源
    }

    /**
     * 查询所有处于“挂失”或“丢失”状态的卡（用于管理端查看）
     * @return CampusCard 列表
     */
    public List<CampusCard> selectLostCards() {
        // TODO
        // 实现说明：
        // 1. SQL: SELECT * FROM CampusCard WHERE Status IN ('挂失','丢失')
        // 2. 执行查询，循环 ResultSet，将每行映射为 CampusCard 并添加到列表
        // 3. 关闭资源并返回列表（如果没有记录返回空列表而非 null）
        return new ArrayList<>();
    }
}
