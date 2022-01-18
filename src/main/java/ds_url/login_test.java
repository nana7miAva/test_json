package ds_url;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class login_test {
    private HttpPost post = new HttpPost("http://ds1:12306/dolphinscheduler/login");


    public String login_ds(String userName, String userPassword) {

        //参数集合
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        //添加参数
        params.add(new BasicNameValuePair("userName", userName));
        params.add(new BasicNameValuePair("userPassword", userPassword));

        try {
            //传参
            post.setEntity(new UrlEncodedFormEntity(params));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //post.addHeader();
        //启动请求
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;
        try {
            response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            jsonObject = JSON.parseObject(result);

        } catch (IOException e) {
            e.printStackTrace();

        }
        return jsonObject.getString("msg");

    }

    public static void main(String[] args) throws Exception {
        login_test login_test = new login_test();
        String result = login_test.login_ds("sql_test", "sql_test");
        System.out.println(result);


    }
}
