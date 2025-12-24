package src.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static Properties props = new Properties();

    static {
        try {
            props.load(DBUtil.class.getClassLoader().getResourceAsStream("config/db.properties"));
            Class.forName(props.getProperty("mysql.driver"));
        } catch (Exception e) {
            // 尝试从文件系统加载，如果 classpath 加载失败（例如在某些 IDE 配置下）
            try {
                java.io.FileInputStream fis = new java.io.FileInputStream("config/db.properties");
                props.load(fis);
                fis.close();
                Class.forName(props.getProperty("mysql.driver"));
            } catch (Exception ex) {
                throw new RuntimeException("MySQL驱动加载失败或配置文件读取异常", ex);
            }
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                props.getProperty("mysql.url"),
                props.getProperty("mysql.username"),
                props.getProperty("mysql.password")
            );
        } catch (SQLException e) {
            throw new RuntimeException("MySQL数据库连接失败", e);
        }
    }

    public static String getDbUrl() {
        return props.getProperty("mysql.url");
    }

    public static String getDbUsername() {
        return props.getProperty("mysql.username");
    }

    public static String getDbPassword() {
        return props.getProperty("mysql.password");
    }

    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn, PreparedStatement pstmt) {
        close(conn, pstmt, null);
    }
}
