package App;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Check.CheckData;
import Check.DocSo;
import Check.Utils;
import Login.login;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.ChiSoDien;
import object.HoaDon;
import object.KhachHang;

/**
 *
 * @author Administrator
 */
public class HoaDonApp {

    CheckData check = new CheckData();
    ArrayList<HoaDon> ListHoaDon = new ArrayList<HoaDon>();

    public void menu() {
        login lg = new login();
        Scanner sc = new Scanner(System.in);
        int choose;
        do {
            System.out.println("+-----------------------------------------------+");
            System.out.println("+         [1] Xem danh sách hóa đơn             +");
            System.out.println("+-----------------------------------------------+");
            System.out.println("+         [2] Tạo hóa đơn theo tháng            +");
            System.out.println("+-----------------------------------------------+");
            System.out.println("+         [3] Xem chi tiết 1 hóa đơn            +");
            System.out.println("+-----------------------------------------------+");
            System.out.println("+         [4] Thoát                             +");
            System.out.println("+-----------------------------------------------+");
            System.out.println("Mời bạn chọn chức năng");
            choose = sc.nextInt();
            switch (choose) {
                case 1:
                    xemDanhSachHoaDon();

                    break;
                case 2:
                    taoHoaDonTheoThang();
                    break;

                case 3:
                    inHoaDonChiTiet();
                    break;
                case 4:
                    lg.mainMenu();
                    break;
                default:
                    System.out.println("Bạn chọn sai chức năng: ");
            }

        } while (choose != 4);

    }

    public String nhapThoiGian() {
        Scanner sc = new Scanner(System.in);
        String time;
        do {
            System.out.println("Mời bạn nhập tháng_năm muốn xem HD(VD: 8_2018):");
            time = sc.nextLine();
            if (check.kiemTraDinhDangThangNam(time) == false) {
                System.err.println("Định dạng ngày tháng sai , nhập lại theo định dạng 8_2018: ");

            }

        } while (check.kiemTraDinhDangThangNam(time) == false);
        return time;
    }

    public void xemDanhSachHoaDon() {
        String time = nhapThoiGian();
        String arr[] = time.split("_");
        File file = new File("data/HD/" + arr[1] + "/" + time + ".txt");
        if (file.exists()) {
            docFileHoaDon(time);
            System.out.println("+-------------------------------------------------------------------------------------------------+");
            System.out.println("+                          DANH SÁCH HÓA ĐƠN TIỀN ĐIỆN THÁNG " + time + "                              +");
            System.out.println("+-------------------------------------------------------------------------------------------------+");
            System.out.println("+  STT  +         Mã HĐ          +      Họ tên KH         +   Tổng số điên   +   Thành tiền       +");
            System.out.println("+-------+------------------------+------------------------+------------------+--------------------+");
            int stt = 1;
            KhachHangApp khApp = new KhachHangApp();
            khApp.docFileKH();
            DecimalFormat df = new DecimalFormat("#,###");
            for (int i = 0; i < ListHoaDon.size(); i++) {
                HoaDon hd = ListHoaDon.get(i);
                System.out.printf("|%7d|", stt++);
                System.out.printf("%24s|%24s|%18s|%20s|\n", hd.getMaHoaDon(), khApp.TimKiemMotKhachHang(hd.getMakhachHang()).getHoTen(), hd.getTongSoDien(), hd.getThanhTien());

            }
            System.out.println("+-------+------------------------+------------------------+------------------+--------------------+");
        } else {
            System.err.println("Hiện tại chưa có dữ liệu hóa đơn tiền điện tháng " + time);

        }
    }

    public void taoHoaDonTheoThang() {
        Scanner sc = new Scanner(System.in);
        String time;
        do {
            System.out.println("Mời bạn nhập tháng_năm muốn xem hóa HĐ(VD: 9_2018): ");
            time = sc.nextLine();
            if (check.kiemTraDinhDangThangNam(time) == false) {
                System.err.println("Định dạng ngày tháng sai, nhập lại theo dạng 9_2018: ");

            }

        } while (check.kiemTraDinhDangThangNam(time) == false);
        taoFolder(time);
        taoFileHoaDonTheoThang(time);

    }

    public void taoFolder(String time) {
        String arr[] = time.split("_");
        File folderYear = new File("data/HD/" + arr[1]);
        if (!folderYear.exists()) {
            folderYear.mkdir();

        }

    }

    public void taoFileHoaDonTheoThang(String time) {
        String arr[] = time.split("_");
        if (kiemTraTonTaiFileCSD(time)) {
            File file = new File("data/HD/" + arr[1] + "/" + time + ".txt");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    System.out.println("Tạo mới file HD " + time + "thành công !");
                    themDuLieuHoaDonTheoThang(time);
                } catch (IOException ex) {
                    Logger.getLogger(HoaDonApp.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                themDuLieuHoaDonTheoThang(time);
            }

        } else {
            System.err.println("Hiện tại chưa có dữ liệu về chỉ số điện tháng " + time + " !");
        }

    }

    public boolean kiemTraTonTaiFileCSD(String time) {
        String arr[] = time.split("_");
        File file = new File("data/csd/" + arr[1] + "/" + arr[0] + "_" + arr[1] + ".txt");
        if (file.exists()) {
            return true;

        }
        return false;
    }

    public void themDuLieuHoaDonTheoThang(String time) {
        Scanner sc = new Scanner(System.in);
        String arr[] = time.split("_");
        ChiSoDienApp csdApp = new ChiSoDienApp();
        Date date = new Date();
        docFileHoaDon(time);
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY_MM_dd_hh_mm_ss");
        if (kiemTraTonTaiFileCSD(time)) {
            csdApp.docChiSoDienThangHienTai(arr[0], arr[1]);
            for (int i = 0; i < csdApp.listCSDThangHienTai.size(); i++) {
                if (csdApp.listCSDThangHienTai.get(i).getTrangThai() == 0) {
                    ChiSoDien csd = csdApp.listCSDThangHienTai.get(i);
                    HoaDon hd = new HoaDon();
                    hd.setMaHoaDon(csd.getMaKH() + "_" + sdf.format(date));
                    hd.setMakhachHang(csd.getMaKH());
                    hd.setNam(csd.getNam());
                    hd.setThang(csd.getThang());
                    hd.setTongSoDien(csd.getChiSoDienMoi() - csd.getChiSoDienCu());
                    int vat = (tinhTienTheoBac(hd.getTongSoDien()) * 10) / 100;
                    hd.setThanhTien(tinhTienTheoBac(hd.getTongSoDien()) + vat);
                    ListHoaDon.add(hd);
                    capNhatTrangThaiCSDCoHoaDon(hd.getMakhachHang(), time);

                }

            }
            ghiFileHoaDon(time);
            System.out.println("Đã khởi tạo hóa đơn thành công: ");

        }

    }

    public void capNhatTrangThaiCSDCoHoaDon(String maKH, String time) {
        String arr[] = time.split("_");
        ChiSoDienApp csdApp = new ChiSoDienApp();
        csdApp.docChiSoDienThangHienTai(arr[0], arr[1]);
        for (int i = 0; i < csdApp.listCSDThangHienTai.size(); i++) {
            ChiSoDien csd = csdApp.listCSDThangHienTai.get(i);
            if (maKH.equals(csdApp.listCSDThangHienTai.get(i).getMaKH())) {
                csd.setTrangThai(1);
                csdApp.listCSDThangHienTai.set(i, csd);
                csdApp.ghiDuLieuVaoFile(arr[0], arr[1]);
                break;

            }

        }
    }

    public void ghiFileHoaDon(String time) {
        try {
            String arr[] = time.split("_");
            BufferedWriter bw = null;
            FileWriter fw = null;
            String data = "";
            for (int i = 0; i < ListHoaDon.size(); i++) {
                String row = "";
                row += ListHoaDon.get(i).getMaHoaDon() + "\t";
                row += ListHoaDon.get(i).getMakhachHang() + "\t";
                row += ListHoaDon.get(i).getNam() + "\t";
                row += ListHoaDon.get(i).getThang() + "\t";
                row += ListHoaDon.get(i).getTongSoDien() + "\t";
                row += ListHoaDon.get(i).getThanhTien() + "\n";
                data += row;
            }
            fw = new FileWriter("data/HD/" + arr[1] + "/" + time + ".txt");
            bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(HoaDonApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void docFileHoaDon(String time) {
        try {
            String arrTime[] = time.split("_");
            BufferedReader br = null;
            FileReader fr = null;
            ListHoaDon = new ArrayList<>();
            fr = new FileReader("data/HD/" + arrTime[1] + "/" + time + ".txt");
            br = new BufferedReader(fr);
            String s = null;
            try {
                while ((s = br.readLine()) != null) {
                    String arr[] = s.split("\t");
                    HoaDon hd = new HoaDon();
                    hd.setMaHoaDon(arr[0]);
                    hd.setMakhachHang(arr[1]);
                    hd.setNam(Integer.parseInt(arr[2]));
                    hd.setThang(Integer.parseInt(arr[3]));
                    hd.setTongSoDien(Integer.parseInt(arr[4]));
                    hd.setThanhTien(Integer.parseInt(arr[5]));
                    ListHoaDon.add(hd);

                }
            } catch (IOException ex) {
                Logger.getLogger(HoaDonApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HoaDonApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int tinhTienTheoBac(int tongSoDien) {
        Utils util = new Utils();
        int tongtien = 0;
        if (tongSoDien > 400) {
            tongtien = ((tongSoDien - 400) * util.DON_GIA_BAC_6) + (util.DON_GIA_BAC_1 * util.MAX_BAC_1) + (util.DON_GIA_BAC_2 * util.MAX_BAC_2) + (util.DON_GIA_BAC_3 * util.MAX_BAC_3) + (util.DON_GIA_BAC_4 * util.MAX_BAC_4) + (util.DON_GIA_BAC_5 * util.MAX_BAC_5);

        } else if (tongSoDien > 300) {
            tongtien = ((tongSoDien - 300) * util.DON_GIA_BAC_5) + (util.DON_GIA_BAC_1 * util.MAX_BAC_1) + (util.DON_GIA_BAC_2 * util.MAX_BAC_2) + (util.DON_GIA_BAC_3 * util.MAX_BAC_3) + (util.DON_GIA_BAC_4 * util.MAX_BAC_4);

        } else if (tongSoDien > 200) {
            tongtien = ((tongSoDien - 200) * util.DON_GIA_BAC_4) + (util.DON_GIA_BAC_1 * util.MAX_BAC_1) + (util.DON_GIA_BAC_2 * util.MAX_BAC_2) + (util.DON_GIA_BAC_3 * util.MAX_BAC_3);

        } else if (tongSoDien > 100) {
            tongtien = ((tongSoDien - 100) * util.DON_GIA_BAC_3) + (util.DON_GIA_BAC_1 * util.MAX_BAC_1) + (util.DON_GIA_BAC_2 * util.MAX_BAC_2);

        } else if (tongSoDien > 50) {
            tongtien = ((tongSoDien - 50) * util.DON_GIA_BAC_2) + (util.DON_GIA_BAC_1 * util.MAX_BAC_1);

        } else if (tongSoDien > 0) {
            tongtien = ((tongSoDien) * util.DON_GIA_BAC_1);

        }
        return tongtien;
    }

    public void inHoaDonChiTiet() {
        Scanner sc = new Scanner(System.in);
        String time = nhapThoiGian();
        String arr[] = time.split("_");
        System.out.println("Bạn đang muốn xem chi tiết hóa đơn ở trong tháng: " + time);
        File file = new File("data/HD/" + arr[1] + "/" + time + ".txt");
        if (!file.exists()) {
            System.out.println("Dữ liệu hóa đơn của tháng " + time + " chưa cập nhật...!");
        } else {
            docFileHoaDon(time);
            System.out.println("Mời bạn nhập mã khách hàng cần xem: ");
            String maKH = sc.nextLine();
            for (int i = 0; i < ListHoaDon.size(); i++) {
                HoaDon hd = ListHoaDon.get(i);
                if (maKH.equals(hd.getMakhachHang())) {
                    KhachHangApp khApp = new KhachHangApp();
                    khApp.docFileKH();
                    KhachHang kh = khApp.TimKiemMotKhachHang(maKH);
                    ChiSoDienApp csdApp = new ChiSoDienApp();
                    ChiSoDien csd = csdApp.layChiSoDien(maKH, arr[0], arr[1]);
                    Utils util = new Utils();

                    DecimalFormat df = new DecimalFormat("#,###");
                    System.out.println("+--------------------------------------------------------------------------------------+");
                    System.out.println("+                             BIÊN LAI HÓA ĐƠN TIỀN ĐIỆN                               +");
                    System.out.println("+--------------------------------------------------------------------------------------+");
                    System.out.println("| Công ty Điện lực Zent Group                                                          |");
                    System.out.println("| Địa chỉ tầng 5-số 2A- Trại Cá-Trương Định-Hà Nội                                     |");
                    System.out.println("| SĐT:012545666335  Email: abc@gmail.com                                               |");
                    System.out.printf("| Mã HĐ: %-11s                                                      |\n", hd.getMaHoaDon());
                    System.out.printf("| Mã KH: %-13s                                                                 |\n", kh.getMaKH());
                    System.out.printf("| Tên khách hàng: %-24s", kh.getHoTen());
                    System.out.printf("| SĐT: %-13s ", kh.getSoDienThoai());
                    System.out.printf("|Địa chỉ: %-14s|\n", kh.getDiaChi());
                    System.out.println("+--------------------------------------------------------------------------------------+");
                    System.out.println("| Chỉ số cũ      |  Chỉ số mới    | Điện năng TT   |   Đơn giá        |   Thành tiền   |");
                    System.out.println("+----------------+----------------+----------------+------------------+----------------+");
                    System.out.printf("|%16d|", csd.getChiSoDienCu());
                    System.out.printf("%16d|", csd.getChiSoDienMoi());
                    System.out.printf("%-16d|", hd.getTongSoDien());
                    System.out.printf("%18s|", "");
                    System.out.printf("%16s|\n", "");

                    if (hd.getTongSoDien() > 400) {
                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_1);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_1));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_1 * util.DON_GIA_BAC_1));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_2);
                        System.out.printf("%18|", df.format(util.DON_GIA_BAC_2));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_2 * util.DON_GIA_BAC_2));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_3);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_3));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_3 * util.DON_GIA_BAC_3));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_4);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_4));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_4 * util.DON_GIA_BAC_4));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_5);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_5));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_5 * util.DON_GIA_BAC_5));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", hd.getTongSoDien() - 400);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_6));
                        System.out.printf("%16s|\n", df.format((hd.getTongSoDien() - 400) * util.DON_GIA_BAC_6));

                    } else if (hd.getTongSoDien() > 300) {
                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_1);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_1));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_1 * util.DON_GIA_BAC_1));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%1s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_2);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_2));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_2 * util.DON_GIA_BAC_2));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_3);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_3));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_3 * util.DON_GIA_BAC_3));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_4);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_4));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_4 * util.DON_GIA_BAC_4));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", hd.getTongSoDien() - 300);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_5));
                        System.out.printf("%16s|\n", df.format((hd.getTongSoDien() - 300) * util.DON_GIA_BAC_5));

                    } else if (hd.getTongSoDien() > 200) {
                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_1);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_1));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_1 * util.DON_GIA_BAC_1));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_2);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_2));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_2 * util.DON_GIA_BAC_2));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_3);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_3));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_3 * util.DON_GIA_BAC_3));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", hd.getTongSoDien() - 200);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_4));
                        System.out.printf("%16s|\n", df.format((hd.getTongSoDien() - 200) * util.DON_GIA_BAC_4));

                    } else if (hd.getTongSoDien() > 100) {
                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_1);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_1));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_1 * util.DON_GIA_BAC_1));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_2);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_2));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_2 * util.DON_GIA_BAC_2));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", hd.getTongSoDien() - 100);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_3));
                        System.out.printf("%16s|\n", df.format((hd.getTongSoDien() - 100) * util.DON_GIA_BAC_3));

                    } else if (hd.getTongSoDien() > 50) {
                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", util.MAX_BAC_1);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_1));
                        System.out.printf("%16s|\n", df.format(util.MAX_BAC_1 * util.DON_GIA_BAC_1));

                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", hd.getTongSoDien() - 50);
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_2));
                        System.out.printf("%16s|\n", df.format((hd.getTongSoDien() - 50) * util.DON_GIA_BAC_2));

                    } else if (hd.getTongSoDien() > 0) {
                        System.out.printf("|%16s|", "");
                        System.out.printf("%16s|", "");
                        System.out.printf("%16d|", hd.getTongSoDien());
                        System.out.printf("%18s|", df.format(util.DON_GIA_BAC_1));
                        System.out.printf("%16s|\n", df.format(hd.getTongSoDien() * util.DON_GIA_BAC_1));
                    }
                    System.out.println("+----------------+----------------+----------------+------------------+----------------+");
                    System.out.printf("|                                                       TỔNG:      %20s|\n ", df.format(tinhTienTheoBac(hd.getTongSoDien())));
                    int vat = (tinhTienTheoBac(hd.getTongSoDien()) * 10) / 100;
                    int tongTien = tinhTienTheoBac(hd.getTongSoDien() + vat);
                    System.out.printf("|                                                    Thuế VAT:   %21s|\n", df.format(vat));
                    System.out.printf("|                                  Tổng số tiền cần thanh toán: %23s|\n ", df.format(tinhTienTheoBac(hd.getTongSoDien()) + vat));
                    System.out.println("+-------------------------------------------------------------------------------------+");
                    DocSo ds = new DocSo();
                    ds.inchuthongke(String.valueOf(hd.getThanhTien()).replace(".0", ""));
                    System.out.println("+--------------------------------------------------------------------------------------+");
                    util.sendmail(kh.getEmail(), String.valueOf(hd.getTongSoDien()), tongTien, time, kh.getHoTen());
                }

            }
        }

    }
}
