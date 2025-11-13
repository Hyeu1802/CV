/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author A
 */
public class Ban {

    private int banID;
    private String tenBan;
    private String trangThai;

    public Ban() {
    }

    public Ban(int banID, String tenBan, String trangThai) {
        this.banID = banID;
        this.tenBan = tenBan;
        this.trangThai = trangThai;
    }

    public int getBanID() {
        return banID;
    }

    public void setBanID(int banID) {
        this.banID = banID;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    
}
