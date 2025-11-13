/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;


public class HoaDon {
    String NhanVienId;
    String BanId;
    int tongtien;
    Date thoigiantao;

    public HoaDon() {
    }

    public HoaDon(String NhanVienId, String BanId, int tongtien, Date thoigiantao) {
        this.NhanVienId = NhanVienId;
        this.BanId = BanId;
        this.tongtien = tongtien;
        this.thoigiantao = thoigiantao;
    }

    public String getNhanVienId() {
        return NhanVienId;
    }

    public void setNhanVienId(String NhanVienId) {
        this.NhanVienId = NhanVienId;
    }

    public String getBanId() {
        return BanId;
    }

    public void setBanId(String BanId) {
        this.BanId = BanId;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public Date getThoigiantao() {
        return thoigiantao;
    }

    public void setThoigiantao(Date thoigiantao) {
        this.thoigiantao = thoigiantao;
    }
    
    
    
}
