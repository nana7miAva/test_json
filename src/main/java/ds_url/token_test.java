package ds_url;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
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

public class token_test {
    public static void main(String[] args) throws URISyntaxException, IOException {
        //获取登录用户的token
        String url = "http://ds1:12306/dolphinscheduler/access-tokens";

        //有参数的get请求
        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.addParameter("pageNo", "1");
        uriBuilder.addParameter("pageSize", "20");
        uriBuilder.addParameter("searchVal", "");
        URI build = uriBuilder.build();


        //根据参数生成get请求对象
        HttpGet httpGet = new HttpGet(build);
        //添加请求头 admin的token
        httpGet.setHeader("token", "c16a1e3a9946df5746ee6ef7c23e9f05");
        //生成连接 执行
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();

        String token_json = EntityUtils.toString(entity);
        //<username=token>
        HashMap<String, String> hashMap = new HashMap<String, String>();
        JSONArray token_jsonArray = JSON.parseObject(token_json).getJSONObject("data").getJSONArray("totalList");
        for (int i = 0; i < token_jsonArray.size(); i++) {
            hashMap.put(token_jsonArray.getJSONObject(i).getString("userName"), token_jsonArray.getJSONObject(i).getString("token"));
        }
        System.out.println(hashMap);

        response.close();
        client.close();
    }


}
