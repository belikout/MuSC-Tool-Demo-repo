package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.edu.nju.mutestdemo.EnumType.MuType;
import java.util.ArrayList;

public class IfStatement extends Statement {
    private Object condition;
    private Object trueBody;
    private Object falseBody;

    public Object getCondition() {
        return condition;
    }

    public void setCondition(Object condition) {
        this.condition = condition;
    }

    public Object getTrueBody() {
        return trueBody;
    }

    public void setTrueBody(Object trueBody) {
        this.trueBody = trueBody;
    }

    public Object getFalseBody() {
        return falseBody;
    }

    public void setFalseBody(Object falseBody) {
        this.falseBody = falseBody;
    }
    public void output(int space){
        for(int i=0;i<space;i++)
            System.out.print("    ");
        System.out.print("if(");
        ExpressionStatement.printPart(condition);
        System.out.print(") ");
        printBody(space,trueBody);
        if(falseBody!=null) {
            for(int i=0;i<space;i++)
                System.out.print("    ");
            System.out.print("else ");
            printBody(space,falseBody);
        }
    }
    public void outputToLine(ArrayList<MuType>types,int space){

        String content="";
        content+="if(";
        Statement.lineContent+=content;
        String mutate = Statement.lineContent;
        content+=ExpressionStatement.printPartToLine(types,condition);
        content+=") ";
        if(types.contains(MuType.CSC)){
            Mutant.mutateLineNums.add(Mutant.lines.size());
            Mutant.mutateLineTypeNums.add(MuType.CSC.ordinal());
            Mutant.mutateLine.add(mutate+"true)");
            Mutant.mutateLineRepairFromNums.add(Statement.lineContent.length()+1);
            Mutant.mutateLineNums.add(Mutant.lines.size());
            Mutant.mutateLineTypeNums.add(MuType.CSC.ordinal());
            Mutant.mutateLine.add(mutate+"false)");
            Mutant.mutateLineRepairFromNums.add(Statement.lineContent.length()+1);
        }
        Statement.lineContent+=") ";
        printBodyToLine(types,content,space,trueBody);
        content="";
        if(falseBody!=null) {
            content+="else ";
            Statement.lineContent+=content;
            printBodyToLine(types,content,space,falseBody);
        }
    }
    public static void printBody(int space,Object body){
        if (((JSONObject) body).getString("type").equals("Block")) {
            System.out.println("{");
            Statement.ListOutput(space + 1, ((JSONObject) body).getJSONArray("statements"));
        }
        else{
            System.out.println();
            JSONArray arr=new JSONArray();
            arr.add(body);
            Statement.ListOutput(space + 1, arr);
        }
        if (((JSONObject) body).getString("type").equals("Block")){
            for(int i=0;i<space;i++)
                System.out.print("    ");
            System.out.println("}");
        }

    }
    public static void printBodyToLine(ArrayList<MuType>types,String content,int space,Object body){
        if (((JSONObject) body).getString("type").equals("Block")) {
            content+="{";
            Mutant.lines.add(new Line(content,new ArrayList<MuType>(),space));
            Statement.lineContent="";
            Statement.ListOutputToLine(space + 1, ((JSONObject) body).getJSONArray("statements"),types);
        }
        else{
            Mutant.lines.add(new Line(content,new ArrayList<MuType>(),space));
            Statement.lineContent="";
            JSONArray arr=new JSONArray();
            arr.add(body);
            Statement.ListOutputToLine(space + 1, arr,types);
        }
        if (((JSONObject) body).getString("type").equals("Block")){
            Mutant.lines.add(new Line("}",new ArrayList<MuType>(),space));
            Statement.lineContent="";
        }

    }
}
