//package src.util;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
///**
// * 简单的 JDBC 连接测试。
// *
// * 用法：
// *   java DBConnectionTest <jdbcUrl> <username> <password> [driverClass]
// *
// * 示例：
// *   H2（内存中）：
// *     java -cp .;h2.jar DBConnectionTest jdbc:h2:mem:testdb sa "" org.h2.Driver
// *
// *   MySQL：
// *     java -cp .;mysql-connector-j.jar DBConnectionTest jdbc:mysql://localhost:3306/test user pass com.mysql.cj.jdbc.Driver
// */
//public class DBConnectionTest {
//    // 简单封装的打印方法，方便后续统一修改输出行为
//    private static void println(String msg) {
//        System.out.println(msg);
//    }
//
//    public static void main(String[] args) {
//        // 说明：如果未提供参数，则使用默认 MySQL 本地连接信息。
//        // 支持的命令行参数（按顺序）：
//        //   1: jdbcUrl
//        //   2: username
//        //   3: password
//        //   4: driverClass （可选）
//        //
//        // 如果只提供部分参数，剩余参数将使用默认值。
//        String defaultUrl = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC";
//        String defaultUser = "root";
//        String defaultPass = "12345678";
//        String defaultDriver = "com.mysql.cj.jdbc.Driver";
//
//        // 使用默认值，然后根据传入的 args 逐项覆盖
//        String url = defaultUrl;
//        String user = defaultUser;
//        String pass = defaultPass;
//        String driverClass = defaultDriver;
//
//        if (args.length >= 1 && args[0] != null && !args[0].isEmpty()) {
//            url = args[0];
//        }
//        if (args.length >= 2 && args[1] != null && !args[1].isEmpty()) {
//            user = args[1];
//        }
//        if (args.length >= 3 && args[2] != null && !args[2].isEmpty()) {
//            pass = args[2];
//        }
//        if (args.length >= 4 && args[3] != null && !args[3].isEmpty()) {
//            driverClass = args[3];
//        }
//
//        // 打印最终使用的参数，便于确认
//        println("Using JDBC URL: " + url);
//        println("Using username: " + user);
//        // 出于安全考虑，不直接打印密码明文，但为了调试可选择打印（此处仅提示长度）
//        println("Password length: " + (pass == null ? 0 : pass.length()));
//        println("Driver class: " + driverClass);
//
//        // 可选：显式加载 JDBC 驱动类（有些环境下不需要，但显式加载可以帮助诊断）
//        if (driverClass != null && !driverClass.isEmpty()) {
//            try {
//                Class.forName(driverClass);
//                println("Loaded driver: " + driverClass);
//            } catch (ClassNotFoundException e) {
//                // 驱动无法找到时打印错误并退出
//                println("Driver class not found: " + driverClass);
//                // **重要提示**: 如果看到这个错误，通常意味着 MySQL (或其他数据库) 的 JDBC 驱动 JAR 文件
//                // 没有被添加到项目的类路径 (classpath) 中。
//                // 在 IntelliJ IDEA 中，请通过 "Project Structure" -> "Libraries" 添加驱动 JAR 文件。
//                // 如果通过命令行运行，请确保使用 -cp 或 -classpath 参数包含了驱动 JAR 文件的路径。
//                e.printStackTrace();
//                return;
//            }
//        }
//
//        // 步骤 1: 确保数据库存在。先连接到服务器，然后创建数据库。
//        try {
//            // 注意：这里的 URL 不包含数据库名 'test'
//            String serverUrl = "jdbc:mysql://localhost:3306?useSSL=false&serverTimezone=UTC";
//            println("Checking if database 'test' exists...");
//            try (Connection conn = DriverManager.getConnection(serverUrl, user, pass);
//                 Statement st = conn.createStatement()) {
//                st.executeUpdate("CREATE DATABASE IF NOT EXISTS test");
//                println("Database 'test' is ready.");
//            }
//        } catch (SQLException e) {
//            println("Failed to create database 'test'. Please check MySQL permissions.");
//            println("Message: " + e.getMessage());
//            e.printStackTrace();
//            return; // 如果创建数据库失败，则终止程序
//        }
//
//        // 步骤 2: 连接到 'test' 数据库并执行测试
//        println("Connecting to: " + url);
//        // 使用 try-with-resources 确保 Connection 会被自动关闭（避免资源泄露）
//        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
//            println("Connection successful.");
//            // 简单的 smoke test：创建表、插入一行、查询该行（兼容 H2/MySQL/SQLite 等）
//            try (Statement st = conn.createStatement()) {
//                // 创建表（如果不存在），使用相对可移植的 SQL
//                String createSql = "CREATE TABLE IF NOT EXISTS demo_conn_test (id INT PRIMARY KEY, name VARCHAR(100))";
//                st.executeUpdate(createSql);
//
//                // 尝试插入一条记录；若插入冲突（主键重复），捕获异常并忽略
//                try {
//                    st.executeUpdate("INSERT INTO demo_conn_test (id, name) VALUES (1, 'hello')");
//                } catch (SQLException ignored) {
//                    // 忽略重复键或其他可被接受的错误，让测试继续
//                }
//
//                // 查询刚才插入的记录以验证读取能力
//                try (ResultSet rs = st.executeQuery("SELECT id, name FROM demo_conn_test WHERE id = 1")) {
//                    if (rs.next()) {
//                        int id = rs.getInt(1);
//                        String name = rs.getString(2);
//                        println("Query OK: id=" + id + ", name=" + name);
//                    } else {
//                        println("Query returned no rows.");
//                    }
//                }
//            }
//            println("Test completed.");
//        } catch (SQLException e) {
//            // 连接或 SQL 操作发生异常时打印堆栈，便于定位问题
//            println("Connection failed:");
//            // 打印更详细的 SQL 异常信息，帮助诊断问题原因
//            println("Message: " + e.getMessage());
//            println("SQL State: " + e.getSQLState());
//            println("Error Code: " + e.getErrorCode());
//            e.printStackTrace();
//        }
//    }
//}
