package cn.edu.nju.mutestdemo.Model;
import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;
import java.util.ArrayList;

public class Event extends Unit {
    private boolean isAnonymous;
    private Object parameters;

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }
    public void output(int space){
        for(int i=0;i<space;i++)
            System.out.print("    ");
        System.out.print("event"+" ");
        System.out.print(getName()+"(");
        //处理parameter
        Parameter.ListOutput(parameters);
        System.out.print(")");
        if(isAnonymous)
            System.out.print(" anonymous");
        System.out.println(";");

    }
    public void addToMutant(ArrayList<MuType>types,int space){
        String content="event"+" "+getName()+"(";
        content+=Parameter.ListOutputToLine(parameters)+")";
        if(isAnonymous)
            content+=" anonymous";
        content+=";";
        Mutant.lines.add(new Line(content,new ArrayList<MuType>(),space));
    }
    public Object getParameters() {
        return parameters;
    }

    public void setParameters(Object parameters) {
        this.parameters = parameters;
    }
}
