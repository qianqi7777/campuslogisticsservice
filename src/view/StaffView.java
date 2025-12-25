package src.view;

import src.entity.Staff;
import java.util.Scanner;

/**
 * 职工控制台视图
 * 提供职工相关的操作菜单
 */
public class StaffView {
    private Staff staff;
    private Scanner scanner = new Scanner(System.in);

    public StaffView(Staff staff) {
        this.staff = staff;
    }

    public void show() {
        while (true) {
            System.out.println("\n=== 职工主菜单 (" + staff.getEName() + ") ===");
            System.out.println("1. 报修服务 (处理报修/更新状态/查看评价)");
            System.out.println("2. 预约服务");
            System.out.println("3. 校园卡服务 (查询/充值/挂失)");
            System.out.println("0. 注销登录");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    showRepairMenu();
                    break;
                case "2":
                    showReservationMenu();
                    break;
                case "3":
                    showCardMenu();
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }

    private void showRepairMenu() {
        System.out.println("\n--- 报修服务 ---");
        System.out.println("1. 处理报修单");
        System.out.println("2. 更新维修状态");
        System.out.println("3. 查看评价");
        System.out.println("0. 返回");
        // TODO: 读取输入并调用 RepairService
    }

    private void showReservationMenu() {
        System.out.println("\n--- 预约服务 ---");
        System.out.println("1. 管理场馆信息");
        System.out.println("2. 预约场馆");
        System.out.println("0. 返回");
        // TODO: 读取输入并调用 ReservationService / VenueService
    }

    private void showCardMenu() {
        System.out.println("\n--- 校园卡服务 ---");
        System.out.println("1. 查询余额");
        System.out.println("2. 充值");
        System.out.println("3. 挂失");
        System.out.println("0. 返回");
        // TODO: 读取输入并调用 CampusCardService
    }
}
