package cn.edu.nju.mutestdemo.Model;
import cn.edu.nju.mutestdemo.EnumType.MuType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class SourceUnit {
    private String type;
    private Object[] children;
    public String gettType(){return type;}
    public Object[] getChildren(){return children;}
    public void setType(String type){this.type=type;}
    public void setChildren(Object[]children){this.children=children;}
    public  void output(){
        for(int i=0;i<children.length;i++){
            if(((JSONObject)children[i]).getString("type").equals("ContractDefinition")) {
                JSON.parseObject(children[i].toString(), Contract.class).output();
            }
            if(((JSONObject)children[i]).getString("type").equals("PragmaDirective")) {
                JSON.parseObject(children[i].toString(), Pragma.class).output();
            }
            if(((JSONObject)children[i]).getString("type").equals("ImportDirective")) {
                JSON.parseObject(children[i].toString(), Import.class).output();
            }
        }
    }
    public void addToMutant(ArrayList<MuType>types){
        for(int i=0;i<children.length;i++){
            if(((JSONObject)children[i]).getString("type").equals("PragmaDirective")) {
                JSON.parseObject(children[i].toString(), Pragma.class).addToMutant(new ArrayList<MuType>());
            }
            if(((JSONObject)children[i]).getString("type").equals("ContractDefinition")) {
                JSON.parseObject(children[i].toString(), Contract.class).addToMutant(types);
            }
            if(((JSONObject)children[i]).getString("type").equals("ImportDirective")) {
                JSON.parseObject(children[i].toString(), Import.class).addToMutant(new ArrayList<MuType>());
            }
        }
    }
}
