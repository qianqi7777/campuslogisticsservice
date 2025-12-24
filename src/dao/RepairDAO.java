package src.dao;

import src.entity.Repair;
import src.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * RepairDAO：维修申请相关的数据访问对象
 * 提供维修申请的增查改等数据库操作
 */
public class RepairDAO {
    /**
     * 插入一条维修申请
     * @param repair 要插入的 Repair 实体
     */
    public void insertRepair(Repair repair) {
        // TODO
        // 实现说明：
        // 1. SQL: INSERT INTO Repair (Content, SubmitTime, Status, SubmitterID, HandlerID) VALUES (?, ?, ?, ?, ?)
        //    - SubmitTime 可使用 CURRENT_TIMESTAMP 或由 Java 端传入
        //    - HandlerID 在提交时通常为空
        // 2. 使用 PreparedStatement 设置参数并执行更新
        // 3. 可通过 getGeneratedKeys 获取 RepairID 并设置回 repair
        // 4. 关闭资源并处理异常
    }

    /**
     * 根据维修单 ID 查询维修记录
     * @param repairId 维修单 ID
     * @return Repair 对象或 null
     */
    public Repair selectById(int repairId) {
        // TODO
        // 实现说明：
        // 1. SQL: SELECT RepairID, Content, SubmitTime, Status, SubmitterID, HandlerID FROM Repair WHERE RepairID = ?
        // 2. 执行查询并将结果映射到 Repair 对象
        // 3. 关闭资源并返回 Repair 或 null
        return null;
    }

    /**
     * 根据提交者 ID 和状态查询维修列表（用于学生查看自己提交的单）
     * @param submitterId 提交人学号
     * @param status 期望的状态，如 "待处理"、"已完成" 等；若为 null 则不按状态过滤
     * @return Repair 列表
     */
    public List<Repair> selectBySubmitterIdAndStatus(String submitterId, String status) {
        // TODO
        // 实现说明：
        // 1. 如果 status 为 null，SQL: SELECT * FROM Repair WHERE SubmitterID = ? ORDER BY SubmitTime DESC
        //    否则 SQL: SELECT * FROM Repair WHERE SubmitterID = ? AND Status = ? ORDER BY SubmitTime DESC
        // 2. 执行查询并将每行映射为 Repair 加入列表
        // 3. 关闭资源并返回列表
        return new ArrayList<>();
    }

    /**
     * 查询所有维修申请（用于管理员查看）
     * @return Repair 列表
     */
    public List<Repair> selectAll() {
        // TODO
        // 实现说明：
        // 1. SQL: SELECT RepairID, Content, SubmitTime, Status, SubmitterID, HandlerID FROM Repair ORDER BY SubmitTime DESC
        // 2. 执行查询并映射结果集到列表
        // 3. 关闭资源并返回
        return new ArrayList<>();
    }

    /**
     * 更新指定维修单的处理人和状态（管理员指定负责人时使用）
     * @param repairId 维修单 ID
     * @param handlerId 处理人工号
     * @param status 新状态
     */
    public void updateHandlerAndStatus(int repairId, String handlerId, String status) {
        // TODO
        // 实现说明：
        // 1. SQL: UPDATE Repair SET HandlerID = ?, Status = ? WHERE RepairID = ?
        // 2. 执行更新并检查受影响行数
        // 3. 关闭资源并处理异常
    }

    /**
     * 更新维修单状态（例如设置为已完成）
     * @param repairId 维修单 ID
     * @param status 要设置的状态
     */
    public void updateStatus(int repairId, String status) {
        // TODO
        // 实现说明：
        // 1. SQL: UPDATE Repair SET Status = ? WHERE RepairID = ?
        // 2. 执行更新并处理异常
    }

    /**
     * 根据维修单 ID 查询处理人工号（用于查询该单是否已被分配）
     * @param repairId 维修单 ID
     * @return 处理人工号字符串或 null
     */
    public String selectHandlerIdByRepairId(int repairId) {
        // TODO
        // 实现说明：
        // 1. SQL: SELECT HandlerID FROM Repair WHERE RepairID = ?
        // 2. 执行查询并返回 HandlerID（可能为 null）
        // 3. 关闭资源并处理异常
        return null;
    }
}
