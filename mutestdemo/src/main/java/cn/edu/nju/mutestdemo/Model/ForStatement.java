package cn.edu.nju.mutestdemo.Model;

import com.alibaba.fastjson.JSONObject;
import cn.edu.nju.mutestdemo.EnumType.MuType;
import java.util.ArrayList;

public class ForStatement {
    private Object conditionExpression;
    private Object initExpression;
    private String type;
    private Object body;
    private Object loopExpression;

    public Object getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(Object conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    public Object getInitExpression() {
        return initExpression;
    }

    public void setInitExpression(Object initExpression) {
        this.initExpression = initExpression;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Object getLoopExpression() {
        return loopExpression;
    }

    public void setLoopExpression(Object loopExpression) {
        this.loopExpression = loopExpression;
    }
    public void output(int space){
        for(int i=0;i<space;i++)
            System.out.print("    ");
        System.out.print("for (");
        if(initExpression!=null)
            Statement.printPart(initExpression);
        System.out.print(";");
        if(conditionExpression!=null) {
            if ((!((JSONObject) conditionExpression).getString("type").equals("VariableDeclarationStatement")) && (!((JSONObject) conditionExpression).getString("type").equals("ExpressionStatement")))
                ExpressionStatement.printPart(conditionExpression);
            else
                Statement.printPart(conditionExpression);
        }
        System.out.print(";");
        if(loopExpression!=null&&((JSONObject)loopExpression).getJSONObject("expression")!=null)
            Statement.printPart(loopExpression);
        System.out.print(")");
        IfStatement.printBody(space,body);
    }
    public void outputToLine(ArrayList<MuType>types,int space){
        String content="";
        for(int i=0;i<space;i++)
            System.out.print("    ");
        content+="for (";
        if(initExpression!=null)
            content+=Statement.printPartToLine(initExpression);
        content+=";";
        if(conditionExpression!=null) {
            if ((!((JSONObject) conditionExpression).getString("type").equals("VariableDeclarationStatement")) && (!((JSONObject) conditionExpression).getString("type").equals("ExpressionStatement")))
                content+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),conditionExpression);
            else
                content+=Statement.printPartToLine(conditionExpression);
        }
        content+=";";
        if(loopExpression!=null&&((JSONObject)loopExpression).getJSONObject("expression")!=null)
            content+=Statement.printPartToLine(loopExpression);
        content+=")";
        IfStatement.printBodyToLine(new ArrayList<MuType>(),content,space,body);
    }
}
