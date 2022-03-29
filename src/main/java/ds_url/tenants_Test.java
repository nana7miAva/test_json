package ds_url;

import com.sun.javafx.fxml.builder.URLBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class tenants_Test {
    //获取海豚的租户

    public static void main(String[] args) throws Exception {


        String url = "http://ds1:12306/dolphinscheduler/tenants";

        //有参数的get请求
        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.addParameter("pageNo", "1");
        uriBuilder.addParameter("pageSize", "20");
        uriBuilder.addParameter("searchVal", "");
        URI build = uriBuilder.build();


        //根据参数生成get请求对象
        HttpGet httpGet = new HttpGet(build);
        //添加请求头 admin的token
        httpGet.setHeader("token", "ff53ac501e5b419d90aab0a30e778c49");
        //生成连接 执行
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();

        String ten_json = EntityUtils.toString(entity);
        System.out.println(ten_json);
        response.close();
        client.close();
    }


}
