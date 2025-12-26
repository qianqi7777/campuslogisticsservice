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
        // 进入菜单时自动清理过期预约
        reservationService.cleanExpiredReservations();

        while (true) {
            System.out.println("\n--- 预约服务 ---");
            System.out.println("1. 查看可用场地");
            System.out.println("2. 查询场地状态 (指定ID)");
            System.out.println("3. 提交预约申请");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    java.util.List<src.entity.Venue> venues = reservationService.getAvailableVenues();
                    System.out.println("--- 可用场地列表 ---");
                    if (venues.isEmpty()) {
                        System.out.println("当前没有可用场地。");
                    } else {
                        for (src.entity.Venue v : venues) {
                            System.out.printf("ID: %d, 名称: %s, 容量: %d, 位置: %s\n",
                                    v.getVenueID(), v.getVenueName(), v.getCapacity(), v.getLocation());
                        }
                    }
                    break;
                case "2":
                    System.out.print("请输入场地ID：");
                    try {
                        int vid = Integer.parseInt(scanner.nextLine());
                        reservationService.checkVenueStatus(vid);
                    } catch (NumberFormatException e) {
                        System.out.println("输入格式错误。");
                    }
                    break;
                case "3":
                    System.out.print("请输入场地ID：");
                    int venueId;
                    try {
                        venueId = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("ID格式错误。");
                        break;
                    }

                    // 显示当前时间作为样例
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String nowStr = sdf.format(new java.util.Date());
                    System.out.println("当前时间: " + nowStr);
                    System.out.print("请输入预约时间 (格式: yyyy-MM-dd HH:mm:ss)：");
                    String dateStr = scanner.nextLine();

                    System.out.print("请输入时长 (小时)：");
                    int duration;
                    try {
                        duration = Integer.parseInt(scanner.nextLine());
                        if (duration <= 0) {
                            System.out.println("时长必须大于0。");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("时长格式错误。");
                        break;
                    }

                    try {
                        Reservation r = new Reservation();
                        r.setVenueID(venueId);
                        r.setReserverID(student.getSid());
                        r.setResTime(Timestamp.valueOf(dateStr));
                        r.setDuration(duration);

                        String result = reservationService.submitReservationWithCheck(r);
                        System.out.println(result);
                    } catch (IllegalArgumentException e) {
                        System.out.println("时间格式错误，请参考样例格式。");
                    }
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
            System.out.println("4. 解除挂失");
            System.out.println("5. 注册校园卡");
            System.out.println("0. 返回");
            System.out.print("请选择：");

            String choice = scanner.nextLine();
            if ("0".equals(choice)) return;

            switch (choice) {
                case "1":
                    cardService.getCardInfo(student.getSid(), "student");
                    break;
                case "2":
                    System.out.print("请输入卡号 (回车默认充值本卡)：");
                    String cid = scanner.nextLine();
                    if (cid.trim().isEmpty()) {
                        src.entity.CampusCard myCard = cardService.getCardInfo(student.getSid(), "student");
                        if (myCard != null) {
                            cid = myCard.getCardID();
                        } else {
                            // getCardInfo 已经打印了未找到信息
                            break;
                        }
                    }
                    System.out.print("请输入金额：");
                    try {
                        java.math.BigDecimal amount = new java.math.BigDecimal(scanner.nextLine());
                        cardService.recharge(cid, amount);
                    } catch (NumberFormatException e) {
                        System.out.println("金额格式错误");
                    }
                    break;
                case "3":
                    System.out.println("正在提交挂失请求...");
                    cardService.reportLoss(student.getSid(), "student");
                    break;
                case "4":
                    System.out.println("正在解除挂失...");
                    cardService.cancelLoss(student.getSid(), "student");
                    break;
                case "5":
                    System.out.print("请输入新卡号：");
                    String newCid = scanner.nextLine();
                    if (newCid.trim().isEmpty()) {
                        System.out.println("卡号不能为空");
                        break;
                    }
                    src.entity.CampusCard newCard = new src.entity.CampusCard();
                    newCard.setCardID(newCid);
                    newCard.setUserID(student.getSid());
                    newCard.setUserType("student");
                    newCard.setBalance(java.math.BigDecimal.ZERO);
                    newCard.setStatus("正常");
                    if (cardService.addCard(newCard)) {
                        System.out.println("校园卡注册成功！");
                    } else {
                        System.out.println("校园卡注册失败（可能卡号已存在或您已绑定校园卡）。");
                    }
                    break;
                default:
                    System.out.println("无效选项");
            }
        }
    }
}
