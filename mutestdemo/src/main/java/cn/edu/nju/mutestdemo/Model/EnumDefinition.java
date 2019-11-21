package cn.edu.nju.mutestdemo.Model;
import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import com.alibaba.fastjson.JSONObject;
import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class EnumDefinition extends Unit {
    private Object[]members;

    public Object[] getMembers() {
        return members;
    }

    public void setMembers(Object[] members) {
        this.members = members;
    }
    public void output(int space){
        for(int i=0;i<space;i++)
            System.out.print("    ");
        System.out.print("enum "+getName()+" {");
        if(members!=null&&members.length!=0){
            for(int i=0;i<members.length;i++){
                System.out.print(((JSONObject)members[i]).getString("name"));
                if(i!=members.length-1)
                    System.out.print(", ");
            }
        }
        System.out.println("}");
    }
    public void addToMutant(ArrayList<MuType>types,int space){
        String content="enum "+getName()+" {";
        if(members!=null&&members.length!=0){
            for(int i=0;i<members.length;i++){
                content+=((JSONObject)members[i]).getString("name");
                if(i!=members.length-1)
                    content=", ";
            }
        }
        Mutant.lines.add(new Line(content+"}",new ArrayList<MuType>(),space));
        Statement.lineContent="";
    }
}
