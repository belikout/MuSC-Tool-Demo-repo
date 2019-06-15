package cn.edu.nju.mutestdemo.ASTMutation;

import cn.edu.nju.mutestdemo.Model.Line;

import java.util.ArrayList;

public class Mutant {
    public static ArrayList<Line>lines=new ArrayList<Line>();
    public static ArrayList<Integer>mutateLineNums=new ArrayList<Integer>();
    public static ArrayList<Integer>mutateLineTypeNums=new ArrayList<Integer>();
    public static ArrayList<Integer> mutateLineRepairFromNums =new ArrayList<Integer>();
    public static ArrayList<String>mutateLine=new ArrayList<String>();
    public static void clear(){
        lines=new ArrayList<Line>();
        mutateLineNums=new ArrayList<Integer>();
        mutateLineTypeNums=new ArrayList<Integer>();
        mutateLineRepairFromNums =new ArrayList<Integer>();
        mutateLine=new ArrayList<String>();
    }
    public static void Repair(){
        String space="";
        for(int i=0;i<mutateLine.size();i++){
            space="";
            for(int j=0;j<lines.get(mutateLineNums.get(i)).getSpace();j++)
                space+="    ";

            mutateLine.set(i,space+mutateLine.get(i)+lines.get(mutateLineNums.get(i)).getContent().substring(mutateLineRepairFromNums.get(i)));
        }
        for(int i=0;i<lines.size();i++){
            space="";
            for(int j=0;j<lines.get(i).getSpace();j++)
                space+="    ";
            lines.get(i).setContent(space+lines.get(i).getContent());
        }

    }
}
