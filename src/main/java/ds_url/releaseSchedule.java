package ds_url;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
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
import java.util.HashMap;
import java.util.Map;

//定时任务的上线和下线
public class releaseSchedule {
    //
    public static void main(String[] args) {
        String token = "ff53ac501e5b419d90aab0a30e778c49";
        String projectCode = "4000994290048";
        JSONObject jsonObject = queryScheduleList.queryScheduleList(token, projectCode);
        Map<String, String> scheduleMap = new HashMap<String, String>();
        JSONArray dataArray = jsonObject.getJSONArray("data");

        for (int i = 0; i < dataArray.size(); i++) {
            scheduleMap.put(
                    dataArray.getJSONObject(i).getString("processDefinitionName")
                    , dataArray.getJSONObject(i).getString("id")
            );
        }

        System.out.println(scheduleMap);

        String id = scheduleMap.get("多task_mysql+自定义datax");

        //上线
        System.out.println(offline(token, projectCode, id));

    }

    public static JSONObject offline(String token, String projectCode, String id) {


        String url = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/schedules/" + id + "/offline";

        //post请求对象
        HttpPost post = new HttpPost(url);

        //参数集合
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        params.add(new BasicNameValuePair("projectCode", projectCode));


        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;
        try {
            //参数放入请求对象
            post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            //添加header参数
            post.addHeader("token", token);

            //启动执行请求 获得返回值
            client = HttpClients.createDefault();
            response = client.execute(post);

            //返回的entity对象
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");

            //返回内容
            jsonObject = JSON.parseObject(result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                response.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return jsonObject;


    }


    public static JSONObject online(String token, String projectCode, String id) {


        String url = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/schedules/" + id + "/online";

        //post请求对象
        HttpPost post = new HttpPost(url);

        //参数集合
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        params.add(new BasicNameValuePair("projectCode", projectCode));


        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;
        try {
            //参数放入请求对象
            post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            //添加header参数
            post.addHeader("token", token);

            //启动执行请求 获得返回值
            client = HttpClients.createDefault();
            response = client.execute(post);

            //返回的entity对象
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");

            //返回内容
            jsonObject = JSON.parseObject(result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                response.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return jsonObject;


    }

}

