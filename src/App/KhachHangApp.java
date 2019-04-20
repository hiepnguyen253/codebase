/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Check.CheckData;
import Check.Utils;
import Login.login;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.KhachHang;

/**
 *
 * @author Administrator
 */
public class KhachHangApp {

    ArrayList<KhachHang> ListKH = new ArrayList<KhachHang>();
    private String fileName = "Data/kh/Khachhang.txt";
    CheckData check = new CheckData();
    Utils util = new Utils();

    public void ghiFileKH() {
        try {
            BufferedWriter bw = null;
            FileWriter fw = null;
            String data = "";
            for (int i = 0; i < ListKH.size(); i++) {
                String row = "";
                row += ListKH.get(i).getMaKH() + "\t";
                row += ListKH.get(i).getHoTen() + "\t";
                row += ListKH.get(i).getSoDienThoai() + "\t";
                row += ListKH.get(i).getEmail() + "\t";
                row += ListKH.get(i).getDiaChi() + "\t";
                row += ListKH.get(i).getSoCongTo() + "\n";
                data += row;

            }
            fw = new FileWriter(this.fileName);
            bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(KhachHangApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void docFileKH() {
        try {
            BufferedReader br = null;
            FileReader fr = null;
            ListKH = new ArrayList<>();
            fr = new FileReader(this.fileName);
            br = new BufferedReader(fr);
            String s = null;
            while ((s = br.readLine()) != null) {
                String s_arr[] = s.split("\t");
                KhachHang kh = new KhachHang();
                kh.setMaKH(s_arr[0]);
                kh.setHoTen(s_arr[1]);
                kh.setSoDienThoai(s_arr[2]);
                kh.setEmail(s_arr[3]);
                kh.setDiaChi(s_arr[4]);
                kh.setSoCongTo(s_arr[5]);
                ListKH.add(kh);

            }
        } catch (IOException ex) {
            Logger.getLogger(KhachHangApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void XemDanhSach(ArrayList<KhachHang> ListKH) {
        if (ListKH.isEmpty()) {
            System.out.println("Chưa có khách hàng nào được lưu trong danh sách.");
            System.out.println("");
        } else {
            System.out.println("+--------------------------------------------------------------------------------------------------------------------------------+");
            System.out.println("|                                                         DANH SÁCH KHÁCH HÀNG                                                   |");
            System.out.println("+-------+------------------------+-------------------+------------------------------+---------------------+----------------------+");
            System.out.println("| Mã KH |         Họ Tên         |   Số Điện Thoại   |             Email            |        Địa Chỉ      |      Số Công Tơ Điện |");
            System.out.println("+-------+------------------------+-------------------+------------------------------+---------------------+----------------------+");
            for (int i = 0; i < ListKH.size(); i++) {
                ListKH.get(i).inTT();
            }
            System.out.println("+--------------------------------------------------------------------------------------------------------------------------------+");
        }
    }

    public ArrayList<KhachHang> TimKiemTheoTuKhoa() {
        ArrayList<KhachHang> ListKHTimKiem = new ArrayList<>();
        String find;
        System.out.println("Mời bạn nhập vào từ khóa tìm kiếm: ");
        Scanner sc = new Scanner(System.in);
        find = sc.nextLine();
        for (int i = 0; i < ListKH.size(); i++) {
            if (ListKH.get(i).getMaKH().contains(find) || ListKH.get(i).getSoDienThoai().contains(find) || ListKH.get(i).getEmail().contains(find) || ListKH.get(i).getDiaChi().contains(find) || ListKH.get(i).getHoTen().contains(find) || ListKH.get(i).getSoCongTo().contains(find)) {
                ListKHTimKiem.add(ListKH.get(i));
            }
        }
        return ListKHTimKiem;
    }

    public boolean KiemTraMaKH(String maKH) {
        boolean check = false;
        KhachHang kh = new KhachHang();
        for (int i = 0; i < ListKH.size(); i++) {
            kh = ListKH.get(i);
            if (maKH.equalsIgnoreCase(kh.getMaKH()) == true) {
                check = true;
                break;

            }

        }
        return check;
    }

    public void ThemMoiKH() {
        KhachHang kh = new KhachHang();

        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Mời bạn nhập vào mã khách hàng: ");
            kh.setMaKH(sc.nextLine());
            if ("".equals(kh.getMaKH()) == true) {
                System.out.println("Mã khách hàng không được bỏ trống. ");

            }
        } while ("".equals(kh.getMaKH()) == true);
        if (KiemTraMaKH(kh.getMaKH()) == true) {
            System.out.println("Mã khách hàng đã tồn tại. Thêm không thành công.");

        } else {
            do {
                System.out.println("Mời bạn nhập tên khách hàng: ");
                kh.setHoTen(sc.nextLine());

            } while (check.KiemTraTen(kh.getHoTen()));
            do {
                System.out.println("Mời bạn nhập số điện thoại của khách hàng: ");
                kh.setSoDienThoai(sc.nextLine());
            } while (check.KiemTraSoDienThoai(kh.getSoDienThoai()));

            do {
                System.out.println("Mời bạn nhập email của khách hàng: ");
                kh.setEmail(sc.nextLine());

            } while (check.kiemTraEmail(kh.getEmail()));
            do {
                System.out.println("Mời bạn nhập địa chỉ của khách hàng: ");
                kh.setDiaChi(sc.nextLine());
                if ("".equals(kh.getDiaChi()) == true) {
                    System.out.println("Địa chỉ của khách hàng không được bỏ trống. ");

                }

            } while ("".equals(kh.getDiaChi()) == true);
            do {
                System.out.println("Mời bạn nhập số công tơ của khách hàng: ");
                kh.setSoCongTo(sc.nextLine());
                if ("".equals(kh.getSoCongTo()) == true) {
                    System.out.println("Số công tơ của khách hàng không được bỏ trống. ");

                }

            } while ("".equals(kh.getSoCongTo()) == true);
            ListKH.add(kh);
            ghiFileKH();

        }

    }
    int chiso;

    public KhachHang TimKiemMotKhachHang(String MaKH) {
        for (int i = 0; i < ListKH.size(); i++) {
            if (MaKH.equals(ListKH.get(i).getMaKH())) {
                chiso = i;
                return ListKH.get(i);
            }

        }
        return null;
    }

    public void SuaThongTin() {
        Scanner sc = new Scanner(System.in);
        KhachHang kh = new KhachHang();
        String maKH;
        do {
            System.out.println("Mời bạn nhập mã khách hàng cần kiểm tra: ");
            maKH = sc.nextLine();
            kh = TimKiemMotKhachHang(maKH);
            if (kh == null) {
                System.out.println("Mã khách hàng bạn vừa nhập không tồn tại. ");

            }

        } while (kh == null);
        if (kh != null) {
            String hoTenCu = kh.getHoTen();
            String soDienThoaiCu = kh.getSoDienThoai();
            String emailCu = kh.getEmail();
            String diaChiCu = kh.getDiaChi();
            String soCongToCu = kh.getSoCongTo();
            System.out.println("Mời bạn nhập thông tin khách hàng: ");
            System.out.println("Mã khách hàng: " + kh.getMaKH());
            System.out.println("Tên khách hàng cũ: " + kh.getHoTen());
            do {
                System.out.println("Mời bạn nhập tên khách hàng mới: ");
                kh.setHoTen(sc.nextLine());
                if (check.KiemTraSuaTen(kh.getHoTen())) {
                    System.out.println("Tên khách hàng không được chứa số. ");

                }

            } while (check.KiemTraSuaTen(kh.getHoTen()));
            kh.setHoTen(util.chuanHoaVietHoa(kh.getHoTen()));
            System.out.println("Số điện thoại cũ: " + kh.getSoDienThoai());
            do {
                System.out.println("Mời bạn nhập số điện thoại của khách hàng mới: ");
                kh.setSoDienThoai(sc.nextLine());
                if (check.KiemTraSoDienThoai(kh.getSoDienThoai())) {
                    System.out.println("Số điện thoại của khách hàng không hợp lệ. ");

                } else if ("".equals(kh.getSoDienThoai())) {
                    kh.setSoDienThoai(soDienThoaiCu);

                }

            } while (check.KiemTraSoDienThoai(kh.getSoDienThoai()));
            System.out.println("Email cũ: " + kh.getEmail());
            do {
                System.out.println("Mời bạn nhập email của khách hàng mới: ");
                kh.setEmail(sc.nextLine());
                if (check.kiemTraEmail(kh.getEmail())) {
                    System.out.println("Email không hợp lệ. ");

                } else if ("".equals(kh.getEmail())) {
                    kh.setEmail(emailCu);

                }

            } while (check.kiemTraEmail(kh.getEmail()));
            System.out.println("Địa chỉ cũ: " + kh.getDiaChi());
            System.out.println("Mời bạn nhập địa chỉ mới: ");
            kh.setDiaChi(sc.nextLine());
            if ("".equals(kh.getDiaChi())) {
                kh.setDiaChi(diaChiCu);

            }
            System.out.println("Số công tơ cũ: " + kh.getSoCongTo());
            System.out.println("Mời bạn nhập số công tơ mới: ");
            kh.setSoCongTo(sc.nextLine());
            if ("".equals(kh.getSoCongTo())) {
                kh.setSoCongTo(soCongToCu);

            }
            ListKH.set(chiso, kh);
            ghiFileKH();
            System.out.println("Cập nhập thông tin của khách hàng có MSKH " + kh.getMaKH() + " thành công.");

        }

    }

    public void xoaKhachHang() {
        String maKH;
        Scanner sc = new Scanner(System.in);
        System.out.println("Mời bạn nhập mã khách hàng cần xóa: ");
        maKH = sc.nextLine();

        KhachHang kh = TimKiemMotKhachHang(maKH);
        if (kh == null) {
            System.out.println("Khách hàng không tồn tại. ");

        } else {
            ListKH.remove(kh);
            ghiFileKH();
            System.out.println("Bạn vừa xóa thành công khách hàng có mã " + kh.getMaKH() + "");

        }

    }

    public void menuKH() {
        Scanner sc = new Scanner(System.in);
        int n;
        login lg = new login();
        do {
            System.out.println("+----------------------------------------------------------------+");
            System.out.println("|                      Mời bạn chọn chức năng                    |");
            System.out.println("+----------------------------------------------------------------+");
            System.out.println("|                 1: Xem danh sách khách hàng.                   |");
            System.out.println("+----------------------------------------------------------------+");
            System.out.println("|                 2: Tìm kiếm khách hàng theo từ khóa.           |");
            System.out.println("+----------------------------------------------------------------+");
            System.out.println("|                 3: Thêm mới một khách hàng.                    |");
            System.out.println("+----------------------------------------------------------------+");
            System.out.println("|                 4: Sửa thông tin khách hàng.                   |");
            System.out.println("+----------------------------------------------------------------+");
            System.out.println("|                 5: Xóa khách hàng.                             |");
            System.out.println("+----------------------------------------------------------------+");
            System.out.println("|                 6: Thoát                                       |");
            System.out.println("+----------------------------------------------------------------+");
            System.out.println("");
            System.out.print("Mời bạn chọn chức năng: ");
            n = sc.nextInt();

            switch (n) {
                case 1:
                    XemDanhSach(ListKH);
                    break;
                case 2:
                    XemDanhSach(TimKiemTheoTuKhoa());
                    break;
                case 3:
                    ThemMoiKH();
                    break;
                case 4:
                    SuaThongTin();
                    break;
                case 5:
                    xoaKhachHang();
                    break;
                case 6:
                    lg.mainMenu();

                    break;
                default:
                    System.out.println("Chức năng không hợp lệ mời bạn nhập lại");
                    break;
            }

        } while (n != 6);

    }

}
