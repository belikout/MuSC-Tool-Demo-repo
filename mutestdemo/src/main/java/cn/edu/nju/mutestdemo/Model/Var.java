package cn.edu.nju.mutestdemo.Model;

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
        return str;
    }
    public static String printTypeToLine(Object typeName) {
        Var v=new Var();
        v.typeName=typeName;
        return v.printTypeToLine();
    }

}
