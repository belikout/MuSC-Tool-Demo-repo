package cn.edu.nju.mutestdemo.MutationTestUtil;

import cn.edu.nju.mutestdemo.FileUtils.ReplaceFileUtil;
import cn.edu.nju.mutestdemo.FileUtils.CMDStreamManage;
import cn.edu.nju.mutestdemo.Model.MutationTestResult;

import java.io.*;
import java.util.ArrayList;

public class MutationTestStater {
    static String ProjectPath="C:\\Users\\belikout\\Desktop\\metacoin-box-master";
    public static MutationTestResult start(String path) {
        if(path!=null&&path!="")
        ProjectPath=path;
        File file = new File(ProjectPath + "\\Mutants");
        ArrayList<File> list = new ArrayList<File>();
        String testFileName="";
        ArrayList<String> mutFileName=new ArrayList<String>();
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    continue;
                } else {
                    System.out.println("文件:" + file2.getAbsolutePath());
                    if(file2.getName().substring(0,4).equals("mut_")){
                        mutFileName.add(file2.getName());
                    }
                    else if(file2.getName().substring(0,4).equals("ori_")){
                        testFileName=file2.getName().substring(4,file2.getName().length());
                    }
                }
            }
        }
        generateStarterBatFile();
        File logFileDir=new File(ProjectPath+"\\MutationTestLog");
        if(!logFileDir.exists()){//如果文件夹不存在
            logFileDir.mkdir();//创建文件夹
        }
        for(int i=0;i<mutFileName.size();i++){
            ReplaceFileUtil.replaceFile(ProjectPath+"\\contracts\\"+testFileName,ProjectPath+"\\Mutants\\"+mutFileName.get(i));
            //对每个变异体进行truffle test
            generateMutationTest(i);
        }
        //通过log文件判断是否结束测试
        boolean isEnd=false;
        while(!isEnd){
            if(isLogEnd(mutFileName.size()))isEnd=true;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ReplaceFileUtil.replaceFile(ProjectPath+"\\contracts\\"+testFileName,ProjectPath+"\\Mutants\\ori_"+testFileName);
        System.out.println("End Test");
        //分析log返回结果
        MutationTestResult res=TestResultAnalysis.getTestresult(ProjectPath);
        //删除log文件夹
        return res;
    }
    public static void main(String[] args) {
        start("");
    }
    private static boolean isLogEnd(int logNum){
        File file = new File(ProjectPath + "\\MutationTestLog");
        File[] files = file.listFiles();
        if(files.length==logNum*2)return true;
        return false;
    }
    public static void generateStarterBatFile(){
        File file = new File(ProjectPath + "\\StartMutationTest.bat");
        String content="";
        content+="cd /d "+ProjectPath+"\r\n";
        content+="truffle test";
        FileWriter writer= null;
        try {
            writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void generateMutationTest(int num){
        try {
            File filePath = new File(ProjectPath + "\\StartMutationTest.bat");
            Process proc = Runtime.getRuntime().exec(filePath.toString());
            CMDStreamManage errorStream = new CMDStreamManage(proc.getErrorStream(), "Error",ProjectPath,num);
            CMDStreamManage outputStream  = new CMDStreamManage(proc.getInputStream(), "Output",ProjectPath,num);
            errorStream.start();
            outputStream.start();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("Start Mutation Test");

    }

}
