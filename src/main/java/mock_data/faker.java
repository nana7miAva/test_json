package mock_data;

import com.github.javafaker.Faker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class faker {
    public static void main(String[] args) throws Exception {
        Faker faker = new Faker(Locale.CHINA);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        connection = getConnection("172.16.10.27", "test", "dba", "dba*#2022");


        List<fakerBean> list = new ArrayList<fakerBean>();
        for (int i = 0; i < 100; i++) {
            fakerBean fakerBean = new fakerBean();
            fakerBean.setCity(faker.address().city());
            fakerBean.setName(faker.name().name());
            fakerBean.setPhone(faker.phoneNumber().cellPhone());
            fakerBean.setSalary(faker.random().nextInt(10, 20) * 100.1);
            fakerBean.setUniversity(faker.university().name());
            fakerBean.setStreet(faker.address().streetAddress());
            fakerBean.setJob(faker.job().title());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String format = formatter.format(date);
            fakerBean.setLoad_date(format);
            list.add(fakerBean);

        }
        //开始插入数据

            for (fakerBean fakerBean : list) {

                String sql = "INSERT into mock_z VALUES (?,?,?,?,?,?,?,?,?);";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setObject(1, fakerBean.getId());
                preparedStatement.setObject(2, fakerBean.getName());
                preparedStatement.setObject(3, fakerBean.getPhone());
                preparedStatement.setObject(4, fakerBean.getUniversity());
                preparedStatement.setObject(5, fakerBean.getCity());
                preparedStatement.setObject(6, fakerBean.getStreet());
                preparedStatement.setObject(7, fakerBean.getSalary());
                preparedStatement.setObject(8, fakerBean.getJob());
                preparedStatement.setObject(9, fakerBean.getLoad_date());
                int i = preparedStatement.executeUpdate();
                //preparedStatement.addBatch();
            }


            preparedStatement.executeBatch();



        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    private static Connection getConnection(String host, String dbName, String user, String paw) {
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + dbName, user, paw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }


}
