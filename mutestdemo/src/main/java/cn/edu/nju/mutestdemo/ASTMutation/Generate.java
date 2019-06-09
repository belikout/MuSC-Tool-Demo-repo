package cn.edu.nju.mutestdemo.ASTMutation;

import cn.edu.nju.mutestdemo.EnumType.MuType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import cn.edu.nju.mutestdemo.Model.SourceUnit;

import java.io.*;
import java.util.ArrayList;

public class Generate {
    public static void main(String[]args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\\\Users\\\\belikout\\\\Desktop\\\\ast.json"));// 读取原始json文件

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
        //String json= GenASTServiceClient.genAST("C:\\Users\\belikout\\Desktop\\sol.txt");

            SourceUnit su= JSON.parseObject(json, SourceUnit.class);
            ArrayList<MuType>type=new ArrayList<MuType>();
            type.add(MuType.AOR);
            type.add(MuType.ASR);
            su.addToMutant(type);
            System.out.println(Mutant.lines.size());
            Mutant.Repair();
            for(int i=0;i<Mutant.lines.size();i++){
                if(Mutant.lines.get(i).getSpace()>0)
                    for(int j=0;j<Mutant.lines.get(i).getSpace();j++)
                        System.out.print("    ");
                    System.out.println(Mutant.lines.get(i).getContent());
            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    /*public void generateMutant(Object mutypes){
        System.out.println("okkkkkkkkkkkkkkkkk");
        System.out.println(mutypes==null);
        ArrayList<ArrayList> arr=new ArrayList<ArrayList>();
        JSONArray res=new JSONArray();
        JSONArray oriLine=new JSONArray();
        JSONArray mutLine=new JSONArray();
        JSONArray mutLineNum=new JSONArray();
        JSONArray mutLinetype=new JSONArray();
        res.add(oriLine);
        res.add(mutLine);
        res.add(mutLineNum);
        res.add(mutLinetype);

    }*/
}
