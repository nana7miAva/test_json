package whzt;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import whzt.SHA256;

import java.io.IOException;
import java.nio.charset.Charset;

public class ztDemo {


    private static final String URL = "https://wenhai.wengegroup.com/wenhai-api/essql/getDataBySql";
    private static final String APP_KEY = "NZJ1pmi7";
    private static final String APP_SECRET = "fe4c483130e46aa55b69564ba1c05f3ca13adc7e";

    public static void requestForHttp(String url, String param) throws IOException {


        /** 创建HttpClient */
        CloseableHttpClient httpClient = HttpClients.createDefault();
        /** httpPost请求头以及请求体 */
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        String currtime = String.valueOf(System.currentTimeMillis());
        httpPost.addHeader("appKey", APP_KEY);
        httpPost.addHeader("sign", SHA256.getDigest(APP_KEY + currtime + APP_SECRET));
        httpPost.addHeader("timeStamp", currtime);


        httpPost.setEntity(new StringEntity(param, Charset.forName("UTF-8")));
        /** 发送请求*/
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        /** 解析请求，json格式的返回数据*/
        try {
            Header[] contentType = httpResponse.getHeaders("Content-Type");
            HttpEntity httpEntity = httpResponse.getEntity();
            String resp = EntityUtils.toString(httpEntity, "UTF-8");
            EntityUtils.consume(httpEntity);
            System.out.println(resp);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public static void main(String[] args) throws IOException {
        JSONObject params = new JSONObject();
        params.put("start_time", "2022-08-07 00:00:00");
        params.put("end_time", "2020-08-08 20:00:00");
        //params.put("page_size", "30");
        //params.put("kws_include", "美");
        /*String esSql = "SELECT *  FROM major_info_202203 where " +
                "pubtime >= '"+startTime+"' and pubtime <= '"+endTime+"' and  site='"+site+"' and gid > "+integer+" order by gid asc limit 150";*/

        params.put("sql","SELECT title,content,pubtime,insert_time,picture_index,site,gid FROM news_info where " +
                "insert_time>='2022-08-01 00:00:00' and title = '人民网' and  pubtime >='2022-08-01 00:00:00' limit 10");

        /** 处理结果 */
        ztDemo.requestForHttp(URL, params.toJSONString());
    }


}
