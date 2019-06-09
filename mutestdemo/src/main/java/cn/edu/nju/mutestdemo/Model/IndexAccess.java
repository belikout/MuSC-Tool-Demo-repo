package cn.edu.nju.mutestdemo.Model;

import java.util.ArrayList;
import cn.edu.nju.mutestdemo.EnumType.MuType;
public class IndexAccess  {
    private Object index;
    private String type;
    private Object base;

    public Object getIndex() {
        return index;
    }

    public void setIndex(Object index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getBase() {
        return base;
    }

    public void setBase(Object base) {
        this.base = base;
    }
    public void output(){
        ExpressionStatement.printPart(base);
        System.out.print("[");
        ExpressionStatement.printPart(index);
        System.out.print("]");
    }
    public String outputToLine(ArrayList<MuType> types){
        String str=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),base)+"[";
        str+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),index)+"]";
        return str;
    }
}
