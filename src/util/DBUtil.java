package src.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库工具类
 * 负责数据库连接的获取、配置加载以及资源的释放
 */
public class DBUtil {
    // 存储数据库配置属性
    private static Properties props = new Properties();

    // 静态代码块，类加载时执行，用于加载数据库驱动和配置文件
    static {
        try {
            // 尝试从 classpath 加载 db.properties 配置文件
            props.load(DBUtil.class.getClassLoader().getResourceAsStream("config/db.properties"));
            // 加载 MySQL 驱动类
            Class.forName(props.getProperty("mysql.driver"));
        } catch (Exception e) {
            // 尝试从文件系统加载，如果 classpath 加载失败（例如在某些 IDE 配置下）
            try {
                java.io.FileInputStream fis = new java.io.FileInputStream("config/db.properties");
                props.load(fis);
                fis.close();
                Class.forName(props.getProperty("mysql.driver"));
            } catch (Exception ex) {
                // 如果两种方式都失败，抛出运行时异常
                throw new RuntimeException("MySQL驱动加载失败或配置文件读取异常", ex);
            }
        }
    }

    /**
     * 获取数据库连接
     * @return Connection 数据库连接对象
     */
    public static Connection getConnection() {
        try {
            // 使用 DriverManager 获取连接
            return DriverManager.getConnection(
                props.getProperty("mysql.url"),
                props.getProperty("mysql.username"),
                props.getProperty("mysql.password")
            );
        } catch (SQLException e) {
            throw new RuntimeException("MySQL数据库连接失败", e);
        }
    }

    /**
     * 获取数据库连接 URL
     * @return String 数据库 URL
     */
    public static String getDbUrl() {
        return props.getProperty("mysql.url");
    }

    /**
     * 获取数据库用户名
     * @return String 用户名
     */
    public static String getDbUsername() {
        return props.getProperty("mysql.username");
    }

    /**
     * 获取数据库密码
     * @return String 密码
     */
    public static String getDbPassword() {
        return props.getProperty("mysql.password");
    }

    /**
     * 关闭数据库资源
     * @param conn 数据库连接
     * @param pstmt 预编译语句对象
     * @param rs 结果集
     */
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            // 关闭结果集
            if (rs != null) rs.close();
            // 关闭预编译语句对象
            if (pstmt != null) pstmt.close();
            // 关闭数据库连接
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库资源（不包含结果集）
     * @param conn 数据库连接
     * @param pstmt 预编译语句对象
     */
    public static void close(Connection conn, PreparedStatement pstmt) {
        close(conn, pstmt, null);
    }
}
