package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.EnumType.MuType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class VariableDeclarationStatement extends StateVariableDefinition {
    public void output(){
        Variable v;
        if((getVariables().length>0&&getVariables()[0]==null)||(getVariables().length>0&&((JSONObject)getVariables()[0]).getJSONObject("typeName")==null))
            System.out.print("var");
        if(getVariables().length>1)
            System.out.print("(");
        for(int i=0;i<getVariables().length;i++){
            if(getVariables()[i]!=null) {
                v = JSON.parseObject(getVariables()[i].toString(), Variable.class);
                if(v.getTypeName()!=null)
                    v.printType();
                if(v.getStorageLocation()!=null)
                    System.out.print(" "+v.getStorageLocation());
                System.out.print(" " + v.getName());
            }
            if(i!=getVariables().length-1)
                System.out.print(", ");
        }
        if(getVariables().length>1)
            System.out.print(")");
        if(getInitialValue()!=null){
            System.out.print(" = ");
            ExpressionStatement.printPart(getInitialValue());
        }
    }
    public String outputToLine(){
        String str="";
        Variable v;
        //if((getVariables().length>0&&getVariables()[0]==null)||(getVariables().length>0&&((JSONObject)getVariables()[0]).getJSONObject("typeName")==null))
        //    str+="var";
        if(getVariables().length>1)
            str+="(";
        for(int i=0;i<getVariables().length;i++){
            if(getVariables()[i]!=null) {
                v = JSON.parseObject(getVariables()[i].toString(), Variable.class);
                if(v.getTypeName()!=null)
                    str+=v.printTypeToLine();
                if(v.getStorageLocation()!=null)
                    str+=" "+v.getStorageLocation();
                str+=" " + v.getName();
            }
            if(i!=getVariables().length-1)
                str+=", ";
        }
        if(getVariables().length>1)
            str+=")";
        if(getInitialValue()!=null){
            str+=" = ";
            str+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),getInitialValue());
        }
        return str;
    }
}
