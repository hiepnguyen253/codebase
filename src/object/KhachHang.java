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
public class KhachHang {

    //Thuoc tinh
    private String maKH;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private String soCongTo;

    public KhachHang() {

    }

    public KhachHang(String maKH, String hoTen, String soDienThoai, String email, String diaChi, String soCongTo) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.soCongTo = soCongTo;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoCongTo() {
        return soCongTo;
    }

    public void setSoCongTo(String soCongTo) {
        this.soCongTo = soCongTo;
    }

    

    public void inTT() {
        System.out.printf("|%7s|%24s|%19s|%30s|%21s|%22s|\n", getMaKH(), getHoTen(), getSoDienThoai(), getEmail(), getDiaChi(), getSoCongTo());
    }

}
