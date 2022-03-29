package ds_url;

//在一个Process中创建多个连续的Task 根据参数json数组的顺序执行

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

public class createConProDataX {
    public static void main(String[] args) {

        String projectCode = "4000994290048"; //zzz
        String token = "ff53ac501e5b419d90aab0a30e778c49";
        String taskDefinitionJson = " [" +
                "{" +
                "\"code\": 4548464548782," +
                "\"name\": \"datax1\"," +
                "\"version\": 1," +
                "\"description\": \"xxxxx\", " +
                "\"delayTime\": 0," +
                "\"taskType\": \"DATAX\"," +
                "\"taskParams\": {" +
                "\"customConfig\": 0," +
                "\"dsType\": \"MYSQL\"," +
                "\"dataSource\": 1," +
                "\"dtType\": \"MYSQL\"," +
                "\"dataTarget\": 1," +
                "\"sql\": \"testtesttesttesttesttesttest\"," +
                "\"targetTable\": \"test\"," +
                "\"jobSpeedByte\": 0," +
                "\"jobSpeedRecord\": 1000," +
                "\"preStatements\": [" +
                "" +
                "]," +
                "\"postStatements\": [" +
                "" +
                "]," +
                "\"xms\": 1," +
                "\"xmx\": 1," +
                "\"dependence\": {" +
                "" +
                "}," +
                "\"conditionResult\": {" +
                "\"successNode\": [" +
                "" +
                "]," +
                "\"failedNode\": [" +
                "" +
                "]" +
                "}," +
                "\"waitStartTimeout\": {" +
                "" +
                "}," +
                "\"switchResult\": {" +
                "" +
                "}" +
                "}," +
                "\"flag\": \"YES\"," +
                "\"taskPriority\": \"MEDIUM\"," +
                "\"workerGroup\": \"default\"," +
                "\"failRetryTimes\": 0," +
                "\"failRetryInterval\": 1," +
                "\"timeoutFlag\": \"CLOSE\"," +
                "\"timeoutNotifyStrategy\": null," +
                "\"timeout\": 0," +
                "\"environmentCode\": -1" +
                "}," +
                "{" +
                "\"code\": 8786321548526," +
                "\"name\": \"data2\"," +
                "\"description\": \"22222222222\"," +
                "\"taskType\": \"DATAX\"," +
                "\"taskParams\": {" +
                "\"customConfig\": 0," +
                "\"dsType\": \"MYSQL\"," +
                "\"dataSource\": 1," +
                "\"dtType\": \"MYSQL\"," +
                "\"dataTarget\": 1," +
                "\"sql\": \"222222222222222\"," +
                "\"targetTable\": \"22222222\"," +
                "\"jobSpeedByte\": 0," +
                "\"jobSpeedRecord\": 1000," +
                "\"preStatements\": [" +
                "" +
                "]," +
                "\"postStatements\": [" +
                "" +
                "]," +
                "\"xms\": 1," +
                "\"xmx\": 1," +
                "\"dependence\": {" +
                "" +
                "}," +
                "\"conditionResult\": {" +
                "\"successNode\": [" +
                "" +
                "]," +
                "\"failedNode\": [" +
                "" +
                "]" +
                "}," +
                "\"waitStartTimeout\": {" +
                "" +
                "}," +
                "\"switchResult\": {" +
                "" +
                "}" +
                "}," +
                "\"flag\": \"YES\"," +
                "\"taskPriority\": \"MEDIUM\"," +
                "\"workerGroup\": \"default\"," +
                "\"failRetryTimes\": \"0\"," +
                "\"failRetryInterval\": \"1\"," +
                "\"timeoutFlag\": \"CLOSE\"," +
                "\"timeoutNotifyStrategy\": \"\"," +
                "\"timeout\": 0," +
                "\"delayTime\": \"0\"," +
                "\"environmentCode\": -1" +
                "}" +
                "]";
        String taskRelationJson = "[\n" +
                "\t{\n" +
                "\t\t\"name\": \"\",\n" +
                "\t\t\"preTaskCode\": 4548464548782,\n" +
                "\t\t\"preTaskVersion\": 1,\n" +
                "\t\t\"postTaskCode\": 8786321548526,\n" +
                "\t\t\"postTaskVersion\": 0,\n" +
                "\t\t\"conditionType\": 0,\n" +
                "\t\t\"conditionParams\": {\n" +
                "\t\t\t\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"name\": \"\",\n" +
                "\t\t\"preTaskCode\": 0,\n" +
                "\t\t\"preTaskVersion\": 0,\n" +
                "\t\t\"postTaskCode\": 4548464548782,\n" +
                "\t\t\"postTaskVersion\": 1,\n" +
                "\t\t\"conditionType\": 0,\n" +
                "\t\t\"conditionParams\": {\n" +
                "\t\t\t\n" +
                "\t\t}\n" +
                "\t}\n" +
                "]";

        String dataxJson="";



        System.out.println(createProcessDataX("多task测试_3", projectCode, token, taskDefinitionJson, taskRelationJson));

    }


    /*
     * processCode 流程id
     * projectCode 项目id
     * token 用户令牌 控制权限
     * taskDefinitionJson 定义task具体配置的json串 ***特殊格式***
     * taskRelationJson  表名task关系json串
     * */
    public static JSONObject createProcessDataX(String processName, String projectCode, String token, String taskDefinitionJson, String taskRelationJson) {


        String url = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/process-definition";

        //post请求对象
        HttpPost post = new HttpPost(url);

        //参数集合
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        //自己制定taskCode
        Long processCode_1 = Long.valueOf(1234567812);
        Long processCode_2 = Long.valueOf(765432132);

        //String locations_str = "  {     \"taskCode\": " + processCode + ",     \"x\": 147,     \"y\": 159   }";
        String locations_str = "[{\"taskCode\":" + processCode_1 + ",\"x\":160,\"y\":74},{\"taskCode\":" + processCode_2 + ",\"x\":170,\"y\":230}]";


        //添加参数
        params.add(new BasicNameValuePair("locations", locations_str));
        params.add(new BasicNameValuePair("name", processName));
        //关键参数
        //System.out.println(taskDefinitionJson);
        params.add(new BasicNameValuePair("taskDefinitionJson", taskDefinitionJson));
        //System.out.println(taskRelationJson);
        params.add(new BasicNameValuePair("taskRelationJson", taskRelationJson));
        params.add(new BasicNameValuePair("tenantCode", "isi"));
        //非必填
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
