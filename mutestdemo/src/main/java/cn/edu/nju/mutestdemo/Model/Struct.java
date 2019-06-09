package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import cn.edu.nju.mutestdemo.EnumType.MuType;
public class Struct extends Unit {
    private Object[] members;

    public Object[] getMembers() {
        return members;
    }

    public void setMembers(Object[] members) {
        this.members = members;
    }
    public void output(int space){
        for(int i=0;i<space;i++)
            System.out.print("    ");
        System.out.println("struct "+getName()+" {");
        // 处理members
        if(members!=null)
            for(int i=0;i<members.length;i++){
                if(((JSONObject)members[i]).getString("type").equals("VariableDeclaration"))
                    JSON.parseObject(members[i].toString(), Variable.class).output(space+1);
            }
        for(int i=0;i<space;i++)
            System.out.print("    ");
        System.out.println("}");
    }
    public void addToMutant(ArrayList<MuType>types,int space){
        Mutant.lines.add(new Line("struct "+getName()+" {",new ArrayList<MuType>(),space));
        // 处理members
        if(members!=null)
            for(int i=0;i<members.length;i++){
                if(((JSONObject)members[i]).getString("type").equals("VariableDeclaration"))
                    JSON.parseObject(members[i].toString(), Variable.class).outputToLine(types,space+1);
            }
        Mutant.lines.add(new Line("}",new ArrayList<MuType>(),space));
    }
}
