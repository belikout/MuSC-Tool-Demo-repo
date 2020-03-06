package cn.edu.nju.mutestdemo.Model;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;
public class BinaryOperation {

    private Object left;
    private Object right;
    private String type;
    private String operator;
    private HashMap<String,Integer> AOROp =new HashMap<String,Integer>();
    private String[] AOROpArray ={"+","-","*","/","%"};
    private HashMap<String,Integer> ASROp =new HashMap<String,Integer>();
    private String[] ASROpArray ={"+=","-=","*=","/=","%=","&="};
    private HashMap<String,Integer> ROROp =new HashMap<String,Integer>();
    private String[] ROROpArray ={">",">=","<","<=","==","!="};
    private HashMap<String,Integer> COROp =new HashMap<String,Integer>();
    private String[] COROpArray ={"&","&&","|","||"};
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
        ROROp.put(">",0);
        ROROp.put(">=",1);
        ROROp.put("<",2);
        ROROp.put("<=",3);
        ROROp.put("==",4);
        ROROp.put("!=",5);
        COROp.put("&",0);
        COROp.put("&&",1);
        COROp.put("|",2);
        COROp.put("||",3);

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
        ExpressionStatement.printPart(left);
        System.out.print(" "+operator+" ");
        ExpressionStatement.printPart(right);
    }
    public String outputToLine(ArrayList<MuType>types){
        String str=ExpressionStatement.printPartToLine(types,left);
        if(types.contains(MuType.AOR)&& AOROp.containsKey(operator))
        {
            for(int i=1;i<=AOROp.size()-1;i++) {
                String mutate = Statement.lineContent + " " + AOROpArray[(AOROp.get(operator) + i) % 5] + " ";
                Mutant.mutateLineNums.add(Mutant.lines.size());
                Mutant.mutateLineTypeNums.add(MuType.AOR.ordinal());
                Mutant.mutateLine.add(mutate);
                Mutant.mutateLineRepairFromNums.add(Statement.lineContent.length() + 3);
            }
        }
        if(types.contains(MuType.ASR)&& ASROp.containsKey(operator))
        {
            for(int i=1;i<=ASROp.size()-1;i++) {
                String mutate = Statement.lineContent + " " + ASROpArray[(ASROp.get(operator) + i) % 6] + " ";
                Mutant.mutateLineNums.add(Mutant.lines.size());
                Mutant.mutateLineTypeNums.add(MuType.ASR.ordinal());
                Mutant.mutateLine.add(mutate);
                Mutant.mutateLineRepairFromNums.add(Statement.lineContent.length() + 4);
            }
        }
        if(types.contains(MuType.ROR)&& ROROp.containsKey(operator))
        {
            for(int i=1;i<=ROROp.size()-1;i++) {
                String mutate = Statement.lineContent + " " + ROROpArray[(ROROp.get(operator) + i) % 6] + " ";
                Mutant.mutateLineNums.add(Mutant.lines.size());
                Mutant.mutateLineTypeNums.add(MuType.ROR.ordinal());
                Mutant.mutateLine.add(mutate);
                if (operator.length() == 1)
                    Mutant.mutateLineRepairFromNums.add(Statement.lineContent.length() + 3);
                else
                    Mutant.mutateLineRepairFromNums.add(Statement.lineContent.length() + 4);
            }
        }
        if(types.contains(MuType.COR)&& COROp.containsKey(operator))
        {
            for(int i=0;i<=COROp.size()-1;i++) {
                if(COROpArray[i].equals(operator)){
                    String mutate = Statement.lineContent + " " + COROpArray[(COROp.get(operator) + 2) % 4] + " ";
                    Mutant.mutateLineNums.add(Mutant.lines.size());
                    Mutant.mutateLineTypeNums.add(MuType.COR.ordinal());
                    Mutant.mutateLine.add(mutate);
                    if (operator.length() == 1)
                        Mutant.mutateLineRepairFromNums.add(Statement.lineContent.length() + 3);
                    else
                        Mutant.mutateLineRepairFromNums.add(Statement.lineContent.length() + 4);
                }

            }

        }
        str+=" "+operator+" ";
        Statement.lineContent+=" "+operator+" ";
        String temp=ExpressionStatement.printPartToLine(types,right);
        str+=temp;
        //Statement.lineContent+=temp;
        return str;
    }
}
