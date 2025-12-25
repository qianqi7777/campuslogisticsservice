package src.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * 数据库初始化工具类
 * 用于在程序启动时检查并创建数据库及相关表结构
 */
public class DatabaseInitializer {

    /**
     * 初始化数据库
     * 检查数据库是否存在，不存在则创建，并创建所有必要的表
     */
    public static void initDatabase() {
        System.out.println("正在检查并初始化数据库...");
        String targetDb = "CampusServiceDB";
        String fullUrl = DBUtil.getDbUrl();
        // 提取基础 URL (去掉数据库名)，以便连接到 MySQL 服务而不是特定数据库
        // 例如：jdbc:mysql://localhost:3306/CampusServiceDB -> jdbc:mysql://localhost:3306
        String baseUrl = fullUrl.substring(0, fullUrl.indexOf("/", 13)); // jdbc:mysql://host:port
        // 处理参数，保留 URL 参数（如时区配置等）
        if (fullUrl.contains("?")) {
             baseUrl += fullUrl.substring(fullUrl.indexOf("?"));
        }

        String user = DBUtil.getDbUsername();
        String password = DBUtil.getDbPassword();

        // 使用 try-with-resources 自动关闭连接和 Statement
        try (Connection conn = DriverManager.getConnection(baseUrl, user, password);
             Statement stmt = conn.createStatement()) {

            // 1. 创建数据库
            // 如果数据库不存在，则创建，指定字符集为 utf8mb4 以支持多语言
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + targetDb + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            System.out.println("数据库 " + targetDb + " 检查/创建完成。");

            // 2. 切换到该数据库并创建表
            stmt.executeUpdate("USE " + targetDb);

            // 调用创建表的方法，按顺序创建所有必要的表
            createTables(stmt);

            System.out.println("数据库表初始化完成。");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("数据库初始化失败：" + e.getMessage());
        }
    }

    /**
     * 创建所有数据表
     * @param stmt Statement 对象
     * @throws Exception 创建过程中可能抛出的异常
     */
    private static void createTables(Statement stmt) throws Exception {
        // Student 表：存储学生信息
        // 包含学号、姓名、学院、手机号、年级、密码等字段
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Student (" +
                "SID CHAR(10) PRIMARY KEY COMMENT '学号'," +
                "SName VARCHAR(20) NOT NULL COMMENT '学生姓名'," +
                "College VARCHAR(50) NOT NULL COMMENT '所属学院'," +
                "Phone VARCHAR(11) COMMENT '手机号'," +
                "Grade VARCHAR(10) NOT NULL COMMENT '年级'," +
                "Password VARCHAR(20) DEFAULT '123456' COMMENT '默认登录密码'" +
                ") COMMENT '学生信息表'");

        // Staff 表：存储教职工信息
        // 包含工号、姓名、部门、手机号、职位、密码等字段
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Staff (" +
                "EID CHAR(8) PRIMARY KEY COMMENT '工号'," +
                "EName VARCHAR(20) NOT NULL COMMENT '教职工姓名'," +
                "Dept VARCHAR(30) NOT NULL COMMENT '所属部门'," +
                "Phone VARCHAR(11) COMMENT '手机号'," +
                "Position VARCHAR(20) NOT NULL COMMENT '职位'," +
                "Password VARCHAR(20) DEFAULT 'admin123' COMMENT '默认登录密码'" +
                ") COMMENT '教职工信息表'");

        // 插入默认管理员账号
        // 账号名：root123，密码：123456
        stmt.executeUpdate("INSERT IGNORE INTO Staff (EID, EName, Dept, Phone, Position, Password) VALUES ('admin001', 'root123', '管理中心', '13800000000', '超级管理员', '123456')");

        // Repair 表：存储维修申请信息
        // 包含维修单ID、内容、提交时间、状态、提交人、处理人等字段
        // 外键关联 Student(SID) 和 Staff(EID)
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Repair (" +
                "RepairID INT PRIMARY KEY AUTO_INCREMENT COMMENT '维修单ID'," +
                "Content VARCHAR(200) NOT NULL COMMENT '维修内容'," +
                "SubmitTime DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间'," +
                "Status VARCHAR(10) NOT NULL DEFAULT '待处理' COMMENT '状态'," +
                "SubmitterID CHAR(10) COMMENT '提交人学号'," +
                "HandlerID CHAR(8) COMMENT '处理人工号'," +
                "FOREIGN KEY (SubmitterID) REFERENCES Student(SID) ON DELETE SET NULL," +
                "FOREIGN KEY (HandlerID) REFERENCES Staff(EID) ON DELETE SET NULL" +
                ") COMMENT '维修申请表'");

        // Evaluation 表：存储维修评价信息
        // 包含评价ID、关联维修单ID、评分、评价内容等字段
        // 外键关联 Repair(RepairID)
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Evaluation (" +
                "EvalID INT PRIMARY KEY AUTO_INCREMENT COMMENT '评价ID'," +
                "RepairID INT NOT NULL COMMENT '关联维修单ID'," +
                "Score INT NOT NULL COMMENT '评分' CHECK(Score BETWEEN 1 AND 5)," +
                "Comment VARCHAR(200) COMMENT '评价内容'," +
                "FOREIGN KEY (RepairID) REFERENCES Repair(RepairID) ON DELETE CASCADE" +
                ") COMMENT '维修评价表'");

        // LostItem 表：存储失物招领信息
        // 包含物品ID、名称、地点、发布时间、状态、发布人等字段
        // 外键关联 Student(SID)
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS LostItem (" +
                "ItemID INT PRIMARY KEY AUTO_INCREMENT COMMENT '物品ID'," +
                "ItemName VARCHAR(50) NOT NULL COMMENT '物品名称'," +
                "Place VARCHAR(50) NOT NULL COMMENT '丢失/捡拾地点'," +
                "PublishTime DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间'," +
                "Status VARCHAR(10) NOT NULL DEFAULT '未认领' COMMENT '状态'," +
                "PublisherID CHAR(10) COMMENT '发布人学号'," +
                "FOREIGN KEY (PublisherID) REFERENCES Student(SID) ON DELETE SET NULL" +
                ") COMMENT '失物招领表'");

        // Venue 表：存储场馆信息
        // 包含场馆ID、名称、容量、位置、是否可用等字段
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Venue (" +
                "VenueID INT PRIMARY KEY AUTO_INCREMENT COMMENT '场馆ID'," +
                "VenueName VARCHAR(30) NOT NULL COMMENT '场馆名称'," +
                "Capacity INT NOT NULL COMMENT '容量' CHECK(Capacity>0)," +
                "Location VARCHAR(50) NOT NULL COMMENT '所在位置'," +
                "IsAvailable VARCHAR(2) NOT NULL DEFAULT '是' COMMENT '是否可用'" +
                ") COMMENT '场馆信息表'");

        // Reservation 表：存储场馆预约信息
        // 包含预约ID、关联场馆ID、预约人、时间、时长、审核状态等字段
        // 外键关联 Venue(VenueID) 和 Student(SID)
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Reservation (" +
                "ResID INT PRIMARY KEY AUTO_INCREMENT COMMENT '预约ID'," +
                "VenueID INT NOT NULL COMMENT '关联场馆ID'," +
                "ReserverID CHAR(10) COMMENT '预约人学号'," +
                "ResTime DATE NOT NULL COMMENT '预约日期'," +
                "Duration INT NOT NULL COMMENT '使用时长' CHECK(Duration>0)," +
                "AuditStatus VARCHAR(10) NOT NULL DEFAULT '待审核' COMMENT '审核状态'," +
                "FOREIGN KEY (VenueID) REFERENCES Venue(VenueID) ON DELETE CASCADE," +
                "FOREIGN KEY (ReserverID) REFERENCES Student(SID) ON DELETE SET NULL" +
                ") COMMENT '场馆预约表'");

        // CampusCard 表：存储校园卡信息
        // 包含卡号、关联用户ID、用户类型、余额、状态、开卡时间等字段
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS CampusCard (" +
                "CardID CHAR(12) PRIMARY KEY COMMENT '校园卡ID'," +
                "UserID VARCHAR(10) NOT NULL COMMENT '关联用户ID'," +
                "UserType VARCHAR(10) NOT NULL COMMENT '用户类型'," +
                "Balance DECIMAL(10,2) DEFAULT 0.00 COMMENT '账户余额'," +
                "Status VARCHAR(10) NOT NULL DEFAULT '正常' COMMENT '状态'," +
                "CreateTime DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '开卡时间'" +
                ") COMMENT '校园卡表'");
    }
}
