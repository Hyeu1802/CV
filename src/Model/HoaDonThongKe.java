/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;


public class HoaDonThongKe {
    private Date thoiGianTao;
    private int thoiGianSuDung;
    private int soLuong;
    private int tongTien;
    private int BanId;

    public HoaDonThongKe() {
    }

    public HoaDonThongKe(Date thoiGianTao, int thoiGianSuDung, int soLuong, int tongTien, int BanId) {
        this.thoiGianTao = thoiGianTao;
        this.thoiGianSuDung = thoiGianSuDung;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.BanId = BanId;
    }

    public Date getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Date thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public int getThoiGianSuDung() {
        return thoiGianSuDung;
    }

    public void setThoiGianSuDung(int thoiGianSuDung) {
        this.thoiGianSuDung = thoiGianSuDung;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getBanId() {
        return BanId;
    }

    public void setBanId(int BanId) {
        this.BanId = BanId;
    }

    
    
    
}
