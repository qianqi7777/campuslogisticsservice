package src;

import src.util.DatabaseInitializer;
import src.view.LoginView;

/**
 * 主程序入口类
 * 负责初始化数据库并启动登录界面
 */
public class Main {
    /**
     * 程序主入口方法
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 1. 初始化数据库
        // 调用工具类检查并创建数据库及表结构，确保运行环境就绪
        DatabaseInitializer.initDatabase();

        // 2. 启动登录界面
        System.out.println("欢迎使用校园服务管理系统！");
        // 创建登录视图对象并调用 show() 方法显示主菜单
        new LoginView().show();
    }
}


