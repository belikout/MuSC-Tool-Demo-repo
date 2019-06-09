package cn.edu.nju.mutestdemo.Model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.edu.nju.mutestdemo.EnumType.MuType;
import java.util.ArrayList;

public class StateVariableDefinition {
    private String type;
    private Object[] variables;
    private Object initialValue;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object[] getVariables() {
        return variables;
    }

    public void setVariables(Object[] variables) {
        this.variables = variables;
    }

    public Object getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(Object initialValue) {
        this.initialValue = initialValue;
    }

    public void output(int space){
        JSON.parseObject(((JSONObject)variables[0]).toString(), StateVariable.class).output(space);
    }
    public void addToMutant(ArrayList<MuType> types,int space){
        JSON.parseObject(((JSONObject)variables[0]).toString(), StateVariable.class).addToMutant(types,space);
    }
}
