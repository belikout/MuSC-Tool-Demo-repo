package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;
import com.alibaba.fastjson.JSONObject;

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
        String tempStr="";
        String temp="";
        Statement.lineContent+="(";
        if(names.length>0) {
            Statement.lineContent+="{";
            temp="{ "+Argument.ListOutputWithNameToLine(types,names, arguments)+"}";
            str+=temp;
            tempStr+=temp;
            Statement.lineContent+="}";
        }
        else {
            temp=Argument.ListOutputToLine(types, arguments);
            str += temp;
            tempStr+=temp;
        }

        if(((JSONObject)expression).getString("type").equals("Identifier")) {
            if(types.contains(MuType.RSD)&&((JSONObject) expression).getString("name").equals("require")){
                Mutant.mutateLineNums.add(Mutant.lines.size());
                Mutant.mutateLineTypeNums.add(MuType.RSD.ordinal());
                Mutant.mutateLine.add("//");
                Mutant.mutateLineRepairFromNums.add(0);
            }
            if(types.contains(MuType.RSC)&&((JSONObject) expression).getString("name").equals("require")){
                Mutant.mutateLineNums.add(Mutant.lines.size());
                Mutant.mutateLineTypeNums.add(MuType.RSC.ordinal());
                Mutant.mutateLine.add("require(!("+tempStr+")");
                Mutant.mutateLineRepairFromNums.add(Statement.lineContent.length());
            }
        }

        str+=")";
        Statement.lineContent+=")";
        return str;
    }
}
