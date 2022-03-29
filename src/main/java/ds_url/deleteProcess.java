package ds_url;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//通过id删除process
public class deleteProcess {
    public static void main(String[] args) {
        String token = "ff53ac501e5b419d90aab0a30e778c49";
        String projectCode = "4000994290048";
        String processCode = "4221848584320";
        JSONObject jsonObject = deleteProcess(token, projectCode, processCode);
        System.out.println(jsonObject);

    }


    public static JSONObject deleteProcess(String token, String projectCode, String processCode) {
        String deleteUrl = "http://ds1:12306/dolphinscheduler/projects/" + projectCode + "/process-definition/" + processCode;


        URIBuilder urlBuilder = null;
        CloseableHttpClient httpClient = null;
        JSONObject result = null;

        CloseableHttpResponse httpResponse = null;
        try {
            urlBuilder = new URIBuilder(deleteUrl);
            urlBuilder.addParameter("projectCode", projectCode);
            urlBuilder.addParameter("code", processCode);

            URI build = urlBuilder.build();
            HttpDelete httpDelete = new HttpDelete(build);
            httpDelete.setHeader("token", token);


            httpClient = HttpClients.createDefault();

            httpResponse = httpClient.execute(httpDelete);
            HttpEntity entity = httpResponse.getEntity();
            String msg = EntityUtils.toString(entity, "utf-8");
            result = JSONObject.parseObject(msg);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }
}
