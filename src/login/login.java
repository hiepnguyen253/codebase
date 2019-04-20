
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login;

import java.util.Scanner;
import App.KhachHangApp;
import App.ChiSoDienApp;
import App.HoaDonApp;

/**
 *
 * @author Admin
 */
public class login {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String acc, pass;
        int dem = 0;
        do {
            System.out.println("+-------------------------ĐĂNG NHẬP+-------------------------+");
            System.out.println("Mời bạn nhập tài khoản: ");
            acc = sc.nextLine();
            System.out.println("Mời bạn nhập mật khẩu: ");
            pass = sc.nextLine();
            if (acc.equals("Hiep") && pass.equals("12345")) {
                dem++;
                System.err.println("Đăng nhập thành công! ");
                new login().mainMenu();
            } else {
                System.err.println("Tài khoản hoặc mật khẩu sai!");
            }
        } while (dem == 0);

    }

    @SuppressWarnings("empty-statement")
    public void mainMenu() {
        Scanner sc = new Scanner(System.in);
        int chon;
        System.out.println("+----------QUẢN LÍ THANH TOÁN TIỀN ĐIỆN----------+");
        System.out.println("|          1.QUẢN LÍ KHÁCH HÀNG                  |");
        System.out.println("+------------------------------------------------+");
        System.out.println("|          2.QUẢN LÍ CHỈ SỐ ĐIỆN KHÁCH HÀNG      |");
        System.out.println("+------------------------------------------------+");
        System.out.println("|          3.QUẢN LÍ HÓA ĐƠN TIỀN ĐIỆN HÀNG THÁNG|");
        System.out.println("+------------------------------------------------+");
        System.out.println("|          4.THOÁT                               |");
        System.out.println("+------------------------------------------------+");
        System.out.print("Mời bạn chọn chức năng: ");
        chon = sc.nextInt();
        switch (chon) {
            case 1:
                KhachHangApp khApp = new KhachHangApp();
                khApp.docFileKH();
                khApp.menuKH();
                break;

            case 2:
                ChiSoDienApp csd = new ChiSoDienApp();
                csd.nhapThoiGian();
                break;

            case 3:
                HoaDonApp hd = new HoaDonApp();
                hd.menu();
                break;
            case 4:
                break;

            default:
                System.out.println("Không hợp lệ ! Vui lòng nhập lại !");
                break;

        }
        while (chon != 4);
    }

}
