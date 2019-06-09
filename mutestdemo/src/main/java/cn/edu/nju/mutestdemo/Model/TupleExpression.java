package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class TupleExpression {
    private boolean isArray;
    private Object[] components;
    private String type;

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public Object[] getComponents() {
        return components;
    }

    public void setComponents(Object[] components) {
        this.components = components;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void output(){
        if(!isArray){
            System.out.print("(");
            printComponent();
            System.out.print(")");
        }
        else{
            System.out.print("[");
            printComponent();
            System.out.print("]");
        }

    }
    public String outputToLine(ArrayList<MuType> types){
        String str="";
        if(!isArray){
            str+="("+printComponentToLine()+")";
        }
        else{
            str+="["+printComponentToLine()+"]";
        }
        return str;
    }
    private  void printComponent(){
        for(int i=0;i<components.length;i++) {
            ExpressionStatement.printPart(components[i]);
            if(i!=components.length-1)
                System.out.print(", ");
        }
    }
    private  String printComponentToLine(){
        String str="";
        for(int i=0;i<components.length;i++) {
            str+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),components[i]);
            if(i!=components.length-1)
                str+=", ";
        }
        return str;
    }
}
