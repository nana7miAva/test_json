package ds_url;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class updateProcess {
    public static void main(String[] args) {

        String token = "ff53ac501e5b419d90aab0a30e778c49";
        //projectCode
        HashMap<String, String> projectMap = GetProject.getProjectMap(token);
        //System.out.println(projectMap.get("zzz"));
        String projectCode = projectMap.get("zzz");

        Map<String, String> processCodeMap = getProcessCode.getProcessCodeMap(projectMap.get("zzz"), token);
        System.out.println(processCodeMap.get("多task_mysql+自定义datax"));

        String processCode = processCodeMap.get("多task_mysql+自定义datax");
        String dataXJson = "{" +
                "  \"job\": {" +
                "    \"setting\": {" +
                "      \"speed\": {" +
                "        \"channel\": 3," +

                "        \"byte\": 1048576" +
                "      }," +
                "      \"errorLimit\": {" +
                "        \"record\": 0," +
                "        \"percentage\": 0.02" +

                "      }" +
                "    }," +
                "    \"content\": [" +
                "      {" +
                "        \"reader\": {" +
                "          \"name\": \"hdfsreader\"," +
                "          \"parameter\": {" +
                "            \"path\": \"/warehouse/tablespace/managed/hive/test.db/x2_abc_test\"," +
                "            \"defaultFS\": \"hdfs://192.168.10.103:8020\"," +
                "            \"fileType\": \"text\"," +
                "            \"fieldDelimiter\": \",\"," +
                "            \"skipHeader\": false," +
                "            \"column\": [" +
                "              {" +
                "                \"index\": \"0\"," +
                "                \"type\": \"long\"" +
                "              }," +
                "              {" +
                "                \"index\": \"1\"," +
                "                \"type\": \"long\"" +
                "              }," +
                "              {" +
                "                \"index\": \"2\"," +
                "                \"type\": \"date\"" +
                "              }" +
                "            ]" +
                "          }" +
                "        }," +
                "        \"writer\": {" +
                "          \"name\": \"mysqlwriter\"," +
                "          \"parameter\": {" +
                "            \"username\": \"dba\"," +
                "            \"password\": \"dba*#2020\"," +
                "            \"column\": [" +
                "              \"`id`\"," +
                "              \"`score`\"," +
                "              \"`create_time`\"" +
                "            ]," +
                "            \"collectMode\": \"all\"," +
                "            \"writeMode\": \"insert\"," +
                "            \"connection\": [" +
                "              {" +
                "                \"table\": [" +
                "                  \"x2_abc_test\"" +
                "                ]," +
                "                \"jdbcUrl\": \"jdbc:mysql://192.168.10.170:3306/test\"" +
                "              }" +
                "            ]" +
                "          }" +
                "        }" +
                "      }" +
                "    ]" +
                "  }" +
                "}";
        //TODO 传进来的dataX的json串中   "要替换成\"  不能出现换行符  否则识别不出来!!!!!
        String replaceAll = dataXJson.replaceAll("\"", "\\\\\"").replaceAll("", "");


        String locations = "[{\"taskCode\":4541456234752,\"x\":280,\"y\":160},{\"taskCode\":4541468903936,\"x\":340,\"y\":330}]";
        String name = "多task_mysql+自定义datax_改名";
        String taskDefinitionJson = "[" +
                "    {" +
                "        \"code\": 4541456234752," +
                "        \"name\": \"多task_1\"," +
                "        \"version\": 1," +
                "        \"description\": \"task_!测试\"," +
                "        \"delayTime\": 0," +
                "        \"taskType\": \"DATAX\"," +
                "        \"taskParams\": {" +
                "            \"customConfig\": 1," +
                "            \"json\": \"" + replaceAll + "\"," +
                "" +
                "            \"localParams\": []," +
                "" +
                "            \"xms\": 1," +
                "            \"xmx\": 1," +
                "            \"dependence\": {}," +
                "            \"conditionResult\": {" +
                "                \"successNode\": []," +
                "                \"failedNode\": []" +
                "            }," +
                "            \"waitStartTimeout\": {}," +
                "            \"switchResult\": {}" +
                "        }," +
                "        \"flag\": \"YES\"," +
                "        \"taskPriority\": \"MEDIUM\"," +
                "        \"workerGroup\": \"default\"," +
                "        \"failRetryTimes\": 0," +
                "        \"failRetryInterval\": 1," +
                "        \"timeoutFlag\": \"OPEN\"," +
                "        \"timeoutNotifyStrategy\": \"WARN\"," +
                "        \"timeout\": 30," +
                "        \"environmentCode\": -1" +
                "    }," +
                "    {" +
                "        \"code\": 4541468903936," +
                "        \"name\": \"多task_2\"," +
                "        \"version\": 1," +
                "        \"description\": \"多task_2\"," +
                "        \"delayTime\": 0," +
                "        \"taskType\": \"SQL\"," +
                "        \"taskParams\": {" +
                "            \"type\": \"MYSQL\"," +
                "            \"datasource\": 1," +
                "            \"sql\": \"INSERT INTO test VALUES (a,b)\"," +
                "            \"udfs\": \"\"," +
                "            \"sqlType\": \"1\"," +
                "            \"sendEmail\": false," +
                "            \"displayRows\": 10," +
                "            \"title\": \"\"," +
                "            \"groupId\": null," +
                "            \"localParams\": []," +
                "            \"connParams\": \"\"," +
                "            \"preStatements\": []," +
                "            \"postStatements\": []," +
                "            \"dependence\": {}," +
                "            \"conditionResult\": {" +
                "                \"successNode\": []," +
                "                \"failedNode\": []" +
                "            }," +
                "            \"waitStartTimeout\": {}," +
                "            \"switchResult\": {}" +
                "        }," +
                "        \"flag\": \"YES\"," +
                "        \"taskPriority\": \"MEDIUM\"," +
                "        \"workerGroup\": \"default\"," +
                "        \"failRetryTimes\": 0," +
                "        \"failRetryInterval\": 1," +
                "        \"timeoutFlag\": \"OPEN\"," +
                "        \"timeoutNotifyStrategy\": \"WARN\"," +
                "        \"timeout\": 5," +
                "        \"environmentCode\": -1" +
                "    }" +
                "]";

        String taskRelationJson = "[" +
                "  {" +
                "    \"name\": \"\"," +
                "    \"preTaskCode\": 4541456234752," +
                "    \"preTaskVersion\": 1," +
                "    \"postTaskCode\": 4541468903936," +
                "    \"postTaskVersion\": 1," +
                "    \"conditionType\": 0," +
                "    \"conditionParams\": {}" +
                "  }," +
                "  {" +
                "    \"name\": \"\"," +
                "    \"preTaskCode\": 0," +
                "    \"preTaskVersion\": 0," +
                "    \"postTaskCode\": 4541456234752," +
                "    \"postTaskVersion\": 1," +
                "    \"conditionType\": 0," +
                "    \"conditionParams\": {}" +
                "  }" +
                "]";

        String tenantCode = "hdfs";
        String description = "更新流程测试";
        String globalParams = "[]";
        String releaseState = "ONLINE";
        String timeout = "0";

        JSONObject jsonObject = updateProcess(token, projectCode, processCode, locations, name, taskDefinitionJson,
                taskRelationJson, tenantCode, description, globalParams, releaseState, timeout);
        System.out.println(jsonObject);

    }


    //更新流程定义  流程上线的操作


    public static JSONObject updateProcess(String token, String projectCode, String processCode, String locations,
                                           String name, String taskDefinitionJson, String taskRelationJson, String tenantCode,
                                           String description, String globalParams, String releaseState,
                                           String timeout) {
        String putUrl = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/process-definition/" + processCode;
        //有参数的put请求
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        URIBuilder uriBuilder = null;
        JSONObject result = null;
        try {
            uriBuilder = new URIBuilder(putUrl);
            uriBuilder.addParameter("projectCode", projectCode);
            uriBuilder.addParameter("code", processCode);
            uriBuilder.addParameter("locations", locations);
            uriBuilder.addParameter("name", name);
            uriBuilder.addParameter("taskDefinitionJson", taskDefinitionJson);
            uriBuilder.addParameter("taskRelationJson", taskRelationJson);
            uriBuilder.addParameter("tenantCode", tenantCode);
            uriBuilder.addParameter("description", description);
            uriBuilder.addParameter("globalParams", globalParams);
            uriBuilder.addParameter("releaseState", releaseState);
            uriBuilder.addParameter("timeout", timeout);


            URI build = uriBuilder.build();
            //根据参数生成put请求对象
            HttpPut httpPut = new HttpPut(build);
            //添加请求头 admin的token
            httpPut.setHeader("token", token);
            //生成连接 执行
            client = HttpClients.createDefault();

            response = client.execute(httpPut);
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
