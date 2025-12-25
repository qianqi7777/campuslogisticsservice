package src.view;

import src.entity.Staff;
import java.util.Scanner;

/**
 * 管理员控制台视图：AdminView
 * 提供简单的命令行菜单，供管理员执行各类管理操作（当前部分功能为占位）
 */
public class AdminView {
    private Staff staff;
    private Scanner scanner = new Scanner(System.in);

    public AdminView(Staff staff) {
        this.staff = staff;
    }

    /**
     * 显示管理员主菜单并响应输入
     * 当前实现为占位，真实功能应调用 AdminService 中具体业务方法
     */
    public void show() {
        while (true) {
            // 打印菜单选项
            System.out.println("\n=== 管理员主菜单 (" + staff.getEName() + ") ===");
            System.out.println("1. 审核场馆预约");
            System.out.println("2. 分配维修任务");
            System.out.println("3. 更新维修状态为已完成");
            System.out.println("4. 按学院统计报修数量");
            System.out.println("5. 查看维修评价统计");
            System.out.println("6. 校园卡挂失/恢复");
            System.out.println("0. 注销登录");
            System.out.print("请选 择：");

            // 读取用户输入
            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            // 占位提示，后续应调用 Service 方法
            System.out.println("功能 [" + choice + "] 尚未实现具体逻辑，仅为示例菜单。");
        }
    }
}
