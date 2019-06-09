package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class Argument extends Unit {
    public static void ListOutput(Object[] arguments){
        if(arguments.length>0){
            for(int i=0;i<arguments.length;i++){
                ExpressionStatement.printPart(arguments[i]);
                if(i!=arguments.length-1)
                    System.out.print(", ");
            }
        }
    }
    public static String ListOutputToLine(Object[] arguments){
        String str="";
        if(arguments.length>0){
            for(int i=0;i<arguments.length;i++){
                str+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),arguments[i]);
                if(i!=arguments.length-1)
                    str+=", ";
            }
        }
        return str;
    }
    public static void ListOutputWithName(String[] names,Object[] arguments){
        if(arguments.length>0){
            for(int i=0;i<arguments.length;i++){
                System.out.print(names[i]+": ");
                ExpressionStatement.printPart(arguments[i]);
                if(i!=arguments.length-1)
                    System.out.print(", ");
            }
        }
    }
    public static String ListOutputWithNameToLine(String[] names,Object[] arguments){
        String str="";
        if(arguments.length>0){
            for(int i=0;i<arguments.length;i++){
                str+=names[i]+": ";
                str+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),arguments[i]);
                if(i!=arguments.length-1)
                    str+=", ";
            }
        }
        return str;
    }
}
