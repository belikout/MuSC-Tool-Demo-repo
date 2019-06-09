package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;

import java.util.ArrayList;
import cn.edu.nju.mutestdemo.EnumType.MuType;
public class BreakStatement {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void output(int space){
        for(int j=0;j<space;j++)
            System.out.print("    ");
        System.out.println("break;");
    }
    public void outputToLine(ArrayList<MuType> types, int space){
        Mutant.lines.add(new Line("break;",new ArrayList<MuType>(),space));
    }
    public void output(){
        System.out.println("break;");
    }
}
