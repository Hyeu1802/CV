/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Administrator
 */
public class NhapHang {

    private int id;
    private String tenHang;
    private String ngayNhap;
    private int soLuong;
    private int giaDon;
    private int tongGia;
    private String thangNhap;

    public NhapHang() {
    }

    public NhapHang(int id, String tenHang, String ngayNhap, int soLuong, int giaDon, int tongGia, String thangNhap) {
        this.id = id;
        this.tenHang = tenHang;
        this.ngayNhap = ngayNhap;
        this.soLuong = soLuong;
        this.giaDon = giaDon;
        this.tongGia = tongGia;
        this.thangNhap = thangNhap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaDon() {
        return giaDon;
    }

    public void setGiaDon(int giaDon) {
        this.giaDon = giaDon;
    }

    public int getTongGia() {
        return tongGia;
    }

    public void setTongGia(int tongGia) {
        this.tongGia = tongGia;
    }

    public String getThangNhap() {
        return thangNhap;
    }

    public void setThangNhap(String thangNhap) {
        this.thangNhap = thangNhap;
    }

}
