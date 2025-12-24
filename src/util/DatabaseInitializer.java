package src.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initDatabase() {
        System.out.println("正在检查并初始化数据库...");
        String targetDb = "CampusServiceDB";
        String fullUrl = DBUtil.getDbUrl();
        // 提取基础 URL (去掉数据库名)
        String baseUrl = fullUrl.substring(0, fullUrl.indexOf("/", 13)); // jdbc:mysql://host:port
        // 处理参数
        if (fullUrl.contains("?")) {
             baseUrl += fullUrl.substring(fullUrl.indexOf("?"));
        }

        String user = DBUtil.getDbUsername();
        String password = DBUtil.getDbPassword();

        try (Connection conn = DriverManager.getConnection(baseUrl, user, password);
             Statement stmt = conn.createStatement()) {

            // 1. 创建数据库
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + targetDb + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            System.out.println("数据库 " + targetDb + " 检查/创建完成。");

            // 2. 切换到该数据库并创建表
            stmt.executeUpdate("USE " + targetDb);

            createTables(stmt);

            System.out.println("数据库表初始化完成。");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("数据库初始化失败：" + e.getMessage());
        }
    }

    private static void createTables(Statement stmt) throws Exception {
        // Student
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Student (" +
                "SID CHAR(10) PRIMARY KEY COMMENT '学号'," +
                "SName VARCHAR(20) NOT NULL COMMENT '学生姓名'," +
                "College VARCHAR(50) NOT NULL COMMENT '所属学院'," +
                "Phone VARCHAR(11) COMMENT '手机号'," +
                "Grade VARCHAR(10) NOT NULL COMMENT '年级'," +
                "Password VARCHAR(20) DEFAULT '123456' COMMENT '默认登录密码'" +
                ") COMMENT '学生信息表'");

        // Staff
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Staff (" +
                "EID CHAR(8) PRIMARY KEY COMMENT '工号'," +
                "EName VARCHAR(20) NOT NULL COMMENT '教职工姓名'," +
                "Dept VARCHAR(30) NOT NULL COMMENT '所属部门'," +
                "Phone VARCHAR(11) COMMENT '手机号'," +
                "Position VARCHAR(20) NOT NULL COMMENT '职位'," +
                "Password VARCHAR(20) DEFAULT 'admin123' COMMENT '默认登录密码'" +
                ") COMMENT '教职工信息表'");

        // Repair
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

        // Evaluation
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Evaluation (" +
                "EvalID INT PRIMARY KEY AUTO_INCREMENT COMMENT '评价ID'," +
                "RepairID INT NOT NULL COMMENT '关联维修单ID'," +
                "Score INT NOT NULL CHECK(Score BETWEEN 1 AND 5) COMMENT '评分'," +
                "Comment VARCHAR(200) COMMENT '评价内容'," +
                "FOREIGN KEY (RepairID) REFERENCES Repair(RepairID) ON DELETE CASCADE" +
                ") COMMENT '维修评价表'");

        // LostItem
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS LostItem (" +
                "ItemID INT PRIMARY KEY AUTO_INCREMENT COMMENT '物品ID'," +
                "ItemName VARCHAR(50) NOT NULL COMMENT '物品名称'," +
                "Place VARCHAR(50) NOT NULL COMMENT '丢失/捡拾地点'," +
                "PublishTime DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间'," +
                "Status VARCHAR(10) NOT NULL DEFAULT '未认领' COMMENT '状态'," +
                "PublisherID CHAR(10) COMMENT '发布人学号'," +
                "FOREIGN KEY (PublisherID) REFERENCES Student(SID) ON DELETE SET NULL" +
                ") COMMENT '失物招领表'");

        // Venue
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Venue (" +
                "VenueID INT PRIMARY KEY AUTO_INCREMENT COMMENT '场馆ID'," +
                "VenueName VARCHAR(30) NOT NULL COMMENT '场馆名称'," +
                "Capacity INT NOT NULL CHECK(Capacity>0) COMMENT '容量'," +
                "Location VARCHAR(50) NOT NULL COMMENT '所在位置'," +
                "IsAvailable VARCHAR(2) NOT NULL DEFAULT '是' COMMENT '是否可用'" +
                ") COMMENT '场馆信息表'");

        // Reservation
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Reservation (" +
                "ResID INT PRIMARY KEY AUTO_INCREMENT COMMENT '预约ID'," +
                "VenueID INT NOT NULL COMMENT '关联场馆ID'," +
                "ReserverID CHAR(10) COMMENT '预约人学号'," +
                "ResTime DATE NOT NULL COMMENT '预约日期'," +
                "Duration INT NOT NULL CHECK(Duration>0) COMMENT '使用时长'," +
                "AuditStatus VARCHAR(10) NOT NULL DEFAULT '待审核' COMMENT '审核状态'," +
                "FOREIGN KEY (VenueID) REFERENCES Venue(VenueID) ON DELETE CASCADE," +
                "FOREIGN KEY (ReserverID) REFERENCES Student(SID) ON DELETE SET NULL" +
                ") COMMENT '场馆预约表'");

        // CampusCard
        // 注意：MySQL 8.0 以下可能不支持 CHECK 约束，但语法通常兼容。
        // 复杂的 CHECK 约束在某些 MySQL 版本中可能被忽略，这里保留 SQL 逻辑。
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
