package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;

import java.util.ArrayList;
import cn.edu.nju.mutestdemo.EnumType.MuType;
public class ThrowStatement {
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
        System.out.println("throw;");
    }
    public void outputToLine(ArrayList<MuType>types,int space){
        Mutant.lines.add(new Line("throw;",new ArrayList<MuType>(),space));
        Statement.lineContent="";
    }
    public void output(){
        System.out.println("throw;");
    }
}
