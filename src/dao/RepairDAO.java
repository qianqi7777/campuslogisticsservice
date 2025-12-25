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
        // SQL 插入语句
        String sql = "INSERT INTO Repair (Content, SubmitTime, Status, SubmitterID, HandlerID) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();
            // 创建 PreparedStatement，并指定返回生成的主键
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // 设置参数
            pstmt.setString(1, repair.getContent());
            // 如果提交时间为空，则使用当前系统时间
            if (repair.getSubmitTime() != null) {
                pstmt.setTimestamp(2, repair.getSubmitTime());
            } else {
                pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            }
            pstmt.setString(3, repair.getStatus());
            pstmt.setString(4, repair.getSubmitterID());
            pstmt.setString(5, repair.getHandlerID());
            // 执行插入
            pstmt.executeUpdate();
            // 获取自增主键
            rs = pstmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                repair.setRepairID(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("插入维修记录失败", e);
        } finally {
            // 关闭资源
            DBUtil.close(conn, pstmt, rs);
        }
    }

    /**
     * 根据维修单 ID 查询维修记录
     * @param repairId 维修单 ID
     * @return Repair 对象或 null
     */
    public Repair selectById(int repairId) {
        // SQL 查询语句
        String sql = "SELECT RepairID, Content, SubmitTime, Status, SubmitterID, HandlerID FROM Repair WHERE RepairID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, repairId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Repair r = new Repair();
                r.setRepairID(rs.getInt("RepairID"));
                r.setContent(rs.getString("Content"));
                r.setSubmitTime(rs.getTimestamp("SubmitTime"));
                r.setStatus(rs.getString("Status"));
                r.setSubmitterID(rs.getString("SubmitterID"));
                r.setHandlerID(rs.getString("HandlerID"));
                return r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 根据提交者 ID 和状态查询维修列表（用于学生查看自己提交的单）
     * @param submitterId 提交人学号
     * @param status 期望的状态，如 "待处理"、"已完成" 等；若为 null 则不按状态过滤
     * @return Repair 列表
     */
    public List<Repair> selectBySubmitterIdAndStatus(String submitterId, String status) {
        List<Repair> list = new ArrayList<>();
        String sql;
        // 根据是否传入状态构建不同的 SQL
        if (status == null) {
            sql = "SELECT RepairID, Content, SubmitTime, Status, SubmitterID, HandlerID FROM Repair WHERE SubmitterID = ? ORDER BY SubmitTime DESC";
        } else {
            sql = "SELECT RepairID, Content, SubmitTime, Status, SubmitterID, HandlerID FROM Repair WHERE SubmitterID = ? AND Status = ? ORDER BY SubmitTime DESC";
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, submitterId);
            if (status != null) pstmt.setString(2, status);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Repair r = new Repair();
                r.setRepairID(rs.getInt("RepairID"));
                r.setContent(rs.getString("Content"));
                r.setSubmitTime(rs.getTimestamp("SubmitTime"));
                r.setStatus(rs.getString("Status"));
                r.setSubmitterID(rs.getString("SubmitterID"));
                r.setHandlerID(rs.getString("HandlerID"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    /**
     * 查询所有维修申请（用于管理员查看）
     * @return Repair 列表
     */
    public List<Repair> selectAll() {
        List<Repair> list = new ArrayList<>();
        // SQL 查询语句，按提交时间降序排列
        String sql = "SELECT RepairID, Content, SubmitTime, Status, SubmitterID, HandlerID FROM Repair ORDER BY SubmitTime DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Repair r = new Repair();
                r.setRepairID(rs.getInt("RepairID"));
                r.setContent(rs.getString("Content"));
                r.setSubmitTime(rs.getTimestamp("SubmitTime"));
                r.setStatus(rs.getString("Status"));
                r.setSubmitterID(rs.getString("SubmitterID"));
                r.setHandlerID(rs.getString("HandlerID"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    /**
     * 更新指定维修单的处理人和状态（管理员指定负责人时使用）
     * @param repairId 维修单 ID
     * @param handlerId 处理人工号
     * @param status 新状态
     */
    public void updateHandlerAndStatus(int repairId, String handlerId, String status) {
        // SQL 更新语句
        String sql = "UPDATE Repair SET HandlerID = ?, Status = ? WHERE RepairID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, handlerId);
            pstmt.setString(2, status);
            pstmt.setInt(3, repairId);
            int updated = pstmt.executeUpdate();
            if (updated <= 0) {
                System.out.println("updateHandlerAndStatus: 没有找到匹配的维修记录 (repairId=" + repairId + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新维修处理人和状态失败", e);
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }

    /**
     * 更新维修单状态
     * @param repairId 维修单ID
     * @param status 新状态
     * @return 是否成功
     */
    public boolean updateStatus(int repairId, String status) {
        // SQL 更新语句
        String sql = "UPDATE Repair SET Status = ? WHERE RepairID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            pstmt.setInt(2, repairId);
            int updated = pstmt.executeUpdate();
            if (updated <= 0) {
                System.out.println("updateStatus: 没有找到匹配的维修记录 (repairId=" + repairId + ")");
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新维修状态失败", e);
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }

    /**
     * 分配维修人员
     * @param repairId 维修单ID
     * @param handlerId 处理人工号
     * @return 是否成功
     */
    public boolean updateHandler(int repairId, String handlerId) {
        // SQL 更新语句
        String sql = "UPDATE Repair SET HandlerID = ? WHERE RepairID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, handlerId);
            pstmt.setInt(2, repairId);
            int updated = pstmt.executeUpdate();
            if (updated <= 0) {
                System.out.println("updateHandler: 没有找到匹配的维修记录 (repairId=" + repairId + ")");
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("分配维修人员失败", e);
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }

    /**
     * 根据提交人查询维修单
     * @param submitterId 提交人学号
     * @return 维修单列表
     */
    public List<Repair> selectBySubmitter(String submitterId) {
        List<Repair> list = new ArrayList<>();
        // SQL 查询语句
        String sql = "SELECT RepairID, Content, SubmitTime, Status, SubmitterID, HandlerID FROM Repair WHERE SubmitterID = ? ORDER BY SubmitTime DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, submitterId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Repair r = new Repair();
                r.setRepairID(rs.getInt("RepairID"));
                r.setContent(rs.getString("Content"));
                r.setSubmitTime(rs.getTimestamp("SubmitTime"));
                r.setStatus(rs.getString("Status"));
                r.setSubmitterID(rs.getString("SubmitterID"));
                r.setHandlerID(rs.getString("HandlerID"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }

    /**
     * 根据处理人查询维修单
     * @param handlerId 处理人工号
     * @return 维修单列表
     */
    public List<Repair> selectByHandler(String handlerId) {
        List<Repair> list = new ArrayList<>();
        // SQL 查询语句
        String sql = "SELECT RepairID, Content, SubmitTime, Status, SubmitterID, HandlerID FROM Repair WHERE HandlerID = ? ORDER BY SubmitTime DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, handlerId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Repair r = new Repair();
                r.setRepairID(rs.getInt("RepairID"));
                r.setContent(rs.getString("Content"));
                r.setSubmitTime(rs.getTimestamp("SubmitTime"));
                r.setStatus(rs.getString("Status"));
                r.setSubmitterID(rs.getString("SubmitterID"));
                r.setHandlerID(rs.getString("HandlerID"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return list;
    }
}
