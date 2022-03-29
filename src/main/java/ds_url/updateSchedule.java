package ds_url;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

//更新定时调度
public class updateSchedule {
    public static void main(String[] args) {
        String token = "ff53ac501e5b419d90aab0a30e778c49";
        String id = "40";
        String projectCode = "4000994290048";

        System.out.println(updateSchedule(token, id, projectCode));


    }

    public static JSONObject updateSchedule(String token, String id, String projectCode) {

        String putUrl = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/schedules/" + id;
        //有参数的put请求
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        URIBuilder uriBuilder = null;
        JSONObject result = null;
        try {
            uriBuilder = new URIBuilder(putUrl);
            uriBuilder.addParameter("projectCode", projectCode);
            uriBuilder.addParameter("id", id);
            uriBuilder.addParameter("schedule", "{\"startTime\":\"2022-02-23 00:00:00\",\"endTime\":\"2122-02-23 00:00:00\",\"crontab\":\"5 53 15 * * ? *\",\"timezoneId\":\"Asia/Saigon\"}");
            uriBuilder.addParameter("failureStrategy", "CONTINUE");
            uriBuilder.addParameter("processInstancePriority", "MEDIUM");
            uriBuilder.addParameter("warningGroupId", "1");
            uriBuilder.addParameter("warningType", "NONE");
            uriBuilder.addParameter("workerGroup", "ds2");
            uriBuilder.addParameter("environmentCode", null);

            URI build = uriBuilder.build();
            //根据参数生成put请求对象
            HttpPut httpPut = new HttpPut(build);
            //添加请求头 admin的token
            httpPut.setHeader("token", token);
            //生成连接 执行
            client = HttpClients.createDefault();

            response = client.execute(httpPut);
            HttpEntity entity = response.getEntity();

            String proList = EntityUtils.toString(entity, "utf-8");
            result = JSONObject.parseObject(proList);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
                if (client != null)
                    client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //return result.getJSONObject("data").getJSONObject("processDefinition").getString("releaseState");
        return result;

    }

}
