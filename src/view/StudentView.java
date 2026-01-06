package src.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import src.entity.Student;
import src.entity.Repair;
import src.entity.Reservation;
import src.entity.Venue;
import src.entity.CampusCard;
import src.service.RepairService;
import src.service.ReservationService;
import src.service.CampusCardService;

/**
 * 学生控制台视图：Swing图形化界面
 */
public class StudentView extends JFrame {
    private Student student;
    private RepairService repairService = new RepairService();
    private ReservationService reservationService = new ReservationService();
    private CampusCardService cardService = new CampusCardService();

    private DefaultTableModel repairTableModel;
    private DefaultTableModel reservationTableModel;
    private DefaultTableModel venueTableModel;
    private JLabel cardInfoLabel;

    public StudentView(Student student) {
        this.student = student;
        setTitle("学生服务系统 - " + student.getSName());
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("主页", createHomePanel());
        tabbedPane.addTab("报修服务", createRepairPanel());
        tabbedPane.addTab("场地预约", createReservationPanel());
        tabbedPane.addTab("校园卡", createCardPanel());

        this.add(tabbedPane);
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'><h2>欢迎同学: " + student.getSName() + "</h2><p>学号: " + student.getSid() + "</p><p>学院: " + student.getCollege() + "</p></div></html>", SwingConstants.CENTER);
        panel.add(welcomeLabel, BorderLayout.CENTER);

        JButton logoutBtn = new JButton("注销登录");
        logoutBtn.addActionListener(e -> {
            new LoginView().setVisible(true);
            this.dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(logoutBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }

    // --- Repair Panel ---
    private JPanel createRepairPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Repair List
        String[] columns = {"维修单ID", "内容", "提交时间", "状态", "评分", "评价"};
        repairTableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(repairTableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Tools
        JPanel tools = new JPanel();
        JButton refreshBtn = new JButton("刷新我的报修");
        JButton submitBtn = new JButton("提交报修");
        JButton evalBtn = new JButton("评价");

        refreshBtn.addActionListener(e -> loadRepairData());
        submitBtn.addActionListener(e -> showSubmitRepairDialog());
        evalBtn.addActionListener(e -> showEvaluateDialog(table));

        tools.add(refreshBtn);
        tools.add(submitBtn);
        tools.add(evalBtn);
        panel.add(tools, BorderLayout.NORTH);

        loadRepairData();
        return panel;
    }

    private void loadRepairData() {
        repairTableModel.setRowCount(0);
        List<Repair> list = repairService.getRepairsByStudent(student.getSid());
        for (Repair r : list) {
            // Need to fetch evaluation if exists, currently just showing empty or basic info
            // Since evaluation is stored separately, we might not have it here easily without querying.
            // For simplicity, we just list the repair info. Evaluation can be shown if we enhanced the DAO/Service to join tables or query.
            // But I'll leave score/comment empty in the table for now or implement a quick check if needed.
            // RepairService has getEvaluation(repairId).
            src.entity.Evaluation eval = repairService.getEvaluation(r.getRepairID());
            String score = (eval != null) ? String.valueOf(eval.getScore()) : "-";
            String comment = (eval != null) ? eval.getComment() : "-";

            repairTableModel.addRow(new Object[]{
                r.getRepairID(), r.getContent(), r.getSubmitTime(), r.getStatus(), score, comment
            });
        }
    }

    private void showSubmitRepairDialog() {
        String content = JOptionPane.showInputDialog(this, "请输入报修内容描述:");
        if (content != null && !content.trim().isEmpty()) {
            Repair r = new Repair();
            r.setContent(content.trim());
            r.setSubmitTime(new Timestamp(System.currentTimeMillis()));
            r.setStatus("待处理");
            r.setSubmitterID(student.getSid());

            if (repairService.submitRepair(r) > 0) {
                JOptionPane.showMessageDialog(this, "报修提交成功");
                loadRepairData();
            } else {
                JOptionPane.showMessageDialog(this, "提交失败");
            }
        }
    }

    private void showEvaluateDialog(JTable table) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String status = (String) repairTableModel.getValueAt(row, 3);
            if (!"已完成".equals(status)) {
                JOptionPane.showMessageDialog(this, "只有已完成的维修单可以评价");
                return;
            }
            // Check if already evaluated
            String existingScore = (String) repairTableModel.getValueAt(row, 4);
            if (!"-".equals(existingScore)) {
                JOptionPane.showMessageDialog(this, "该维修单已评价");
                return;
            }

            int rid = (int) repairTableModel.getValueAt(row, 0);

            JDialog d = new JDialog(this, "评价维修", true);
            d.setSize(300, 200);
            d.setLayout(new GridLayout(3, 2));
            d.setLocationRelativeTo(this);

            JTextField scoreF = new JTextField();
            JTextField commentF = new JTextField();

            d.add(new JLabel("评分 (1-5):")); d.add(scoreF);
            d.add(new JLabel("评价内容:")); d.add(commentF);

            JButton ok = new JButton("提交");
            ok.addActionListener(e -> {
                try {
                    int s = Integer.parseInt(scoreF.getText());
                    if (s < 1 || s > 5) throw new NumberFormatException();
                    repairService.evaluateRepair(rid, commentF.getText(), s);
                    JOptionPane.showMessageDialog(d, "评价成功");
                    d.dispose();
                    loadRepairData();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(d, "评分请输入1-5的整数");
                }
            });
            d.add(ok);
            d.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "请选择一个维修单");
        }
    }

    // --- Reservation Panel ---
    private JPanel createReservationPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Left: Venue List
        JPanel venuePanel = new JPanel(new BorderLayout());
        venuePanel.setBorder(BorderFactory.createTitledBorder("可用场馆"));
        String[] vCols = {"ID", "名称", "容量", "位置"};
        venueTableModel = new DefaultTableModel(vCols, 0);
        JTable venueTable = new JTable(venueTableModel);
        venuePanel.add(new JScrollPane(venueTable), BorderLayout.CENTER);

        JButton refreshVenue = new JButton("刷新场馆");
        refreshVenue.addActionListener(e -> loadVenueData());
        venuePanel.add(refreshVenue, BorderLayout.SOUTH);

        // Right: My Reservations
        JPanel resPanel = new JPanel(new BorderLayout());
        resPanel.setBorder(BorderFactory.createTitledBorder("我的预约"));
        String[] rCols = {"ID", "场馆ID", "时间", "时长(h)", "状态"};
        reservationTableModel = new DefaultTableModel(rCols, 0);
        JTable resTable = new JTable(reservationTableModel);
        resPanel.add(new JScrollPane(resTable), BorderLayout.CENTER);

        JPanel resTools = new JPanel();
        JButton refreshRes = new JButton("刷新预约");
        JButton bookBtn = new JButton("预约选中场馆");

        refreshRes.addActionListener(e -> loadReservationData());
        bookBtn.addActionListener(e -> showBookDialog(venueTable));

        resTools.add(refreshRes);
        resTools.add(bookBtn);
        resPanel.add(resTools, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, venuePanel, resPanel);
        split.setResizeWeight(0.4);
        mainPanel.add(split, BorderLayout.CENTER);

        loadVenueData();
        loadReservationData();

        return mainPanel;
    }

    private void loadVenueData() {
        venueTableModel.setRowCount(0);
        List<Venue> list = reservationService.getAvailableVenues();
        for (Venue v : list) {
            venueTableModel.addRow(new Object[]{v.getVenueID(), v.getVenueName(), v.getCapacity(), v.getLocation()});
        }
    }

    private void loadReservationData() {
        reservationTableModel.setRowCount(0);
        List<Reservation> list = reservationService.getReservationsByUser(student.getSid());
        for (Reservation r : list) {
            reservationTableModel.addRow(new Object[]{
                r.getResID(), r.getVenueID(), r.getResTime(), r.getDuration(), r.getAuditStatus()
            });
        }
    }

    private void showBookDialog(JTable venueTable) {
        int row = venueTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "请先选择一个场馆");
            return;
        }
        int vid = (int) venueTableModel.getValueAt(row, 0);
        String vName = (String) venueTableModel.getValueAt(row, 1);

        JDialog d = new JDialog(this, "预约场馆 - " + vName, true);
        d.setSize(350, 200);
        d.setLayout(new GridLayout(4, 2));
        d.setLocationRelativeTo(this);

        JTextField dateF = new JTextField("2023-01-01 10:00:00"); // Placeholder example
        JTextField durF = new JTextField("1.0");

        d.add(new JLabel("开始时间 (yyyy-MM-dd HH:mm:ss):")); d.add(dateF);
        d.add(new JLabel("时长 (小时):")); d.add(durF);
        d.add(new JLabel("注意:")); d.add(new JLabel("格式需严格匹配"));

        JButton ok = new JButton("提交申请");
        ok.addActionListener(e -> {
            try {
                Timestamp ts = Timestamp.valueOf(dateF.getText());
                int dur = Integer.parseInt(durF.getText());

                Reservation r = new Reservation();
                r.setVenueID(vid);
                r.setReserverID(student.getSid());
                r.setResTime(ts);
                r.setDuration(dur);

                String result = reservationService.submitReservationWithCheck(r);
                JOptionPane.showMessageDialog(d, result);
                if (result.contains("已提交")) {
                    d.dispose();
                    loadReservationData();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(d, "时间格式错误或输入非法: " + ex.getMessage());
            }
        });
        d.add(ok);
        d.setVisible(true);
    }

    // --- Card Panel ---
    private JPanel createCardPanel() {
        JPanel panel = new JPanel(null);

        cardInfoLabel = new JLabel("正在获取校园卡信息...");
        cardInfoLabel.setBounds(50, 50, 600, 30);
        cardInfoLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        panel.add(cardInfoLabel);

        JButton refreshBtn = new JButton("刷新信息");
        refreshBtn.setBounds(50, 100, 100, 30);
        refreshBtn.addActionListener(e -> loadCardInfo());
        panel.add(refreshBtn);

        JButton rechargeBtn = new JButton("充值");
        rechargeBtn.setBounds(160, 100, 100, 30);
        rechargeBtn.addActionListener(e -> {
            String amount = JOptionPane.showInputDialog(this, "请输入充值金额:");
            if (amount != null) {
                try {
                    CampusCard c = cardService.getCardInfo(student.getSid(), "student");
                    if (c != null) {
                        cardService.recharge(c.getCardID(), new BigDecimal(amount));
                        JOptionPane.showMessageDialog(this, "充值操作完成");
                        loadCardInfo();
                    } else {
                        JOptionPane.showMessageDialog(this, "未找到校园卡");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "金额格式错误");
                }
            }
        });
        panel.add(rechargeBtn);

        JButton lossBtn = new JButton("挂失");
        lossBtn.setBounds(270, 100, 100, 30);
        lossBtn.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "确定要挂失吗？") == JOptionPane.YES_OPTION) {
                cardService.reportLoss(student.getSid(), "student");
                JOptionPane.showMessageDialog(this, "挂失请求已提交");
                loadCardInfo();
            }
        });
        panel.add(lossBtn);

        loadCardInfo();
        return panel;
    }

    private void loadCardInfo() {
        CampusCard c = cardService.getCardInfo(student.getSid(), "student");
        if (c != null) {
            cardInfoLabel.setText(String.format("卡号: %s | 余额: %.2f | 状态: %s", c.getCardID(), c.getBalance(), c.getStatus()));
            if ("挂失".equals(c.getStatus())) {
                cardInfoLabel.setForeground(Color.RED);
            } else {
                cardInfoLabel.setForeground(Color.BLACK);
            }
        } else {
            cardInfoLabel.setText("您尚未办理校园卡");
        }
    }
}

