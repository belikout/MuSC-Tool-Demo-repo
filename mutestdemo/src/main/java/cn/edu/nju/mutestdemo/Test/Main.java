package cn.edu.nju.mutestdemo.Test;

import cn.edu.nju.mutestdemo.Model.SourceUnit;
import cn.edu.nju.mutestdemo.Sol2AST.GenASTServiceClient;
import com.alibaba.fastjson.JSON;

public class Main {
    public static void main(String args[]){
        String json= GenASTServiceClient.genAST("C:\\Users\\belikout\\Desktop\\sol.txt");
        SourceUnit su= JSON.parseObject(json, SourceUnit.class);
        su.output();
    }
}
