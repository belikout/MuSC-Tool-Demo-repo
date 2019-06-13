package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class Conditional {
    private Object condition;
    private Object trueExpression;
    private Object falseExpression;
    private String type;

    public Object getCondition() {
        return condition;
    }

    public void setCondition(Object condition) {
        this.condition = condition;
    }

    public Object getTrueExpression() {
        return trueExpression;
    }

    public void setTrueExpression(Object trueExpression) {
        this.trueExpression = trueExpression;
    }

    public Object getFalseExpression() {
        return falseExpression;
    }

    public void setFalseExpression(Object falseExpression) {
        this.falseExpression = falseExpression;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void output(){
        ExpressionStatement.printPart(condition);
        System.out.print(" ? ");
        ExpressionStatement.printPart(trueExpression);
        System.out.print(" : ");
        ExpressionStatement.printPart(falseExpression);
    }
    public String outputToLine(ArrayList<MuType> types){
        String str=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),condition)+" ? ";
        Statement.lineContent+=" ? ";
        str+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),trueExpression)+" : ";
        Statement.lineContent+=" : ";
        str+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),falseExpression);
        return str;
    }
}
