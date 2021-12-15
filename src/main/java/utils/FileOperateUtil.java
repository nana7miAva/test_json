package utils;

import java.io.*;

public class FileOperateUtil {
    /**
     * 读取文件文本
     * @author Andot
     * @date 2018-4-1 16:39:19
     * @param filePath 文件路径
     * @return 文件内容
     * */
    public static String fileReadText(String filePath){
        BufferedReader br=null;
        String allstr = "";
        try {
            File file=new File(filePath);

            br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            String str="";
            while((str=br.readLine())!=null){
                byte[] bytes=str.getBytes("utf-8");
                allstr += new String(bytes, "utf-8") + "\r\n";
            }
            br.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return allstr;
    }


    /**
     * 写入到文件
     * @author Andot
     * @date 2018-4-1 16:39:19
     * @param allstr 需要写入的文本
     * @param filePath 文件路径
     * */
    public static void textWriteFile(String allstr, String filePath) {
        BufferedWriter bw=null;
        try {
            File targetFile=new File(filePath);
            if(!targetFile.exists()){
                targetFile.createNewFile();
            }
            bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8"));
            bw.write(allstr);
            bw.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
