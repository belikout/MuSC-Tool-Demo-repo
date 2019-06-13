package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class MemberAccess {
    private Object expression;
    private String memberName;
    private String type;

    public Object getExpression() {
        return expression;
    }

    public void setExpression(Object expression) {
        this.expression = expression;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void output(){
        ExpressionStatement.printPart(expression);
        System.out.print("."+memberName);
    }
    public String outputToLine(ArrayList<MuType> types){
        String str=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),expression)+"."+memberName;
        Statement.lineContent+="."+memberName;
        return str;
    }
}
