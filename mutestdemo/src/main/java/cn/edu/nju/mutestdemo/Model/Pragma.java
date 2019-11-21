package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class Pragma extends Unit {
    private String value;
    public String getValue(){return value;}
    public void setValue(String value){this.value=value;}
    @Override
    public void output(){
        System.out.print("pragma "+getName()+" ");
        System.out.print(getValue());
        System.out.println(";");
    }
    public void addToMutant(ArrayList<MuType>types){
        String content="pragma "+getName()+" "+getValue()+";" ;
        Line line =new Line(content,new ArrayList<MuType>(),0);
        Mutant.lines.add(line);
        Statement.lineContent="";
    }
}
