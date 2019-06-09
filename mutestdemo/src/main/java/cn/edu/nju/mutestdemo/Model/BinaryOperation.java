package cn.edu.nju.mutestdemo.Model;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;
public class BinaryOperation {
    public static String string="";
    private Object left;
    private Object right;
    private String type;
    private String operator;
    private HashMap<String,Integer> AOROp =new HashMap<String,Integer>();
    private String[] AOROpArray ={"+","-","*","/","%"};
    private HashMap<String,Integer> ASROp =new HashMap<String,Integer>();
    private String[] ASROpArray ={"+=","-=","*=","/=","%=","&="};
    public BinaryOperation(){
        AOROp.put("+",0);
        AOROp.put("-",1);
        AOROp.put("*",2);
        AOROp.put("/",3);
        AOROp.put("%",4);
        ASROp.put("+=",0);
        ASROp.put("-=",1);
        ASROp.put("*=",2);
        ASROp.put("/=",3);
        ASROp.put("%=",4);
        ASROp.put("&=",5);
    }
    public Object getLeft() {
        return left;
    }

    public void setLeft(Object left) {
        this.left = left;
    }

    public Object getRight() {
        return right;
    }

    public void setRight(Object right) {
        this.right = right;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void output(){
        mutate();
        ExpressionStatement.printPart(left);
        System.out.print(" "+operator+" ");
        ExpressionStatement.printPart(right);
    }
    public String outputToLine(ArrayList<MuType>types){
        String str=ExpressionStatement.printPartToLine(types,left);
        string+=str;
        if(types.contains(MuType.AOR)&& AOROp.containsKey(operator))
        {
            String mutate=string+" "+ AOROpArray[(AOROp.get(operator)+1)%5]+" ";
            Mutant.mutateLineNums.add(Mutant.lines.size());
            Mutant.mutateLineTypeNums.add(MuType.AOR.ordinal());
            Mutant.mutateLine.add(mutate);
            Mutant.mutateLineRepairFromNums.add(string.length()+3);
        }
        if(types.contains(MuType.ASR)&& ASROp.containsKey(operator))
        {
            String mutate=string+" "+ ASROpArray[(ASROp.get(operator)+1)%6]+" ";
            Mutant.mutateLineNums.add(Mutant.lines.size());
            Mutant.mutateLineTypeNums.add(MuType.ASR.ordinal());
            Mutant.mutateLine.add(mutate);
            Mutant.mutateLineRepairFromNums.add(string.length()+4);
        }
        str+=" "+operator+" ";
        string+=" "+operator+" ";
        String temp=ExpressionStatement.printPartToLine(types,right);
        str+=temp;
        string+=temp;
        return str;
    }
    public void mutate(){
        if(operator.equals("<"))operator=">";
        if(operator.equals("=="))operator=">";
    }

}
