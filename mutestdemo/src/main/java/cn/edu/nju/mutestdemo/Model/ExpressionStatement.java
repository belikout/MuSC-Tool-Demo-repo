package cn.edu.nju.mutestdemo.Model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import cn.edu.nju.mutestdemo.EnumType.MuType;
public class ExpressionStatement extends Statement{
    //Function call,BinaryOperation,Identifier,NumberLiteral,StringLiteral,indexAccess,MemberAccess
public static void printPart(Object expr){
    if(((JSONObject)expr).getString("type").equals("Identifier"))
        System.out.print(((JSONObject)expr).getString("name"));
    else if(((JSONObject)expr).getString("type").equals("NumberLiteral")) {
        System.out.print(((JSONObject) expr).getString("number"));
        if(((JSONObject) expr).getString("subdenomination")!=null)
            System.out.print(" "+((JSONObject) expr).getString("subdenomination"));
    }
    else if(((JSONObject)expr).getString("type").equals("StringLiteral"))
        System.out.print("\""+((JSONObject)expr).getString("value")+"\"");
    else if(((JSONObject)expr).getString("type").equals("HexLiteral"))
        System.out.print(((JSONObject)expr).getString("value"));
    else if(((JSONObject)expr).getString("type").equals("BooleanLiteral"))
        System.out.print(((JSONObject)expr).getString("value"));
    else if(((JSONObject)expr).getString("type").equals("BinaryOperation"))
        JSON.parseObject(expr.toString(),BinaryOperation.class).output();
    else if(((JSONObject)expr).getString("type").equals("UnaryOperation"))
        JSON.parseObject(expr.toString(), UnaryOperation.class).output();
    else if(((JSONObject)expr).getString("type").equals("IndexAccess"))
        JSON.parseObject(expr.toString(),IndexAccess.class).output();
    else if(((JSONObject)expr).getString("type").equals("MemberAccess"))
        JSON.parseObject(expr.toString(),MemberAccess.class).output();
    else if(((JSONObject)expr).getString("type").equals("FunctionCall"))
        JSON.parseObject(expr.toString(),FunctionCall.class).output();
    else if(((JSONObject)expr).getString("type").equals("TupleExpression"))
        JSON.parseObject(expr.toString(), TupleExpression.class).output();
    else if(((JSONObject)expr).getString("type").equals("NewExpression")){
        System.out.print("new ");
        Var.printType(((JSONObject)expr).getJSONObject("typeName"));
    }
    else if(((JSONObject)expr).getString("type").equals("ElementaryTypeNameExpression")){
        Var.printType(((JSONObject)expr).getJSONObject("typeName"));
    }
    else if(((JSONObject)expr).getString("type").equals("Conditional"))
        JSON.parseObject(expr.toString(),Conditional.class).output();
}
    public static String printPartToLine(ArrayList<MuType> types,Object expr){
    String str="";
        if(((JSONObject)expr).getString("type").equals("Identifier"))
            str+=((JSONObject)expr).getString("name");
        else if(((JSONObject)expr).getString("type").equals("NumberLiteral")) {
            str+=((JSONObject) expr).getString("number");
            if(((JSONObject) expr).getString("subdenomination")!=null)
                str+=" "+((JSONObject) expr).getString("subdenomination");
        }
        else if(((JSONObject)expr).getString("type").equals("StringLiteral"))
            str+="\""+((JSONObject)expr).getString("value")+"\"";
        else if(((JSONObject)expr).getString("type").equals("HexLiteral"))
            str+=((JSONObject)expr).getString("value");
        else if(((JSONObject)expr).getString("type").equals("BooleanLiteral"))
            str+=((JSONObject)expr).getString("value");
        else if(((JSONObject)expr).getString("type").equals("BinaryOperation"))
            str+=JSON.parseObject(expr.toString(),BinaryOperation.class).outputToLine(types);
        else if(((JSONObject)expr).getString("type").equals("UnaryOperation"))
            str+=JSON.parseObject(expr.toString(), UnaryOperation.class).outputToLine(types);
        else if(((JSONObject)expr).getString("type").equals("IndexAccess"))
            str+=JSON.parseObject(expr.toString(),IndexAccess.class).outputToLine(types);
        else if(((JSONObject)expr).getString("type").equals("MemberAccess"))
            str+=JSON.parseObject(expr.toString(),MemberAccess.class).outputToLine(types);
        else if(((JSONObject)expr).getString("type").equals("FunctionCall"))
            str+=JSON.parseObject(expr.toString(),FunctionCall.class).outputToLine(types);
        else if(((JSONObject)expr).getString("type").equals("TupleExpression"))
            str+=JSON.parseObject(expr.toString(), TupleExpression.class).outputToLine(types);
        else if(((JSONObject)expr).getString("type").equals("NewExpression")){
            str+="new ";
            str+=Var.printTypeToLine(((JSONObject)expr).getJSONObject("typeName"));
        }
        else if(((JSONObject)expr).getString("type").equals("ElementaryTypeNameExpression")){
            str+=Var.printTypeToLine(((JSONObject)expr).getJSONObject("typeName"));
        }
        else if(((JSONObject)expr).getString("type").equals("Conditional"))
            str+=JSON.parseObject(expr.toString(),Conditional.class).outputToLine(types);
        return str;
    }
}
