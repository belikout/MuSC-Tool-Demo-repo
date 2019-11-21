package cn.edu.nju.mutestdemo.Model;

import java.util.ArrayList;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;
public class DoWhileStatement extends WhileStatement {
    @Override
    public void output(int space){
        for(int i=0;i<space;i++)
            System.out.print("    ");
        System.out.print("do");
        IfStatement.printBody(space,getBody());
        for(int i=0;i<space;i++)
            System.out.print("    ");
        System.out.print("while(");
        ExpressionStatement.printPart(getCondition());
        System.out.println(");");
    }
    public void outputToLine(int space){
        String content="do";
        IfStatement.printBodyToLine(new ArrayList<MuType>(),content,space,getBody());
        content="while(";
        content+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),getCondition());
        content+=");";
        Mutant.lines.add(new Line(content,new ArrayList<MuType>(),space));
        Statement.lineContent="";
    }
}
