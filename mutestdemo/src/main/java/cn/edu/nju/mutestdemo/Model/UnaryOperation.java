package cn.edu.nju.mutestdemo.Model;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;
public class UnaryOperation {
    private String type;
    private boolean isPrefix;
    private String operator;
    private Object subExpression;

    public UnaryOperation(){

    }
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
        boolean hasAOR=false;
        if(types.contains(MuType.AOR))
            hasAOR=true;
        if(isPrefix) {
            str += operator;
            if(hasAOR) {
                String temp=Statement.lineContent;
                if (operator.equals("++"))
                    temp=temp+"--";
                else if(operator.equals("--"))
                    temp=temp+"++";
                Mutant.mutateLineNums.add(Mutant.lines.size());
                Mutant.mutateLineTypeNums.add(MuType.AOR.ordinal());
                Mutant.mutateLine.add(temp);
                Mutant.mutateLineRepairFromNums.add(Statement.lineContent.length()+2);
            }
            Statement.lineContent+=operator;
        }
        if(subExpression!=null) {
            str+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),subExpression);

        }
        if(!isPrefix) {
            str += operator;
            if(hasAOR) {
                String temp=Statement.lineContent;
                if (operator.equals("++"))
                    temp=temp+"--";
                else if(operator.equals("--"))
                    temp=temp+"++";
                Mutant.mutateLineNums.add(Mutant.lines.size());
                Mutant.mutateLineTypeNums.add(MuType.AOR.ordinal());
                Mutant.mutateLine.add(temp);
                Mutant.mutateLineRepairFromNums.add(Statement.lineContent.length()+2);
            }
            Statement.lineContent+=operator;
        }
        return str;
    }
}
