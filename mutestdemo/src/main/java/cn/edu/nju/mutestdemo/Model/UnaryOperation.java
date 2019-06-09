package cn.edu.nju.mutestdemo.Model;

import java.util.ArrayList;
import cn.edu.nju.mutestdemo.EnumType.MuType;
public class UnaryOperation {
    private String type;
    private boolean isPrefix;
    private String operator;
    private Object subExpression;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPrefix() {
        return isPrefix;
    }

    public void setPrefix(boolean prefix) {
        isPrefix = prefix;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getSubExpression() {
        return subExpression;
    }

    public void setSubExpression(Object subExpression) {
        this.subExpression = subExpression;
    }
    public void output(){
        if(isPrefix)
            System.out.print(operator);
        if(subExpression!=null) {
            ExpressionStatement.printPart(subExpression);
        }
        if(!isPrefix)
            System.out.print(operator);
    }
    public String outputToLine(ArrayList<MuType> types){
        String str="";
        if(isPrefix)
            str+=operator;
        if(subExpression!=null) {
            str+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),subExpression);
        }
        if(!isPrefix)
            str+=operator;
        return str;
    }
}
