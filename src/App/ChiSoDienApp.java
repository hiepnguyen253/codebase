/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Check.CheckData;
import Login.login;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.ChiSoDien;
import object.KhachHang;

/**
 *
 * @author Administrator
 */
public class ChiSoDienApp {

    CheckData check = new CheckData();
    ArrayList<ChiSoDien> listCSD = new ArrayList<ChiSoDien>();
    ArrayList<ChiSoDien> listCSDThangHienTai = new ArrayList<ChiSoDien>();

    public void nhapThoiGian() {
        Scanner sc = new Scanner(System.in);
        login lg = new login();
        String time;
        do {
            System.out.println("Mời bạn nhập tháng_năm tạo chỉ số điện(VD: 9_2018): ");
            time = sc.nextLine();
            if (check.kiemTraDinhDangThangNam(time) == false) {
                System.err.println("Định dạng ngày tháng sai, mời bạn nhập lại ngày tháng theo định dạng 9_2018:");

            }

        } while (check.kiemTraDinhDangThangNam(time) == false);
        taoFolderTheoNam(time);
        taoFileMoi(time);
        String check;
        do {
            nhapMaKH(time);
            System.out.println("Nhập 0 nếu bạn muốn thoát khỏi chức năng này hoặc nhấn ENTER để tiếp tục. ");
            check = sc.nextLine();
            if (check.equals("0")) {
                lg.mainMenu();
            }

        } while (check.equals(""));

    }

    public void taoFolderTheoNam(String time) {
        String arr[] = time.split("_");
        File FolderYear = new File("data/csd/" + arr[1]);
        if (!FolderYear.exists()) {
            FolderYear.mkdir();

        }
    }

    public void taoFileMoi(String time) {
        String arr[] = time.split("_");
        File file = new File("data/csd/" + arr[1] + "/" + time + ".txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Tạo file " + time + " thành công. ");
            } catch (IOException ex) {
                Logger.getLogger(ChiSoDienApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Đã có file" + time + ". Bây giờ bạn chỉ cần nhập dữ liệu vào.");
        }
    }

    public void nhapMaKH(String time) {
        String arrTime[] = time.split("_");
        String thangHienTai = arrTime[0];
        String namHienTai = arrTime[1];
        Scanner sc = new Scanner(System.in);
        System.out.println("Mời bạn nhập mã khách hàng: ");
        String maKH = sc.nextLine();
        KhachHangApp khApp = new KhachHangApp();
        khApp.docFileKH();
        if (khApp.KiemTraMaKH(maKH)) {
            ArrayList<KhachHang> list = new ArrayList<KhachHang>();
            list.add(khApp.TimKiemMotKhachHang(maKH));
            khApp.XemDanhSach(list);
            nhapCSDMoi(maKH, thangHienTai, namHienTai);

        } else {
            System.out.println("Mã khách hàng bạn vừa nhập không có trên hệ thống. ");
            nhapMaKH(time);
        }
    }

    public void nhapCSDMoi(String maKH, String thang, String nam) {
        KhachHangApp khApp = new KhachHangApp();
        khApp.docFileKH();
        Scanner sc = new Scanner(System.in);
        docChiSoDienTheoThang(thang, nam);
        int dem = 0;
        for (int i = 0; i < listCSDThangHienTai.size(); i++) {
            if (maKH.equals(listCSDThangHienTai.get(i).getMaKH())) {
                System.err.println("Chỉ số điện tháng" + thang + "/" + nam + "cua khách hàng " + khApp.TimKiemMotKhachHang(maKH).getHoTen() + " đã được cập nhật. ");
                dem++;
                break;
            }

        }
        int CSDMoiThangTruoc;
        if (dem == 0) {
            int CSDMoi;
            if (thang.equals("1")) {
                nam = String.valueOf(Integer.parseInt(nam) - 1);
                CSDMoiThangTruoc = layChiSoDienThangTruoc(maKH, "12", nam);

            } else {
                CSDMoiThangTruoc = layChiSoDienThangTruoc(maKH, String.valueOf(Integer.parseInt(thang) - 1), nam);
            }

            do {
                System.out.println("Mời bạn nhập chỉ số điện mới của khách hàng " + khApp.TimKiemMotKhachHang(maKH).getHoTen() + " trong tháng " + thang + " năm " + nam + ": ");
                CSDMoi = sc.nextInt();
                if (CSDMoi < CSDMoiThangTruoc) {
                    System.out.println("Chỉ số điện mới phải lớn " + "hơn hoặc bằng chỉ số điện cũ. ");
                    System.out.println("Chỉ số điện đến tháng " + String.valueOf(Integer.parseInt(thang) - 1) + " năm " + nam + ": " + CSDMoiThangTruoc);

                }
            } while (CSDMoi < layChiSoDienThangTruoc(maKH, String.valueOf(Integer.parseInt(thang) - 1), nam));
            docChiSoDienThangHienTai(thang, nam);
            ChiSoDien csd = new ChiSoDien();
            csd.setMaKH(maKH);
            csd.setChiSoDienCu(CSDMoiThangTruoc);
            csd.setChiSoDienMoi(CSDMoi);
            csd.setThang(Integer.parseInt(thang));
            csd.setNam(Integer.parseInt(nam));
            csd.setTrangThai(0);
            listCSDThangHienTai.add(csd);
            ghiDuLieuVaoFile(thang, nam);

        }
    }

    public int layChiSoDienThangTruoc(String maKH, String thang, String nam) {
        docChiSoDienTheoThang(thang, nam);
        for (int i = 0; i < listCSD.size(); i++) {
            if (maKH.equals(listCSD.get(i).getMaKH())) {
                return listCSD.get(i).getChiSoDienMoi();

            }

        }
        return 0;
    }

    public void docChiSoDienTheoThang(String thang, String nam) {
        try {
            BufferedReader br = null;
            FileReader fr = null;
            listCSD = new ArrayList<>();
            fr = new FileReader("data/csd/" + nam + "/" + thang + "_" + nam + ".txt");
            br = new BufferedReader(fr);
            String s = null;
            try {
                while ((s = br.readLine()) != null) {
                    String arr[] = s.split("\t");
                    ChiSoDien csd = new ChiSoDien();
                    csd.setMaKH(arr[0]);
                    csd.setChiSoDienCu(Integer.parseInt(arr[1]));
                    csd.setChiSoDienMoi(Integer.parseInt(arr[2]));
                    csd.setThang(Integer.parseInt(arr[3]));
                    csd.setNam(Integer.parseInt(arr[4]));
                    csd.setTrangThai(Integer.parseInt(arr[5]));
                    listCSD.add(csd);

                }
            } catch (IOException ex) {
                Logger.getLogger(ChiSoDienApp.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChiSoDienApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void docChiSoDienThangHienTai(String thang, String nam) {
        try {
            BufferedReader br = null;
            FileReader fr = null;
            listCSDThangHienTai = new ArrayList<>();
            fr = new FileReader("data/csd/" + nam + "/" + thang + "_" + nam + ".txt");
            br = new BufferedReader(fr);
            String s = null;

            try {
                while ((s = br.readLine()) != null) {
                    String arr[] = s.split("\t");
                    ChiSoDien csd = new ChiSoDien();
                    csd.setMaKH(arr[0]);
                    csd.setChiSoDienCu(Integer.parseInt(arr[1]));
                    csd.setChiSoDienMoi(Integer.parseInt(arr[2]));
                    csd.setThang(Integer.parseInt(arr[3]));
                    csd.setNam(Integer.parseInt(arr[4]));
                    csd.setTrangThai(Integer.parseInt(arr[5]));
                    listCSDThangHienTai.add(csd);

                }
            } catch (IOException ex) {
                Logger.getLogger(ChiSoDienApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChiSoDienApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ghiDuLieuVaoFile(String thang, String nam) {
        try {
            BufferedWriter bw = null;
            FileWriter fw = null;
            String data = "";
            for (int i = 0; i < listCSDThangHienTai.size(); i++) {
                String row = "";
                row += listCSDThangHienTai.get(i).getMaKH() + "\t";
                row += listCSDThangHienTai.get(i).getChiSoDienCu() + "\t";
                row += listCSDThangHienTai.get(i).getChiSoDienMoi() + "\t";
                row += listCSDThangHienTai.get(i).getThang() + "\t";
                row += listCSDThangHienTai.get(i).getNam() + "\t";
                row += listCSDThangHienTai.get(i).getTrangThai() + "\n";
                data += row;

            }
            fw = new FileWriter("data/csd/" + nam + "/" + thang + "_" + nam + ".txt");
            bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(ChiSoDienApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ChiSoDien layChiSoDien(String maKH, String thang, String nam) {
        docChiSoDienThangHienTai(thang, nam);
        for (int i = 0; i < listCSDThangHienTai.size(); i++) {
            if (maKH.equals(listCSDThangHienTai.get(i).getMaKH())) {
                return listCSDThangHienTai.get(i);

            }

        }
        return null;
    }

    public static void main(String[] args) {
        ChiSoDienApp kh = new ChiSoDienApp();
        kh.nhapThoiGian();

    }

}
