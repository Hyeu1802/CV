/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;


public class HoaDonTG {
    String HoaDonId, BanId;
    Date ThoiGianBatDau;
    Date ThoiGianKetThuc;

    public HoaDonTG() {
    }

    public HoaDonTG(String HoaDonId, String BanId, Date ThoiGianBatDau, Date ThoiGianKetThuc) {
        this.HoaDonId = HoaDonId;
        this.BanId = BanId;
        this.ThoiGianBatDau = ThoiGianBatDau;
        this.ThoiGianKetThuc = ThoiGianKetThuc;
    }

    public String getHoaDonId() {
        return HoaDonId;
    }

    public void setHoaDonId(String HoaDonId) {
        this.HoaDonId = HoaDonId;
    }

    public String getBanId() {
        return BanId;
    }

    public void setBanId(String BanId) {
        this.BanId = BanId;
    }

    public Date getThoiGianBatDau() {
        return ThoiGianBatDau;
    }

    public void setThoiGianBatDau(Date ThoiGianBatDau) {
        this.ThoiGianBatDau = ThoiGianBatDau;
    }

    public Date getThoiGianKetThuc() {
        return ThoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Date ThoiGianKetThuc) {
        this.ThoiGianKetThuc = ThoiGianKetThuc;
    }
    
    
}
