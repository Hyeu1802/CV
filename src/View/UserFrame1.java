/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import DAO.BillardsDAO;
import Model.DichVuChiTiet;
import Model.ThucDon;
import Model.ThueGay;
import com.sun.jdi.connect.spi.Connection;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JTextArea;

/**
 *
 * @author Linh
 */
public class UserFrame1 extends javax.swing.JFrame {

    ArrayList<ThucDon> thucdons;
    ArrayList<ThueGay> thuegays;
    ArrayList<DichVuChiTiet> dichvuchitiets;
    private List<List<Object[]>> danhSachDichVu;
    int idselected = 0;
    private final Color MAU_BAT = new Color(179, 229, 252);
    private final Color MAU_TAT = new Color(245, 245, 245);
    private ArrayList<Date> startTimes;
    private ArrayList<Date> endTimes;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    private static final double GIA_MOI_GIO = 50000;
    private int selectedTableIndex = -1;
    private JPanel detailsPanel;
    private JLabel banLabel;
    private JLabel trangThaiLabel;
    private JLabel gioVaoLabel;
    private JLabel gioRaLabel;
    private JTable serviceTable;
    private JLabel tongTienLabel;
    private JPanel tablePanel;

    /**
     * Creates new form UserFramer
     */
    public UserFrame1() {
        initComponents();
        setLocationRelativeTo(null);

        thucdons = new ArrayList<>();
        thuegays = new ArrayList<>();

        danhSachDichVu = new ArrayList<>();
        startTimes = new ArrayList<>();
        endTimes = new ArrayList<>();

        if (SharedTables.getTables().isEmpty()) {
            SharedTables.addTable(btnTable1);
            SharedTables.addTable(btnTable2);
            SharedTables.addTable(btnTable3);
            SharedTables.addTable(btnTable4);
            SharedTables.addTable(btnTable5);
            SharedTables.addTable(btnTable6);
        }

        for (int i = 0; i < SharedTables.getTables().size(); i++) {
            danhSachDichVu.add(new ArrayList<>());
            startTimes.add(null);
            endTimes.add(null);
        }

        for (JButton table : SharedTables.getTables()) {
            table.setBackground(MAU_TAT);
        }

        setupDetailsPanel();

        for (int i = 0; i < SharedTables.getTables().size(); i++) {
            addTableListener(SharedTables.getTables().get(i), i);
        }

        rdo_TatCa.addActionListener(e -> {
            for (JButton table : SharedTables.getTables()) {
                table.setVisible(true);
            }
        });

        rdo_DangSuDung.addActionListener(e -> {
            for (int i = 0; i < SharedTables.getTableStates().size(); i++) {
                SharedTables.getTables().get(i).setVisible(SharedTables.getTableStates().get(i));
            }
        });

        rdo_ConLai.addActionListener(e -> {
            for (int i = 0; i < SharedTables.getTableStates().size(); i++) {
                SharedTables.getTables().get(i).setVisible(!SharedTables.getTableStates().get(i));
            }
        });

        rdo_TatCa.setSelected(true);
        setSize(900, 600);
    }

    private void setupDetailsPanel() {
        detailsPanel = new JPanel();
        detailsPanel.setLayout(null);
        detailsPanel.setPreferredSize(new Dimension(400, 500));
        detailsPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("BILLARDS CLUB 7 MÀU");
        titleLabel.setBounds(50, 10, 300, 30);
        ImageIcon logoIcon = null;
        try {
            ImageIcon originalLogoIcon = new ImageIcon(getClass().getResource("/icon/billard.png"));
            Image scaledImage = originalLogoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            logoIcon = new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.err.println("Lỗi khi tải biểu tượng logo: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Không thể tải biểu tượng logo. Kiểm tra đường dẫn file hình ảnh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        if (logoIcon != null) {
            titleLabel.setIcon(logoIcon);
            titleLabel.setIconTextGap(10);
        }
        detailsPanel.add(titleLabel);

        banLabel = new JLabel("Bàn: ");
        banLabel.setBounds(20, 50, 200, 20);
        detailsPanel.add(banLabel);

        ImageIcon statusIcon = new ImageIcon(getClass().getResource("/icon/iconTrangThai.png"));
        ImageIcon clockIcon = new ImageIcon(getClass().getResource("/icon/iconThoiGian.png"));
        ImageIcon tongTienIcon = new ImageIcon(getClass().getResource("/icon/iconTongTien.png"));

        trangThaiLabel = new JLabel("Trạng thái: ");
        trangThaiLabel.setBounds(20, 80, 200, 20);
        trangThaiLabel.setIcon(statusIcon);
        trangThaiLabel.setIconTextGap(5);
        detailsPanel.add(trangThaiLabel);

        gioVaoLabel = new JLabel("Giờ vào: ");
        gioVaoLabel.setBounds(20, 110, 300, 20);
        gioVaoLabel.setIcon(clockIcon);
        gioVaoLabel.setIconTextGap(5);
        detailsPanel.add(gioVaoLabel);

        gioRaLabel = new JLabel("Giờ ra: ");
        gioRaLabel.setBounds(20, 140, 300, 20);
        gioRaLabel.setIcon(clockIcon);
        gioRaLabel.setIconTextGap(5);
        detailsPanel.add(gioRaLabel);

        JLabel lichSuLabel = new JLabel("Lịch sử dịch vụ");
        lichSuLabel.setBounds(20, 170, 200, 20);
        detailsPanel.add(lichSuLabel);

        String[] columnNames = {"Mặt hàng", "SL", "Đơn giá", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        serviceTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(serviceTable);
        scrollPane.setBounds(20, 200, 350, 150);
        detailsPanel.add(scrollPane);

        tongTienLabel = new JLabel("Tổng Tiền: 0 VND");
        tongTienLabel.setBounds(20, 360, 200, 20);
        tongTienLabel.setIcon(tongTienIcon);
        tongTienLabel.setIconTextGap(5);
        detailsPanel.add(tongTienLabel);

        JButton thanhToanButton = new JButton("Thanh toán");
        thanhToanButton.setBounds(150, 400, 120, 30);
        thanhToanButton.addActionListener(e -> {
            int tableIndex = getSelectedTableIndex();
            if (tableIndex == -1) {
                JOptionPane.showMessageDialog(UserFrame1.this, "Vui lòng chọn một bàn để thanh toán!");
                return;
            }

            if (SharedTables.getTableStates().get(tableIndex)) {
                JOptionPane.showMessageDialog(UserFrame1.this, "Bàn đang sử dụng, vui lòng tắt bàn trước khi thanh toán!");
                return;
            }

            if (startTimes.get(tableIndex) == null || endTimes.get(tableIndex) == null) {
                JOptionPane.showMessageDialog(UserFrame1.this, "Bàn chưa được sử dụng, không thể thanh toán!");
                return;
            }

            double tongTienDichVu = danhSachDichVu.get(tableIndex).stream().mapToDouble(row -> (double) row[3]).sum();
            double tienGio = tinhTienGioChoi(tableIndex);
            double tongTien = tongTienDichVu + tienGio;
            int tongtien = (int) tongTien;
            java.sql.Date thoiGianTao = new java.sql.Date(System.currentTimeMillis());
            boolean checkDT = BillardsDAO.newDT(thoiGianTao, tongtien);
            if (checkDT) {
                int confirm = JOptionPane.showConfirmDialog(UserFrame1.this,
                        "Tiền giờ chơi: " + tienGio + " VND\nTiền dịch vụ: " + tongTienDichVu + " VND\nTổng tiền: " + tongTien + " VND\nBạn có chắc chắn muốn thanh toán?",
                        "Xác nhận thanh toán", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    StringBuilder billContent = new StringBuilder();
                    billContent.append("========== HÓA ĐƠN THANH TOÁN ==========\n");
                    billContent.append("BILLARDS CLUB 7 MÀU\n");
                    billContent.append("---------------------------------------\n");
                    billContent.append("Bàn: ").append(tableIndex + 1).append("\n");
                    billContent.append("Giờ vào: ").append(startTimes.get(tableIndex) != null ? dateFormat.format(startTimes.get(tableIndex)) : "N/A").append("\n");
                    billContent.append("Giờ ra: ").append(endTimes.get(tableIndex) != null ? dateFormat.format(endTimes.get(tableIndex)) : "N/A").append("\n");
                    billContent.append("---------------------------------------\n");
                    billContent.append("Lịch sử dịch vụ:\n");
                    billContent.append(String.format("%-15s %-5s %-10s %-10s\n", "Mặt hàng", "SL", "Đơn giá", "Thành tiền"));
                    billContent.append("---------------------------------------\n");
                    for (Object[] row : danhSachDichVu.get(tableIndex)) {
                        billContent.append(String.format("%-15s %-5s %-10s %-10s\n", row[0], row[1], row[2], row[3]));
                    }
                    billContent.append("---------------------------------------\n");
                    billContent.append("Tiền giờ: ").append(String.format("%.0f", tienGio)).append(" VND\n");
                    billContent.append("Tiền dịch vụ: ").append(String.format("%.0f", tongTienDichVu)).append(" VND\n");
                    billContent.append("Tổng tiền: ").append(String.format("%.0f", tongTien)).append(" VND\n");
                    billContent.append("=======================================\n");
                    billContent.append("Cảm ơn quý khách! Hẹn gặp lại!\n");

                    JOptionPane.showMessageDialog(UserFrame1.this, billContent.toString(), "Hóa đơn", JOptionPane.INFORMATION_MESSAGE);

                    JOptionPane.showMessageDialog(UserFrame1.this, "Thanh toán thành công!\nTổng tiền: " + tongTien + " VND\nCảm ơn quý khách!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                    SharedTables.getTableStates().set(tableIndex, false);
                    SharedTables.getTables().get(tableIndex).setBackground(MAU_TAT);
                    startTimes.set(tableIndex, null);
                    endTimes.set(tableIndex, null);
                    danhSachDichVu.get(tableIndex).clear();

                    DefaultTableModel tableModel = (DefaultTableModel) serviceTable.getModel();
                    tableModel.setRowCount(0);
                    tongTienLabel.setText("Tổng Tiền: 0 VND");
                    updateTableDetails(tableIndex);
                }
            }
        });
        detailsPanel.add(thanhToanButton);

        ChiTiet_1.setLayout(new BorderLayout());
        ChiTiet_1.add(detailsPanel, BorderLayout.CENTER);

        ChiTiet_1.revalidate();
        ChiTiet_1.repaint();
        jPanel4.revalidate();
        jPanel4.repaint();
    }

    private void addTableListener(JButton table, int index) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    SharedTables.getTableStates().set(index, !SharedTables.getTableStates().get(index));
                    if (SharedTables.getTableStates().get(index)) {
                        startTimes.set(index, new Date());
                        endTimes.set(index, null);
                        table.setBackground(MAU_BAT);
                    } else {
                        endTimes.set(index, new Date());
                        table.setBackground(MAU_TAT);
                    }

                    if (rdo_DangSuDung.isSelected()) {
                        for (int i = 0; i < SharedTables.getTableStates().size(); i++) {
                            SharedTables.getTables().get(i).setVisible(SharedTables.getTableStates().get(i));
                        }
                    } else if (rdo_ConLai.isSelected()) {
                        for (int i = 0; i < SharedTables.getTableStates().size(); i++) {
                            SharedTables.getTables().get(i).setVisible(!SharedTables.getTableStates().get(i));
                        }
                    }

                    selectedTableIndex = index;
                    updateTableDetails(index);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    selectedTableIndex = index;
                    updateTableDetails(index);
                }
            }
        });
    }

    private void updateTableDetails(int index) {
        if (banLabel == null || trangThaiLabel == null || gioVaoLabel == null || gioRaLabel == null || serviceTable == null || tongTienLabel == null) {
            System.out.println("Một trong các thành phần giao diện bị null!");
            return;
        }

        banLabel.setText("Bàn: " + (index + 1));
        trangThaiLabel.setText("Trạng thái: " + (SharedTables.getTableStates().get(index) ? "Đang sử dụng" : "Không sử dụng"));
        gioVaoLabel.setText("Giờ vào: " + (startTimes.get(index) != null ? dateFormat.format(startTimes.get(index)) : "Chưa sử dụng"));
        gioRaLabel.setText("Giờ ra: " + (endTimes.get(index) != null ? dateFormat.format(endTimes.get(index)) : (SharedTables.getTableStates().get(index) ? "Đang sử dụng" : "Chưa sử dụng")));

        DefaultTableModel model = (DefaultTableModel) serviceTable.getModel();
        model.setRowCount(0);
        for (Object[] row : danhSachDichVu.get(index)) {
            model.addRow(row);
        }

        double tienGio = 0;
        if (!SharedTables.getTableStates().get(index) && startTimes.get(index) != null && endTimes.get(index) != null) {
            tienGio = tinhTienGioChoi(index);
            model.addRow(new Object[]{"Tiền giờ chơi", "-", GIA_MOI_GIO + " VND/giờ", tienGio});
        }

        double tongTienDichVu = danhSachDichVu.get(index).stream().mapToDouble(row -> (double) row[3]).sum();
        double tongTien = tongTienDichVu + tienGio;
        tongTienLabel.setText("Tổng Tiền: " + tongTien + " VND");

        detailsPanel.revalidate();
        detailsPanel.repaint();
        ChiTiet_1.revalidate();
        ChiTiet_1.repaint();
        jPanel4.revalidate();
        jPanel4.repaint();
    }

    private int getSelectedTableIndex() {
        return selectedTableIndex;
    }


    private double tinhTienGioChoi(int index) {
        if (startTimes.get(index) == null || endTimes.get(index) == null) {
            return 0;
        }
        long thoiGianSuDung = endTimes.get(index).getTime() - startTimes.get(index).getTime();
        double soGio = thoiGianSuDung / (1000.0 * 60 * 60);
        return Math.round(soGio * GIA_MOI_GIO);
    }

    public void loadTable(ArrayList<ThucDon> list) {
        DefaultTableModel df = new DefaultTableModel();
        df.addColumn("STT");
        df.addColumn("Tên món");
        df.addColumn("Giá bán");
        int stt = 1;
        for (int i = 0; i < list.size(); i++) {
            ThucDon td = list.get(i);
            String[] row = new String[]{stt++ + "", td.getTenMon(), td.getGiaBan()};
            df.addRow(row);
        }
        lbl_ThucDon.setModel(df);
    }

    public void loadTable1(ArrayList<ThueGay> list) {
        DefaultTableModel df = new DefaultTableModel();
        df.addColumn("STT");
        df.addColumn("Tên gậy");
        df.addColumn("Giá thuê");
        int stt = 1;
        for (int i = 0; i < list.size(); i++) {
            ThueGay tg = list.get(i);
            String[] row = new String[]{stt++ + "", tg.getTenGay(), tg.getGiaThue()};
            df.addRow(row);
        }
        lbl_GayCT.setModel(df);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jDialog1 = new javax.swing.JDialog();
        jFrame1 = new javax.swing.JFrame();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_TenGay = new javax.swing.JTextField();
        txt_GiaThue = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lbl_GayCT = new javax.swing.JTable();
        lb_Anh = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        rdo_TatCa = new javax.swing.JRadioButton();
        rdo_DangSuDung = new javax.swing.JRadioButton();
        rdo_ConLai = new javax.swing.JRadioButton();
        ChiTiet_1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnTable1 = new javax.swing.JButton();
        btnTable2 = new javax.swing.JButton();
        btnTable3 = new javax.swing.JButton();
        btnTable4 = new javax.swing.JButton();
        btnTable5 = new javax.swing.JButton();
        btnTable6 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_TenMon = new javax.swing.JTextField();
        txt_GiaBan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        lbl_ThucDon = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        lbAnhMon = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_soluong = new javax.swing.JTextField();
        btn_goi = new javax.swing.JButton();
        btn_lichsu = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(0, 204, 102));

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Chi Tiết");

        jLabel2.setText("Tên gậy:");

        jLabel3.setText("Giá:");

        jButton2.setBackground(new java.awt.Color(0, 204, 102));
        jButton2.setText("Thuê gậy");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 102, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Gậy");

        lbl_GayCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Tên gậy", "Giá thuê"
            }
        ));
        lbl_GayCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_GayCTMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(lbl_GayCT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(146, 146, 146)
                            .addComponent(jLabel1))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_GiaThue, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_TenGay, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(275, 275, 275)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_Anh, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jButton2)
                .addGap(142, 142, 142))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(271, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel1)
                        .addGap(23, 23, 23)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_TenGay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_GiaThue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lb_Anh, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(248, 248, 248))
        );

        jTabbedPane1.addTab("Thuê gậy", new javax.swing.ImageIcon(getClass().getResource("/icon/iconThueGay.png")), jPanel3); // NOI18N

        buttonGroup1.add(rdo_TatCa);
        rdo_TatCa.setText("Tất cả");

        buttonGroup1.add(rdo_DangSuDung);
        rdo_DangSuDung.setText("Đang sử dụng");

        buttonGroup1.add(rdo_ConLai);
        rdo_ConLai.setText("Còn lại");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdo_TatCa, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdo_DangSuDung, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdo_ConLai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdo_TatCa)
                    .addComponent(rdo_DangSuDung)
                    .addComponent(rdo_ConLai))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ChiTiet_1Layout = new javax.swing.GroupLayout(ChiTiet_1);
        ChiTiet_1.setLayout(ChiTiet_1Layout);
        ChiTiet_1Layout.setHorizontalGroup(
            ChiTiet_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 434, Short.MAX_VALUE)
        );
        ChiTiet_1Layout.setVerticalGroup(
            ChiTiet_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );

        jPanel1.setLayout(new java.awt.GridLayout(5, 5, 45, 37));

        btnTable1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8971902f-18fb-47e0-890c-c5750abf50e0.png"))); // NOI18N
        btnTable1.setText("Bàn 1");
        btnTable1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTable1.setMaximumSize(new java.awt.Dimension(100, 100));
        btnTable1.setMinimumSize(new java.awt.Dimension(100, 100));
        btnTable1.setName(""); // NOI18N
        btnTable1.setPreferredSize(new java.awt.Dimension(70, 70));
        btnTable1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnTable1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnTable1);

        btnTable2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8971902f-18fb-47e0-890c-c5750abf50e0.png"))); // NOI18N
        btnTable2.setText("Bàn 2");
        btnTable2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTable2.setPreferredSize(new java.awt.Dimension(70, 70));
        btnTable2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnTable2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTable2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTable2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnTable2);

        btnTable3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTable3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8971902f-18fb-47e0-890c-c5750abf50e0.png"))); // NOI18N
        btnTable3.setText("Bàn 3");
        btnTable3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTable3.setPreferredSize(new java.awt.Dimension(70, 70));
        btnTable3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnTable3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnTable3);

        btnTable4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTable4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8971902f-18fb-47e0-890c-c5750abf50e0.png"))); // NOI18N
        btnTable4.setText("Bàn 4");
        btnTable4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTable4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnTable4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnTable4);

        btnTable5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTable5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8971902f-18fb-47e0-890c-c5750abf50e0.png"))); // NOI18N
        btnTable5.setText("Bàn 5");
        btnTable5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTable5.setPreferredSize(new java.awt.Dimension(70, 70));
        btnTable5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnTable5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnTable5);

        btnTable6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTable6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8971902f-18fb-47e0-890c-c5750abf50e0.png"))); // NOI18N
        btnTable6.setText("Bàn 6");
        btnTable6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTable6.setPreferredSize(new java.awt.Dimension(70, 70));
        btnTable6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnTable6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(btnTable6);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(671, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(ChiTiet_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChiTiet_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Phòng Bàn", new javax.swing.ImageIcon(getClass().getResource("/icon/iconhongBan.png")), jPanel4); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("Chi tiết");

        jLabel6.setText("Tên món :");

        jLabel7.setText("Giá:");

        lbl_ThucDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Tên món", "Giá bán"
            }
        ));
        lbl_ThucDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_ThucDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lbl_ThucDon);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Thực đơn:");

        jLabel9.setText("Số lượng:");

        btn_goi.setText("Gọi");
        btn_goi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_goiActionPerformed(evt);
            }
        });

        btn_lichsu.setBackground(new java.awt.Color(102, 102, 255));
        btn_lichsu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_lichsu.setText("Lịch sử gọi món");
        btn_lichsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lichsuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_lichsu)
                .addGap(35, 35, 35))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_TenMon)
                                            .addComponent(txt_GiaBan, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                                            .addComponent(txt_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btn_goi, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbAnhMon, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(284, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(btn_lichsu, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_TenMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_GiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txt_soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_goi)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(22, 22, 22))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(lbAnhMon, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );

        jTabbedPane1.addTab("Thực đơn", new javax.swing.ImageIcon(getClass().getResource("/icon/iconThucDon.png")), jPanel2); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("USER");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/menu.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 923, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)))
                .addGap(18, 18, 18))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        thucdons = BillardsDAO.getAllTD();
        loadTable(thucdons);
        thuegays = BillardsDAO.getAllTG();
        loadTable1(thuegays);
    }//GEN-LAST:event_formWindowOpened

    private void lbl_ThucDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_ThucDonMouseClicked
        int row = lbl_ThucDon.getSelectedRow();
        if (row != -1) {
            ThucDon td = thucdons.get(row);
            txt_TenMon.setText(thucdons.get(row).getTenMon());
            txt_GiaBan.setText(thucdons.get(row).getGiaBan());
            txt_soluong.setText("" + (row + 1));

            idselected = thucdons.get(row).getID();

            String hinhAnhPath = thucdons.get(row).getAnhMon();
            if (hinhAnhPath != null && !hinhAnhPath.isEmpty()) {
                try {
                    ImageIcon icon = new ImageIcon(hinhAnhPath);
                    Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    lbAnhMon.setIcon(new ImageIcon(image));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi load hình ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    lbAnhMon.setIcon(null);
                }
            } else {
                lbAnhMon.setIcon(null);
            }
        }


    }//GEN-LAST:event_lbl_ThucDonMouseClicked

    private void btn_goiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_goiActionPerformed
        int row = lbl_ThucDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn một món để gọi!");
            return;
        }

        int tableIndex = getSelectedTableIndex();
        if (tableIndex == -1) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn một bàn trước khi gọi món!");
            return;
        }

        if (!SharedTables.getTableStates().get(tableIndex)) {
            JOptionPane.showMessageDialog(rootPane, "Bàn chưa được bật, vui lòng bật bàn trước khi gọi món!");
            return;
        }

        String tenMon = thucdons.get(row).getTenMon();
        String giaBan = thucdons.get(row).getGiaBan();
        int soLuong = Integer.parseInt(txt_soluong.getText());
        double thanhTien = Double.parseDouble(giaBan) * soLuong;

        danhSachDichVu.get(tableIndex).add(new Object[]{tenMon, soLuong, giaBan, thanhTien});
        updateTableDetails(tableIndex);
        JOptionPane.showMessageDialog(rootPane, "Đã gọi món: " + tenMon + " với số lượng: " + soLuong);
    }//GEN-LAST:event_btn_goiActionPerformed

    private void btn_lichsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lichsuActionPerformed
        JDialog lichSuDialog = new JDialog(this, "Lịch sử đặt hàng", true);
        lichSuDialog.setSize(600, 400);

        DefaultTableModel model = (DefaultTableModel) serviceTable.getModel();
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        lichSuDialog.add(scrollPane);
        lichSuDialog.setLocationRelativeTo(this);
        lichSuDialog.setVisible(true);
    }//GEN-LAST:event_btn_lichsuActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String tenGay = txt_TenGay.getText().trim();
        String giaThue = txt_GiaThue.getText().trim();

        if (tenGay.isEmpty() || giaThue.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn gậy để thuê!");
            return;
        }

        int tableIndex = getSelectedTableIndex();
        if (tableIndex == -1) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn một bàn trước khi thuê gậy!");
            return;
        }

        if (!SharedTables.getTableStates().get(tableIndex)) {
            JOptionPane.showMessageDialog(rootPane, "Bàn chưa được bật, vui lòng bật bàn trước khi thuê gậy!");
            return;
        }

        try {
            double giaThuee = Double.parseDouble(giaThue);
            boolean checkAdd = BillardsDAO.ThueGay(tenGay, giaThuee);

            if (checkAdd) {
                JOptionPane.showMessageDialog(rootPane, "Thuê gậy thành công!");
                danhSachDichVu.get(tableIndex).add(new Object[]{tenGay, 1, giaThuee, giaThuee});
                updateTableDetails(tableIndex);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Thuê gậy thất bại! Có thể gậy không có sẵn.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Giá thuê phải là một số hợp lệ!");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void lbl_GayCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_GayCTMouseClicked
        int row = lbl_GayCT.getSelectedRow();
        if (row != -1) {
            txt_TenGay.setText(thuegays.get(row).getTenGay());
            txt_GiaThue.setText(thuegays.get(row).getGiaThue());

            String hinhAnhPath = thuegays.get(row).getAnhGay();
            if (hinhAnhPath != null && !hinhAnhPath.isEmpty()) {
                try {
                    ImageIcon icon = new ImageIcon(hinhAnhPath);
                    Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    lb_Anh.setIcon(new ImageIcon(image));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi load hình ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    lb_Anh.setIcon(null);
                }
            } else {
                lb_Anh.setIcon(null);
            }
        }
        idselected = thuegays.get(row).getID();
    }//GEN-LAST:event_lbl_GayCTMouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void btnTable2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTable2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTable2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserFrame1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChiTiet_1;
    private javax.swing.JButton btnTable1;
    private javax.swing.JButton btnTable2;
    private javax.swing.JButton btnTable3;
    private javax.swing.JButton btnTable4;
    private javax.swing.JButton btnTable5;
    private javax.swing.JButton btnTable6;
    private javax.swing.JButton btn_goi;
    private javax.swing.JButton btn_lichsu;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbAnhMon;
    private javax.swing.JLabel lb_Anh;
    private javax.swing.JTable lbl_GayCT;
    private javax.swing.JTable lbl_ThucDon;
    private javax.swing.JRadioButton rdo_ConLai;
    private javax.swing.JRadioButton rdo_DangSuDung;
    private javax.swing.JRadioButton rdo_TatCa;
    private javax.swing.JTextField txt_GiaBan;
    private javax.swing.JTextField txt_GiaThue;
    private javax.swing.JTextField txt_TenGay;
    private javax.swing.JTextField txt_TenMon;
    private javax.swing.JTextField txt_soluong;
    // End of variables declaration//GEN-END:variables
}
