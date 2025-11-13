/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Administrator
 */
public class NhanVien {

    String HoTen;
    String TenTaiKhoan;
    String CaLamViec;
    int Role;
    int MatKhau;

    public NhanVien() {
    }

    public NhanVien(String HoTen, String TenTaiKhoan, String CaLamViec, int Role, int MatKhau) {
        this.HoTen = HoTen;
        this.TenTaiKhoan = TenTaiKhoan;
        this.CaLamViec = CaLamViec;
        this.Role = Role;
        this.MatKhau = MatKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }

    public void setTenTaiKhoan(String TenTaiKhoan) {
        this.TenTaiKhoan = TenTaiKhoan;
    }

    public String getCaLamViec() {
        return CaLamViec;
    }

    public void setCaLamViec(String CaLamViec) {
        this.CaLamViec = CaLamViec;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int Role) {
        this.Role = Role;
    }

    public int getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(int MatKhau) {
        this.MatKhau = MatKhau;
    }
 
}
