package src.view;

import src.entity.Student;
import src.service.RepairService;
import src.service.ReservationService;
import src.service.CampusCardService;
import src.entity.Repair;
import src.entity.Reservation;
import java.sql.Timestamp;
import java.util.Scanner;

/**
 * 学生控制台视图：StudentView
 * 提供学生用户可以执行的操作菜单（当前为占位示例）
 */
public class StudentView {
    private Student student;
    private Scanner scanner = new Scanner(System.in);
    private RepairService repairService = new RepairService();
    private ReservationService reservationService = new ReservationService();
    private CampusCardService cardService = new CampusCardService();

    public StudentView(Student student) {
        this.student = student;
    }

    /**
     * 显示学生主菜单并响应输入（实际功能由 Service 层实现并在此调用）
     */
    public void show() {
        while (true) {
            // 打印学生菜单
            System.out.println("\n=== 学生主菜单 (" + student.getSName() + ") ===");
            System.out.println("1. 报修服务 (提交/评价)");
            System.out.println("2. 预约服务 (查询/申请)");
            System.out.println("3. 校园卡服务 (查询/充值/挂失)");
            System.out.println("0. 注销登录");
            System.out.print("请选择：");

            // 读取用户输入
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
        while (true) {
            System.out.println("\n--- 报修服务 ---");
            System.out.println("1. 提交报修单");
            System.out.println("2. 评价维修");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    System.out.print("请输入报修内容：");
                    String content = scanner.nextLine();
                    Repair r = new Repair();
                    r.setContent(content);
                    r.setSubmitterID(student.getSid());
                    r.setStatus("待处理");
                    int repairId = repairService.submitRepair(r);
                    System.out.println("报修单已提交，您的报修单ID为：" + repairId);
                    break;
                case "2":
                    System.out.print("请输入维修单ID：");
                    int rid = Integer.parseInt(scanner.nextLine());
                    System.out.print("请输入评分 (1-5)：");
                    int score = Integer.parseInt(scanner.nextLine());
                    System.out.print("请输入评价内容：");
                    String comment = scanner.nextLine();
                    repairService.evaluateRepair(rid, comment, score);
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }

    private void showReservationMenu() {
        while (true) {
            System.out.println("\n--- 预约服务 ---");
            System.out.println("1. 查询场地状态");
            System.out.println("2. 提交预约申请");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    System.out.print("请输入场地ID：");
                    int vid = Integer.parseInt(scanner.nextLine());
                    reservationService.checkVenueStatus(vid);
                    break;
                case "2":
                    System.out.print("请输入场地ID：");
                    int venueId = Integer.parseInt(scanner.nextLine());
                    System.out.print("请输入预约时间 (yyyy-MM-dd HH:mm:ss)：");
                    String dateStr = scanner.nextLine();
                    System.out.print("请输入时长 (小时)：");
                    int duration = Integer.parseInt(scanner.nextLine());

                    Reservation r = new Reservation();
                    r.setVenueID(venueId);
                    r.setReserverID(student.getSid());
                    r.setResTime(Timestamp.valueOf(dateStr));
                    r.setDuration(duration);
                    reservationService.submitReservation(r);
                    System.out.println("预约申请已提交，等待审核。");
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }

    private void showCardMenu() {
        while (true) {
            System.out.println("\n--- 校园卡服务 ---");
            System.out.println("1. 查询余额");
            System.out.println("2. 充值");
            System.out.println("3. 挂失");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    cardService.getCardInfo(student.getSid());
                    break;
                case "2":
                    System.out.print("请输入卡号：");
                    String cid = scanner.nextLine();
                    System.out.print("请输入金额：");
                    java.math.BigDecimal amount = new java.math.BigDecimal(scanner.nextLine());
                    cardService.recharge(cid, amount);
                    break;
                case "3":
                    System.out.print("请输入卡号：");
                    String cid2 = scanner.nextLine();
                    cardService.reportLoss(cid2);
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }
}
