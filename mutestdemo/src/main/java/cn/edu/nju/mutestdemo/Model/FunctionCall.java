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
        String str=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),expression);
        str+="(";
        if(names.length>0) {
            str+="{ "+Argument.ListOutputWithNameToLine(names, arguments)+"}";
        }
        else
            str+=Argument.ListOutputToLine(arguments);
        str+=")";
        return str;
    }
}
