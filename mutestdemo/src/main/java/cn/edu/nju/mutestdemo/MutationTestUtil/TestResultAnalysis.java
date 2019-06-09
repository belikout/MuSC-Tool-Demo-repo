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
        File file = new File(ProjectPath + "\\MutationTestLog");
        ArrayList<String> mutFileName=new ArrayList<String>();
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    continue;
                } else {
                    if(file2.getName().substring(0,17).equals("MutationTestDebug")){
                        if(isKill(file2))res.setTkill(res.getTkill()+1);
                        else res.settLive(res.gettLive()+1);
                    }
                }
            }
        }
        res.settTotal(res.gettLive()+res.getTkill());
        res.settScore(100*res.getTkill()/res.gettTotal());
        return res;
    }
    private static boolean isKill(File file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));// 读取原始json文件

            String line  = null;
            while ((line = br.readLine()) != null) {
                if(line.contains(" failing\u001B[0m")){
                    return true;
                }
            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
