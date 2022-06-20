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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class GetProcessDetails {
    //通过这个接口获取详细信息

    public static void main(String[] args) {

        String code1 = "5117163891424";
        String code2 = "5114790042976";
        String projectCode = "5114778137952";
        String token = "af6452737bdf484f5d922c51caa4f053";


        JSONObject json1 = getProcessDetails(projectCode, token, code1);
        JSONObject json2 = getProcessDetails(projectCode, token, code2); //SUB_PROCESS


        JSONObject data1 = json1.getJSONObject("data");
        JSONObject data2 = json2.getJSONObject("data");

        //data 里面 有三个 json数组
        // processTaskRelationList  依赖关系
        // taskDefinitionList 包含的task的详细信息
        // processDefinition  本身这个流程的信息

        JSONArray taskDefinitionList2 = data2.getJSONArray("taskDefinitionList");
        JSONObject processDefinition2 = data2.getJSONObject("processDefinition");
        Iterator<Object> iterator2 = taskDefinitionList2.iterator();

        String name = processDefinition2.getString("name");
        List<HashMap<String, String>> hashMaps = new ArrayList<HashMap<String, String>>();
        while (iterator2.hasNext()) {
            HashMap<String, String> resultMap = new HashMap<String, String>();
            JSONObject next = (JSONObject) iterator2.next();

            resultMap.put("code", next.getString("code"));
            resultMap.put("name", next.getString("name"));
            resultMap.put("projectCode", next.getString("projectCode"));
            resultMap.put("taskType", next.getString("taskType"));
            String taskType = next.getString("taskType");
            hashMaps.add(resultMap);

            //resultMap.clear();
        }


        for (HashMap<String, String> hashMap : hashMaps) {
            if (hashMap.get("taskType").equals("SUB_PROCESS")) {
                System.out.println("包含子流程");
                System.out.println(hashMaps);
                break;

            }

        }
    }

    public static JSONObject getProcessDetails(String projectCode, String token, String code) {
        String url = "http://172.16.10.34:12345/dolphinscheduler/projects/" + projectCode + "/process-definition/" + code;


        //有参数的get请求
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        URIBuilder uriBuilder = null;
        JSONObject result = null;
        try {
            uriBuilder = new URIBuilder(url);
            uriBuilder.addParameter("projectCode", projectCode);
            uriBuilder.addParameter("code", code);
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
}
