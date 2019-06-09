package cn.edu.nju.mutestdemo.AST2Sol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import cn.edu.nju.mutestdemo.Model.SourceUnit;

import java.io.*;

public class AST2Sol {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 读取原始json文件并进行操作和输出
        trans("C:\\\\Users\\\\belikout\\\\Desktop\\\\ast.json","C:\\Users\\belikout\\Desktop\\sol.txt");
    }
    public static void trans(String inPath,String outPath){
        try {
            BufferedReader br = new BufferedReader(new FileReader(inPath));// 读取原始json文件
            try {
                //文件生成路径
                PrintStream ps=new PrintStream(outPath);
                System.setOut(ps);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String s  = null;
            String json="";
            while ((s = br.readLine()) != null) {
                try {
                    json+=s;
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            SourceUnit su= JSON.parseObject(json, SourceUnit.class);
            su.output();
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
