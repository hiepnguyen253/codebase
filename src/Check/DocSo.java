package Check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 *
 * @author Admin
 */
public class DocSo {

    public final String KHONG = "không";
    public final String MOT = "một";
    public final String HAI = "hai";
    public final String BA = "ba";
    public final String BON = "bốn";
    public final String NAM = "năm";
    public final String SAU = "sáu";
    public final String BAY = "bảy";
    public final String TAM = "tám";
    public final String CHIN = "chín";
    public final String LAM = "lăm";
    public final String LE = "lẻ";
    public final String MUOI = "mươi";
    public final String MUOIF = "mười";
    public final String MOTS = "mốt";
    public final String TRAM = "trăm";
    public final String NGHIN = "nghìn";
    public final String TRIEU = "triệu";
    public final String TY = "tỷ";

    public final String[] number = {KHONG, MOT, HAI, BA,
        BON, NAM, SAU, BAY, TAM, CHIN};

    //Hàm chính đọc số
    public ArrayList<String> readNum(String a) {
        ArrayList<String> kq = new ArrayList<String>();

        //Cắt chuổi string chử số ra thành các chuổi nhỏ 3 chử số
        ArrayList<String> List_Num = Split(a, 3);

        while (List_Num.size() != 0) {
            //Xét 3 số đầu tiên của chuổi (số đầu tiên của List_Num)
            switch (List_Num.size() % 3) {
                //3 số đó thuộc hàng trăm
                case 1:
                    kq.addAll(read_3num(List_Num.get(0)));
                    break;
                // 3 số đó thuộc hàng nghìn
                case 2:
                    ArrayList<String> nghin = read_3num(List_Num.get(0));
                    if (!nghin.isEmpty()) {
                        kq.addAll(nghin);
                        kq.add(NGHIN);
                    }
                    break;
                //3 số đó thuộc hàng triệu
                case 0:
                    ArrayList<String> trieu = read_3num(List_Num.get(0));
                    if (!trieu.isEmpty()) {
                        kq.addAll(trieu);
                        kq.add(TRIEU);
                    }
                    break;
            }

            //Xét nếu 3 số đó thuộc hàng tỷ
            if (List_Num.size() == (List_Num.size() / 3) * 3 + 1 && List_Num.size() != 1) {
                kq.add(TY);
            }

            //Xóa 3 số đầu tiên để tiếp tục 3 số kế
            List_Num.remove(0);
        }

        return kq;
    }
    //Đọc 3 số

    public ArrayList<String> read_3num(String a) {
        ArrayList<String> kq = new ArrayList<String>();
        int num = -1;
        try {
            num = Integer.parseInt(a);
        } catch (Exception ex) {
        }
        if (num == 0) {
            return kq;
        }

        int hang_tram = -1;
        try {
            hang_tram = Integer.parseInt(a.substring(0, 1));
        } catch (Exception ex) {
        }
        int hang_chuc = -1;
        try {
            hang_chuc = Integer.parseInt(a.substring(1, 2));
        } catch (Exception ex) {
        }
        int hang_dv = -1;
        try {
            hang_dv = Integer.parseInt(a.substring(2, 3));
        } catch (Exception ex) {
        }

        //xét hàng trăm
        if (hang_tram != -1) {
            kq.add(number[hang_tram]);
            kq.add(TRAM);
        }

        //xét hàng chục
        switch (hang_chuc) {
            case -1:
                break;
            case 1:
                kq.add(MUOIF);
                break;
            case 0:
                if (hang_dv != 0) {
                    kq.add(LE);
                }
                break;
            default:
                kq.add(number[hang_chuc]);
                kq.add(MUOI);
                break;
        }

        //xét hàng đơn vị
        switch (hang_dv) {
            case -1:
                break;
            case 1:
                if ((hang_chuc != 0) && (hang_chuc != 1) && (hang_chuc != -1)) {
                    kq.add(MOTS);
                } else {
                    kq.add(number[hang_dv]);
                }
                break;
            case 5:
                if ((hang_chuc != 0) && (hang_chuc != -1)) {
                    kq.add(LAM);
                } else {
                    kq.add(number[hang_dv]);
                }
                break;
            case 0:
                if (kq.isEmpty()) {
                    kq.add(number[hang_dv]);
                }
                break;
            default:
                kq.add(number[hang_dv]);
                break;
        }
        return kq;
    }

    public ArrayList<String> Split(String str, int chunkSize) {
        int du = str.length() % chunkSize;
        //Nếu độ dài chuổi không phải bội số của chunkSize thì thêm # vào trước cho đủ.
        if (du != 0) {
            for (int i = 0; i < (chunkSize - du); i++) {
                str = "#" + str;
            }
        }
        return splitStringEvery(str, chunkSize);
    }

    //Hàm cắt chuổi ra thành chuổi nhỏ
    public ArrayList<String> splitStringEvery(String s, int interval) {
        ArrayList<String> arrList = new ArrayList<String>();
        int arrayLength = (int) Math.ceil(((s.length() / (double) interval)));
        String[] result = new String[arrayLength];
        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        }
        result[lastIndex] = s.substring(j);

        /*
          Có thể dùng hàm sau để cắt nhưng hiệu suất sẽ thấp hơn cách trên
         result = s.split("(?<=\\G.{" + interval + "})");
         */
        arrList.addAll(Arrays.asList(result));
        return arrList;
    }

    public void inchuthongke(String a) {

        ArrayList<String> kq = readNum(a);
        String chuatk = "Số tiền bằng chữ: ";
        for (int i = 0; i < kq.size(); i++) {
            chuatk += kq.get(i) + " ";
        }
        System.out.printf("|              %10s%s   |\n", chuatk, "đồng");
    }

}
