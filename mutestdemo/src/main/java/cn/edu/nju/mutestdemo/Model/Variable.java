package cn.edu.nju.mutestdemo.Model;

import java.util.ArrayList;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;
public class Variable extends Var {
    private boolean isIndexed;
    private String storageLocation;
    private boolean isStateVar;

    public boolean isIndexed() {
        return isIndexed;
    }

    public void setIndexed(boolean indexed) {
        isIndexed = indexed;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public boolean isStateVar() {
        return isStateVar;
    }

    public void setStateVar(boolean stateVar) {
        isStateVar = stateVar;
    }
    public void output(int space){
        for(int i=0;i<space;i++)
            System.out.print("    ");
        printType();
        if(storageLocation!=null)
            System.out.print(" "+storageLocation);
        System.out.println(" "+getName()+";");
    }
    public void outputToLine(ArrayList<MuType> types, int space){
        String content=printTypeToLine();
        if(storageLocation!=null)
            content+=" "+storageLocation;
        System.out.println(" "+getName()+";");
        Mutant.lines.add(new Line(content+" "+getName()+";",new ArrayList<MuType>(),space));
        Statement.lineContent="";
    }
}
