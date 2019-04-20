/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

/**
 *
 * @author Administrator
 */
public class HoaDon {

    //Thuộc tính
    private String maHoaDon;
    private String makhachHang;
    private int tongSoDien;
    private int thang;
    private int thanhTien;
    private int nam;

    public HoaDon() {

    }

    public HoaDon(String maHoaDon, String maKhachHang, int tongSoDien, int thang, int thanhTien, int nam) {
        this.maHoaDon = maHoaDon;
        this.makhachHang = maKhachHang;
        this.tongSoDien = tongSoDien;
        this.thang = thang;
        this.thanhTien = thanhTien;
        this.nam = nam;

    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMakhachHang() {
        return makhachHang;
    }

    public void setMakhachHang(String makhachHang) {
        this.makhachHang = makhachHang;
    }

    public int getTongSoDien() {
        return tongSoDien;
    }

    public void setTongSoDien(int tongSoDien) {
        this.tongSoDien = tongSoDien;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }
}
