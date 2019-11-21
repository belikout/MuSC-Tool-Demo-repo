package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.edu.nju.mutestdemo.EnumType.MuType;
import java.util.ArrayList;

public class Contract extends Unit {
    private Object[] baseContracts;
    private Object[] subNodes;
    private String kind;
    public Object[] getBaseContracts(){return baseContracts;}
    public Object[] getSubNodes(){return subNodes;}
    public String getKind(){return kind;}
    public void setBaseContracts(Object[] baseContracts){this.baseContracts=baseContracts;}
    public void setSubNodes(Object[] subNodes){this.subNodes=subNodes;}
    public void setKind(String kind){this.kind=kind;}
    @Override
    public void output(){

        System.out.print(kind+" "+getName()+" ");
        if(getBaseContracts()!=null&&getBaseContracts().length!=0){
            System.out.print("is ");
            for(int i=0;i<getBaseContracts().length;i++){
                System.out.print(((JSONObject)getBaseContracts()[i]).getJSONObject("baseName").getString("namePath"));
                if(i!=getBaseContracts().length-1)
                    System.out.print(",");
            }
        }
        System.out.println("{");
        //处理SubNotes
        if(subNodes!=null&&subNodes.length>0)
            for(int i=0;i<subNodes.length;i++){
                if(((JSONObject)subNodes[i]).getString("type").equals("StateVariableDeclaration")) {
                    JSON.parseObject(subNodes[i].toString(), StateVariableDefinition.class).output(1);
                }
                if(((JSONObject)subNodes[i]).getString("type").equals("ModifierDefinition")) {
                    JSON.parseObject(subNodes[i].toString(), Modifier.class).output(1);
                }
                if(((JSONObject)subNodes[i]).getString("type").equals("EventDefinition")) {
                    JSON.parseObject(subNodes[i].toString(), Event.class).output(1);
                }
                if(((JSONObject)subNodes[i]).getString("type").equals("FunctionDefinition")) {
                    JSON.parseObject(subNodes[i].toString(),Function.class).output(1);
                }
                if(((JSONObject)subNodes[i]).getString("type").equals("StructDefinition")) {
                    JSON.parseObject(subNodes[i].toString(), Struct.class).output(1);
                }
                if(((JSONObject)subNodes[i]).getString("type").equals("UsingForDeclaration")) {
                    JSON.parseObject(subNodes[i].toString(), UsingForDeclaration.class).output(1);
                }
                if(((JSONObject)subNodes[i]).getString("type").equals("EnumDefinition")) {
                    JSON.parseObject(subNodes[i].toString(), EnumDefinition.class).output(1);
                }
            }
        System.out.println("}");
    }
    public void addToMutant(ArrayList<MuType> types){
        String content=kind+" "+getName()+" ";
        if(getBaseContracts()!=null&&getBaseContracts().length!=0){
            content+="is ";
            for(int i=0;i<getBaseContracts().length;i++){
                content+=((JSONObject)getBaseContracts()[i]).getJSONObject("baseName").getString("namePath");
                if(i!=getBaseContracts().length-1)
                    content+=",";
            }
        }
        content+="{";
        Line line=new Line(content,new ArrayList<MuType>(),0);
        Mutant.lines.add(line);
        Statement.lineContent="";

        //处理SubNotes
        if(subNodes!=null&&subNodes.length>0)
            for(int i=0;i<subNodes.length;i++){
                if(((JSONObject)subNodes[i]).getString("type").equals("StateVariableDeclaration")) {
                    JSON.parseObject(subNodes[i].toString(), StateVariableDefinition.class).addToMutant(types,1);
                }
                if(((JSONObject)subNodes[i]).getString("type").equals("ModifierDefinition")) {
                    JSON.parseObject(subNodes[i].toString(), Modifier.class).addToMutant(types,1);
                }
                if(((JSONObject)subNodes[i]).getString("type").equals("EventDefinition")) {
                    JSON.parseObject(subNodes[i].toString(), Event.class).addToMutant(types,1);
                }
                if(((JSONObject)subNodes[i]).getString("type").equals("FunctionDefinition")) {
                    JSON.parseObject(subNodes[i].toString(),Function.class).addToMutant(types,1);
                }
                if(((JSONObject)subNodes[i]).getString("type").equals("StructDefinition")) {
                    JSON.parseObject(subNodes[i].toString(), Struct.class).addToMutant(types,1);
                }
                if(((JSONObject)subNodes[i]).getString("type").equals("UsingForDeclaration")) {
                    JSON.parseObject(subNodes[i].toString(), UsingForDeclaration.class).addToMutant(types,1);
                }
                if(((JSONObject)subNodes[i]).getString("type").equals("EnumDefinition")) {
                    JSON.parseObject(subNodes[i].toString(), EnumDefinition.class).addToMutant(types,1);
                }
            }
        Mutant.lines.add(new Line("}",types,0));
        Statement.lineContent="";
    }
}
