package ds_url;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

//流程实例的调用
//                                         重跑,从失败节点开始执行,恢复失败,停止,暂停
//executeType :执行类型,可用值:NONE,REPEAT_RUNNING,RECOVER_SUSPENDED_PROCESS,START_FAILURE_TASK_PROCESS,STOP,PAUSE
public class executeProcessByID {
    public static void main(String[] args) throws Exception {
        //processInstanceId从列表中获取

        String projectCode = "4000994290048";
        String token = "ff53ac501e5b419d90aab0a30e778c49";


        String processInstanceId = "33";
        System.out.println(executeProcessByID("REPEAT_RUNNING", processInstanceId, token, projectCode));


    }

    public static JSONObject executeProcessByID(String executeType, String processInstanceId, String token, String projectCode) throws Exception {
        String url = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/executors/execute";
        //post请求对象
        HttpPost post = new HttpPost(url);

        //参数集合
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

        //添加参数
        params.add(new BasicNameValuePair("executeType", executeType));
        params.add(new BasicNameValuePair("processInstanceId", processInstanceId));
        params.add(new BasicNameValuePair("projectCode", projectCode));

        //参数放入请求对象
        post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));

        //添加header参数
        //post.addHeader("Accept", Accept);
        //post.addHeader("content-type", "application/json");
        post.addHeader("token", token);

        //启动执行请求 获得返回值
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(post);
        //返回的entity对象
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "utf-8");


        System.out.println(result);
        //返回内容
        JSONObject jsonObject = JSON.parseObject(result);

        response.close();
        client.close();


        return jsonObject;

    }
}
