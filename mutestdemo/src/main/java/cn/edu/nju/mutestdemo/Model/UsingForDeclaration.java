package cn.edu.nju.mutestdemo.Model;
import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class UsingForDeclaration {
    private String libraryName;
    private Object typeName;
    private String type;

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public Object getTypeName() {
        return typeName;
    }

    public void setTypeName(Object typeName) {
        this.typeName = typeName;
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
        System.out.print("using "+libraryName+" for ");
        if(typeName!=null)
            Var.printType(typeName);
        else
            System.out.print("*");
        System.out.println(";");
    }
    public void addToMutant(ArrayList<MuType> types, int space){
        String content="using "+libraryName+" for ";
        if(typeName!=null)
            content+=Var.printTypeToLine(typeName);
        else
            content+="*";
        Mutant.lines.add(new Line(content+";",new ArrayList<MuType>(),space));
        Statement.lineContent="";
    }
}
