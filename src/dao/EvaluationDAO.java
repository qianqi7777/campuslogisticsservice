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
        String sql = "INSERT INTO Evaluation (RepairID, Score, Comment) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, evaluation.getRepairID());
            pstmt.setInt(2, evaluation.getScore());
            pstmt.setString(3, evaluation.getComment());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                evaluation.setEvalID(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("插入评价记录失败", e);
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
    }

    /**
     * 查询所有评价记录
     * @return 评价列表（可能为空）
     */
    public List<Evaluation> selectAll() {
        List<Evaluation> list = new ArrayList<>();
        String sql = "SELECT EvalID, RepairID, Score, Comment FROM Evaluation ORDER BY EvalID DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Evaluation e = new Evaluation();
                e.setEvalID(rs.getInt("EvalID"));
                e.setRepairID(rs.getInt("RepairID"));
                e.setScore(rs.getInt("Score"));
                e.setComment(rs.getString("Comment"));
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }
}
