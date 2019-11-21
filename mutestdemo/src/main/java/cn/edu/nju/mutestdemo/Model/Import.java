package cn.edu.nju.mutestdemo.Model;

import java.util.ArrayList;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;
public class Import {
    private String type;
    private String path;
    private String unitAlias;
    private String[][] symbolAliases;
    public String getType(){return type;}
    public void setType(String type){this.type=type;}
    public String getPath(){return path;}
    public void setPath(String path){this.path=path;}
    public String getUnitAlias(){return unitAlias;}
    public void setUnitAlias(String unitAlias){this.unitAlias=unitAlias;}
    public String[][] getSymbolAliases(){return symbolAliases;}
    public void setSymbolAliases(String[][] symbolAliases){this.symbolAliases=symbolAliases;}
    public void output(){
        System.out.print("import ");
        if(unitAlias!=null)
            System.out.print("\""+getPath()+"\" as "+getUnitAlias()+";");
        else if(symbolAliases!=null){
            System.out.print("{");
            for(int i=0;i<symbolAliases.length;i++) {
                if (symbolAliases[i][1]!=null)
                    System.out.print(symbolAliases[i][0] + " as " + symbolAliases[i][1]);
                else
                    System.out.print(symbolAliases[i][0]);
                if(i!=symbolAliases.length-1)
                    System.out.print(",");
            }
            System.out.print("} from \""+getPath()+"\";");
        }
        else
            System.out.print("\""+path+"\";");
        System.out.println();
    }
    public void addToMutant(ArrayList<MuType>types){
        String content ="import ";
        //System.out.print("import ");
        if(unitAlias!=null)
            content+="\""+getPath()+"\" as "+getUnitAlias()+";";
            //System.out.print("\""+getPath()+"\" as "+getUnitAlias()+";");
        else if(symbolAliases!=null){
            content+="{";
            for(int i=0;i<symbolAliases.length;i++) {
                if (symbolAliases[i][1]!=null)
                    content+=symbolAliases[i][0] + " as " + symbolAliases[i][1];
                else
                    content+=symbolAliases[i][0];
                if(i!=symbolAliases.length-1)
                    content+=",";
                    System.out.print(",");
            }
            content+="} from \""+getPath()+"\";";
        }
        else
            content+="\""+path+"\";";
        Line line=new Line(content,new ArrayList<MuType>(),0);
        Mutant.lines.add(line);
        Statement.lineContent="";

    }
}
