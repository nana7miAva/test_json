package ds_url;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;


/*{
     "database": "test",
     "host": "ds1",
     "id": 0,
     "name": "api_test",
     "note": "",
     "other": {},
     "password": "dba%2021",
     "port": 3306,
     "type": "MYSQL",
     "userName": "dba"
}*/

//创建数据源连接
//连接数据源
public class createDatasourceTest {
    public static void main(String[] args) {

        String token = "ff53ac501e5b419d90aab0a30e778c49";

        //传参
        /*HashMap<String, String> conMap = new HashMap<String, String>();
        conMap.put("database", "test");
        conMap.put("host", "ds1");
        conMap.put("name", "api_test");
        conMap.put("password", "dba%2021");
        conMap.put("port", "MYSQL");
        conMap.put("id", "0");
        conMap.put("note", "");
        conMap.put("other", "{}");*/


        //json参数
        String jsonconfig = "{\n" +
                "     \"database\": \"test\",\n" +
                "     \"host\": \"ds1\",\n" +
                "     \"id\": 0,\n" +
                "     \"name\": \"api_test2\",\n" +
                "     \"note\": \"\",\n" +
                "     \"other\": {},\n" +
                "     \"password\": \"dba%2021\",\n" +
                "     \"port\": 3306,\n" +
                "     \"type\": \"MYSQL\",\n" +
                "     \"userName\": \"dba\"\n" +
                "}";

        createDatasourceTest createDatasourceTest = new createDatasourceTest();
        /*String datasource = createDatasourceTest.createDatasource(JSON.parseObject(jsonconfig), token);
        System.out.println(datasource);*/

        String connectDatasource = createDatasourceTest.connectDatasource(JSON.parseObject(jsonconfig), token);
        System.out.println(connectDatasource);

    }

    public String createDatasource(JSONObject jsonObject, String token) {
        String url = "http://ds1:12306/dolphinscheduler/datasources";


        //参数放入请求对象
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        JSONObject resultJson = null;

        try {
            //post请求对象
            HttpPost post = new HttpPost(url);
            //参数集合
            //List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            /*if (conMap != null) {
                List<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>();
                for (String key : conMap.keySet()) {
                    paramList.add(new BasicNameValuePair(key, conMap.get(key)));
                }*/

            //装填参数

            StringEntity config = new StringEntity(jsonObject.toString(), "utf-8");
            //config.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            post.setEntity(config);


            post.addHeader("token", token);
            post.addHeader("content-type", "application/json");


            //启动执行请求 获得返回值
            client = HttpClients.createDefault();
            response = client.execute(post);
            //返回的entity对象
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "utf-8");
                resultJson = JSON.parseObject(result);
            }
            //返回内容


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return  "创建新的数据库连接 :" + resultJson.getString("msg");


    }


    public String connectDatasource(JSONObject jsonObject, String token) {
        String url = "http://ds1:12306/dolphinscheduler/datasources/connect";


        //参数放入请求对象
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        JSONObject resultJson = null;

        try {
            //post请求对象
            HttpPost post = new HttpPost(url);
            //参数集合
            //List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            /*if (conMap != null) {
                List<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>();
                for (String key : conMap.keySet()) {
                    paramList.add(new BasicNameValuePair(key, conMap.get(key)));
                }*/

            //装填参数

            StringEntity config = new StringEntity(jsonObject.toString(), "utf-8");
            //config.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            post.setEntity(config);


            post.addHeader("token", token);
            post.addHeader("content-type", "application/json");


            //启动执行请求 获得返回值
            client = HttpClients.createDefault();
            response = client.execute(post);
            //返回的entity对象
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "utf-8");
                resultJson = JSON.parseObject(result);
            }
            //返回内容


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return  "数据源连接 :" + resultJson.getString("msg");


    }


}

