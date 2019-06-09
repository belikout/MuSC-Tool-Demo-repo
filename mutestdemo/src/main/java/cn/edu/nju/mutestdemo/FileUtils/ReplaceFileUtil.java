package cn.edu.nju.mutestdemo.FileUtils;

import java.io.*;

public class ReplaceFileUtil {
    public static void replaceFile(String oriPath,String conPath){
        String content="";
        try {
            BufferedReader br = new BufferedReader(new FileReader(conPath));// 读取内容文件
            String s  = null;
            while ((s = br.readLine()) != null) {
                content+=s+"\n";
            }
            br.close();
            FileWriter writer=new FileWriter(new File(oriPath));
            writer.write(content);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
