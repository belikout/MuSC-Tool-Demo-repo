package cn.edu.nju.mutestdemo.Model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Var extends Unit {
    private Object typeName;

    public Object getTypeName() {
        return typeName;
    }

    public void setTypeName(Object typeName) {
        this.typeName = typeName;
    }
    public void printType(){
        if(((JSONObject)typeName).getString("type").equals("UserDefinedTypeName"))
            System.out.print(((JSONObject)typeName).getString("namePath"));
        else if(((JSONObject)typeName).getString("type").equals("ElementaryTypeName")) {
            System.out.print(((JSONObject) typeName).getString("name"));
            if(((JSONObject) typeName).getString("stateMutability")!=null)
                System.out.print(" "+((JSONObject) typeName).getString("stateMutability"));
        }
        else if(((JSONObject)typeName).getString("type").equals("ArrayTypeName")){
            printType(((JSONObject)typeName).getJSONObject("baseTypeName"));
            System.out.print("[");
            if(((JSONObject)typeName).getJSONObject("length")!=null)
                ExpressionStatement.printPart(((JSONObject)typeName).getJSONObject("length"));
            System.out.print("]");
        }
        else if(((JSONObject)typeName).getString("type").equals("Mapping")){
            System.out.print("mapping"+" "+"(");
            printType(((JSONObject)typeName).getJSONObject("keyType"));
            System.out.print(" => ");
            printType(((JSONObject)typeName).getJSONObject("valueType"));
            System.out.print(")");
        }
    }
    public static void printType(Object typeName) {
        Var v=new Var();
        v.typeName=typeName;
        v.printType();
    }
    public String printTypeToLine(){
        String str="";
        if(((JSONObject)typeName).getString("type").equals("UserDefinedTypeName"))
            str+=((JSONObject)typeName).getString("namePath");
        else if(((JSONObject)typeName).getString("type").equals("ElementaryTypeName")) {
            str+=((JSONObject)typeName).getString("name");
            if(((JSONObject) typeName).getString("stateMutability")!=null)
                str+=" "+((JSONObject) typeName).getString("stateMutability");
        }
        else if(((JSONObject)typeName).getString("type").equals("ArrayTypeName")){
            str+=printTypeToLine(((JSONObject)typeName).getJSONObject("baseTypeName"));
            str+="[";
            if(((JSONObject)typeName).getJSONObject("length")!=null)
                str+=ExpressionStatement.printPartToLine(((JSONObject)typeName).getJSONObject("length"));
            str+="]";
        }
        else if(((JSONObject)typeName).getString("type").equals("Mapping")){
            str+="mapping"+" "+"(";
            str+=printTypeToLine(((JSONObject)typeName).getJSONObject("keyType"));
            str+=" => ";
            str+=printTypeToLine(((JSONObject)typeName).getJSONObject("valueType"));
            str+=")";

        }
        else if(((JSONObject)typeName).getString("type").equals("FunctionTypeName")){
            str+="function"+" "+"(";
            int pTypeNum=((JSONObject)typeName).getJSONArray("parameterTypes").size();
            for(int i=0;i<pTypeNum;i++) {
                str += JSON.parseObject(((JSONObject)typeName).getJSONArray("parameterTypes").get(i).toString(), Parameter.class).outputToLine();
                if(i!=pTypeNum-1)str+=",";
            }
            str+=")";
            if(!((JSONObject) typeName).getString("visibility").equals("default")){
                str+=" "+((JSONObject) typeName).getJSONObject("visibility")+" ";
            }
            if(((JSONObject) typeName).getString("stateMutability")!=null){
                str+=" "+((JSONObject) typeName).getString("stateMutability")+" ";
            }
            if(((JSONObject) typeName).getJSONArray("returnTypes").size()>0){
                str+=" returns(";
                JSONArray pList=((JSONObject) typeName).getJSONArray("returnTypes");
                if(pList.size()>0){
                    for(int i=0;i<pList.size();i++){
                        str+= JSON.parseObject(pList.get(i).toString(), Parameter.class).outputToLine();
                        if(i!=pList.size()-1)
                            str+=", ";
                    }
                }
                str+=") ";
            }
        }
        return str;
    }
    public static String printTypeToLine(Object typeName) {
        Var v=new Var();
        v.typeName=typeName;
        return v.printTypeToLine();
    }

}
