package cn.edu.nju.mutestdemo.Model;
import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class StateVariable extends Var {
    private boolean isIndexed;
    private Object expression;
    private String visibility;
    private boolean isStateVar;
    private boolean isDeclaredConst;

    public boolean isIndexed() {
        return isIndexed;
    }

    public void setIndexed(boolean indexed) {
        isIndexed = indexed;
    }

    public Object getExpression() {
        return expression;
    }

    public void setExpression(Object expression) {
        this.expression = expression;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public boolean isStateVar() {
        return isStateVar;
    }

    public void setStateVar(boolean stateVar) {
        isStateVar = stateVar;
    }

    public boolean isDeclaredConst() {
        return isDeclaredConst;
    }

    public void setDeclaredConst(boolean declaredConst) {
        isDeclaredConst = declaredConst;
    }

    public void output(int space){
        for(int i=0;i<space;i++)
            System.out.print("    ");
        if(getTypeName()!=null)
            printType();
        if(!visibility.equals("default"))
            System.out.print(" "+visibility);
        if(isDeclaredConst())
            System.out.print(" constant");
        System.out.print(" "+getName());
        if(expression!=null){
            System.out.print(" = ");
            ExpressionStatement.printPart(expression);
        }
        System.out.println(";");
    }
    public void addToMutant(ArrayList<MuType> types, int space){
        String content="";
        if(getTypeName()!=null)
            content=printTypeToLine();
        if(!visibility.equals("default"))
            content+=" "+visibility;
        if(isDeclaredConst())
            content+=" constant";
        content+=" "+getName();
        if(expression!=null){
            content+=" = ";
            content+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),expression);
        }
        content+=";";
        Mutant.lines.add(new Line(content,new ArrayList<MuType>(),space));
    }
}
