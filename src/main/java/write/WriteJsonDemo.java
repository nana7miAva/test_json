package write;

import utils.FileOperateUtil;

public class WriteJsonDemo {
    public static void main(String[] args) {
        String path ="src/main/java/x1.json";
        String line ="{1 ,2 ,3 ,[,,]}";
        FileOperateUtil.textWriteFile(line,path);
    }
}
