package src;

import src.util.DatabaseInitializer;
import src.view.LoginView;

public class Main {
    public static void main(String[] args) {
        // 1. 初始化数据库
        DatabaseInitializer.initDatabase();

        // 2. 启动登录界面
        System.out.println("欢迎使用校园服务管理系统！");
        new LoginView().show();
    }
}
