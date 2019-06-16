package cn.edu.nju.mutestdemo.MutationTestUtil;

import cn.edu.nju.mutestdemo.Model.MutationTestResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TestResultAnalysis {
    public static MutationTestResult getTestresult(String ProjectPath){
        MutationTestResult res=new MutationTestResult();
        File file = new File(ProjectPath + "\\MuSC_MutationTestLog");
        ArrayList<String> mutFileName=new ArrayList<String>();
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    continue;
                } else {
                    if(file2.getName().substring(0,17).equals("MutationTestDebug")){
                        if(isKill(file2).equals("yes"))res.setKill(res.getKill()+1);
                        else res.setLive(res.getLive()+1);
                    }
                }
            }
        }
        res.setTotal(res.getLive()+res.getKill());
        res.setScore(100*res.getKill()/res.getTotal());
        return res;
    }
    private static String isKill(File file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));// 读取log

            String line  = null;
            while ((line = br.readLine()) != null) {
                if(line.contains(" failing\u001B[0m")){
                    return "yes";
                }
                else if(line.contains("Compilation failed."))
                    return "fail";
                else if(line.contains("Could not connect to your Ethereum client."))
                    return "can't connect client";

            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "no";
    }
}
