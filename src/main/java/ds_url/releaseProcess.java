package ds_url;

import com.alibaba.fastjson.JSON;
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

//创建的流程发布(上线)  上线之后才能执行定时调度的操作
public class releaseProcess {


    public static void main(String[] args) {

        String token = "ff53ac501e5b419d90aab0a30e778c49";

        HashMap<String, String> projectMap = GetProject.getProjectMap(token);
        //name也要作为参数传递
        String projectName="zzz";
        String projectCode = projectMap.get(projectName);
        System.out.println(projectCode);


        Map<String, String> processCodeMap = getProcessCode.getProcessCodeMap(projectCode, token);
        String processCode = processCodeMap.get("多task_mysql+自定义datax_2");

        System.out.println(processCode);


        //String zzz = onLine(token, projectCode, processCode, projectName);
        //System.out.println(zzz);

        String xxx = offLine(token, projectCode, processCode, projectName);
        System.out.println(xxx);



    }


    //上线
    public static String onLine(String token, String projectCode, String processCode, String name) {


        String url = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/process-definition/" + processCode + "/release";

        //post请求对象
        HttpPost post = new HttpPost(url);

        //参数集合
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        //添加参数
        params.add(new BasicNameValuePair("code", processCode));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("projectCode", projectCode));
        params.add(new BasicNameValuePair("releaseState", "ONLINE"));
        //params.add(new BasicNameValuePair("releaseState", "OFFLINE"));
        //关键参数


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

        return jsonObject.toJSONString();


    }

    //下线

    public static String offLine(String token, String projectCode, String processCode, String name) {


        String url = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/process-definition/" + processCode + "/release";

        //post请求对象
        HttpPost post = new HttpPost(url);

        //参数集合
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        //添加参数
        params.add(new BasicNameValuePair("code", processCode));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("projectCode", projectCode));
        params.add(new BasicNameValuePair("releaseState", "OFFLINE"));
        //关键参数


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

        return jsonObject.toJSONString();


    }

}

