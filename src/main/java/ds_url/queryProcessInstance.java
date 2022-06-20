package ds_url;

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

//流程实例列表
public class queryProcessInstance {
    public static void main(String[] args) {

        String projectCode="4000994290048";
        String token="ff53ac501e5b419d90aab0a30e778c49";
        JSONObject jsonObject = queryProcessInstanceList(projectCode,token);
        //System.out.println(jsonObject);
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("totalList");
        for (int i = 0; i <jsonArray.size() ; i++) {
            System.out.println(jsonArray.getJSONObject(i));
            System.out.println("---");
        }

    }


    public static JSONObject queryProcessInstanceList(String projectCode, String token) {
        String url = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/process-instances";


        //有参数的get请求
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        URIBuilder uriBuilder = null;
        JSONObject result = null;
        try {
            uriBuilder = new URIBuilder(url);
            uriBuilder.addParameter("projectCode", projectCode);
            uriBuilder.addParameter("pageSize", "50");
            uriBuilder.addParameter("pageNo", "1");
            URI build = uriBuilder.build();


            //根据参数生成get请求对象
            HttpGet httpGet = new HttpGet(build);
            //添加请求头 admin的token
            httpGet.setHeader("token", token);
            //生成连接 执行
            client = HttpClients.createDefault();
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();

            String proList = EntityUtils.toString(entity,"utf-8");
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
}
