/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Check;

import java.text.DecimalFormat;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Administrator
 */
public class Utils {

    public static final int DON_GIA_BAC_1 = 1484;
    public static final int DON_GIA_BAC_2 = 1533;
    public static final int DON_GIA_BAC_3 = 1786;
    public static final int DON_GIA_BAC_4 = 2242;
    public static final int DON_GIA_BAC_5 = 2503;
    public static final int DON_GIA_BAC_6 = 2587;
    public static final int MAX_BAC_1 = 50;
    public static final int MAX_BAC_2 = 50;
    public static final int MAX_BAC_3 = 100;
    public static final int MAX_BAC_4 = 100;
    public static final int MAX_BAC_5 = 100;
    static final String DATE_PATTERN = "([1-9]|1[012])_((19|20)\\d\\d)";

    public String chuanHoaKhoangTrang(String str) {
        str = str.trim();
        str = str.replaceAll("\\s+", " "); // sử dụng \\s+ đại diện cho khoảng trắng.
        return str;
    }

    public String chuanHoaVietHoa(String str) {
        str = chuanHoaKhoangTrang(str);
        String arr[] = str.split(" ");
        str = "";
        for (int i = 0; i < arr.length; i++) {
            str += String.valueOf(arr[i].charAt(0)).toUpperCase() + arr[i].substring(1);
            if (i < arr.length - 1) {
                str += " ";
            }
            if (i == arr.length - 1) {
                str += ".";
            }
        }
        return str;
    }

    public void sendmail(String emailAdress, String tongSoDien, int tongTien, String time, String tenKH) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("hhnguyen253@gmail.com", "hoanghiep1996"));
            email.setSSLOnConnect(true);
            email.setFrom("hhnguyen253@gmail.com", "Công ty điện lực Zent Group");
            email.setSubject("Thông báo tiền điện tháng: " + time);
            DecimalFormat df = new DecimalFormat("#,###");

            email.setMsg("Công ty điện lực Zent Group trân trọng thông báo tiền điện tháng " + time + "\n tháng " + time + " quý khách là: " + tongTien);
            email.addTo("hhnguyen253@gmail.com");
            email.send();
            System.err.println("Gửi mail thành công, vui lòng check lại email");
            System.out.println("\n");
        } catch (Exception e) {
            System.out.println("Gửi không thành công!");
        }

    }
}
