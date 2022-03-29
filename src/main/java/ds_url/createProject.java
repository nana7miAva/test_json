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

import java.util.ArrayList;
//创建项目

public class createProject {
    public static void main(String[] args) throws Exception {
        createProject("zzz", "zzz", "ff53ac501e5b419d90aab0a30e778c49", "application/json");
    }

    public static String createProject(String description, String projectName, String token, String Accept) throws Exception {
        String url = "http://ds1:12306/dolphinscheduler/projects";
        //post请求对象
        HttpPost post = new HttpPost(url);

        //参数集合
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        //添加参数
        params.add(new BasicNameValuePair("description", description));
        params.add(new BasicNameValuePair("projectName", projectName));

        //参数放入请求对象
        post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

        //添加header参数
        post.addHeader("Accept", Accept);
        //post.addHeader("content-type", "application/json");
        post.addHeader("token", token);

        //启动执行请求 获得返回值
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(post);
        //返回的entity对象
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "utf-8");


        System.out.println(result);
        //返回内容
        JSONObject jsonObject = JSON.parseObject(result);
        /*System.out.println(
                jsonObject.getString("msg") + "\n"
                        + "创建新项目 :" + jsonObject.getString("success")

        );*/
        response.close();
        client.close();
        return jsonObject.getString("msg") + "\n"
                + "创建新项目 :" + jsonObject.getString("success");

    }
}
