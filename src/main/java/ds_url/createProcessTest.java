package ds_url;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import java.util.HashMap;
//创建一个mysql流程

public class createProcessTest {
    public static void main(String[] args) {

        //通过token来控制用户权限 但是好像没用

        //获取project的projectcode
        String user_token = "ff53ac501e5b419d90aab0a30e778c49";
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
        String projectCode = codeMap.get("zzz");
        System.out.println(projectCode);
        //token
        String token = "ff53ac501e5b419d90aab0a30e778c49";
        //传参 调用方法
        createProcessTest createProcessTest = new createProcessTest();
       // createProcessTest.createProcess(projectCode, token);

    }


    public JSONObject createProcess(String projectCode, String token) {

        String url = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/process-definition";

        //post请求对象
        HttpPost post = new HttpPost(url);

        //参数集合
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        //自己制定taskCode
        Long processCode = Long.valueOf(2);

        //String locations_str="[  {     \"taskCode\": "+processCode+",     \"x\": 147,     \"y\": 159   }]";
        String locations_str = "  {     \"taskCode\": " + processCode + ",     \"x\": 147,     \"y\": 159   }";
        JSONObject locations_json = JSON.parseObject(locations_str);


        //添加参数
        params.add(new BasicNameValuePair("locations", locations_json.toJSONString()));
        params.add(new BasicNameValuePair("name", "api_test2"));
        params.add(new BasicNameValuePair("projectCode", projectCode));
        //关键参数

        String taskDefinitionJson = "[" +
                "  {" +
                "    \"code\": " + processCode + "," +
                "    \"name\": \"aaa\"," +
                "    \"description\": \"\"," +
                "    \"taskType\": \"SQL\"," +
                "    \"taskParams\": {" +
                "      \"type\": \"MYSQL\"," +
                "      \"datasource\": 1," +
                "      \"sql\": \"select * from ds_test\"," +
                "      \"udfs\": \"\"," +
                "      \"sqlType\": \"0\"," +
                "      \"sendEmail\": false," +
                "      \"displayRows\": 10," +
                "      \"title\": \"\"," +
                "      \"groupId\": null," +
                "      \"localParams\": []," +
                "      \"connParams\": \"\"," +
                "      \"preStatements\": []," +
                "      \"postStatements\": []," +
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


        String taskRelationJson = " [" +
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
        params.add(new BasicNameValuePair("description", "api创建process测试流程"));
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
}

/* 创建process 需要的参数
locations: [
{
    "taskCode": 4185426181760,
    "x": 147,
    "y": 159
  }
]
name: aaa
taskDefinitionJson: [
  {
    "code": 4185426181760,
    "name": "aaa",
    "description": "",
    "taskType": "SQL",
    "taskParams": {
      "type": "MYSQL",
      "datasource": 1,
      "sql": "a",
      "udfs": "",
      "sqlType": "0",
      "sendEmail": false,
      "displayRows": 10,
      "title": "",
      "groupId": null,
      "localParams": [],
      "connParams": "",
      "preStatements": [],
      "postStatements": [],
      "dependence": {},
      "conditionResult": {
        "successNode": [],
        "failedNode": []
      },
      "waitStartTimeout": {},
      "switchResult": {}
    },
    "flag": "YES",
    "taskPriority": "MEDIUM",
    "workerGroup": "default",
    "failRetryTimes": "0",
    "failRetryInterval": "1",
    "timeoutFlag": "CLOSE",
    "timeoutNotifyStrategy": "",
    "timeout": 0,
    "delayTime": "0",
    "environmentCode": -1
  }
]
taskRelationJson: [
  {
    "name": "",
    "preTaskCode": 0,
    "preTaskVersion": 0,
    "postTaskCode": 1,
    "postTaskVersion": 0,
    "conditionType": 0,
    "conditionParams": {}
  }
]
tenantCode: default
description: dsd
globalParams: []
timeout: 0
*
* */