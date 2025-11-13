/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import DAO.BillardsDAO;
import Model.Ban;
import Model.DichVuChiTiet;
import Model.HoaDon;
import Model.HoaDonThongKe;
import Model.NhanVien;
import Model.NhapHang;
import Model.ThucDon;
import Model.ThueGay;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import View.SharedTables;
import java.awt.event.MouseListener;

/**
 *
 * @author Administrator
 */
public class AdminFrame1 extends javax.swing.JFrame {

    ArrayList<Ban> bans;
    ArrayList<ThucDon> thucdons;
    ArrayList<ThueGay> thuegays;
    ArrayList<HoaDonThongKe> hoadons;
    private int currentPage = 1;
    private int totalPage = 10;
    int idselected = 0;
    private Color MAU_BAT = new Color(179, 229, 252);
    private Color MAU_TAT = new Color(245, 245, 245);
    private ArrayList<Date> startTimes;
    private ArrayList<Date> endTimes;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    private double GIA_MOI_GIO = 50000;
    private JPanel detailsPanel;
    private JLabel banLabel;
    private JLabel trangThaiLabel;
    private JLabel gioVaoLabel;
    private JLabel gioRaLabel;
    private JTable serviceTable;
    private JLabel tongTienLabel;

    /**
     * Creates new form AdminFrame
     */
    public AdminFrame1() {
        initComponents();
        setLocationRelativeTo(null);

        thucdons = new ArrayList<>();
        thuegays = new ArrayList<>();

        SharedTables.registerAdmin(this);

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

        for (JButton table : SharedTables.getTables()) {
            table.setBackground(MAU_TAT);
        }

        for (int i = 0; i < SharedTables.getTables().size(); i++) {
            startTimes.add(null);
            endTimes.add(null);
        }

        jPanel3.removeAll();
        for (JButton table : SharedTables.getTables()) {
            jPanel3.add(table);
        }
        jPanel3.add(btnAddTables);
        jPanel3.revalidate();
        jPanel3.repaint();

        setupDetailsPanel();

        for (int i = 0; i < SharedTables.getTables().size(); i++) {
            addTableListener(SharedTables.getTables().get(i), i);
        }

        rdoTatCa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JButton table : SharedTables.getTables()) {
                    table.setVisible(true);
                }
            }
        });

        rdoDangSuDung.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < SharedTables.getTableStates().size(); i++) {
                    SharedTables.getTables().get(i).setVisible(SharedTables.getTableStates().get(i));
                }
            }
        });

        rdoConLai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < SharedTables.getTableStates().size(); i++) {
                    SharedTables.getTables().get(i).setVisible(!SharedTables.getTableStates().get(i));
                }
            }
        });

        btnAddTables.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewTable();
            }
        });

        rdoTatCa.setSelected(true);

        setSize(900, 600);
        tblbangNhapHang.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Tên Hàng", "Ngày Nhập", "Số Lượng", "Giá Đơn", "Tổng Giá"
                }
        ));

        setSize(900, 600);

        startAutoRefresh();
    }

    private void setupDetailsPanel() {
        detailsPanel = new JPanel();
        detailsPanel.setLayout(null);
        detailsPanel.setPreferredSize(new Dimension(400, 500));

        JLabel titleLabel = new JLabel("BILLARDS CLUB 7 MÀU");
        titleLabel.setBounds(50, 10, 300, 30);
        ImageIcon logoIcon = null;
        try {
            ImageIcon logo = new ImageIcon(getClass().getResource("/icon/billard.png"));
            Image scaledImage = logo.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            logoIcon = new ImageIcon(scaledImage);
        } catch (Exception e) {
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
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        serviceTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(serviceTable);
        scrollPane.setBounds(20, 200, 350, 150);
        detailsPanel.add(scrollPane);

        tongTienLabel = new JLabel("Tổng Tiền: ");
        tongTienLabel.setBounds(20, 360, 350, 20);
        tongTienLabel.setIcon(tongTienIcon);
        tongTienLabel.setIconTextGap(5);
        detailsPanel.add(tongTienLabel);

        JButton inHoaDonButton = new JButton("In hóa đơn");
        inHoaDonButton.setBounds(150, 400, 100, 30);
        inHoaDonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedTableIndex = -1;
                try {
                    String banText = banLabel.getText().split(" ")[1];
                    selectedTableIndex = Integer.parseInt(banText) - 1;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AdminFrame1.this, "Không thể xác định bàn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                StringBuilder billContent = new StringBuilder();
                billContent.append("========== HÓA ĐƠN THANH TOÁN ==========\n");
                billContent.append("BILLARDS CLUB 7 MÀU\n");
                billContent.append("---------------------------------------\n");
                billContent.append(banLabel.getText()).append("\n");
                billContent.append(trangThaiLabel.getText()).append("\n");
                billContent.append(gioVaoLabel.getText()).append("\n");
                billContent.append(gioRaLabel.getText()).append("\n");
                billContent.append("---------------------------------------\n");
                billContent.append("Lịch sử dịch vụ:\n");

                DefaultTableModel tableModel = (DefaultTableModel) serviceTable.getModel();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String matHang = tableModel.getValueAt(i, 0).toString();
                    String soLuong = tableModel.getValueAt(i, 1).toString();
                    String donGia = tableModel.getValueAt(i, 2).toString();
                    String thanhTien = tableModel.getValueAt(i, 3).toString();
                    billContent.append(String.format("%-15s %-5s %-10s %-10s\n", matHang, soLuong, donGia, thanhTien));
                }

                double tienGio = giaMoiGio(selectedTableIndex);
                billContent.append("---------------------------------------\n");
                billContent.append(String.format("%-15s %-5s %-10s %-10s\n", "Tiền giờ", "c",
                        String.format("%.0f", GIA_MOI_GIO) + "/giờ", String.format("%.0f", tienGio)));

                billContent.append("---------------------------------------\n");
                billContent.append(tongTienLabel.getText()).append("\n");
                billContent.append("=======================================\n");
                billContent.append("Cảm ơn quý khách! Hẹn gặp lại!\n");

                JOptionPane.showMessageDialog(AdminFrame1.this, billContent.toString(), "Hóa đơn", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        detailsPanel.add(inHoaDonButton);

        ChiTiet.setLayout(new BorderLayout());
        ChiTiet.add(detailsPanel, BorderLayout.CENTER);

        ChiTiet.revalidate();
        ChiTiet.repaint();
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    private void addTableListener(JButton table, int index) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (index < 0 || index >= SharedTables.getTables().size()) {
                    return;
                }

                if (e.getClickCount() == 2) {
                    int confirm = JOptionPane.showConfirmDialog(
                            AdminFrame1.this,
                            "Bạn có chắc chắn muốn xóa Bàn " + (index + 1) + "?",
                            "Xác nhận xóa bàn",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        SharedTables.removeTable(index);
                    }
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    SharedTables.setTableState(index, !SharedTables.getTableStates().get(index));
                    if (SharedTables.getTableStates().get(index)) {
                        startTimes.set(index, new Date());
                        endTimes.set(index, null);
                    } else {
                        endTimes.set(index, new Date());
                    }
                    table.setBackground(SharedTables.getTableStates().get(index) ? MAU_BAT : MAU_TAT);
                    updateTableDetails(index);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    updateTableDetails(index);
                }
            }
        });
    }

    private void updateTableDetails(int index) {
        banLabel.setText("Bàn: " + (index + 1));
        trangThaiLabel.setText("Trạng thái: " + (SharedTables.getTableStates().get(index) ? "Đang sử dụng" : "Không sử dụng"));

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        if (startTimes.get(index) != null) {
            gioVaoLabel.setText("Giờ vào: " + sdf.format(startTimes.get(index)));
        } else {
            gioVaoLabel.setText("Giờ vào: ");
        }

        if (endTimes.get(index) != null) {
            gioRaLabel.setText("Giờ ra: " + sdf.format(endTimes.get(index)));
        } else {
            gioRaLabel.setText("Giờ ra: ");
        }

        DefaultTableModel model = (DefaultTableModel) serviceTable.getModel();
        model.setRowCount(0);
        tongTienLabel.setText("Tổng Tiền: ");
    }

    private void addNewTable() {
        int newTableIndex = SharedTables.getTables().size() + 1;
        JButton newTable = new JButton("Bàn " + newTableIndex);

        newTable.setBackground(MAU_TAT);
        newTable.setFont(new Font("Segoe UI", Font.BOLD, 16));
        newTable.setPreferredSize(new Dimension(70, 70));

        SharedTables.addTable(newTable);
        startTimes.add(null);
        endTimes.add(null);

        int index = SharedTables.getTables().size() - 1;
        addTableListener(newTable, index);

        jPanel3.removeAll();
        for (JButton table : SharedTables.getTables()) {
            jPanel3.add(table);
        }
        jPanel3.add(btnAddTables);

        if (rdoDangSuDung.isSelected()) {
            for (int i = 0; i < SharedTables.getTableStates().size(); i++) {
                SharedTables.getTables().get(i).setVisible(SharedTables.getTableStates().get(i));
            }
        } else if (rdoConLai.isSelected()) {
            for (int i = 0; i < SharedTables.getTableStates().size(); i++) {
                SharedTables.getTables().get(i).setVisible(!SharedTables.getTableStates().get(i));
            }
        } else {
            for (JButton table : SharedTables.getTables()) {
                table.setVisible(true);
            }
        }

        jPanel3.revalidate();
        jPanel3.repaint();
    }

    private void startAutoRefresh() {
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedTableIndex = -1;
                try {
                    String banText = banLabel.getText().split(" ")[1];
                    selectedTableIndex = Integer.parseInt(banText) - 1;
                } catch (Exception ex) {
                    return;
                }
                if (selectedTableIndex >= 0) {
                    updateTableDetails(selectedTableIndex);
                }
            }
        });
        timer.start();
    }

    private double giaMoiGio(int index) {
        if (startTimes.get(index) != null) {
            Date endTime = endTimes.get(index) != null ? endTimes.get(index) : new Date();
            long diff = endTime.getTime() - startTimes.get(index).getTime();
            double hours = diff / (1000.0 * 60 * 60);
            return hours * GIA_MOI_GIO;
        }
        return 0;
    }

    public void updateTableRemove(int removedIndex) {
        jPanel3.removeAll();

        for (int i = 0; i < SharedTables.getTables().size(); i++) {
            JButton table = SharedTables.getTables().get(i);
            for (MouseListener listener : table.getMouseListeners()) {
                table.removeMouseListener(listener);
            }
            addTableListener(table, i);
            jPanel3.add(table);
        }

        jPanel3.add(btnAddTables);

        jPanel3.revalidate();
        jPanel3.repaint();

        try {
            String banText = banLabel.getText().split(" ")[1];
            int selectedTableIndex = Integer.parseInt(banText) - 1;
            if (selectedTableIndex == removedIndex) {
                banLabel.setText("Bàn: ");
                trangThaiLabel.setText("Trạng thái: ");
                gioVaoLabel.setText("Giờ vào: ");
                gioRaLabel.setText("Giờ ra: ");
                DefaultTableModel model = (DefaultTableModel) serviceTable.getModel();
                model.setRowCount(0);
                tongTienLabel.setText("Tổng Tiền: ");
            } else if (selectedTableIndex > removedIndex) {
                updateTableDetails(selectedTableIndex - 1);
            }
        } catch (Exception ex) {
        }
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
        lblThucDon.setModel(df);
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
        lblGayCT.setModel(df);
    }

    public void loadTableHoaDon(ArrayList<HoaDonThongKe> listhd) {
        DefaultTableModel df = new DefaultTableModel();

        df.addColumn("STT");
        df.addColumn("Ngày");
        df.addColumn("Tổng hóa đơn");

        int stt = 1;
        for (int i = 0; i < listhd.size(); i++) {
            HoaDonThongKe hds = listhd.get(i);
            String[] row = new String[]{stt++ + "", hds.getThoiGianTao() + "", hds.getTongTien() + "" + " đ"};
            df.addRow(row);
        }
        tblChitiet.setModel(df);
        tblChitiet.setEnabled(false);
    }

    public void loadTongDoanhThu() {
        txtDoanhThu.setEditable(false);
        int tongDoanhThu = BillardsDAO.getTongDoanhThu();
        txtDoanhThu.setText(tongDoanhThu + " đ");
    }

    public void loadTableNhanVien() {
        DefaultTableModel model = (DefaultTableModel) tblbangNV.getModel();
        model.setRowCount(0);

        BillardsDAO dao = new BillardsDAO();
        List<NhanVien> list = dao.getAllNhanVien();

        for (NhanVien nv : list) {
            model.addRow(new Object[]{
                nv.getHoTen(),
                nv.getTenTaiKhoan(),
                nv.getCaLamViec(),
                nv.getRole() == 1 ? "Quản Lý" : "Nhân viên",
                nv.getMatKhau()
            });
        }

    }

    public void loadTableNhapHang() {
        DefaultTableModel model = (DefaultTableModel) tblbangNhapHang.getModel();
        model.setRowCount(0);

        BillardsDAO dao = new BillardsDAO();
        List<NhapHang> list = dao.getAllNhapHang();

        int tongGiaTotal = 0;

        for (NhapHang nh : list) {
            int tongGia = nh.getTongGia();

            model.addRow(new Object[]{
                nh.getId(),
                nh.getTenHang(),
                nh.getNgayNhap(),
                nh.getSoLuong(),
                nh.getGiaDon(),
                tongGia
            });

            tongGiaTotal += tongGia;
        }
        txttongChiNhap.setText(String.valueOf(tongGiaTotal));
    }

    private void clearFormNhanVien() {
        txthoTenNV.setText("");
        txttenTaiKhoanNV.setText("");
        txtmatKhauNV.setText("");
        rdocaSang.setSelected(true);
        rdocaToi.setSelected(true);
    }

    public void clearFormNhapHang() {
        txthangNhap.setText("");
        txtngayNhap.setText("");
        txtsoLuongNhap.setText("");
        txtgiaDonNhap.setText("" + " đ");
        txttongGiaDonNhap.setText("");
    }

    private boolean validateFormNhapHang() {
        String tenHang = txthangNhap.getText().trim();
        String soLuongStr = txtsoLuongNhap.getText().trim();
        String giaDonStr = txtgiaDonNhap.getText().trim();
        String ngayNhap = txtngayNhap.getText().trim();

        if (tenHang.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập tên hàng.", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            txthangNhap.requestFocus();
            return false;
        }

        if (soLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng.", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            txtsoLuongNhap.requestFocus();
            return false;
        }

        try {
            int soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên dương!", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                txtsoLuongNhap.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Số lượng phải là số nguyên!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            txtsoLuongNhap.requestFocus();
            return false;
        }

        if (giaDonStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập giá đơn.", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            txtgiaDonNhap.requestFocus();
            return false;
        }

        try {
            int giaDon = Integer.parseInt(giaDonStr);
            if (giaDon <= 0) {
                JOptionPane.showMessageDialog(null, "Giá đơn phải là số nguyên dương!", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                txtgiaDonNhap.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Giá đơn phải là số nguyên!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            txtgiaDonNhap.requestFocus();
            return false;
        }

        if (ngayNhap.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập ngày nhập.", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            txtngayNhap.requestFocus();
            return false;
        }

        return true;
    }

    private boolean validateFormNhanVien() {
        String hoTen = txthoTenNV.getText().trim();
        String taiKhoan = txttenTaiKhoanNV.getText().trim();
        String matKhauStr = txtmatKhauNV.getText().trim();

        if (hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!");
            return false;
        }

        if (taiKhoan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên tài khoản!");
            return false;
        }

        if (matKhauStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!");
            return false;
        }

        if (!rdocaSang.isSelected() && !rdocaToi.isSelected() && !rdochieu.isSelected() && !rdofull.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ca làm việc!");
            return false;
        }

        if (!rdoquanLy.isSelected() && !rdonhanVien.isSelected()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vai trò!");
            return false;
        }

        try {
            int matKhau = Integer.parseInt(matKhauStr);
            if (matKhau <= 0) {
                JOptionPane.showMessageDialog(this, "Mật khẩu phải là số nguyên dương!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải là số nguyên!");
            return false;
        }

        return true;
    }

    private boolean validateFormGay() {
        String tenGay = txtTenGay.getText().trim();
        String giaThue = txtGiaThue.getText().trim();

        if (tenGay.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập tên gậy!");
            return false;
        }

        if (giaThue.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập giá thuê!");
            return false;
        }

        try {
            int gia = Integer.parseInt(giaThue);
            if (gia <= 0) {
                JOptionPane.showMessageDialog(rootPane, "Giá thuê phải là số nguyên dương!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Giá thuê phải là số nguyên!");
            return false;
        }

        return true;
    }

    private boolean validateFormThucDon() {
        String tenMon = txtTenMon.getText().trim();
        String giaBan = txtGiaBan.getText().trim();

        if (tenMon.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập tên món!");
            return false;
        }

        if (giaBan.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập giá bán!");
            return false;
        }

        try {
            int gia = Integer.parseInt(giaBan);
            if (gia <= 0) {
                JOptionPane.showMessageDialog(rootPane, "Giá bán phải là số nguyên dương!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Giá bán phải là số nguyên!");
            return false;
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        checkbox1 = new java.awt.Checkbox();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        panel1 = new java.awt.Panel();
        ChiTiet = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        rdoTatCa = new javax.swing.JRadioButton();
        rdoDangSuDung = new javax.swing.JRadioButton();
        rdoConLai = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        btnTable1 = new javax.swing.JButton();
        btnTable2 = new javax.swing.JButton();
        btnTable3 = new javax.swing.JButton();
        btnTable4 = new javax.swing.JButton();
        btnTable5 = new javax.swing.JButton();
        btnTable6 = new javax.swing.JButton();
        btnAddTables = new javax.swing.JButton();
        tablePanel = new javax.swing.JPanel();
        panel2 = new java.awt.Panel();
        jLabel3 = new javax.swing.JLabel();
        txtTenMon = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lblThucDon = new javax.swing.JTable();
        lbAnhMon = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panel3 = new java.awt.Panel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChitiet = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        txtDoanhThu = new javax.swing.JTextField();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        txtTrang = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        txtngayNhap = new javax.swing.JTextField();
        btnaddNhapHang = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        btnupdateNhapHang = new javax.swing.JButton();
        txthangNhap = new javax.swing.JTextField();
        btndeleteNhapHang = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        txtsoLuongNhap = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txttongChiNhap = new javax.swing.JTextField();
        txtgiaDonNhap = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblbangNhapHang = new javax.swing.JTable();
        jLabel23 = new javax.swing.JLabel();
        txttongGiaDonNhap = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lblGayCT = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        lbAnh = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTenGay = new javax.swing.JTextField();
        txtGiaThue = new javax.swing.JTextField();
        btnThemGay = new javax.swing.JButton();
        btnSuaGay = new javax.swing.JButton();
        btnXoaGay = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        btndeleteNV = new javax.swing.JButton();
        txtmatKhauNV = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        rdocaSang = new javax.swing.JRadioButton();
        rdocaToi = new javax.swing.JRadioButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblbangNV = new javax.swing.JTable();
        rdofull = new javax.swing.JRadioButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        rdonhanVien = new javax.swing.JRadioButton();
        txttenTaiKhoanNV = new javax.swing.JTextField();
        rdoquanLy = new javax.swing.JRadioButton();
        jLabel26 = new javax.swing.JLabel();
        btnthemNV = new javax.swing.JButton();
        txthoTenNV = new javax.swing.JTextField();
        btnupdateNV = new javax.swing.JButton();
        rdochieu = new javax.swing.JRadioButton();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 854, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 815, Short.MAX_VALUE)
        );

        checkbox1.setLabel("checkbox1");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("USER");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/replace.png"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(0, 204, 102));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/menu.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("ADMIN");

        jTabbedPane2.setBackground(new java.awt.Color(236, 236, 236));

        panel1.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N

        javax.swing.GroupLayout ChiTietLayout = new javax.swing.GroupLayout(ChiTiet);
        ChiTiet.setLayout(ChiTietLayout);
        ChiTietLayout.setHorizontalGroup(
            ChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 434, Short.MAX_VALUE)
        );
        ChiTietLayout.setVerticalGroup(
            ChiTietLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 489, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(236, 236, 236));

        buttonGroup1.add(rdoTatCa);
        rdoTatCa.setText("Tất cả");
        rdoTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTatCaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoDangSuDung);
        rdoDangSuDung.setText("Đang sử dụng");
        rdoDangSuDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDangSuDungActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoConLai);
        rdoConLai.setText("Còn lại");
        rdoConLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoConLaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdoTatCa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoDangSuDung)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoConLai)
                .addContainerGap(340, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoTatCa)
                    .addComponent(rdoDangSuDung)
                    .addComponent(rdoConLai)))
        );

        jPanel3.setLayout(new java.awt.GridLayout(5, 5, 45, 37));

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
        jPanel3.add(btnTable1);

        btnTable2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8971902f-18fb-47e0-890c-c5750abf50e0.png"))); // NOI18N
        btnTable2.setText("Bàn 2");
        btnTable2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTable2.setPreferredSize(new java.awt.Dimension(70, 70));
        btnTable2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnTable2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(btnTable2);

        btnTable3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTable3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8971902f-18fb-47e0-890c-c5750abf50e0.png"))); // NOI18N
        btnTable3.setText("Bàn 3");
        btnTable3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTable3.setPreferredSize(new java.awt.Dimension(70, 70));
        btnTable3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnTable3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(btnTable3);

        btnTable4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTable4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8971902f-18fb-47e0-890c-c5750abf50e0.png"))); // NOI18N
        btnTable4.setText("Bàn 4");
        btnTable4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTable4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnTable4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(btnTable4);

        btnTable5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTable5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8971902f-18fb-47e0-890c-c5750abf50e0.png"))); // NOI18N
        btnTable5.setText("Bàn 5");
        btnTable5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTable5.setPreferredSize(new java.awt.Dimension(70, 70));
        btnTable5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnTable5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(btnTable5);

        btnTable6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTable6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/8971902f-18fb-47e0-890c-c5750abf50e0.png"))); // NOI18N
        btnTable6.setText("Bàn 6");
        btnTable6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTable6.setPreferredSize(new java.awt.Dimension(70, 70));
        btnTable6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnTable6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(btnTable6);

        btnAddTables.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        btnAddTables.setText("+");
        jPanel3.add(btnAddTables);

        tablePanel.setLayout(new java.awt.GridLayout(1, 0));
        jPanel3.add(tablePanel);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 356, Short.MAX_VALUE))
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                    .addContainerGap(449, Short.MAX_VALUE)
                    .addComponent(ChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(49, 49, 49)))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panel1Layout.createSequentialGroup()
                    .addGap(80, 80, 80)
                    .addComponent(ChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(26, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("Phòng Bàn", new javax.swing.ImageIcon(getClass().getResource("/icon/iconhongBan.png")), panel1); // NOI18N

        jLabel3.setText("Giá:");

        btnThem.setBackground(new java.awt.Color(0, 204, 0));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 0, 255));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(153, 153, 153));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Thực đơn:");

        lblThucDon.setModel(new javax.swing.table.DefaultTableModel(
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
        lblThucDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblThucDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lblThucDon);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Chi Tiết");

        jLabel2.setText("Tên món:");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panel2Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 163, Short.MAX_VALUE))
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(29, 29, 29)
                                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addGap(178, 178, 178)
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbAnhMon, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120)))
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa))
                .addGap(25, 25, 25))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel1)
                        .addGap(46, 46, 46)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(btnThem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSua)
                                .addGap(54, 54, 54)
                                .addComponent(btnXoa))
                            .addComponent(lbAnhMon, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Thực Đơn", new javax.swing.ImageIcon(getClass().getResource("/icon/iconThucDon.png")), panel2); // NOI18N

        jTabbedPane3.setToolTipText("");
        jTabbedPane3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        tblChitiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane2.setViewportView(tblChitiet);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Tổng Doanh Thu");

        btnPre.setText("<");
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        txtTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTrangActionPerformed(evt);
            }
        });

        jLabel10.setText("/5");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(707, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnPre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext)
                        .addGap(33, 33, 33))))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 883, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPre)
                    .addComponent(btnNext)
                    .addComponent(txtTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("DOANH THU", jPanel7);

        btnaddNhapHang.setText("ADD");
        btnaddNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddNhapHangActionPerformed(evt);
            }
        });

        jLabel20.setText("Hàng nhập");

        btnupdateNhapHang.setText("Update");
        btnupdateNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateNhapHangActionPerformed(evt);
            }
        });

        btndeleteNhapHang.setText("Delete");
        btndeleteNhapHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteNhapHangActionPerformed(evt);
            }
        });

        jLabel21.setText("Số lượng");

        jButton4.setText("Tính tổng chi tiêu");

        jLabel25.setText("Tổng chi");

        jLabel22.setText("Giá đơn");

        tblbangNhapHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tblbangNhapHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbangNhapHangMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblbangNhapHang);

        jLabel23.setText("Tổng giá đơn");

        jLabel24.setText("Ngày nhập");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(jLabel24)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtngayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel20)
                                        .addComponent(jLabel21)
                                        .addComponent(jLabel22))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txthangNhap)
                                        .addComponent(txtsoLuongNhap)
                                        .addComponent(txtgiaDonNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)))
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel25)
                                    .addGap(30, 30, 30)
                                    .addComponent(txttongChiNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(txttongGiaDonNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(145, 145, 145)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnaddNhapHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnupdateNhapHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btndeleteNhapHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(647, 647, 647)
                                .addComponent(jButton4)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txthangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnaddNhapHang))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtsoLuongNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnupdateNhapHang))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtgiaDonNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndeleteNhapHang))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jLabel24)
                    .addComponent(txtngayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txttongGiaDonNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txttongChiNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("CHI TIÊU, NHẬP HÀNG", jPanel9);

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Thống Kê", new javax.swing.ImageIcon(getClass().getResource("/icon/iconThongKe.png")), panel3); // NOI18N

        jPanel1.setBackground(new java.awt.Color(235, 235, 235));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Gậy");

        lblGayCT.setModel(new javax.swing.table.DefaultTableModel(
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
        lblGayCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblGayCTMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(lblGayCT);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Chi tiết");

        jLabel6.setText("Tên gậy:");

        jLabel7.setText("Giá:");

        btnThemGay.setBackground(new java.awt.Color(0, 204, 51));
        btnThemGay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemGay.setForeground(new java.awt.Color(255, 255, 255));
        btnThemGay.setText("Thêm");
        btnThemGay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemGayActionPerformed(evt);
            }
        });

        btnSuaGay.setBackground(new java.awt.Color(0, 51, 255));
        btnSuaGay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSuaGay.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaGay.setText("Sửa");
        btnSuaGay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaGayActionPerformed(evt);
            }
        });

        btnXoaGay.setBackground(new java.awt.Color(153, 153, 153));
        btnXoaGay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaGay.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaGay.setText("Xóa");
        btnXoaGay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaGayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(324, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(109, 109, 109)
                .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoaGay)
                    .addComponent(btnSuaGay)
                    .addComponent(btnThemGay))
                .addGap(25, 25, 25))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenGay, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGiaThue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel5)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtTenGay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(105, 105, 105)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel7)
                                            .addComponent(txtGiaThue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnThemGay)
                                        .addGap(50, 50, 50)
                                        .addComponent(btnSuaGay)))
                                .addGap(25, 25, 25)
                                .addComponent(btnXoaGay))
                            .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Thuê Gậy", new javax.swing.ImageIcon(getClass().getResource("/icon/iconThueGay.png")), jPanel1); // NOI18N

        jLabel16.setText("Mật khẩu");

        btndeleteNV.setText("Delete");
        btndeleteNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteNVActionPerformed(evt);
            }
        });

        jLabel17.setText("Ca làm");

        buttonGroup2.add(rdocaSang);
        rdocaSang.setText("Sáng");

        buttonGroup2.add(rdocaToi);
        rdocaToi.setText("Tối");

        tblbangNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Họ tên", "Tên tài khoản", "Ca làm", "Quyền truy cập", "Mật Khẩu"
            }
        ));
        tblbangNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbangNVMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblbangNV);

        buttonGroup2.add(rdofull);
        rdofull.setText("Full");

        jLabel18.setText("Quyền truy cập");

        jLabel19.setText("Tên tài khoản");

        buttonGroup1.add(rdonhanVien);
        rdonhanVien.setText("Nhân viên");

        buttonGroup1.add(rdoquanLy);
        rdoquanLy.setText("Quản Lý");

        jLabel26.setText("Họ tên");

        btnthemNV.setText("ADD");
        btnthemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemNVActionPerformed(evt);
            }
        });

        btnupdateNV.setText("Update");
        btnupdateNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateNVActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdochieu);
        rdochieu.setText("Chiều");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel26))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txttenTaiKhoanNV)
                                        .addComponent(txtmatKhauNV, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
                                    .addComponent(txthoTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(89, 89, 89)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnthemNV)
                                    .addComponent(btnupdateNV)
                                    .addComponent(btndeleteNV)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(rdocaSang)
                                .addGap(18, 18, 18)
                                .addComponent(rdocaToi)
                                .addGap(18, 18, 18)
                                .addComponent(rdochieu)
                                .addGap(18, 18, 18)
                                .addComponent(rdofull))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(rdonhanVien)
                                .addGap(18, 18, 18)
                                .addComponent(rdoquanLy)))))
                .addContainerGap(199, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthemNV)
                    .addComponent(jLabel26)
                    .addComponent(txthoTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnupdateNV)
                    .addComponent(jLabel19)
                    .addComponent(txttenTaiKhoanNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtmatKhauNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndeleteNV))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(rdocaSang)
                    .addComponent(rdocaToi)
                    .addComponent(rdofull)
                    .addComponent(rdochieu))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(rdonhanVien)
                    .addComponent(rdoquanLy))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95))
        );

        jTabbedPane2.addTab("Nhân VIên", new javax.swing.ImageIcon(getClass().getResource("/icon/iconnhanvien.png")), jPanel4); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 932, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(18, 18, 18))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 710, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        thucdons = BillardsDAO.getAllTD();
        loadTable(thucdons);
        thuegays = BillardsDAO.getAllTG();
        loadTable1(thuegays);
        hoadons = BillardsDAO.getHoaDonThongKe();
        loadTableHoaDon(hoadons);
        loadTongDoanhThu();
        loadTableNhanVien();
        loadTableNhapHang();
    }//GEN-LAST:event_formWindowOpened

    private void btnXoaGayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaGayActionPerformed
        int row = lblGayCT.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn một món để Xóa!");
            return;
        }

        int id = thuegays.get(row).getID();

        boolean checkXoa = BillardsDAO.XoaTG(id);
        if (checkXoa) {
            JOptionPane.showMessageDialog(rootPane, "Xóa thành công");
            thuegays = BillardsDAO.getAllTG();
            loadTable1(thuegays);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Xóa thất bại");
        }
    }//GEN-LAST:event_btnXoaGayActionPerformed

    private void btnSuaGayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaGayActionPerformed
        if (!validateFormGay()) {
            return;
        }

        int row = lblGayCT.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn một gậy để sửa!");
            return;
        }

        String TenGay = txtTenGay.getText();
        String GiaThue = txtGiaThue.getText();
        int id = thuegays.get(row).getID();

        if (TenGay.isEmpty() || GiaThue.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try {
            Double.parseDouble(GiaThue);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Giá bán phải là một số hợp lệ!");
            return;
        }

        boolean checkSua = BillardsDAO.SuaTG(TenGay, GiaThue, id);
        if (checkSua) {
            JOptionPane.showMessageDialog(rootPane, "Sửa thành công");
            thuegays = BillardsDAO.getAllTG();
            loadTable1(thuegays);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Sửa thất bại");
        }
    }//GEN-LAST:event_btnSuaGayActionPerformed

    private void btnThemGayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemGayActionPerformed
        if (!validateFormGay()) {
            return;
        }

        String TenGay = txtTenGay.getText();
        String GiaThue = txtGiaThue.getText();

        if (TenGay.isEmpty() || GiaThue.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try {
            Double.parseDouble(GiaThue);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Giá thuê phải là một số hợp lệ!");
            return;
        }

        boolean checkAdd = BillardsDAO.AddTG(TenGay, GiaThue);
        if (checkAdd) {
            JOptionPane.showMessageDialog(rootPane, "Thêm thành công");
            thuegays = BillardsDAO.getAllTG();
            loadTable1(thuegays);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Thêm thất bại");
        }
    }//GEN-LAST:event_btnThemGayActionPerformed

    private void lblGayCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblGayCTMouseClicked
        int row = lblGayCT.getSelectedRow();
        if (row != -1) {
            txtTenGay.setText(thuegays.get(row).getTenGay());
            txtGiaThue.setText(thuegays.get(row).getGiaThue());

            String hinhAnhPath = thuegays.get(row).getAnhGay();
            if (hinhAnhPath != null && !hinhAnhPath.isEmpty()) {
                try {
                    ImageIcon icon = new ImageIcon(hinhAnhPath);
                    Image image = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    lbAnh.setIcon(new ImageIcon(image));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi load hình ảnh: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    lbAnh.setIcon(null);
                }
            } else {
                lbAnh.setIcon(null);
            }
        }
        idselected = thuegays.get(row).getID();
    }//GEN-LAST:event_lblGayCTMouseClicked

    private void lblThucDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblThucDonMouseClicked
        int row = lblThucDon.getSelectedRow();
        if (row != -1) {
            ThucDon td = thucdons.get(row);
            txtTenMon.setText(thucdons.get(row).getTenMon());
            txtGiaBan.setText(thucdons.get(row).getGiaBan());
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
    }//GEN-LAST:event_lblThucDonMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int row = lblThucDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn một món để Xóa!");
            return;
        }

        int id = thucdons.get(row).getID();

        boolean checkSua = BillardsDAO.XoaTD(id);
        if (checkSua) {
            JOptionPane.showMessageDialog(rootPane, "Xóa thành công");
            thucdons = BillardsDAO.getAllTD();
            loadTable(thucdons);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Xóa thất bại");
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (!validateFormThucDon()) {
            return;
        }
        int row = lblThucDon.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn một món để sửa!");
            return;
        }

        String TenMon = txtTenMon.getText();
        String GiaBan = txtGiaBan.getText();
        int id = thucdons.get(row).getID();

        if (TenMon.isEmpty() || GiaBan.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try {
            Double.parseDouble(GiaBan);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Giá bán phải là một số hợp lệ!");
            return;
        }

        boolean checkAdd = BillardsDAO.SuaTD(TenMon, GiaBan, id);
        if (checkAdd) {
            JOptionPane.showMessageDialog(rootPane, "Sửa thành công");
            thucdons = BillardsDAO.getAllTD();
            loadTable(thucdons);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Sửa thất bại");
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (!validateFormThucDon()) {
            return;
        }
        String TenMon = txtTenMon.getText();
        String GiaBan = txtGiaBan.getText();

        if (TenMon.isEmpty() || GiaBan.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try {
            Double.parseDouble(GiaBan);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Giá bán phải là một số hợp lệ!");
            return;
        }

        boolean checkAdd = BillardsDAO.AddTD(TenMon, GiaBan);
        if (checkAdd) {
            JOptionPane.showMessageDialog(rootPane, "Thêm thành công");
            thucdons = BillardsDAO.getAllTD();
            loadTable(thucdons);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Thêm thất bại");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void rdoConLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoConLaiActionPerformed

    }//GEN-LAST:event_rdoConLaiActionPerformed

    private void rdoDangSuDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDangSuDungActionPerformed

    }//GEN-LAST:event_rdoDangSuDungActionPerformed

    private void rdoTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTatCaActionPerformed

    }//GEN-LAST:event_rdoTatCaActionPerformed

    private void txtTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTrangActionPerformed
        int newPage = Integer.parseInt(txtTrang.getText());
        if (newPage >= 1 && newPage <= totalPage) {
            currentPage = newPage;
            hoadons = BillardsDAO.getHoaDonThongKe();
            loadTableHoaDon(hoadons);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Vui long nha so tu 1 den " + totalPage, " Loi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtTrangActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (currentPage < totalPage) {
            currentPage++;
            hoadons = BillardsDAO.getHoaDonThongKe();
            loadTableHoaDon(hoadons);
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        if (currentPage > 1) {
            currentPage--;
            hoadons = BillardsDAO.getHoaDonThongKe();
            loadTableHoaDon(hoadons);
        }
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnaddNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddNhapHangActionPerformed

        if (!validateFormNhapHang()) {
            return;
        }

        String tenHang = txthangNhap.getText().trim();
        String ngayNhap = txtngayNhap.getText().trim();
        String thangNhap = txthangNhap.getText().trim();
        int soLuong = Integer.parseInt(txtsoLuongNhap.getText().trim());
        int giaDon = Integer.parseInt(txtgiaDonNhap.getText().trim());

        int tongGia = soLuong * giaDon;
        txttongGiaDonNhap.setText(String.valueOf(tongGia));

        NhapHang nh = new NhapHang();
        nh.setTenHang(tenHang);
        nh.setNgayNhap(ngayNhap);
        nh.setSoLuong(soLuong);
        nh.setGiaDon(giaDon);
        nh.setTongGia(tongGia);
        nh.setThangNhap(thangNhap);

        BillardsDAO dao = new BillardsDAO();
        boolean success = dao.insert(nh);

        if (success) {
            JOptionPane.showMessageDialog(null, "Thêm mới thành công.");
            loadTableNhapHang();
            clearFormNhapHang();
        } else {
            JOptionPane.showMessageDialog(null, "Thêm mới thất bại.");
        }


    }//GEN-LAST:event_btnaddNhapHangActionPerformed

    private void btnupdateNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateNhapHangActionPerformed
        int row = tblbangNhapHang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một đơn nhập hàng để cập nhật.");
            return;
        }

        if (!validateFormNhapHang()) {
            return;
        }

        String tenHang = txthangNhap.getText().trim();
        String ngayNhap = txtngayNhap.getText().trim();
        String thangNhap = txthangNhap.getText().trim();
        int soLuong = Integer.parseInt(txtsoLuongNhap.getText().trim());
        int giaDon = Integer.parseInt(txtgiaDonNhap.getText().trim());
        int tongGia = soLuong * giaDon;

        txttongGiaDonNhap.setText(String.valueOf(tongGia));

        int id = (int) tblbangNhapHang.getValueAt(row, 0);

        NhapHang nh = new NhapHang();
        nh.setId(id);
        nh.setTenHang(tenHang);
        nh.setNgayNhap(ngayNhap);
        nh.setSoLuong(soLuong);
        nh.setGiaDon(giaDon);
        nh.setTongGia(tongGia);
        nh.setThangNhap(thangNhap);

        BillardsDAO dao = new BillardsDAO();
        boolean success = dao.update(nh);

        if (success) {
            JOptionPane.showMessageDialog(null, "Cập nhật thành công.");
            loadTableNhapHang();
            clearFormNhapHang();
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại.");
        }
    }//GEN-LAST:event_btnupdateNhapHangActionPerformed

    private void btndeleteNhapHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteNhapHangActionPerformed
        int row = tblbangNhapHang.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một đơn nhập hàng để xóa.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa nhập hàng này?", "Xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {

            Object value = tblbangNhapHang.getValueAt(row, 0);
            int id = -1;

            if (value != null) {
                try {
                    if (value instanceof String) {
                        id = Integer.parseInt(value.toString().trim());
                    } else if (value instanceof Integer) {
                        id = (Integer) value;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "ID không hợp lệ.");
                    return;
                }
            }

            if (id != -1) {
                BillardsDAO dao = new BillardsDAO();
                boolean success = dao.delete(id);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công.");
                    loadTableNhapHang();
                    clearFormNhapHang();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "ID không hợp lệ.");
            }
        }

    }//GEN-LAST:event_btndeleteNhapHangActionPerformed

    private void tblbangNhapHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbangNhapHangMouseClicked
        int row = tblbangNhapHang.getSelectedRow();
        if (row != -1) {
            String tenHang = tblbangNhapHang.getValueAt(row, 1).toString();
            String ngayNhap = tblbangNhapHang.getValueAt(row, 2).toString();
            int soLuong = Integer.parseInt(tblbangNhapHang.getValueAt(row, 3).toString());
            int giaDon = Integer.parseInt(tblbangNhapHang.getValueAt(row, 4).toString());
            int tongGia = Integer.parseInt(tblbangNhapHang.getValueAt(row, 5).toString());
            txthangNhap.setText(tenHang);
            txtngayNhap.setText(ngayNhap);
            txtsoLuongNhap.setText(String.valueOf(soLuong));
            txtgiaDonNhap.setText(String.valueOf(giaDon));
            txttongGiaDonNhap.setText(String.valueOf(tongGia));
        }
    }//GEN-LAST:event_tblbangNhapHangMouseClicked

    private void btndeleteNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteNVActionPerformed
        int row = tblbangNV.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản cần xóa.");
            return;
        }
        String tenTaiKhoan = tblbangNV.getValueAt(row, 1).toString();
        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn xóa tài khoản: " + tenTaiKhoan + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            BillardsDAO dao = new BillardsDAO();
            boolean success = dao.deleteNhanVien(tenTaiKhoan);
            if (success) {
                JOptionPane.showMessageDialog(null, "Xóa tài khoản thành công.");
                loadTableNhanVien();
                clearFormNhanVien();
            } else {
                JOptionPane.showMessageDialog(null, "Xóa tài khoản thất bại.");
            }
        }
    }//GEN-LAST:event_btndeleteNVActionPerformed

    private void tblbangNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbangNVMouseClicked
        int row = tblbangNV.getSelectedRow();
        if (row == -1) {
            return;
        }
        String hoTen = tblbangNV.getValueAt(row, 0).toString();
        String taiKhoan = tblbangNV.getValueAt(row, 1).toString();
        String caLam = tblbangNV.getValueAt(row, 2).toString();
        String vaiTro = tblbangNV.getValueAt(row, 3).toString();
        String matKhau = tblbangNV.getValueAt(row, 4).toString();
        txthoTenNV.setText(hoTen);
        txttenTaiKhoanNV.setText(taiKhoan);
        txtmatKhauNV.setText(matKhau);
        if (caLam.equalsIgnoreCase("Ca sáng")) {
            rdocaSang.setSelected(true);
        } else if (caLam.equalsIgnoreCase("Ca tối")) {
            rdocaToi.setSelected(true);
        } else if (caLam.equalsIgnoreCase("Ca chiều")) {
            rdochieu.setSelected(true);
        } else if (caLam.equalsIgnoreCase("Full")) {
            rdofull.setSelected(true);
        }
        if (vaiTro.equalsIgnoreCase("Admin") || vaiTro.equalsIgnoreCase("Quản Lý")) {
            rdoquanLy.setSelected(true);
        } else {
            rdonhanVien.setSelected(true);
        }
    }//GEN-LAST:event_tblbangNVMouseClicked

    private void btnthemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemNVActionPerformed
        if (!validateFormNhanVien()) {
            return;
        }

        String hoTen = txthoTenNV.getText().trim();
        String taiKhoan = txttenTaiKhoanNV.getText().trim();
        String matKhauStr = txtmatKhauNV.getText().trim();
        String caLam;
        if (rdocaSang.isSelected()) {
            caLam = "Ca sáng";
        } else if (rdocaToi.isSelected()) {
            caLam = "Ca tối";
        } else if (rdochieu.isSelected()) {
            caLam = "Ca chiều";
        } else if (rdofull.isSelected()) {
            caLam = "Full";
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ca làm việc!");
            return;
        }
        int role;
        if (rdoquanLy.isSelected()) {
            role = 1;
        } else if (rdonhanVien.isSelected()) {
            role = 0;
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn vai trò!");
            return;
        }
        if (hoTen.isEmpty() || taiKhoan.isEmpty() || matKhauStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        int matKhau;
        try {
            matKhau = Integer.parseInt(matKhauStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải là số!");
            return;
        }
        NhanVien nv = new NhanVien(hoTen, taiKhoan, caLam, role, matKhau);
        BillardsDAO dao = new BillardsDAO();
        boolean themThanhCong = dao.themNhanVien(nv);
        if (themThanhCong) {
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
            loadTableNhanVien();
            clearFormNhanVien();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!");
        }
    }//GEN-LAST:event_btnthemNVActionPerformed

    private void btnupdateNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateNVActionPerformed
        if (!validateFormNhanVien()) {
            return;
        }

        int row = tblbangNV.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một tài khoản để cập nhật.");
            return;
        }
        String hoTen = txthoTenNV.getText().trim();
        String taiKhoan = txttenTaiKhoanNV.getText().trim();
        int matKhau = 0;
        try {
            matKhau = Integer.parseInt(txtmatKhauNV.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Mật khẩu phải là số nguyên.");
            return;
        }
        String caLam = "";
        if (rdocaSang.isSelected()) {
            caLam = "Ca sáng";
        } else if (rdocaToi.isSelected()) {
            caLam = "Ca tối";
        } else if (rdochieu.isSelected()) {
            caLam = "Ca chiều";
        } else if (rdofull.isSelected()) {
            caLam = "Full";
        }
        int role = rdoquanLy.isSelected() ? 1 : 0;
        NhanVien nv = new NhanVien();
        nv.setHoTen(hoTen);
        nv.setTenTaiKhoan(taiKhoan);
        nv.setMatKhau(matKhau);
        nv.setCaLamViec(caLam);
        nv.setRole(role);
        BillardsDAO dao = new BillardsDAO();
        boolean success = dao.updateNhanVien(nv);
        if (success) {
            JOptionPane.showMessageDialog(null, "Cập nhật tài khoản thành công.");
            loadTableNhanVien();
            clearFormNhanVien();
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại.");
        }
    }//GEN-LAST:event_btnupdateNVActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminFrame1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChiTiet;
    private javax.swing.JButton btnAddTables;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaGay;
    private javax.swing.JButton btnTable1;
    private javax.swing.JButton btnTable2;
    private javax.swing.JButton btnTable3;
    private javax.swing.JButton btnTable4;
    private javax.swing.JButton btnTable5;
    private javax.swing.JButton btnTable6;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemGay;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaGay;
    private javax.swing.JButton btnaddNhapHang;
    private javax.swing.JButton btndeleteNV;
    private javax.swing.JButton btndeleteNhapHang;
    private javax.swing.JButton btnthemNV;
    private javax.swing.JButton btnupdateNV;
    private javax.swing.JButton btnupdateNhapHang;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private java.awt.Checkbox checkbox1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lbAnh;
    private javax.swing.JLabel lbAnhMon;
    private javax.swing.JTable lblGayCT;
    private javax.swing.JTable lblThucDon;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    private java.awt.Panel panel3;
    private javax.swing.JRadioButton rdoConLai;
    private javax.swing.JRadioButton rdoDangSuDung;
    private javax.swing.JRadioButton rdoTatCa;
    private javax.swing.JRadioButton rdocaSang;
    private javax.swing.JRadioButton rdocaToi;
    private javax.swing.JRadioButton rdochieu;
    private javax.swing.JRadioButton rdofull;
    private javax.swing.JRadioButton rdonhanVien;
    private javax.swing.JRadioButton rdoquanLy;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JTable tblChitiet;
    private javax.swing.JTable tblbangNV;
    private javax.swing.JTable tblbangNhapHang;
    private javax.swing.JTextField txtDoanhThu;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaThue;
    private javax.swing.JTextField txtTenGay;
    private javax.swing.JTextField txtTenMon;
    private javax.swing.JTextField txtTrang;
    private javax.swing.JTextField txtgiaDonNhap;
    private javax.swing.JTextField txthangNhap;
    private javax.swing.JTextField txthoTenNV;
    private javax.swing.JTextField txtmatKhauNV;
    private javax.swing.JTextField txtngayNhap;
    private javax.swing.JTextField txtsoLuongNhap;
    private javax.swing.JTextField txttenTaiKhoanNV;
    private javax.swing.JTextField txttongChiNhap;
    private javax.swing.JTextField txttongGiaDonNhap;
    // End of variables declaration//GEN-END:variables
}
