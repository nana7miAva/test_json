package read;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import utils.FileOperateUtil;

public class ReadJsonDemo {
    public static void main(String[] args) {
        StringBuilder path = new StringBuilder("src/main/java/x.json");
        String readText = FileOperateUtil.fileReadText(path.toString());
        JSONObject xjson = JSON.parseObject(readText);

        JSONArray jsonArray = xjson.getJSONObject("job").
                getJSONArray("content").getJSONObject(0).getJSONObject("reader").getJSONObject("parameter").getJSONArray("connection");


        JSONObject connection = jsonArray.getJSONObject(0);
        JSONArray tables = connection.getJSONArray("table");

        if (0 == tables.size()) System.out.println("error");

        if (tables.size()==1){
            System.out.println("单表同步");
        }

        if (tables.size()>1){
            for (int i = 0; i < tables.size(); i++) {
                System.out.println(tables.get(i));
            }
            System.out.println("多表同步");
        }


//        //System.out.println(jsonArray.size());
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONObject o = (JSONObject) jsonArray.get(i);
//            System.out.println(o.getJSONArray("table"));
//        }

        System.out.println(jsonArray);
    }




}
