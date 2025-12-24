package src.dao;

import src.entity.Staff;
import src.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffDAO {
    public Staff selectByEidAndPwd(String eid, String password) {
        String sql = "SELECT * FROM Staff WHERE EID = ? AND Password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, eid);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Staff(
                        rs.getString("EID"),
                        rs.getString("EName"),
                        rs.getString("Dept"),
                        rs.getString("Phone"),
                        rs.getString("Position"),
                        rs.getString("Password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Staff selectByEidAndDept(String eid, String dept) {
        // TODO
        return null;
    }

    public Staff selectByEid(String eid) {
        // TODO
        return null;
    }
}
