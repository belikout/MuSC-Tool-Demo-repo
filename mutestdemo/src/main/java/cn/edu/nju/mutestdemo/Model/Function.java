package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class Function extends Unit {
    private String visibility;
    private boolean isConstructor;
    private String stateMutability;
    private Object returnParameters;
    private Object body;
    private Object[] modifiers;
    private Object parameters;

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public boolean isConstructor() {
        return isConstructor;
    }

    public void setConstructor(boolean constructor) {
        isConstructor = constructor;
    }

    public String getStateMutability() {
        return stateMutability;
    }

    public void setStateMutability(String stateMutability) {
        this.stateMutability = stateMutability;
    }

    public Object getReturnParameters() {
        return returnParameters;
    }

    public void setReturnParameters(Object returnParameters) {
        this.returnParameters = returnParameters;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Object[] getModifiers() {
        return modifiers;
    }

    public void setModifiers(Object[] modifiers) {
        this.modifiers = modifiers;
    }

    public Object getParameters() {
        return parameters;
    }

    public void setParameters(Object parameters) {
        this.parameters = parameters;
    }

    public void output(int space){
        for(int i=0;i<space;i++)
            System.out.print("    ");
        if(getName()==null&&isConstructor())
            System.out.print("constructor(");
        else
            System.out.print("function"+" "+getName()+"(");
        Parameter.ListOutput(parameters);
        System.out.print(") ");

        if(modifiers.length>0){
            for(int i=0;i<modifiers.length;i++){
                JSON.parseObject(modifiers[i].toString(), ModifierInvoc.class).output();
                System.out.print("  ");
            }
        }
        if(!visibility.equals("default"))
            System.out.print(visibility+" ");
        if(stateMutability!=null)
            System.out.print(stateMutability+"  ");
        if(returnParameters!=null) {
            System.out.print("returns(");
            Parameter.ListOutput(returnParameters);
            System.out.print(") ");
        }
        if(body!=null) {
        System.out.println("{");
        //处理body start
            JSONArray statements = ((JSONObject) body).getJSONArray("statements");
            Statement.ListOutput(space + 1, statements);
        //处理body end
        for(int i=0;i<space;i++)
            System.out.print("    ");
        System.out.println("}");
        }
        else
            System.out.println(";");
    }
    public void addToMutant(ArrayList<MuType> types, int space){
        String content="";

        if(getName()==null&&isConstructor())
            content+="constructor(";
        else
            content+="function"+" "+getName()+"(";
        content+=Parameter.ListOutputToLine(parameters);
        content+=") ";

        if(modifiers.length>0){
            for(int i=0;i<modifiers.length;i++){
                content+=JSON.parseObject(modifiers[i].toString(), ModifierInvoc.class).outputToLine();
                content+="  ";
            }
        }
        if(!visibility.equals("default"))
            content+=visibility+" ";
        if(stateMutability!=null)
            content+=stateMutability+"  ";
        if(returnParameters!=null) {
            content+="returns(";
            content+=Parameter.ListOutputToLine(returnParameters);
            content+=") ";
        }
        if(body!=null) {
            content+="{";
            Mutant.lines.add(new Line(content,new ArrayList<MuType>(),space));
            //处理body start
            JSONArray statements = ((JSONObject) body).getJSONArray("statements");
            Statement.ListOutputToLine(space + 1, statements,types);
            //处理body end
            Mutant.lines.add(new Line("}",new ArrayList<MuType>(),space));
        }
        else
            Mutant.lines.add(new Line(content+";",new ArrayList<MuType>(),space));
    }
}
