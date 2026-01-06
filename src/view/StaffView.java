package src.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import src.entity.Staff;
import src.entity.Repair;
import src.entity.Reservation;
import src.entity.Venue;
import src.entity.CampusCard;
import src.service.RepairService;
import src.service.ReservationService;
import src.service.CampusCardService;
import src.service.VenueService;

/**
 * 职工控制台视图：Swing图形化界面
 */
public class StaffView extends JFrame {
    private Staff staff;
    private RepairService repairService = new RepairService();
    private ReservationService reservationService = new ReservationService();
    private CampusCardService cardService = new CampusCardService();
    private VenueService venueService = new VenueService();

    private DefaultTableModel repairTableModel;
    private DefaultTableModel reservationTableModel;
    private DefaultTableModel venueTableModel;
    private JLabel cardInfoLabel;

    public StaffView(Staff staff) {
        this.staff = staff;
        setTitle("职工服务系统 - " + staff.getEName());
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("主页", createHomePanel());
        tabbedPane.addTab("我的工作(维修)", createRepairPanel());
        tabbedPane.addTab("场地预约", createReservationPanel());
        tabbedPane.addTab("校园卡", createCardPanel());

        this.add(tabbedPane);
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'><h2>欢迎职工: " + staff.getEName() + "</h2><p>工号: " + staff.getEid() + "</p><p>职位: " + staff.getPosition() + "</p></div></html>", SwingConstants.CENTER);
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

    // --- Repair Panel (Task Processing) ---
    private JPanel createRepairPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"维修单ID", "内容", "提交时间", "状态", "提交人"};
        repairTableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(repairTableModel);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel tools = new JPanel();
        JButton refreshBtn = new JButton("刷新任务");
        JButton completeBtn = new JButton("标记为已完成");
        JButton viewEvalBtn = new JButton("查看评价");

        refreshBtn.addActionListener(e -> loadRepairTaskData());
        completeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int rid = (int) repairTableModel.getValueAt(row, 0);
                repairService.updateRepairStatus(rid, "已完成");
                JOptionPane.showMessageDialog(this, "操作成功");
                loadRepairTaskData();
            } else {
                JOptionPane.showMessageDialog(this, "请选择一个任务");
            }
        });
        viewEvalBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                 int rid = (int) repairTableModel.getValueAt(row, 0);
                 src.entity.Evaluation eval = repairService.getEvaluation(rid);
                 if (eval != null) {
                     JOptionPane.showMessageDialog(this, "评分: " + eval.getScore() + "\n评价: " + eval.getComment());
                 } else {
                     JOptionPane.showMessageDialog(this, "暂无评价");
                 }
            } else {
                JOptionPane.showMessageDialog(this, "请选择一个任务");
            }
        });

        tools.add(refreshBtn);
        tools.add(completeBtn);
        tools.add(viewEvalBtn);
        panel.add(tools, BorderLayout.NORTH);

        loadRepairTaskData();
        return panel;
    }

    private void loadRepairTaskData() {
        repairTableModel.setRowCount(0);
        // Assuming staff handle repairs using their ID
        List<Repair> list = repairService.getAssignedRepairs(staff.getEid());
        for (Repair r : list) {
            repairTableModel.addRow(new Object[]{
                r.getRepairID(), r.getContent(), r.getSubmitTime(), r.getStatus(), r.getSubmitterID()
            });
        }
    }

    // --- Reservation Panel (Like Student) ---
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
        String[] rCols = {"ID", "场馆ID", "时间", "时长", "状态"};
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
        // Staff can also reserve
        List<Reservation> list = reservationService.getReservationsByUser(staff.getEid());
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

        JTextField dateF = new JTextField("2023-01-01 10:00:00");
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
                r.setReserverID(staff.getEid());
                r.setResTime(ts);
                r.setDuration(dur);

                String result = reservationService.submitReservationWithCheck(r);
                JOptionPane.showMessageDialog(d, result);
                if (result.contains("已提交")) {
                    d.dispose();
                    loadReservationData();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(d, "时间格式错误: " + ex.getMessage());
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
                    CampusCard c = cardService.getCardInfo(staff.getEid(), "staff");
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
                cardService.reportLoss(staff.getEid(), "staff");
                JOptionPane.showMessageDialog(this, "挂失请求已提交");
                loadCardInfo();
            }
        });
        panel.add(lossBtn);

        JButton addCardBtn = new JButton("自助办卡");
        addCardBtn.setBounds(380, 100, 100, 30);
        addCardBtn.addActionListener(e -> {
             CampusCard c = cardService.getCardInfo(staff.getEid(), "staff");
             if (c != null) {
                 JOptionPane.showMessageDialog(this, "您已持有校园卡，无需重复办理。");
                 return;
             }
             String cardId = JOptionPane.showInputDialog(this, "请输入新卡号 (或留空自动生成):");
             if (cardId == null) return;
             if (cardId.trim().isEmpty()) {
                 cardId = "E" + staff.getEid(); // Auto generate for staff
             }
             CampusCard newCard = new CampusCard();
             newCard.setCardID(cardId);
             newCard.setUserID(staff.getEid());
             newCard.setUserType("staff");
             newCard.setBalance(BigDecimal.ZERO);
             newCard.setStatus("正常");

             if (cardService.addCard(newCard)) {
                 JOptionPane.showMessageDialog(this, "办卡成功！卡号: " + cardId);
                 loadCardInfo();
             } else {
                 JOptionPane.showMessageDialog(this, "办卡失败，卡号可能已存在。");
             }
        });
        panel.add(addCardBtn);

        loadCardInfo();
        return panel;
    }

    private void loadCardInfo() {
        CampusCard c = cardService.getCardInfo(staff.getEid(), "staff");
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

