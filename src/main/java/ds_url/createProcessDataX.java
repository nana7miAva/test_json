package ds_url;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class createProcessDataX {
    public static void main(String[] args) {


        String projectCode = "4000994290048"; //zzz
        String token = "ff53ac501e5b419d90aab0a30e778c49";
        String dataXJson = "{\n" +
                "  \"job\": {\n" +
                "    \"setting\": {\n" +
                "      \"speed\": {\n" +
                "        \"channel\": 3,\n" +

                "        \"byte\": 1048576\n" +
                "      },\n" +
                "      \"errorLimit\": {\n" +
                "        \"record\": 0,\n" +
                "        \"percentage\": 0.02\n" +

                "      }\n" +
                "    },\n" +
                "    \"content\": [\n" +
                "      {\n" +
                "        \"reader\": {\n" +
                "          \"name\": \"hdfsreader\",\n" +
                "          \"parameter\": {\n" +
                "            \"path\": \"/warehouse/tablespace/managed/hive/test.db/x2_abc_test\",\n" +
                "            \"defaultFS\": \"hdfs://192.168.10.103:8020\",\n" +
                "            \"fileType\": \"text\",\n" +
                "            \"fieldDelimiter\": \",\",\n" +
                "            \"skipHeader\": false,\n" +
                "            \"column\": [\n" +
                "              {\n" +
                "                \"index\": \"0\",\n" +
                "                \"type\": \"long\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"index\": \"1\",\n" +
                "                \"type\": \"long\"\n" +
                "              },\n" +
                "              {\n" +
                "                \"index\": \"2\",\n" +
                "                \"type\": \"date\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        },\n" +
                "        \"writer\": {\n" +
                "          \"name\": \"mysqlwriter\",\n" +
                "          \"parameter\": {\n" +
                "            \"username\": \"dba\",\n" +
                "            \"password\": \"dba*#2020\",\n" +
                "            \"column\": [\n" +
                "              \"`id`\",\n" +
                "              \"`score`\",\n" +
                "              \"`create_time`\"\n" +
                "            ],\n" +
                "            \"collectMode\": \"all\",\n" +
                "            \"writeMode\": \"insert\",\n" +
                "            \"connection\": [\n" +
                "              {\n" +
                "                \"table\": [\n" +
                "                  \"x2_abc_test\"\n" +
                "                ],\n" +
                "                \"jdbcUrl\": \"jdbc:mysql://192.168.10.170:3306/test\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        //System.out.println(dataXJson);


        //TODO 传进来的dataX的json串中   "要替换成\"  不能出现换行符\n  否则识别不出来!!!!!
        String replaceAll = dataXJson.replaceAll("\"", "\\\\\"").replaceAll("\n", "");
        createProcessDataX createProcessDataX = new createProcessDataX();
        //System.out.println(createProcessDataX.createProcessDataX(projectCode, token));
        System.out.println(createProcessDataX.createProcessDataX(projectCode, token, replaceAll));


    }

    public JSONObject createProcessDataX(String projectCode, String token) {


        String url = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/process-definition";

        //post请求对象
        HttpPost post = new HttpPost(url);

        //参数集合
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        //自己制定taskCode
        Long processCode = Long.valueOf(123456);

        String locations_str = "  {     \"taskCode\": " + processCode + ",     \"x\": 147,     \"y\": 159   }";
        JSONObject locations_json = JSON.parseObject(locations_str);


        //添加参数
        params.add(new BasicNameValuePair("locations", locations_json.toJSONString()));
        params.add(new BasicNameValuePair("name", "dataxTest222321232"));

        //关键参数

        String taskDefinitionJson = "[" +
                "  {" +
                "    \"code\": " + processCode + "," +
                "    \"name\": \"datax\"," +
                "    \"description\": \"datax\"," +
                "    \"taskType\": \"DATAX\"," +
                "    \"taskParams\": {" +
                "      \"customConfig\": 0," +
                "      \"dsType\": \"MYSQL\"," +
                "      \"dataSource\": 1," +
                "      \"dtType\": \"MYSQL\"," +
                "      \"dataTarget\": 1," +
                "      \"sql\": \"sssss\"," +
                "      \"targetTable\": \"asdsda\"," +
                "      \"jobSpeedByte\": 0," +
                "      \"jobSpeedRecord\": 1000," +
                "      \"preStatements\": []," +
                "      \"postStatements\": []," +
                "      \"xms\": 1," +
                "      \"xmx\": 1," +
                "      \"dependence\": {}," +
                "      \"conditionResult\": {" +
                "        \"successNode\": []," +
                "        \"failedNode\": []" +
                "      }," +
                "      \"waitStartTimeout\": {}," +
                "      \"switchResult\": {}" +
                "    }," +
                "    \"flag\": \"YES\"," +
                "    \"taskPriority\": \"MEDIUM\"," +
                "    \"workerGroup\": \"default\"," +
                "    \"failRetryTimes\": \"0\"," +
                "    \"failRetryInterval\": \"1\"," +
                "    \"timeoutFlag\": \"CLOSE\"," +
                "    \"timeoutNotifyStrategy\": \"\"," +
                "    \"timeout\": 0," +
                "    \"delayTime\": \"0\"," +
                "    \"environmentCode\": -1" +
                "  }" +
                "]";

        params.add(new BasicNameValuePair("taskDefinitionJson", taskDefinitionJson));


        String taskRelationJson = "[" +
                "  {" +
                "    \"name\": \"\"," +
                "    \"preTaskCode\": 0," +
                "    \"preTaskVersion\": 0," +
                "    \"postTaskCode\": " + processCode + "," +
                "    \"postTaskVersion\": 0," +
                "    \"conditionType\": 0," +
                "    \"conditionParams\": {}" +
                "  }" +
                "]";


        params.add(new BasicNameValuePair("taskRelationJson", taskRelationJson));
        params.add(new BasicNameValuePair("tenantCode", "default"));
        params.add(new BasicNameValuePair("description", "api创建datax测试流程"));
        params.add(new BasicNameValuePair("globalParams", "[]"));
        params.add(new BasicNameValuePair("timeout", "0"));


        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;
        try {
            //参数放入请求对象
            post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            //添加header参数
            post.addHeader("token", token);

            //启动执行请求 获得返回值
            client = HttpClients.createDefault();
            response = client.execute(post);

            //返回的entity对象
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");

            //返回内容
            jsonObject = JSON.parseObject(result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                response.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return jsonObject;


    }


    public JSONObject createProcessDataX(String projectCode, String token, String dataXjson) {


        String url = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/process-definition";

        //post请求对象
        HttpPost post = new HttpPost(url);

        //参数集合
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        //自己制定taskCode
        Long processCode = Long.valueOf(123456782);

        String locations_str = "  {     \"taskCode\": " + processCode + ",     \"x\": 147,     \"y\": 159   }";
        JSONObject locations_json = JSON.parseObject(locations_str);


        //添加参数
        params.add(new BasicNameValuePair("locations", locations_json.toJSONString()));
        params.add(new BasicNameValuePair("name", "apiTest-20220108_3"));

        //关键参数

        String taskDefinitionJson = "[\n" +
                "  {\n" +
                "    \"code\": " + processCode + ",\n" +
                "    \"name\": \"api创建DataX任务测试3\",\n" +
                "    \"description\": \"aeqweqwewqeqw\",\n" +
                "    \"taskType\": \"DATAX\",\n" +
                "    \"taskParams\": {\n" +
                "      \"customConfig\": 1,\n" +
                "      \"json\": \"" + dataXjson + "\",\n" +
                "      \"localParams\": [\n" +
                "        {\n" +
                "          \"prop\": \"${asdasd}\",\n" +
                "          \"direct\": \"IN\",\n" +
                "          \"type\": \"VARCHAR\",\n" +
                "          \"value\": \"这是测试的自定义参数\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"xms\": 2,\n" +
                "      \"xmx\": 3,\n" +
                "      \"dependence\": {},\n" +
                "      \"conditionResult\": {\n" +
                "        \"successNode\": [],\n" +
                "        \"failedNode\": []\n" +
                "      },\n" +
                "      \"waitStartTimeout\": {},\n" +
                "      \"switchResult\": {}\n" +
                "    },\n" +
                "    \"flag\": \"NO\",\n" +
                "    \"taskPriority\": \"HIGHEST\",\n" +
                "    \"workerGroup\": \"ds1\",\n" +
                "    \"failRetryTimes\": 2,\n" +
                "    \"failRetryInterval\": 10,\n" +
                "    \"timeoutFlag\": \"OPEN\",\n" +
                "    \"timeoutNotifyStrategy\": \"WARN\",\n" +
                "    \"timeout\": 30,\n" +
                "    \"delayTime\": 1,\n" +
                "    \"environmentCode\": 3986512556032\n" +
                "  }\n" +
                "]";

        //System.out.println(taskDefinitionJson);
        params.add(new BasicNameValuePair("taskDefinitionJson", taskDefinitionJson));


        String taskRelationJson = "[" +
                "  {" +
                "    \"name\": \"\"," +
                "    \"preTaskCode\": 0," +
                "    \"preTaskVersion\": 0," +
                "    \"postTaskCode\": " + processCode + "," +
                "    \"postTaskVersion\": 0," +
                "    \"conditionType\": 0," +
                "    \"conditionParams\": {}" +
                "  }" +
                "]";
        //System.out.println(taskRelationJson);


        params.add(new BasicNameValuePair("taskRelationJson", taskRelationJson));
        params.add(new BasicNameValuePair("tenantCode", "isi"));
        params.add(new BasicNameValuePair("description", "test"));
        params.add(new BasicNameValuePair("globalParams", "[]"));
        params.add(new BasicNameValuePair("timeout", "45"));


        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;
        try {
            //参数放入请求对象
            post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            //添加header参数
            post.addHeader("token", token);

            //启动执行请求 获得返回值
            client = HttpClients.createDefault();
            response = client.execute(post);

            //返回的entity对象
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");

            //返回内容
            jsonObject = JSON.parseObject(result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                response.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return jsonObject;


    }
}
