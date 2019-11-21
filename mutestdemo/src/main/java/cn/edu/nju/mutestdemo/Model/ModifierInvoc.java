package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class ModifierInvoc extends Unit {
    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    private Object[] arguments;
    @Override
    public void output(){
        System.out.print(getName());
        if(arguments.length>0) {
            System.out.print("(");
            Argument.ListOutput(arguments);
            System.out.print(")");
        }
    }
    public String outputToLine(ArrayList<MuType>types){
        String str=getName();
        if(arguments.length>0) {
            str+="(";
            str+=Argument.ListOutputToLine(types,arguments);
            str+=")";
        }
        return str;
    }

}
