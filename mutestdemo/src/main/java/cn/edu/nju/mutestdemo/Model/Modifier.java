package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.edu.nju.mutestdemo.EnumType.MuType;
import java.util.ArrayList;

public class Modifier extends Unit {
    private Object body;
    private Object parameters;

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Object getParameters() {
        return parameters;
    }

    public void setParameters(Object parameters) {
        this.parameters = parameters;
    }

    public void output(int space){
        for(int i=0;i<space;i++)
            System.out.print("    ");
        System.out.print("modifier"+" ");
        System.out.print(getName());
        //处理parameter
        if(parameters.getClass().getName().equals("com.alibaba.fastjson.JSONObject")){
            System.out.print("(");
            Parameter.ListOutput(parameters);
            System.out.print(")");
        }
        System.out.println(" {");
        //处理body
        JSONArray statements=((JSONObject)body).getJSONArray("statements");
        Statement.ListOutput(space+1,statements);
        for(int i=0;i<space;i++)
            System.out.print("    ");
        System.out.println("}");

    }
    public void addToMutant(ArrayList<MuType> types,int space){
        String content="";
        content+="modifier"+" ";
        content+=getName();
        //处理parameter
        if(parameters.getClass().getName().equals("com.alibaba.fastjson.JSONObject")){
            content+="(";
            content+=Parameter.ListOutputToLine(parameters);
            content+=")";
        }
        content+=" {";
        Mutant.lines.add(new Line(content,new ArrayList<MuType>(),space));
        content="";
        //处理body
        JSONArray statements=((JSONObject)body).getJSONArray("statements");
        Statement.ListOutputToLine(space+1,statements,new ArrayList<MuType>());
        for(int i=0;i<space;i++)
            content+="    ";
        content+="}";
        Mutant.lines.add(new Line(content,new ArrayList<MuType>(),space));
    }
}
