package hetu;

import java.io.PrintStream;
import java.sql.*;
import java.util.Properties;

public class lkTest {
    public static void main(String[] args) {
        String url = "jdbc:lk://172.16.10.30:8080/hive/test_zjq";
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        //properties.setProperty("Schema", "test_zjq");
        //properties.setProperty("SSL", "true");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, properties);
            String sql = "select * from table_3_3";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                System.out.println(metaData.getColumnLabel(1) + ":" + resultSet.getString(metaData.getColumnLabel(1)));
                System.out.println(metaData.getColumnLabel(2) + ":" + resultSet.getString(metaData.getColumnLabel(2)));

            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("创建hetu连接失败");
        }

        try {
            if (connection != null) {
                connection.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
