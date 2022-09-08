package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class YesterdayDate {

    public static void main(String[] args) {

        String yesterdayDate = "2022-07-15";
        String sql = ("select cast(`id` as string) as uuid_5g,name as name_5g,mobile as phone_5g,email as email_5g from  " +
                "ods_d_5g_qmp_lessee_user where isdeleted='0' and to_date(rksj)='" + yesterdayDate + "'");


        System.out.println(sql);


    }


    private static String getYesterdayDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, -24);
        return dateFormat.format(calendar.getTime());

    }
}
