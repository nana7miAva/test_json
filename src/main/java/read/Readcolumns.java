package read;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import utils.FileOperateUtil;

public class Readcolumns {
    public static void main(String[] args) {

        String columns = FileOperateUtil.fileReadText("src/main/java/columns.json");
        //System.out.println(columns);

        JSONArray jsonObject = JSON.parseArray(columns);

        JSONObject table1Cols = jsonObject.getJSONObject(0);
        JSONObject table2Cols = jsonObject.getJSONObject(1);
        System.out.println(table1Cols);
        System.out.println("=====================");
        System.out.println(table2Cols);
    }
}
