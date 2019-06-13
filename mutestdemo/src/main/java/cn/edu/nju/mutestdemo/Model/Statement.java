package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.edu.nju.mutestdemo.EnumType.MuType;
import java.util.ArrayList;

public class Statement {
    //ExpressionStatement,IfStatement,
    private String type;
    public static String lineContent="";
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static void ListOutput(int space, JSONArray statements){
        for(int i=0;i<statements.size();i++){
            if(((JSONObject)statements.get(i)).getString("type").equals("VariableDeclarationStatement")) {
                for(int j=0;j<space;j++)
                    System.out.print("    ");
                JSON.parseObject(statements.get(i).toString(), VariableDeclarationStatement.class).output();
                System.out.println(";");
            }
            else if(((JSONObject)statements.get(i)).getString("type").equals("ExpressionStatement")) {
                for(int j=0;j<space;j++)
                    System.out.print("    ");
                ExpressionStatement.printPart(((JSONObject) statements.get(i)).getJSONObject("expression"));
                System.out.println(";");
            }
            else if(((JSONObject)statements.get(i)).getString("type").equals("IfStatement"))
                JSON.parseObject(statements.get(i).toString(), IfStatement.class).output(space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("ForStatement"))
                JSON.parseObject(statements.get(i).toString(),ForStatement.class).output(space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("WhileStatement"))
                JSON.parseObject(statements.get(i).toString(), WhileStatement.class).output(space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("DoWhileStatement"))
                JSON.parseObject(statements.get(i).toString(), DoWhileStatement.class).output(space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("ReturnStatement"))
                JSON.parseObject(statements.get(i).toString(),ReturnStatement.class).output(space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("ThrowStatement"))
                JSON.parseObject(statements.get(i).toString(), ThrowStatement.class).output(space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("BreakStatement"))
                JSON.parseObject(statements.get(i).toString(), BreakStatement.class).output(space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("ContinueStatement"))
                JSON.parseObject(statements.get(i).toString(),ContinueStatement.class).output(space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("EmitStatement"))
                JSON.parseObject(statements.get(i).toString(),EmitStatement.class).output(space);
        }
    }
    public static void ListOutputToLine(int space, JSONArray statements, ArrayList<MuType>types){
        String content="";
        for(int i=0;i<statements.size();i++){
            content="";
            lineContent="";
            if(((JSONObject)statements.get(i)).getString("type").equals("VariableDeclarationStatement")) {
                content+=JSON.parseObject(statements.get(i).toString(), VariableDeclarationStatement.class).outputToLine()+";";
                Mutant.lines.add(new Line(content,new ArrayList<MuType>(),space));
            }
            else if(((JSONObject)statements.get(i)).getString("type").equals("ExpressionStatement")) {

                content+=ExpressionStatement.printPartToLine(types,((JSONObject) statements.get(i)).getJSONObject("expression"))+";";
                Mutant.lines.add(new Line(content,new ArrayList<MuType>(),space));
            }
            else if(((JSONObject)statements.get(i)).getString("type").equals("IfStatement"))
                JSON.parseObject(statements.get(i).toString(), IfStatement.class).outputToLine(types,space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("ForStatement"))
                JSON.parseObject(statements.get(i).toString(),ForStatement.class).outputToLine(new ArrayList<MuType>(),space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("WhileStatement"))
                JSON.parseObject(statements.get(i).toString(), WhileStatement.class).outputToLine(new ArrayList<MuType>(),space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("DoWhileStatement"))
                JSON.parseObject(statements.get(i).toString(), DoWhileStatement.class).outputToLine(new ArrayList<MuType>(),space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("ReturnStatement"))
                JSON.parseObject(statements.get(i).toString(),ReturnStatement.class).outputToLine(new ArrayList<MuType>(),space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("ThrowStatement"))
                JSON.parseObject(statements.get(i).toString(), ThrowStatement.class).outputToLine(new ArrayList<MuType>(),space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("BreakStatement"))
                JSON.parseObject(statements.get(i).toString(), BreakStatement.class).outputToLine(new ArrayList<MuType>(),space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("ContinueStatement"))
                JSON.parseObject(statements.get(i).toString(),ContinueStatement.class).outputToLine(new ArrayList<MuType>(),space);
            else if(((JSONObject)statements.get(i)).getString("type").equals("EmitStatement"))
                JSON.parseObject(statements.get(i).toString(),EmitStatement.class).outputToLine(new ArrayList<MuType>(),space);
        }
    }
    public static void printPart(Object statement){
        if(((JSONObject)statement).getString("type").equals("VariableDeclarationStatement"))
            JSON.parseObject(statement.toString(), VariableDeclarationStatement.class).output();
        else if(((JSONObject)statement).getString("type").equals("ExpressionStatement"))
            ExpressionStatement.printPart(((JSONObject) statement).getJSONObject("expression"));
        else if(((JSONObject)statement).getString("type").equals("ReturnStatement"))
            JSON.parseObject(statement.toString(),ReturnStatement.class).output();
        else if(((JSONObject)statement).getString("type").equals("ThrowStatement"))
            JSON.parseObject(statement.toString(), ThrowStatement.class).output();
    }
    public static String printPartToLine(Object statement){
        String str="";
        if(((JSONObject)statement).getString("type").equals("VariableDeclarationStatement"))
            str+=JSON.parseObject(statement.toString(), VariableDeclarationStatement.class).outputToLine();
        else if(((JSONObject)statement).getString("type").equals("ExpressionStatement"))
            str+=ExpressionStatement.printPartToLine(new ArrayList<MuType>(),((JSONObject) statement).getJSONObject("expression"));
        return str;
    }
}
