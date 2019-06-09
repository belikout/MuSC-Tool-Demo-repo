package cn.edu.nju.mutestdemo.Model;
import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class WhileStatement {
    private Object condition;
    private String type;
    private Object body;

    public Object getCondition() {
        return condition;
    }

    public void setCondition(Object condition) {
        this.condition = condition;
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
    public void output(int space){
        for(int i=0;i<space;i++)
            System.out.print("    ");
        System.out.print("while(");
        ExpressionStatement.printPart(condition);
        System.out.print(") ");
        IfStatement.printBody(space,body);
    }public void outputToLine(ArrayList<MuType> types, int space){
        String content="";
        content+="while(";
        content+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),condition);
        content+=") ";
        IfStatement.printBodyToLine(new ArrayList<MuType>(),content,space,body);
    }
}
