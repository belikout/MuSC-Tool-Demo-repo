package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class FunctionCall {
    private Object expression;
    private String[] names;
    private Object[] arguments;
    private String type;

    public Object getExpression() {
        return expression;
    }

    public void setExpression(Object expression) {
        this.expression = expression;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void output(){
        ExpressionStatement.printPart(expression);
        System.out.print("(");
        if(names.length>0) {
            System.out.print("{ ");
            Argument.ListOutputWithName(names, arguments);
            System.out.print(" }");
        }
        else
        Argument.ListOutput(arguments);
        System.out.print(")");
    }
    public String outputToLine(ArrayList<MuType> types){
        String str=ExpressionStatement.printPartToLine(types,expression);
        str+="(";
        Statement.lineContent+="(";
        if(names.length>0) {
            Statement.lineContent+="{";
            str+="{ "+Argument.ListOutputWithNameToLine(types,names, arguments)+"}";
            Statement.lineContent+="}";
        }
        else
            str+=Argument.ListOutputToLine(types,arguments);
        str+=")";
        Statement.lineContent+=")";
        return str;
    }
}
