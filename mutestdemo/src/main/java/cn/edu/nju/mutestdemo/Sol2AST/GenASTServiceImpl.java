package cn.edu.nju.mutestdemo.Sol2AST;

import org.apache.thrift.TException;

public class GenASTServiceImpl implements GenAST.Iface{
    public String genAST(String sol) throws TException {
        System.out.println("receive: "+sol);
        return "result:"+sol;
    }
}
