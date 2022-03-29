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

//ds 任务调度时间参数设置
public class createSchedule {
    public static void main(String[] args) {


        //token
        String token = "ff53ac501e5b419d90aab0a30e778c49";
        //获取projectcod
        HashMap<String, String> projectMap = urlGetProjectTest.getProjectMap(token);

        String projectCode = projectMap.get("zzz");
        System.out.println("zzz" + projectCode);
        //获取processDefinitionCodeMap
        JSONObject jsonObject = getProcessCode.getProcessCode(projectCode, token);
        JSONArray jsonArray = jsonObject.getJSONArray("data");


        HashMap<String, String> projectCodeMap = new HashMap<String, String>();
        for (int i = 0; i < jsonArray.size(); i++) {
            projectCodeMap.put(
                    jsonArray.getJSONObject(i).getJSONObject("processDefinition").getString("name"),
                    jsonArray.getJSONObject(i).getJSONObject("processDefinition").getString("code")
            );
        }

        String processDefinitionCode = projectCodeMap.get("多task_mysql+自定义datax_2");
        System.out.println(processDefinitionCode);


        //流程上线  检查流程是否上线,没上线的需要上线,上线了的接着往下执行

        //检查流程状态
        JSONObject statusJson = getProcessStatus.queryProcessDefinitionByCode(token, projectCode, processDefinitionCode);
        //从状态里面获取这个Process的状态和名字 下面上线流程的时候需要用到这个Process名字
        String ProcessStatus = statusJson.getJSONObject("data").getJSONObject("processDefinition").getString("releaseState");
        //String ProcessName = statusJson.getJSONObject("data").getJSONObject("processDefinition").getString("name");

        //if
        if (ProcessStatus.equals("OFFLINE")) {
            releaseProcess.onLine(token, projectCode, processDefinitionCode, "多task_mysql+自定义datax_2");

        }

        //执行createSchedule 上线之后才能创建定时流程调度


        String schedule = "{\"startTime\":\"2022-02-23 00:00:00\",\"endTime\":\"2122-02-23 00:00:00\",\"crontab\":\"5 53 15 * * ? *\",\"timezoneId\":\"Asia/Saigon\"}";
        System.out.println(createSchedule(token, processDefinitionCode, projectCode, schedule));

    }

    public static JSONObject createSchedule(String token, String processCode, String projectCode, String schedule) {


        String url = "http://ds1:12306//dolphinscheduler/projects/" + projectCode + "/schedules";

        //post请求对象
        HttpPost post = new HttpPost(url);

        //参数集合
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();


        //添加参数

        params.add(new BasicNameValuePair("processDefinitionCode", processCode));
        params.add(new BasicNameValuePair("projectCode", projectCode));
        params.add(new BasicNameValuePair("schedule", schedule));

        //params.add(new BasicNameValuePair("environmentCode", "-1"));
        params.add(new BasicNameValuePair("failureStrategy", "CONTINUE"));//END,CONTINUE
        params.add(new BasicNameValuePair("processInstancePriority", "MEDIUM"));//HIGHEST,HIGH,MEDIUM,LOW,LOWEST
        params.add(new BasicNameValuePair("warningGroupId", "1"));
        params.add(new BasicNameValuePair("warningType", "NONE"));//NONE,SUCCESS,FAILURE,ALL
        params.add(new BasicNameValuePair("workerGroup", "default"));
        params.add(new BasicNameValuePair("workerGroupId", "1"));


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
