/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.DichVuChiTiet;
import Model.HoaDonThongKe;
import Model.NhanVien;
import Model.NhapHang;
import Model.ThucDon;
import Model.ThueGay;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.awt.print.PrinterException;
import javax.swing.JTextArea;
import java.awt.Font;

/**
 *
 * @author Administrator
 */
public class BillardsDAO {

    static String connecString = "jdbc:sqlserver://DESKTOP-860OOP9;databaseName=DATN7;user=sa;password=123;trustServerCertificate=true";
    static String USER = "sa";
    static String PASSWORD = "123";
    
    static String getAllTD = "select * from ThucDon";
    static String creatTD = "insert into ThucDon (TenMon, GiaBan) values (?, ?)";
    static String updateTD = "update ThucDon set TenMon = ?, GiaBan = ? where ID = ? ";
    static String deleteTD = "delete from ThucDon where ID = ?";
    static String getAllTG = "select * from ThueGay";
    static String creatTG = "insert into ThueGay (TenGay, GiaThue) values (?, ?)";
    static String updateTG = "update ThueGay set TenGay = ?, GiaThue = ? where ID = ? ";
    static String deleteTG = "delete from ThueGay where ID = ?";
    static String getAllHD = "select * from HoaDon";
    static String joinCTHD = " SELECT ThoiGianTao,TongTien FROM HoaDon";
    static String createDT = "insert into HoaDon (ThoiGianTao, TongTien) values (?, ?)";
    static String getTongDT = "SELECT SUM(TongTien) FROM HoaDon";
    static String SQL_GET_ALL_NHANVIEN = "SELECT * FROM NhanVien";
    static String SQL_INSERT_NHANVIEN = "INSERT INTO NhanVien (HoTen, TenTaiKhoan, MatKhau, CaLamViec, Role) VALUES (?, ?, ?, ?, ?)";
    static String SQL_UPDATE_NHANVIEN = "UPDATE NhanVien SET HoTen = ?, MatKhau = ?, CaLamViec = ?, Role = ? WHERE TenTaiKhoan = ?";
    static String SQL_DELETE_NHANVIEN = "DELETE FROM NhanVien WHERE TenTaiKhoan = ?";
    static String INSERT_SQL = "INSERT INTO NhapHang (TenHang, NgayNhap, SoLuong, GiaDon, TongGia, ThangNhap) VALUES (?, ?, ?, ?, ?, ?)";
    static String SELECT_ALL = "SELECT * FROM NhapHang";
    static String UPDATE_SQL = "UPDATE NhapHang SET TenHang=?, NgayNhap=?, SoLuong=?, GiaDon=?, TongGia=?, ThangNhap=? WHERE ID=?";
    static String DELETE_SQL = "DELETE FROM NhapHang WHERE ID=?";
    static String DVCT = "SELECT * FROM DichVuChiTiet WHERE Ban = ?";
    static String DVCT1 = "INSERT INTO DichVuChiTiet (Ban, TenHang, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?)";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(connecString, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static NhanVien dangNhap(String TenTaiKhoan, int MatKhau) {
        String query = "SELECT HoTen, [TenTaiKhoan], CaLamViec, Role, MatKhau FROM NhanVien WHERE [TenTaiKhoan] = ? AND MatKhau = ?";

        try ( Connection conn = getConnection();  PreparedStatement stm = conn.prepareStatement(query)) {

            stm.setString(1, TenTaiKhoan);
            stm.setInt(2, MatKhau);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                return new NhanVien(
                        rs.getString("HoTen"),
                        rs.getString("TenTaiKhoan"),
                        rs.getString("CaLamViec"),
                        rs.getInt("Role"),
                        rs.getInt("MatKhau")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<ThucDon> getAllTD() {
        ArrayList<ThucDon> thucdons = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(connecString);
            PreparedStatement stm = con.prepareStatement(getAllTD);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ThucDon td = new ThucDon();
                td.setID(rs.getInt("ID"));
                td.setTenMon(rs.getString("TenMon"));
                td.setGiaBan(rs.getString("GiaBan"));
                td.setAnhMon(rs.getString("AnhMon"));
                thucdons.add(td);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thucdons;
    }

    public static boolean AddTD(String TenMon, String GiaBan) {
        try {
            Connection con = DriverManager.getConnection(connecString);
            PreparedStatement stm = con.prepareStatement(creatTD);
            stm.setString(1, TenMon);
            stm.setString(2, GiaBan);
            int row = stm.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public static boolean SuaTD(String TenMon, String GiaBan, int ID) {
        try {
            Connection con = DriverManager.getConnection(connecString);
            PreparedStatement stm = con.prepareStatement(updateTD);
            stm.setString(1, TenMon);
            stm.setString(2, GiaBan);
            stm.setInt(3, ID);
            int row = stm.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public static boolean XoaTD(int ID) {
        try {
            Connection con = DriverManager.getConnection(connecString);
            PreparedStatement stm = con.prepareStatement(deleteTD);
            stm.setInt(1, ID);
            int row = stm.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public static ArrayList<ThueGay> getAllTG() {
        ArrayList<ThueGay> thuegays = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(connecString);
            PreparedStatement stm = con.prepareStatement(getAllTG);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ThueGay tg = new ThueGay();
                tg.setID(rs.getInt("ID"));
                tg.setTenGay(rs.getString("TenGay"));
                tg.setGiaThue(rs.getString("GiaThue"));
                tg.setAnhGay(rs.getString("AnhGay"));
                thuegays.add(tg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thuegays;
    }

    public static boolean AddTG(String TenGay, String GiaThue) {
        try {
            Connection con = DriverManager.getConnection(connecString);
            PreparedStatement stm = con.prepareStatement(creatTG);
            stm.setString(1, TenGay);
            stm.setString(2, GiaThue);
            int row = stm.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public static boolean SuaTG(String TenGay, String GiaThue, int ID) {
        try {
            Connection con = DriverManager.getConnection(connecString);
            PreparedStatement stm = con.prepareStatement(updateTG);
            stm.setString(1, TenGay);
            stm.setString(2, GiaThue);
            stm.setInt(3, ID);
            int row = stm.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public static boolean XoaTG(int ID) {
        try {
            Connection con = DriverManager.getConnection(connecString);
            PreparedStatement stm = con.prepareStatement(deleteTG);
            stm.setInt(1, ID);
            int row = stm.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public static boolean ThueGay(String tenGay, double giaThuee) {
        try ( Connection con = DriverManager.getConnection(connecString);  PreparedStatement stm = con.prepareStatement(creatTG)) {

            stm.setString(1, tenGay);
            stm.setDouble(2, giaThuee);
            int row = stm.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean newDT(Date ThoiGianTao, int TongTien) {
        try {
            Connection con = DriverManager.getConnection(connecString);
            PreparedStatement stm = con.prepareStatement(createDT);
            stm.setDate(1, ThoiGianTao);
            stm.setInt(2, TongTien);
            int row = stm.executeUpdate();
            return row > 0;
        } catch (Exception e) {
        }
        return false;
    }

    public static ArrayList<HoaDonThongKe> getHoaDonThongKe() {
        ArrayList<HoaDonThongKe> list = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(connecString);
            PreparedStatement stm = con.prepareStatement(joinCTHD);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                HoaDonThongKe hd = new HoaDonThongKe();
                hd.setThoiGianTao(rs.getDate("ThoiGianTao"));
                hd.setTongTien(rs.getInt("TongTien"));
                list.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void createBillsTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS bills (
            id INT AUTO_INCREMENT PRIMARY KEY,
            table_id INT NOT NULL,
            start_time DATETIME NOT NULL,
            end_time DATETIME NOT NULL,
            service_total DOUBLE NOT NULL,
            hourly_total DOUBLE NOT NULL,
            total DOUBLE NOT NULL,
            bill_date DATETIME NOT NULL
        )
        """;
        try ( Connection conn = getConnection();  Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Bảng bills đã được tạo thành công!");
        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo bảng bills: " + e.getMessage());
        }
    }

    public static int getTongDoanhThu() {
        int tongDoanhThu = 0;
        try {
            Connection con = DriverManager.getConnection(connecString);
            PreparedStatement stm = con.prepareStatement(getTongDT);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                tongDoanhThu = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return tongDoanhThu;
    }

    public boolean themNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (HoTen, TenTaiKhoan, CaLamViec, Role, MatKhau) VALUES (?, ?, ?, ?, ?)";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getTenTaiKhoan());
            ps.setString(3, nv.getCaLamViec());
            ps.setInt(4, nv.getRole());
            ps.setInt(5, nv.getMatKhau());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setHoTen(rs.getString("HoTen"));
                nv.setTenTaiKhoan(rs.getString("TenTaiKhoan"));
                nv.setCaLamViec(rs.getString("CaLamViec"));
                nv.setRole(rs.getInt("Role"));
                nv.setMatKhau(rs.getInt("MatKhau"));
                list.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateNhanVien(NhanVien nv) {
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_NHANVIEN)) {

            ps.setString(1, nv.getHoTen());
            ps.setInt(2, nv.getMatKhau());
            ps.setString(3, nv.getCaLamViec());
            ps.setInt(4, nv.getRole());
            ps.setString(5, nv.getTenTaiKhoan());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                JOptionPane.showMessageDialog(null, "Không có tài khoản nào được cập nhật.");
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
            }

            return rows > 0;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật: " + ex.getMessage());
            return false;
        }
    }

    public boolean deleteNhanVien(String tenTaiKhoan) {
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(SQL_DELETE_NHANVIEN)) {

            ps.setString(1, tenTaiKhoan);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy tài khoản để xóa.");
            }

            return rows > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa nhân viên: " + e.getMessage());
            return false;
        }
    }

    public List<NhapHang> getAllNhapHang() {
        List<NhapHang> list = new ArrayList<>();
        String sql = "SELECT * FROM NhapHang";

        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhapHang nh = new NhapHang(
                        rs.getInt("ID"),
                        rs.getString("TenHang"),
                        rs.getString("NgayNhap"),
                        rs.getInt("SoLuong"),
                        rs.getInt("GiaDon"),
                        rs.getInt("TongGia"),
                        rs.getString("ThangNhap")
                );
                list.add(nh);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public boolean insert(NhapHang nh) {
        String INSERT_SQL = "INSERT INTO NhapHang (TenHang, NgayNhap, SoLuong, GiaDon, TongGia, ThangNhap) VALUES (?, ?, ?, ?, ?, ?)";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, nh.getTenHang());
            ps.setString(2, nh.getNgayNhap());
            ps.setInt(3, nh.getSoLuong());
            ps.setInt(4, nh.getGiaDon());
            ps.setInt(5, nh.getTongGia());
            ps.setString(6, nh.getThangNhap());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(NhapHang nh) {
        String UPDATE_SQL = "UPDATE NhapHang SET TenHang = ?, NgayNhap = ?, SoLuong = ?, GiaDon = ?, TongGia = ?, ThangNhap = ? WHERE ID = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, nh.getTenHang());
            ps.setString(2, nh.getNgayNhap());
            ps.setInt(3, nh.getSoLuong());
            ps.setInt(4, nh.getGiaDon());
            ps.setInt(5, nh.getTongGia());
            ps.setString(6, nh.getThangNhap());
            ps.setInt(7, nh.getId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String DELETE_SQL = "DELETE FROM NhapHang WHERE ID = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {

                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addOrderDetail(DichVuChiTiet order) {
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(DVCT1)) {
            ps.setInt(1, order.getBan());
            ps.setString(2, order.getTenHang());
            ps.setInt(3, order.getSoLuong());
            ps.setInt(4, order.getDonGia());
            ps.setInt(5, order.getThanhTien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<DichVuChiTiet> getOrderDetailsByTable(int Ban) {
        ArrayList<DichVuChiTiet> dv = new ArrayList<>();
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(DVCT)) {
            ps.setInt(1, Ban);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DichVuChiTiet dvct = new DichVuChiTiet(
                        rs.getInt("Ban"),
                        rs.getString("TenHang"),
                        rs.getInt("SoLuong"),
                        rs.getInt("DonGia"),
                        rs.getInt("ThanhTien")
                );
                dv.add(dvct);
            }
            System.out.println("Số lượng dịch vụ lấy được cho bàn " + Ban + ": " + dv.size());
            for (DichVuChiTiet order : dv) {
                System.out.println("Bàn: " + order.getBan() + ", Món: " + order.getTenHang() + ", Số lượng: " + order.getSoLuong());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy dữ liệu đơn hàng: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return dv;
    }
}
