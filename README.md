CampusServiceSystem/
├─ src/
│  ├─ entity/          // 实体类（对应数据库表）
│  ├─ dao/             // 数据访问层（数据库CRUD操作）
│  ├─ service/         // 业务逻辑层（处理核心业务流程）
│  ├─ view/            // 视图层（控制台交互：菜单、输入输出）
│  ├─ util/            // 工具类（数据库连接、输入校验、常量定义）
│  └─ Main.java        // 程序入口（启动类）
├─ lib/                // 依赖包（MySQL JDBC驱动）
└─ config/             // 配置文件（数据库连接信息：URL、用户名、密码）
# 校园服务管理系统（Java控制台+MySQL）全功能实现话术整理
## 一、前置准备：改为在java用程序建数据库
```sql
-- 1. 创建数据库（指定编码，避免中文乱码）
CREATE DATABASE IF NOT EXISTS CampusServiceDB 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
USE CampusServiceDB;

-- 2. 学生表（存储学生基础信息+登录密码）
CREATE TABLE IF NOT EXISTS Student (
    SID CHAR(10) PRIMARY KEY COMMENT '学号（主键，10位）',
    SName VARCHAR(20) NOT NULL COMMENT '学生姓名',
    College VARCHAR(50) NOT NULL COMMENT '所属学院',
    Phone VARCHAR(11) COMMENT '手机号（11位，可选）',
    Grade VARCHAR(10) NOT NULL COMMENT '年级（如2024级）',
    Password VARCHAR(20) DEFAULT '123456' COMMENT '默认登录密码'
) COMMENT '学生信息表';

-- 3. 教职工表（管理员表，存储后勤/场馆管理等人员信息）
CREATE TABLE IF NOT EXISTS Staff (
    EID CHAR(8) PRIMARY KEY COMMENT '工号（主键，8位）',
    EName VARCHAR(20) NOT NULL COMMENT '教职工姓名',
    Dept VARCHAR(30) NOT NULL COMMENT '所属部门（如后勤管理处）',
    Phone VARCHAR(11) CHECK(LENGTH(Phone)=11) COMMENT '手机号（必须11位）',
    Position VARCHAR(20) NOT NULL COMMENT '职位（如维修工程师）',
    Password VARCHAR(20) DEFAULT 'admin123' COMMENT '默认登录密码'
) COMMENT '教职工信息表';

-- 4. 维修单表（存储维修申请及处理状态）
CREATE TABLE IF NOT EXISTS Repair (
    RepairID INT PRIMARY KEY AUTO_INCREMENT COMMENT '维修单ID（自增主键）',
    Content VARCHAR(200) NOT NULL COMMENT '维修内容',
    SubmitTime DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间（默认当前时间）',
    Status VARCHAR(10) NOT NULL DEFAULT '待处理' COMMENT '状态：待处理/处理中/已完成',
    SubmitterID CHAR(10) COMMENT '提交人学号（外键，关联学生表）',
    HandlerID CHAR(8) COMMENT '处理人工号（外键，关联教职工表）',
    FOREIGN KEY (SubmitterID) REFERENCES Student(SID) ON DELETE SET NULL,
    FOREIGN KEY (HandlerID) REFERENCES Staff(EID) ON DELETE SET NULL
) COMMENT '维修申请表';

-- 5. 维修评价表（存储学生对维修服务的评价）
CREATE TABLE IF NOT EXISTS Evaluation (
    EvalID INT PRIMARY KEY AUTO_INCREMENT COMMENT '评价ID（自增主键）',
    RepairID INT NOT NULL COMMENT '关联维修单ID（外键）',
    Score INT NOT NULL CHECK(Score BETWEEN 1 AND 5) COMMENT '评分（1-5分）',
    Comment VARCHAR(200) COMMENT '评价内容（可选）',
    FOREIGN KEY (RepairID) REFERENCES Repair(RepairID) ON DELETE CASCADE
) COMMENT '维修评价表';

-- 6. 失物招领表（存储失物信息及认领状态）
CREATE TABLE IF NOT EXISTS LostItem (
    ItemID INT PRIMARY KEY AUTO_INCREMENT COMMENT '物品ID（自增主键）',
    ItemName VARCHAR(50) NOT NULL COMMENT '物品名称',
    Place VARCHAR(50) NOT NULL COMMENT '丢失/捡拾地点',
    PublishTime DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间（默认当前时间）',
    Status VARCHAR(10) NOT NULL DEFAULT '未认领' COMMENT '状态：未认领/已认领',
    PublisherID CHAR(10) COMMENT '发布人学号（外键，关联学生表）',
    FOREIGN KEY (PublisherID) REFERENCES Student(SID) ON DELETE SET NULL
) COMMENT '失物招领表';

-- 7. 场馆表（存储校园场馆基础信息）
CREATE TABLE IF NOT EXISTS Venue (
    VenueID INT PRIMARY KEY AUTO_INCREMENT COMMENT '场馆ID（自增主键）',
    VenueName VARCHAR(30) NOT NULL COMMENT '场馆名称',
    Capacity INT NOT NULL CHECK(Capacity>0) COMMENT '容量（必须大于0）',
    Location VARCHAR(50) NOT NULL COMMENT '所在位置',
    IsAvailable VARCHAR(2) NOT NULL DEFAULT '是' COMMENT '是否可用：是/否'
) COMMENT '场馆信息表';

-- 8. 场馆预约表（存储场馆预约申请及审核状态）
CREATE TABLE IF NOT EXISTS Reservation (
    ResID INT PRIMARY KEY AUTO_INCREMENT COMMENT '预约ID（自增主键）',
    VenueID INT NOT NULL COMMENT '关联场馆ID（外键）',
    ReserverID CHAR(10) COMMENT '预约人学号（外键，关联学生表）',
    ResTime DATE NOT NULL COMMENT '预约日期',
    Duration INT NOT NULL CHECK(Duration>0) COMMENT '使用时长（小时，必须大于0）',
    AuditStatus VARCHAR(10) NOT NULL DEFAULT '待审核' COMMENT '审核状态：待审核/已通过/已拒绝',
    FOREIGN KEY (VenueID) REFERENCES Venue(VenueID) ON DELETE CASCADE,
    FOREIGN KEY (ReserverID) REFERENCES Student(SID) ON DELETE SET NULL
) COMMENT '场馆预约表';

-- 9. 校园卡表（存储校园卡账户信息）
CREATE TABLE IF NOT EXISTS CampusCard (
    CardID CHAR(12) PRIMARY KEY COMMENT '校园卡ID（主键，12位）',
    UserID VARCHAR(10) NOT NULL COMMENT '关联用户ID（学生SID/教职工EID）',
    UserType VARCHAR(10) NOT NULL COMMENT '用户类型：student/admin',
    Balance DECIMAL(10,2) DEFAULT 0.00 COMMENT '账户余额（默认0元）',
    Status VARCHAR(10) NOT NULL DEFAULT '正常' COMMENT '状态：正常/挂失',
    CreateTime DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '开卡时间（默认当前时间）',
    -- 约束：用户类型为student时，UserID必须存在于Student表；为admin时存在于Staff表
    CONSTRAINT chk_user_type CHECK (
        (UserType = 'student' AND UserID IN (SELECT SID FROM Student)) OR
        (UserType = 'admin' AND UserID IN (SELECT EID FROM Staff))
    )
) COMMENT '校园卡表';
```

## 二、数据库连接配置（config/db.properties）
```properties
# MySQL连接信息（根据本地MySQL配置修改）
mysql.url=jdbc:mysql://localhost:3306/CampusServiceDB?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
mysql.username=root
mysql.password=你的MySQL密码（如123456）
mysql.driver=com.mysql.cj.jdbc.Driver
```

## 三、工具类核心实现（util/DBUtil.java）
```java
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static Properties props = new Properties();

    // 静态代码块：加载MySQL驱动和配置文件
    static {
        try {
            props.load(DBUtil.class.getClassLoader().getResourceAsStream("config/db.properties"));
            Class.forName(props.getProperty("mysql.driver"));
        } catch (Exception e) {
            throw new RuntimeException("MySQL驱动加载失败或配置文件读取异常", e);
        }
    }

    // 获取数据库连接
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

    // 关闭资源（Connection+PreparedStatement+ResultSet）
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 关闭资源（Connection+PreparedStatement）
    public static void close(Connection conn, PreparedStatement pstmt) {
        close(conn, pstmt, null);
    }
}
```

## 四、学生端核心功能实现话术（适配MySQL）
### 1. 学生登录功能
```
实现Java控制台学生登录功能（MySQL存储）：
前置条件：已创建Student表、Student实体类（SID、SName、College、Phone、Grade、Password）、StudentDAO及实现类、DBUtil、ScannerUtil、ValidatorUtil、LoginView类
输入信息：学生输入学号（SID，10位）、密码（默认"123456"）
输出结果：登录成功→跳转学生主菜单；失败→提示"学号不存在或密码错误"
实现步骤：
1. LoginView中调用ScannerUtil获取用户输入的SID和Password，通过ValidatorUtil校验：SID非空、长度=10位，Password非空
2. 调用StudentService的login(String sid, String password)方法，传入输入参数
3. StudentService层调用StudentDAO的selectBySidAndPwd(String sid, String password)方法：
   a. DAO层通过DBUtil.getConnection()获取MySQL连接
   b. 编写MySQL查询SQL：SELECT * FROM Student WHERE SID = ? AND Password = ?
   c. 创建PreparedStatement对象，设置参数：pstmt.setString(1, sid); pstmt.setString(2, password);
   d. 执行executeQuery()，获取ResultSet结果集
   e. 若结果集有下一行，封装为Student对象返回；否则返回null
4. StudentService判断：若返回非空Student对象→登录成功；否则返回失败
5. LoginView根据Service返回结果输出提示，成功则进入StudentView的学生主菜单
异常处理：
- 输入SID含非数字→提示"学号格式错误，需为10位数字"
- MySQL查询异常（如SQLSyntaxErrorException）→提示"登录失败，请重试"
```

### 2. 学生提交维修申请功能
```
实现Java控制台学生提交维修申请功能（MySQL存储）：
前置条件：已登录学生（持有当前SID）、Repair实体类（RepairID、Content、SubmitTime、Status、SubmitterID、HandlerID）、RepairDAO及实现类、RepairService、StudentView、DBUtil、ValidatorUtil类
输入信息：学生输入维修内容（Content，非空）
输出结果：提交成功→提示"维修申请提交成功，维修单号：XXX"；失败→提示"提交失败，请检查输入"
实现步骤：
1. StudentView展示"提交维修申请"菜单，调用ScannerUtil获取维修内容，通过ValidatorUtil校验：Content非空、长度≤200字
2. 自动封装Repair对象：SubmitterID=当前登录学生SID，SubmitTime默认当前时间（MySQL表字段自动填充），Status="待处理"，HandlerID=null，Content=输入内容
3. 调用RepairService的addRepair(Repair repair)方法
4. RepairService层调用RepairDAO的insertRepair(Repair repair)方法：
   a. DAO层获取MySQL连接
   b. 编写MySQL插入SQL：INSERT INTO Repair (Content, SubmitterID) VALUES (?, ?)
   c. 创建PreparedStatement对象，设置参数：pstmt.setString(1, repair.getContent()); pstmt.setString(2, repair.getSubmitterID());
   d. 执行executeUpdate()，通过pstmt.getGeneratedKeys()获取自增的RepairID
5. DAO层返回RepairID，RepairService将结果返回给StudentView
6. StudentView输出成功提示，包含维修单号
业务约束：维修内容不能为空，SubmitTime由MySQL自动填充当前时间，Status默认"待处理"
异常处理：
- 输入内容超长→提示"维修内容不能超过200字"
- MySQL插入异常（如SQLIntegrityConstraintViolationException）→提示"提交失败，数据库异常"
```

### 3. 学生评价已完成维修功能
```
实现Java控制台学生评价已完成维修功能（MySQL存储）：
前置条件：已登录学生、Repair表中存在状态为"已完成"的维修单、Evaluation实体类（EvalID、RepairID、Score、Comment）、EvaluationDAO及实现类、EvaluationService、StudentView、DBUtil类
输入信息：学生选择待评价维修单（输入RepairID）、输入评分（1-5分）、输入评价内容（可选，≤200字）
输出结果：评价成功→提示"评价提交成功，感谢您的反馈"；失败→提示"评价失败（维修单不存在/未完成/评分非法）"
实现步骤：
1. StudentView先调用RepairService的selectBySubmitterIdAndStatus(String submitterId, String status)方法，传入当前学生SID和"已完成"，查询待评价维修单列表并展示（含RepairID、Content、SubmitTime）
2. 调用ScannerUtil获取学生输入的RepairID、Score、Comment，通过ValidatorUtil校验：RepairID非空、Score在1-5之间、Comment长度≤200字
3. 调用EvaluationService的addEvaluation(Evaluation evaluation, String submitterId)方法
4. EvaluationService层执行校验：
   a. 调用RepairDAO的selectByIdAndSubmitterId(int repairId, String submitterId)，查询维修单是否存在、属于当前学生且状态为"已完成"
   b. 若校验不通过，返回失败信息；若通过，继续下一步
5. 调用EvaluationDAO的insertEvaluation(Evaluation evaluation)方法：
   a. DAO层获取MySQL连接
   b. 编写MySQL插入SQL：INSERT INTO Evaluation (RepairID, Score, Comment) VALUES (?, ?, ?)
   c. 创建PreparedStatement对象，设置参数：pstmt.setInt(1, evaluation.getRepairID()); pstmt.setInt(2, evaluation.getScore()); pstmt.setString(3, evaluation.getComment());
   d. 执行executeUpdate()，获取影响行数
6. 若影响行数≥1，返回成功；否则返回失败
7. StudentView输出对应提示
业务约束：仅能评价自身提交的、状态为"已完成"的维修单，评分必须为1-5分
异常处理：
- 输入RepairID非数字→提示"维修单号格式错误"
- MySQL插入异常→提示"评价提交失败，请重试"
```

### 4. 学生发布失物招领功能
```
实现Java控制台学生发布失物招领功能（MySQL存储）：
前置条件：已登录学生（持有SID）、LostItem实体类（ItemID、ItemName、Place、PublishTime、Status、PublisherID）、LostItemDAO及实现类、LostItemService、StudentView、DBUtil、ValidatorUtil类
输入信息：学生输入物品名称（ItemName）、丢失地点（Place）
输出结果：发布成功→提示"失物招领发布成功，物品ID：XXX"；失败→提示"发布失败，请检查输入"
实现步骤：
1. StudentView展示"发布失物招领"菜单，调用ScannerUtil获取ItemName和Place，通过ValidatorUtil校验：两者非空、长度≤50字
2. 自动封装LostItem对象：PublisherID=当前学生SID，PublishTime默认当前时间（MySQL自动填充），Status="未认领"，ItemName=输入名称，Place=输入地点
3. 调用LostItemService的addLostItem(LostItem lostItem)方法
4. LostItemService层调用LostItemDAO的insertLostItem(LostItem lostItem)方法：
   a. DAO层获取MySQL连接
   b. 编写MySQL插入SQL：INSERT INTO LostItem (ItemName, Place, PublisherID) VALUES (?, ?, ?)
   c. 创建PreparedStatement对象，设置参数：pstmt.setString(1, lostItem.getItemName()); pstmt.setString(2, lostItem.getPlace()); pstmt.setString(3, lostItem.getPublisherID());
   d. 执行executeUpdate()，通过getGeneratedKeys()获取自增的ItemID
5. DAO层返回ItemID，Service层将结果返回给StudentView
6. StudentView输出发布成功提示及物品ID
业务约束：物品名称和丢失地点不能为空，PublishTime由MySQL自动填充，Status默认"未认领"
异常处理：
- 输入内容超长→提示"物品名称/丢失地点不能超过50字"
- MySQL插入异常→提示"发布失败，数据库异常"
```

### 5. 学生提交场馆预约功能
```
实现Java控制台学生提交场馆预约功能（MySQL存储）：
前置条件：已登录学生（持有SID）、Venue实体类（VenueID、VenueName、Capacity、Location、IsAvailable）、Reservation实体类（ResID、VenueID、ReserverID、ResTime、Duration、AuditStatus）、VenueDAO、ReservationDAO及实现类、ReservationService、StudentView、DBUtil类
输入信息：学生选择场馆（先查看可预约场馆列表）、输入预约日期（ResTime，格式yyyy-MM-dd）、输入使用时长（Duration，≥1小时）
输出结果：提交成功→提示"预约申请已提交，待管理员审核"；失败→提示"预约失败（场馆不可用/日期格式错误/时长非法）"
实现步骤：
1. StudentView先调用VenueService的selectAvailableVenues()方法：
   a. VenueDAO执行MySQL查询：SELECT VenueID, VenueName, Capacity, Location FROM Venue WHERE IsAvailable = '是'
   b. 返回可预约场馆列表，StudentView按格式展示
2. 调用ScannerUtil获取学生输入的VenueID、ResTime、Duration，通过ValidatorUtil校验：
   a. VenueID非空且存在于场馆列表
   b. ResTime格式正确（yyyy-MM-dd）
   c. Duration≥1
3. 自动封装Reservation对象：ReserverID=当前学生SID，AuditStatus="待审核"，VenueID=输入场馆ID，ResTime=输入日期，Duration=输入时长
4. 调用ReservationService的addReservation(Reservation reservation)方法
5. ReservationService层先调用VenueDAO的selectById(int venueId)，验证场馆IsAvailable="是"
6. 验证通过后，调用ReservationDAO的insertReservation(Reservation reservation)方法：
   a. DAO层获取MySQL连接
   b. 编写MySQL插入SQL：INSERT INTO Reservation (VenueID, ReserverID, ResTime, Duration) VALUES (?, ?, ?, ?)
   c. 创建PreparedStatement对象，设置参数：pstmt.setInt(1, reservation.getVenueID()); pstmt.setString(2, reservation.getReserverID()); pstmt.setDate(3, java.sql.Date.valueOf(reservation.getResTime())); pstmt.setInt(4, reservation.getDuration());
   d. 执行executeUpdate()
7. 若执行成功，ReservationService返回成功；否则返回失败
8. StudentView输出对应提示
业务约束：仅能预约IsAvailable="是"的场馆，Duration≥1，AuditStatus默认"待审核"
异常处理：
- 日期格式错误→提示"预约日期格式错误，请输入yyyy-MM-dd"
- MySQL插入异常→提示"预约提交失败，请重试"
```

### 6. 学生查看个人预约记录功能
```
实现Java控制台学生查看个人预约记录功能（MySQL存储）：
前置条件：已登录学生（持有SID）、ReservationDAO、VenueDAO及实现类、ReservationService、StudentView、DBUtil类
输入信息：无（自动关联当前学生SID）
输出结果：控制台打印个人所有预约记录（预约ID、场馆名称、预约日期、时长、审核状态）；无记录→提示"暂无预约记录"
实现步骤：
1. StudentView调用ReservationService的selectByReserverId(String reserverId)方法，传入当前学生SID
2. ReservationService层调用ReservationDAO的selectByReserverId(String reserverId)方法：
   a. DAO层获取MySQL连接
   b. 编写MySQL查询SQL：SELECT ResID, VenueID, ResTime, Duration, AuditStatus FROM Reservation WHERE ReserverID = ?
   c. 设置参数并执行，获取Reservation列表
3. 遍历Reservation列表，对每个预约记录：
   a. 调用VenueDAO的selectById(int venueId)，获取场馆名称（VenueName）
   b. 封装整合数据：ResID、VenueName、ResTime、Duration、AuditStatus
4. ReservationService返回整合后的记录列表
5. StudentView按格式打印记录；若列表为空，提示"暂无预约记录"
异常处理：
- MySQL查询异常→提示"查询失败，请重试"
```

### 7. 学生校园卡账户查询功能
```
实现Java控制台学生校园卡账户查询功能（MySQL存储）：
前置条件：已登录学生（持有SID）、CampusCard实体类（CardID、UserID、UserType、Balance、Status）、CampusCardDAO及实现类、CampusCardService、StudentView、DBUtil类
输入信息：无（自动关联当前学生SID和UserType="student"）
输出结果：控制台打印校园卡信息（卡号、账户余额、当前状态）；无绑定校园卡→提示"未绑定校园卡"
实现步骤：
1. StudentView添加"校园卡账户查询"选项，用户选择后调用CampusCardService的selectByUserIdAndType(String userId, String userType)方法，传入当前学生SID和"student"
2. CampusCardService层调用CampusCardDAO的selectByUserIdAndType(String userId, String userType)方法：
   a. DAO层获取MySQL连接
   b. 编写MySQL查询SQL：SELECT CardID, Balance, Status FROM CampusCard WHERE UserID = ? AND UserType = ?
   c. 设置参数并执行，获取ResultSet
   d. 若有结果，封装为CampusCard对象返回；否则返回null
3. 若返回非空CampusCard对象，StudentView按格式打印："您的校园卡信息\n卡号：XXX\n账户余额：XXX元\n当前状态：XXX"
4. 若返回null，提示"未绑定校园卡"
异常处理：
- MySQL查询异常→提示"查询校园卡账户失败，请重试"
```

### 8. 学生校园卡挂失功能
```
实现Java控制台学生校园卡挂失功能（MySQL存储）：
前置条件：已登录学生（持有SID）、CampusCardDAO及实现类、CampusCardService、StudentView、DBUtil、ScannerUtil类
输入信息：学生输入"Y"确认挂失，其他输入取消操作
输出结果：挂失成功→提示"校园卡已挂失，状态更新为：挂失"；失败→提示"挂失失败（已挂失/未绑定校园卡）"
实现步骤：
1. StudentView添加"挂失校园卡"选项，用户选择后先调用CampusCardService的selectByUserIdAndType(sid, "student")，查询当前学生校园卡信息
2. 若返回null→提示"未绑定校园卡"，终止操作
3. 若返回CampusCard对象且Status="挂失"→提示"您的校园卡已处于挂失状态"，终止操作
4. 若Status="正常"，提示"确认挂失校园卡？（Y/N）"，调用ScannerUtil获取用户输入
5. 若输入"Y"，调用CampusCardService的updateStatusToLost(String userId, String userType)方法
6. CampusCardService层调用CampusCardDAO的updateStatus(String userId, String userType, String status)方法：
   a. DAO层获取MySQL连接
   b. 编写MySQL更新SQL：UPDATE CampusCard SET Status = ? WHERE UserID = ? AND UserType = ?
   c. 设置参数：pstmt.setString(1, "挂失"); pstmt.setString(2, userId); pstmt.setString(3, userType);
   d. 执行executeUpdate()，获取影响行数
7. 若影响行数≥1→返回成功；否则返回失败
8. StudentView输出对应提示
业务约束：仅状态为"正常"的校园卡可执行挂失操作
异常处理：
- MySQL更新异常→提示"挂失操作失败，请重试"
```

## 五、管理员端核心功能实现话术（适配MySQL）
### 1. 管理员登录功能
```
实现Java控制台管理员登录功能（MySQL存储）：
前置条件：已创建Staff表、Staff实体类（EID、EName、Dept、Phone、Position、Password）、StaffDAO及实现类、DBUtil、ScannerUtil、ValidatorUtil、LoginView类
输入信息：管理员输入工号（EID，8位）、默认初始密码（"admin123"）
输出结果：登录成功→跳转管理员主菜单；失败→提示"工号不存在或密码错误"
实现步骤：
1. LoginView调用ScannerUtil获取输入的EID和Password，通过ValidatorUtil校验：EID非空、长度=8位，Password非空
2. 调用AdminService的login(String eid, String password)方法，传入输入参数
3. AdminService层调用StaffDAO的selectByEidAndPwd(String eid, String password)方法：
   a. DAO层获取MySQL连接
   b. 编写MySQL查询SQL：SELECT * FROM Staff WHERE EID = ? AND Password = ?
   c. 创建PreparedStatement对象，设置参数并执行
   d. 若结果集有数据，封装为Staff对象返回；否则返回null
4. AdminService判断：若返回非空→登录成功；否则返回失败
5. LoginView输出提示，成功则进入AdminView的管理员主菜单
异常处理：
- 工号含非数字→提示"工号格式错误，需为8位数字"
- MySQL查询异常→提示"登录失败，请重试"
```

### 2. 管理员审核场馆预约功能
```
实现Java控制台管理员审核场馆预约功能（MySQL存储）：
前置条件：已登录管理员、Reservation实体类、ReservationDAO及实现类、ReservationService、AdminView、DBUtil、ScannerUtil类
输入信息：管理员选择待审核预约（输入ResID）、选择审核结果（"通过"/"拒绝"）
输出结果：审核成功→提示"预约审核已完成，状态更新为：XXX"；失败→提示"审核失败（预约ID不存在/状态非待审核）"
实现步骤：
1. AdminView调用ReservationService的selectPendingReservations()方法：
   a. ReservationDAO执行MySQL查询：SELECT ResID, VenueID, ReserverID, ResTime, Duration FROM Reservation WHERE AuditStatus = '待审核'
   b. 返回待审核列表，AdminView按格式展示
2. 调用ScannerUtil获取管理员输入的ResID和审核结果，通过ValidatorUtil校验：ResID非空、审核结果为"通过"或"拒绝"
3. 转换审核状态："通过"→"已通过"；"拒绝"→"已拒绝"
4. 调用ReservationService的updateAuditStatus(int resId, String auditStatus)方法
5. ReservationService层先调用ReservationDAO的selectById(int resId)：
   a. 若查询结果为null→返回"预约ID不存在"
   b. 若AuditStatus≠"待审核"→返回"状态非待审核"
   c. 若校验通过，继续下一步
6. 调用ReservationDAO的updateAuditStatus(int resId, String auditStatus)方法：
   a. DAO层获取MySQL连接
   b. 编写MySQL更新SQL：UPDATE Reservation SET AuditStatus = ? WHERE ResID = ?
   c. 设置参数并执行，获取影响行数
7. 若审核结果为"已通过"，联动调用VenueDAO的updateIsAvailable(int venueId, String isAvailable)：
   a. SQL：UPDATE Venue SET IsAvailable = '否' WHERE VenueID = ?
   b. 设置参数为预约对应的VenueID
8. 若影响行数≥1→返回成功；否则返回失败
9. AdminView输出对应提示
业务约束：仅能审核状态为"待审核"的预约，审核后状态不可修改
异常处理：
- 输入ResID非数字→提示"预约ID格式错误"
- MySQL更新异常→提示"审核操作失败，请重试"
```

### 3. 管理员分配维修任务功能
```
实现Java控制台管理员分配维修任务功能（MySQL存储）：
前置条件：已登录管理员、Repair实体类、Staff实体类、RepairDAO、StaffDAO及实现类、RepairService、AdminView、DBUtil、ScannerUtil类
输入信息：管理员选择待处理维修单（输入RepairID）、输入处理人工号（EID）
输出结果：分配成功→提示"维修任务已分配给XXX（工号：XXX），状态更新为处理中"；失败→提示"分配失败（维修单ID不存在/工号不存在/处理人非后勤部门/维修单状态非待处理）"
实现步骤：
1. AdminView调用RepairService的selectPendingRepairs()方法：
   a. RepairDAO执行MySQL查询：SELECT RepairID, Content, SubmitterID, SubmitTime FROM Repair WHERE Status = '待处理'
   b. 返回待处理列表，AdminView展示
2. 调用ScannerUtil获取管理员输入的RepairID和EID，通过ValidatorUtil校验两者非空
3. 调用RepairService的assignRepairTask(int repairId, String handlerId)方法
4. RepairService层执行双重校验：
   a. 调用RepairDAO的selectById(int repairId)→若为null或Status≠"待处理"→返回失败
   b. 调用StaffDAO的selectByEidAndDept(String eid, String dept)→查询EID是否存在且Dept="后勤管理处"，若为null→返回失败
5. 校验通过后，调用RepairDAO的updateHandlerAndStatus(int repairId, String handlerId, String status)方法：
   a. DAO层获取MySQL连接
   b. 编写MySQL更新SQL：UPDATE Repair SET HandlerID = ?, Status = '处理中' WHERE RepairID = ?
   c. 设置参数：pstmt.setString(1, handlerId); pstmt.setInt(2, repairId);
   d. 执行executeUpdate()，获取影响行数
6. 调用StaffDAO的selectByEid(handlerId)，获取处理人姓名（EName）
7. 若影响行数≥1→返回成功（含处理人姓名）；否则返回失败
8. AdminView输出分配成功提示，包含处理人姓名和工号
业务约束：仅能为"待处理"的维修单分配任务，处理人必须是后勤管理处的教职工
异常处理：
- MySQL更新异常→提示"任务分配失败，请重试"
```

### 4. 管理员更新维修状态为已完成功能
```
实现Java控制台管理员更新维修状态功能（MySQL存储）：
前置条件：已登录管理员、RepairDAO及实现类、RepairService、AdminView、DBUtil、ScannerUtil类
输入信息：管理员输入维修单ID（RepairID）
输出结果：更新成功→提示"维修单XXX状态已更新为已完成"；失败→提示"更新失败（维修单ID不存在/状态非处理中）"
实现步骤：
1. AdminView调用RepairService的selectHandlingRepairs()方法：
   a. RepairDAO执行MySQL查询：SELECT RepairID, Content, HandlerID, SubmitTime FROM Repair WHERE Status = '处理中'
   b. 返回列表并展示
2. 调用ScannerUtil获取管理员输入的RepairID，通过ValidatorUtil校验非空
3. 调用RepairService的updateToCompleted(int repairId)方法
4. RepairService层调用RepairDAO的selectById(int repairId)：
   a. 若为null→返回"维修单ID不存在"
   b. 若Status≠"处理中"→返回"状态非处理中"
   c. 校验通过，继续下一步
5. 调用RepairDAO的updateStatus(int repairId, String status)方法：
   a. DAO层获取MySQL连接
   b. 编写MySQL更新SQL：UPDATE Repair SET Status = '已完成' WHERE RepairID = ?
   c. 设置参数并执行，获取影响行数
6. 若影响行数≥1→返回成功；否则返回失败
7. AdminView输出对应提示
业务约束：仅能将"处理中"的维修单更新为"已完成"
异常处理：
- 输入RepairID非数字→提示"维修单ID格式错误"
- MySQL更新异常→提示"状态更新失败，请重试"
```

### 5. 管理员按学院统计报修数量功能
```
实现Java控制台管理员按学院统计报修数量功能（MySQL存储）：
前置条件：已登录管理员、StudentDAO、RepairDAO及实现类、AdminService、AdminView、DBUtil类
输入信息：无
输出结果：控制台打印各学院报修数量统计（学院名称、报修次数），按报修次数降序排列；无数据→提示"暂无报修数据"
实现步骤：
1. AdminView调用AdminService的statRepairByCollege()方法
2. AdminService层执行统计逻辑：
   a. 调用RepairDAO的selectAll()方法，获取所有维修单列表（含SubmitterID）
   b. 遍历维修单，对每个SubmitterID，调用StudentDAO的selectCollegeBySid(String sid)→获取对应学院（College）
   c. 使用Map<String, Integer>统计各学院报修次数（key=学院名称，value=次数）
3. 将Map转换为List<Map.Entry<String, Integer>>，按value（次数）降序排序
4. AdminService返回排序后的统计列表
5. AdminView按格式打印：每行展示"学院名称：XXX  报修次数：XXX"
6. 若列表为空，提示"暂无报修数据"
异常处理：
- MySQL查询异常→提示"统计失败，请重试"
```

### 6. 管理员查看维修评价统计功能
```
实现Java控制台管理员查看维修评价统计功能（MySQL存储）：
前置条件：已登录管理员、EvaluationDAO、StaffDAO、RepairDAO及实现类、AdminService、AdminView、DBUtil类
输入信息：无
输出结果：控制台打印各处理人维修评价统计（工号、姓名、处理维修数、平均评分）；无数据→提示"暂无维修评价数据"
实现步骤：
1. AdminView调用AdminService的statRepairEvaluation()方法
2. AdminService层执行统计逻辑：
   a. 调用EvaluationDAO的selectAll()方法，获取所有评价列表（含RepairID、Score）
   b. 遍历每个评价，调用RepairDAO的selectHandlerIdByRepairId(int repairId)→获取处理人EID（HandlerID）
   c. 使用Map<String, List<Integer>>存储各处理人的评分：key=EID，value=评分列表
3. 遍历Map，对每个EID：
   a. 调用StaffDAO的selectByEid(String eid)→获取工号（EID）和姓名（EName）
   b. 计算处理维修数=评分列表大小，平均评分=列表元素总和/大小（保留1位小数）
   c. 封装为EvaluationStat对象（eid、eName、repairCount、avgScore）
4. 收集所有EvaluationStat对象为列表，AdminService返回该列表
5. AdminView按格式打印统计结果；若列表为空，提示"暂无维修评价数据"
业务约束：若某处理人无评价，平均评分显示为0.0
异常处理：
- MySQL查询异常→提示"统计失败，请重试"
```

### 7. 管理员校园卡挂失恢复功能
```
实现Java控制台管理员校园卡挂失恢复功能（MySQL存储）：
前置条件：已登录管理员、CampusCardDAO及实现类、CampusCardService、AdminView、DBUtil、ScannerUtil类
输入信息：管理员输入待恢复的校园卡卡号（CardID，10位）
输出结果：恢复成功→提示"校园卡XXX状态已恢复为：正常"；失败→提示"恢复失败（卡号不存在/当前状态为正常）"
实现步骤：
1. AdminView添加"恢复挂失校园卡"选项，调用CampusCardService的selectLostCards()方法：
   a. CampusCardDAO执行MySQL查询：SELECT CardID, UserID, UserType FROM CampusCard WHERE Status = '挂失'
   b. 返回挂失卡列表，AdminView展示
2. 调用ScannerUtil获取管理员输入的CardID，通过ValidatorUtil校验：CardID非空、长度=12位
3. 调用CampusCardService的recoverCard(String cardId)方法
4. CampusCardService层调用CampusCardDAO的selectByCardId(String cardId)：
   a. 若查询结果为null→返回"卡号不存在"
   b. 若Status="正常"→返回"当前状态为正常"
   c. 若Status="挂失"→校验通过，继续下一步
5. 调用CampusCardDAO的updateStatusByCardId(String cardId, String status)方法：
   a. DAO层获取MySQL连接
   b. 编写MySQL更新SQL：UPDATE CampusCard SET Status = '正常' WHERE CardID = ?
   c. 设置参数并执行，获取影响行数
6. 若影响行数≥1→返回成功；否则返回失败
7. AdminView输出对应提示
业务约束：仅能恢复状态为"挂失"的校园卡
异常处理：
- 输入CardID格式错误→提示"校园卡卡号需为10位"
- MySQL更新异常→提示"恢复操作失败，请重试"
```