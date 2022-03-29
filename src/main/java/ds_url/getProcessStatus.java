package ds_url;

import com.alibaba.fastjson.JSON;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

//根据processid获取到这process的状态
public class getProcessStatus {
    public static void main(String[] args) {
        String token = "ff53ac501e5b419d90aab0a30e778c49";
        String processCode = "4564609582336";
        String projectCode = "4000994290048";

        /*JSONObject jsonObject = queryProcessDefinitionByCode(token, projectCode, processCode);
        String string = jsonObject.getJSONObject("data").getJSONObject("processDefinition").getString("releaseState");
        System.out.println(string);*/

        JSONObject jsonObject = queryProcessDefinitionByCode(token, projectCode, processCode);
        System.out.println(jsonObject);

    }

    public static JSONObject queryProcessDefinitionByCode(String token, String projectCode, String processCode) {
        String requestUrl = "http://ds1:12306//dolphinscheduler/projects/" + projectCode + "/process-definition/" + processCode;


        //有参数的get请求
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        URIBuilder uriBuilder = null;
        JSONObject result = null;
        try {
            uriBuilder = new URIBuilder(requestUrl);
            uriBuilder.addParameter("projectCode", projectCode);
            uriBuilder.addParameter("code", processCode);
            URI build = uriBuilder.build();


            //根据参数生成get请求对象
            HttpGet httpGet = new HttpGet(build);

            //添加请求头 admin的token
            httpGet.setHeader("token", token);
            //生成连接 执行
            client = HttpClients.createDefault();
            response = client.execute(httpGet);
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
