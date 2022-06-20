package ds_url;


//获取任务定时需要的processcode

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class getProcessCode {
    public static void main(String[] args) {

        String token = "ff53ac501e5b419d90aab0a30e778c49";
        HashMap<String, String> projectMap = GetProject.getProjectMap(token);

        String zzz = projectMap.get("zzz");
        JSONObject jsonObject = getProcessCode(zzz, token);
        Map<String, String> processCodeMap = getProcessCodeMap(zzz, token);
        System.out.println(processCodeMap.toString());


        //获取到这些process的processDefinition的code 这个是processcode


    }

    // 返回process的详细信息 json字符串
    public static JSONObject getProcessCode(String projectCode, String token) {

        String url = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/process-definition/all";


        //有参数的get请求
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        URIBuilder uriBuilder = null;
        JSONObject result = null;
        try {
            uriBuilder = new URIBuilder(url);
            uriBuilder.addParameter("projectCode", projectCode);
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
        return result;
    }


    //processcode 和 processname 放到一个Map里面 方面后面用
    public static Map<String, String> getProcessCodeMap(String projectCode, String token) {

        HashMap<String, String> resultMap = new HashMap<String, String>();
        JSONObject processCode = getProcessCode(projectCode, token);
        JSONArray jsonArray = processCode.getJSONArray("data");

        for (int i = 0; i < jsonArray.size(); i++) {
            resultMap.put(
                    jsonArray.getJSONObject(i).getJSONObject("processDefinition").getString("name"),
                    jsonArray.getJSONObject(i).getJSONObject("processDefinition").getString("code")
            );
        }


        return resultMap;
    }

}

