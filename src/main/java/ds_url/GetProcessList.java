package ds_url;



import com.alibaba.fastjson.JSON;
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

//获取当前project的所有process信息 包含有 process_code

public class GetProcessList {
    public static void main(String[] args) {

        //获取project的projectcode
        String user_token = "701d993e6a03ee865e8c9d4fe15f7396";
        urlGetProjectTest urlGetProjectTest = new urlGetProjectTest();
        JSONObject proList = urlGetProjectTest.getProList(user_token);
        JSONArray data = proList.getJSONArray("data");

        HashMap<String, String> codeMap = new HashMap<String, String>();
        for (int i = 0; i < data.size(); i++) {
            codeMap.put(data.getJSONObject(i).getString("name"), data.getJSONObject(i).getString("code"));
        }

        System.out.println(codeMap);

        //在project中创建process

        //根据项目名称获取project_code
        String projectCode = codeMap.get("test");

        GetProcessList processList = new GetProcessList();
        //data.taskDefinitionList.code 就是 process_code
        System.out.println(processList.GetProcessList(projectCode, user_token));

    }


    public JSONObject GetProcessList(String projectCode, String token) {
        String url = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/process-definition/list";


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

            String proList = EntityUtils.toString(entity);
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
