/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Lappro
 */
public class ThucDon {
    int ID;
    String TenMon;
    String GiaBan;
    String AnhMon;

    public ThucDon() {
    }

    public ThucDon(int ID, String TenMon, String GiaBan, String AnhMon) {
        this.ID = ID;
        this.TenMon = TenMon;
        this.GiaBan = GiaBan;
        this.AnhMon = AnhMon;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String TenMon) {
        this.TenMon = TenMon;
    }

    public String getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(String GiaBan) {
        this.GiaBan = GiaBan;
    }

    public String getAnhMon() {
        return AnhMon;
    }

    public void setAnhMon(String AnhMon) {
        this.AnhMon = AnhMon;
    }

    

    
    
    
}
