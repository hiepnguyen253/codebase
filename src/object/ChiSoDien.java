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
public class ChiSoDien {

    //thuộc tính
    private String maKH;
    private int chiSoDienCu;
    private int chiSoDienMoi;
    private int thang;
    private int nam;
    private int trangThai;

    public ChiSoDien() {

    }

    public ChiSoDien(String maKH, int chiSoDienCu, int chiSoDienMoi, int thang, int nam, int trangThai) {
        this.maKH = maKH;
        this.chiSoDienCu = chiSoDienCu;
        this.chiSoDienMoi = chiSoDienMoi;
        this.thang = thang;
        this.nam = nam;
        this.trangThai = trangThai;
        

    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public int getChiSoDienCu() {
        return chiSoDienCu;
    }

    public void setChiSoDienCu(int chiSoDienCu) {
        this.chiSoDienCu = chiSoDienCu;
    }

    public int getChiSoDienMoi() {
        return chiSoDienMoi;
    }

    public void setChiSoDienMoi(int chiSoDienMoi) {
        this.chiSoDienMoi = chiSoDienMoi;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    public void inTTDien(){
        System.out.printf("|%7s|%24s|%19s|%32s|%21s|%22s|\n",getMaKH(),getChiSoDienCu(),getChiSoDienMoi(),getThang(),getNam(),getTrangThai());
    }

}
