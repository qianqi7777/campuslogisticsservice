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
            System.out.println("1. 账号管理 (设置/注销用户)");
            System.out.println("2. 报修服务 (统计/分配)");
            System.out.println("3. 预约服务 (审核)");
            System.out.println("4. 校园卡服务 (挂失/恢复)");
            System.out.println("0. 注销登录");
            System.out.print("请选 择：");

            // 读取用户输入
            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    showAccountMenu();
                    break;
                case "2":
                    showRepairMenu();
                    break;
                case "3":
                    showReservationMenu();
                    break;
                case "4":
                    showCardMenu();
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }

    private void showAccountMenu() {
        System.out.println("\n--- 账号管理 ---");
        System.out.println("1. 设置用户");
        System.out.println("2. 注销用户");
        System.out.println("0. 返回");
        // TODO: 调用 UserService
    }

    private void showRepairMenu() {
        System.out.println("\n--- 报修服务 ---");
        System.out.println("1. 统计报修数量");
        System.out.println("2. 分配维修任务");
        System.out.println("0. 返回");
        // TODO: 调用 RepairService
    }

    private void showReservationMenu() {
        System.out.println("\n--- 预约服务 ---");
        System.out.println("1. 审核预约申请");
        System.out.println("0. 返回");
        // TODO: 调用 ReservationService
    }

    private void showCardMenu() {
        System.out.println("\n--- 校园卡服务 ---");
        System.out.println("1. 处理挂失/恢复");
        System.out.println("0. 返回");
        // TODO: 调用 CampusCardService
    }
}
