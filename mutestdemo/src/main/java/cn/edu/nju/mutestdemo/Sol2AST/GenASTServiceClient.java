package cn.edu.nju.mutestdemo.Sol2AST;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.io.*;

public class GenASTServiceClient {

    public static void main(String[] args) {
        genAST("C:\\\\Users\\\\belikout\\\\Desktop\\\\test.sol");
    }
    public static String genAST(String SolPath){
        System.out.println("客户端启动....");
        TTransport transport = null;
        try {
            transport = new TSocket("localhost", 9898, 30000);
            TProtocol protocol = new TBinaryProtocol(transport);
            GenAST.Client client = new GenAST.Client(protocol);

            transport.open();
            String result = client.genAST(getFile(SolPath));
            System.out.println(result);
            transport.close();
            return result;
        } catch (TTransportException e) {
            e.printStackTrace();
            return "TTransportError";
        } catch (TException e) {
            e.printStackTrace();
            return "TError";
        }
    }
    private static String getFile(String path){
        String out="";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));// 读取sol
            String s  = null;

            while ((s = br.readLine()) != null) {
                out+=s+"\n";
            }
            br.close();
            return out;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "error";
        }
    }

}

