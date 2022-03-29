package ds_url;

//在一个Process中创建多个连续的Task 根据参数json数组的顺序执行
//这个是 mysql类型的task+datax类型的task 一共两个
//taskCode 需要和之前的taskCode不一样 但是同一个task的222222 前后需要一样

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

public class createConProDataX2 {
    public static void main(String[] args) {

        String projectCode = "4000994290048"; //zzz
        String token = "ff53ac501e5b419d90aab0a30e778c49";

        String locations_str = "[\n" +
                "        {\n" +
                "            \"taskCode\": 111111,\n" +
                "            \"x\": 380,\n" +
                "            \"y\": 230\n" +
                "        },\n" +
                "        {\n" +
                "            \"taskCode\": 222222,\n" +
                "            \"x\": 765,\n" +
                "            \"y\": 230\n" +
                "        }\n" +
                "    ]";

        String dataXJson = "{\n" +
                "    \"job\": {\n" +
                "        \"content\": [\n" +
                "            {\n" +
                "                \"reader\": {\n" +
                "                    \"name\": \"mysqlreader\",\n" +
                "                    \"parameter\": {\n" +
                "                        \"username\": \"dba\",\n" +
                "                        \"password\": \"dba%2021\",\n" +
                "                        \n" +
                "                        \"connection\": [\n" +
                "                            {\n" +
                "                               \"querySql\": [\n" +
                "                                    \"select uid,id,name from ds_test;\"\n" +
                "                                ],\n" +
                "                                \"jdbcUrl\": [\n" +
                "                                    \"jdbc:mysql://192.168.10.103:3306/test\"\n" +
                "                                ]\n" +
                "                            }\n" +
                "                        ]\n" +
                "                    }\n" +
                "                },\n" +
                "                \"writer\": {\n" +
                "                    \"name\": \"hdfswriter\",\n" +
                "                    \"parameter\": {\n" +
                "                        \"defaultFS\": \"hdfs://192.168.10.103:8020\",\n" +
                "                        \"fileType\": \"orc\",\n" +
                "                        \"path\": \"/warehouse/tablespace/managed/hive/test.db/ds_test\",\n" +
                "                        \"fileName\": \"ds_test\",\n" +
                "                        \"column\": [\n" +
                "                            {\n" +
                "                                \"name\": \"uid\",\n" +
                "                                \"type\": \"DOUBLE\"\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"name\": \"id\",\n" +
                "                                \"type\": \"INT\"\n" +
                "                            },\n" +
                "                            {\n" +
                "                                \"name\": \"name\",\n" +
                "                                \"type\": \"STRING\"\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"writeMode\": \"append\",\n" +
                "                        \"fieldDelimiter\": \",\",\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        ],\n" +
                "\n" +
                "        \"setting\": {\n" +
                "            \"speed\": {\n" +
                "                \"channel\": \"3\",\n" +
                "                \"byte\": 1048576\n" +
                "            },\n" +
                "            \"errorLimit\": {\n" +
                "                \"record\": 0,\n" +
                "                \"percentage\": 0.02\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";

        String replaceAll = dataXJson.replaceAll("\"", "\\\\\"").replaceAll("\n", "");

        String taskDefinitionJson = "[\n" +
                "        {\n" +
                "            \"code\": 111111,\n" +
                "            \"name\": \"多task_1\",\n" +
                "            \"description\": \"task_!测试\",\n" +
                "            \"taskType\": \"DATAX\",\n" +
                "            \"taskParams\": {\n" +
                "                \"customConfig\": 1,\n" +
                "                \n" +
                "                \"json\": \""+replaceAll+"\",\n" +
                "                \n" +
                "                \"localParams\": [],\n" +
                "                \"xms\": 1,\n" +
                "                \"xmx\": 1,\n" +
                "                \"dependence\": {},\n" +
                "                \"conditionResult\": {\n" +
                "                    \"successNode\": [],\n" +
                "                    \"failedNode\": []\n" +
                "                },\n" +
                "                \"waitStartTimeout\": {},\n" +
                "                \"switchResult\": {}\n" +
                "            },\n" +
                "            \"flag\": \"YES\",\n" +
                "            \"taskPriority\": \"MEDIUM\",\n" +
                "            \"workerGroup\": \"default\",\n" +
                "            \"failRetryTimes\": 0,\n" +
                "            \"failRetryInterval\": \"1\",\n" +
                "            \"timeoutFlag\": \"OPEN\",\n" +
                "            \"timeoutNotifyStrategy\": \"WARN\",\n" +
                "            \"timeout\": 30,\n" +
                "            \"delayTime\": \"0\",\n" +
                "            \"environmentCode\": -1\n" +
                "        },\n" +
                "        {\n" +
                "            \"code\": 222222,\n" +
                "            \"name\": \"多task_2\",\n" +
                "            \"description\": \"多task_2\",\n" +
                "            \"taskType\": \"SQL\",\n" +
                "            \"taskParams\": {\n" +
                "                \"type\": \"MYSQL\",\n" +
                "                \"datasource\": 1,\n" +
                "                \"sql\": \"INSERT INTO test VALUES (a,b)\",\n" +
                "                \"udfs\": \"\",\n" +
                "                \"sqlType\": \"1\",\n" +
                "                \"sendEmail\": false,\n" +
                "                \"displayRows\": 10,\n" +
                "                \"title\": \"\",\n" +
                "                \"groupId\": null,\n" +
                "                \"localParams\": [],\n" +
                "                \"connParams\": \"\",\n" +
                "                \"preStatements\": [],\n" +
                "                \"postStatements\": [],\n" +
                "                \"dependence\": {},\n" +
                "                \"conditionResult\": {\n" +
                "                    \"successNode\": [],\n" +
                "                    \"failedNode\": []\n" +
                "                },\n" +
                "                \"waitStartTimeout\": {},\n" +
                "                \"switchResult\": {}\n" +
                "            },\n" +
                "            \"flag\": \"YES\",\n" +
                "            \"taskPriority\": \"MEDIUM\",\n" +
                "            \"workerGroup\": \"default\",\n" +
                "            \"failRetryTimes\": \"0\",\n" +
                "            \"failRetryInterval\": \"1\",\n" +
                "            \"timeoutFlag\": \"OPEN\",\n" +
                "            \"timeoutNotifyStrategy\": \"WARN\",\n" +
                "            \"timeout\": 5,\n" +
                "            \"delayTime\": \"0\",\n" +
                "            \"environmentCode\": -1\n" +
                "        }\n" +
                "    ]";
        String taskRelationJson = "[\n" +
                "        {\n" +
                "            \"name\": \"\",\n" +
                "            \"preTaskCode\": 111111,\n" +
                "            \"preTaskVersion\": 0,\n" +
                "            \"postTaskCode\": 222222,\n" +
                "            \"postTaskVersion\": 0,\n" +
                "            \"conditionType\": 0,\n" +
                "            \"conditionParams\": {}\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"\",\n" +
                "            \"preTaskCode\": 0,\n" +
                "            \"preTaskVersion\": 0,\n" +
                "            \"postTaskCode\": 111111,\n" +
                "            \"postTaskVersion\": 0,\n" +
                "            \"conditionType\": 0,\n" +
                "            \"conditionParams\": {}\n" +
                "        }\n" +
                "    ]";


        System.out.println(createProcessDataX("多task_mysql+自定义datax_2", projectCode, token, locations_str, taskDefinitionJson, taskRelationJson));

    }


    /*
     * processCode 流程id
     * projectCode 项目id
     * token 用户令牌 控制权限
     * taskDefinitionJson 定义task具体配置的json串 ***特殊格式***
     * taskRelationJson  表名task关系json串
     * */
    public static JSONObject createProcessDataX(String processName, String projectCode, String token, String locations_str, String taskDefinitionJson, String taskRelationJson) {


        String url = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/process-definition";

        //post请求对象
        HttpPost post = new HttpPost(url);

        //参数集合
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        //自己制定taskCode
        Long processCode_1 = Long.valueOf(1234567812);
        Long processCode_2 = Long.valueOf(765432132);


        //添加参数
        params.add(new BasicNameValuePair("locations", locations_str));
        params.add(new BasicNameValuePair("name", processName));

        //关键参数
        params.add(new BasicNameValuePair("taskDefinitionJson", taskDefinitionJson));

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
