package ds_url;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

//获取当前所有项目 列表 包含有project code
public class GetProject {
    public static void main(String[] args) throws Exception {


        String token = "ff53ac501e5b419d90aab0a30e778c49";
        //
        GetProject urlGetTest = new GetProject();
        System.out.println(urlGetTest.getProList(token));
    }

    public static JSONObject getProList(String token) {
        String requestUrl = "http://ds1:12306/dolphinscheduler/projects/list";


        URL url = null;
        BufferedReader bufferedReader = null;
        HttpURLConnection urlConnection = null;
        StringBuilder builder = new StringBuilder();
        try {
            url = new URL(requestUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("content-type", "application/json");
            urlConnection.setRequestProperty("token", token);

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);


            String str = null;
            while ((str = bufferedReader.readLine()) != null) {

                builder.append(str);
            }

        /*JSONArray jsonArray = JSON.parseObject(builder.toString()).getJSONArray("data");
        for (int i = 0; i < jsonArray.size(); i++) {
            String name = (String) jsonArray.getJSONObject(i).get("name");
            System.out.println(name);
        }*/

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (bufferedReader != null)
                    bufferedReader.close();
                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return JSON.parseObject(builder.toString());
    }


    //获取项目和项目code的map  项目=>项目id

    public static HashMap<String, String> getProjectMap(String token) {
        JSONObject proList = getProList(token);

        HashMap<String, String> ResultMap = new HashMap<String, String>();
        JSONArray data = proList.getJSONArray("data");

        for (int i = 0; i < data.size(); i++) {
            ResultMap.put(data.getJSONObject(i).getString("name"), data.getJSONObject(i).getString(
                    "code"));
        }

        return ResultMap;
    }
}
