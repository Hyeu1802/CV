/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


public class ChiTietHoaDon {
    String HoaDonId;
    String DichVuId;
    int SoLuong;
    int Gia; 

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String HoaDonId, String DichVuId, int SoLuong, int Gia) {
        this.HoaDonId = HoaDonId;
        this.DichVuId = DichVuId;
        this.SoLuong = SoLuong;
        this.Gia = Gia;
    }

    public String getHoaDonId() {
        return HoaDonId;
    }

    public void setHoaDonId(String HoaDonId) {
        this.HoaDonId = HoaDonId;
    }

    public String getDichVuId() {
        return DichVuId;
    }

    public void setDichVuId(String DichVuId) {
        this.DichVuId = DichVuId;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int Gia) {
        this.Gia = Gia;
    }
    
}
