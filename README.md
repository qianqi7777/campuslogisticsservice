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