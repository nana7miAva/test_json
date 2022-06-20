package mock_data;

import lombok.Data;

import java.util.Date;


@Data
public class fakerBean {

    private Integer id;

    /**
     * 姓名
     */
    private String Name;
    /**
     * 手机
     */
    private String Phone;
    /**
     * 大学
     */
    private String university;
    /**
     * 城市
     */
    private String city;
    /**
     * 地址
     */
    private String street;

    /**
     * 薪资
     */
    private Double salary;
    /**
     * 工作
     */
    private String job;
    /**
     * 导入时间
     */
    private String load_date;
}
