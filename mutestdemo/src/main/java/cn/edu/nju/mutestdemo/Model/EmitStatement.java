package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;
import java.util.ArrayList;

public class EmitStatement {
    private String type;
    private Object eventCall;

    public Object getEventCall() {
        return eventCall;
    }

    public void setEventCall(Object eventCall) {
        this.eventCall = eventCall;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void output(int space){
        for(int i=0;i<space;i++)
            System.out.print("    ");
        System.out.print("emit ");
        ExpressionStatement.printPart(eventCall);
        System.out.println(";");
    }
    public void outputToLine(ArrayList<MuType> types, int space){
        String content="emit ";
        content+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),eventCall)+";";
        Mutant.lines.add(new Line(content,new ArrayList<MuType>(),space));
    }
}
