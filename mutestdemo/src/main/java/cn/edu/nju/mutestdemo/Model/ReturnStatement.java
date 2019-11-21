package cn.edu.nju.mutestdemo.Model;
import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class ReturnStatement {
    private Object expression;
    private String type;

    public Object getExpression() {
        return expression;
    }

    public void setExpression(Object expression) {
        this.expression = expression;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void output(int space){
        for(int j=0;j<space;j++)
            System.out.print("    ");
        System.out.print("return ");
        if(expression!=null)
            ExpressionStatement.printPart(expression);
        System.out.println(";");
    }
    public void outputToLine(ArrayList<MuType> types, int space){
        String content="return ";
        if(expression!=null)
            content+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),expression);
        content+=";";
        Mutant.lines.add(new Line(content,new ArrayList<MuType>(),space));
        Statement.lineContent="";
    }
    public void output(){
        System.out.print("return ");
        if(expression!=null)
            ExpressionStatement.printPart(expression);
        System.out.println(";");
    }
    public void  outputPartToLine(ArrayList<MuType> types){
        String content="return ";
        if(expression!=null)
            content+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),expression);
        content+=";";
        Mutant.lines.add(new Line(content,new ArrayList<MuType>(),0));
        Statement.lineContent="";
    }
}
