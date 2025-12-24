package src.dao;

import src.entity.Evaluation;
import src.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EvaluationDAO：维修评价相关的数据访问对象
 * 提供评价的插入与查询操作
 */
public class EvaluationDAO {
    /**
     * 插入一条评价记录到数据库
     * @param evaluation 要插入的 Evaluation 实体
     */
    public void insertEvaluation(Evaluation evaluation) {
        // TODO
        // 实现说明：
        // 1. SQL: INSERT INTO Evaluation (RepairID, Score, Comment) VALUES (?, ?, ?)
        // 2. 获取连接，创建 PreparedStatement，并设置参数 evaluation.getRepairID(), evaluation.getScore(), evaluation.getComment()
        // 3. 执行更新 pstmt.executeUpdate()
        // 4. 如使用自增主键，可通过 getGeneratedKeys 获取 evalID 并设置回 evaluation
        // 5. 关闭资源并处理 SQL 异常
    }

    /**
     * 查询所有评价记录
     * @return 评价列表（可能为空）
     */
    public List<Evaluation> selectAll() {
        // TODO
        // 实现说明：
        // 1. SQL: SELECT EvalID, RepairID, Score, Comment FROM Evaluation ORDER BY EvalID DESC
        // 2. 获取连接，执行查询，遍历 ResultSet，将每行封装为 Evaluation 并加入列表
        // 3. 关闭资源并返回列表
        return new ArrayList<>();
    }
}
