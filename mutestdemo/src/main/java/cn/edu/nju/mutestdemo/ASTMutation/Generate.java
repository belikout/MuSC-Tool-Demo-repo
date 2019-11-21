package cn.edu.nju.mutestdemo.ASTMutation;

import cn.edu.nju.mutestdemo.EnumType.MuType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import cn.edu.nju.mutestdemo.Model.SourceUnit;

import java.io.*;
import java.util.ArrayList;
import cn.edu.nju.mutestdemo.Sol2AST.GenASTServiceClient;
public class Generate {
    public static void main(String[]args) {
       /* try {
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
            }*/
        String json= GenASTServiceClient.genAST("C:\\Users\\belikout\\Desktop\\sol0.txt");
            System.out.println(MuType.FSC.ordinal());
            //stateMutability: pure constant payable view
            SourceUnit su= JSON.parseObject(json, SourceUnit.class);
            ArrayList<MuType>type=new ArrayList<MuType>();
            type.add(MuType.AOR);
            type.add(MuType.ASR);
            type.add(MuType.ROR);
            type.add(MuType.COR);
            type.add(MuType.FSC);
            type.add(MuType.FVC);
            type.add(MuType.PKD);
            type.add(MuType.RSD);
            type.add(MuType.RSC);
            type.add(MuType.EUR);
            su.addToMutant(type);
            System.out.println(Mutant.lines.size());
            Mutant.Repair();
            for(int i=0;i<Mutant.mutateLine.size();i++) {
                System.out.println(Mutant.mutateLineTypeNums.get(i)+" "+Mutant.mutateLine.get(i));
                System.out.println("OK");
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
