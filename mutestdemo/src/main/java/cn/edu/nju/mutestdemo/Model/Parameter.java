package cn.edu.nju.mutestdemo.Model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Parameter extends Variable {
    @Override
    public void output(){
        printType();
        if(getStorageLocation()!=null)
            System.out.print(" "+getStorageLocation());
        if(isIndexed())
            System.out.print(" indexed");
        if(getName()!=null)
            System.out.print(" "+getName());
    }
    public String outputToLine(){
        String str="";
        str+=printTypeToLine();
        if(getStorageLocation()!=null)
            str+=" "+getStorageLocation();
        if(isIndexed())
            str+=" indexed";
        if(getName()!=null)
            str+=" "+getName();
        return str;
    }
    public static void ListOutput(Object parameters){
        JSONArray pList=((JSONObject)parameters).getJSONArray("parameters");
        if(pList.size()>0){
            for(int i=0;i<pList.size();i++){
                JSON.parseObject(pList.get(i).toString(), Parameter.class).output();
                if(i!=pList.size()-1)
                    System.out.print(", ");
            }
        }
    }
    public static String ListOutputToLine(Object parameters){
        String str="";
        JSONArray pList=((JSONObject)parameters).getJSONArray("parameters");
        if(pList.size()>0){
            for(int i=0;i<pList.size();i++){
                str+=JSON.parseObject(pList.get(i).toString(), Parameter.class).outputToLine();
                if(i!=pList.size()-1)
                    str+=", ";
            }
        }
        return str;
    }
}
