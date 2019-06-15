package cn.edu.nju.mutestdemo.MutationTestUtil;

import cn.edu.nju.mutestdemo.FileUtils.CopyDir;
import cn.edu.nju.mutestdemo.FileUtils.ReplaceFileUtil;
import cn.edu.nju.mutestdemo.FileUtils.CMDStreamManage;
import cn.edu.nju.mutestdemo.Model.MutantsJSON;
import cn.edu.nju.mutestdemo.Model.MutationTestResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.*;
import java.util.ArrayList;

public class MutationTestStater {
    public static String ProjectPath="";
    private static ArrayList<MutantsJSON> tMutants=new ArrayList<MutantsJSON>();
    private static ArrayList<MutantsJSON> eMutants=new ArrayList<MutantsJSON>();
    public static ArrayList<MutationTestResult> start(String path,String mutantsJSON) {
        if(path!=null&&path!="")
        ProjectPath=path+"\\MuSC_dup";
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
        File logFileDir=new File(ProjectPath+"\\MuSC_MutationTestLog");
        if(!logFileDir.exists()){//如果文件夹不存在
            logFileDir.mkdir();//创建文件夹
        }
        else{
            CopyDir.deleteDir(logFileDir);
            logFileDir.mkdir();//创建文件夹
        }
        for(int i=0;i<mutFileName.size();i++){

            ReplaceFileUtil.replaceFile(ProjectPath+"\\contracts\\"+testFileName,ProjectPath+"\\Mutants\\"+mutFileName.get(i));
            //对每个变异体进行truffle test
            generateMutationTest(i,"AOR");
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
        return null;
        //return res;
    }
    public static ArrayList<MutationTestResult> start0(String path,String mutantsJSON) {
        ProjectPath="";
        tMutants=new ArrayList<MutantsJSON>();
        eMutants=new ArrayList<MutantsJSON>();
        if(path!=null&&path!="")
            ProjectPath=path+"\\MuSC_dup";
        else
            return null;
        generateStarterBatFile();
        File logFileDir=new File(ProjectPath+"\\MuSC_MutationTestLog");
        if(logFileDir.exists()){//如果文件夹存在
            CopyDir.deleteDir(logFileDir);
        }
        //处理传过来的变异体JSON串
        processMutantsJSON(mutantsJSON);
        ArrayList<MutationTestResult>res=new ArrayList<MutationTestResult>();
        if(tMutants.size()>0){
            logFileDir.mkdir();//创建文件夹
            int count=0;
            for(int i=0;i<tMutants.size();i++){
                count+=tMutants.get(i).mutateLine.size();
            }
            int index=0;
            for(int i=0;i<tMutants.size();i++){
                for(int j=0;j<tMutants.get(i).mutateLine.size();j++){
                    ReplaceFileUtil.replaceFileContent(ProjectPath+"\\contracts\\"+tMutants.get(i).conName,getMutantString(tMutants.get(i).mutateLine.get(j),tMutants.get(i).mutateLineNums.get(j),tMutants.get(i).oriLine));
                    generateMutationTest(index,tMutants.get(i).mutateLineType.get(j));
                    index+=1;
                }
            }
            boolean isEnd=false;
            while(!isEnd){
                if(isLogEnd(count))isEnd=true;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            MutationTestResult tRes=TestResultAnalysis.getTestresult(ProjectPath);
            res.add(tRes);
            CopyDir.deleteDir(logFileDir);
        }else{
            res.add(new MutationTestResult());
        }
        if(eMutants.size()>0){
            logFileDir.mkdir();//创建文件夹
            int count=0;
            for(int i=0;i<eMutants.size();i++){
                count+=eMutants.get(i).mutateLine.size();
            }
            int index=0;
            for(int i=0;i<eMutants.size();i++){
                for(int j=0;j<eMutants.get(i).mutateLine.size();j++){
                    ReplaceFileUtil.replaceFileContent(ProjectPath+"\\contracts\\"+eMutants.get(i).conName,getMutantString(eMutants.get(i).mutateLine.get(j),eMutants.get(i).mutateLineNums.get(j),eMutants.get(i).oriLine));
                    generateMutationTest(index,eMutants.get(i).mutateLineType.get(j));
                    index+=1;
                }
            }
            boolean isEnd=false;
            while(!isEnd){
                if(isLogEnd(count))isEnd=true;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            MutationTestResult eRes=TestResultAnalysis.getTestresult(ProjectPath);
            res.add(eRes);
            CopyDir.deleteDir(logFileDir);
        }else{
            res.add(new MutationTestResult());
        }
        //删除log文件夹
        CopyDir.deleteDir(logFileDir);
        return res;
    }
    public static void main(String[] args) {
        start("","");
    }
    private static boolean isLogEnd(int logNum){
        File file = new File(ProjectPath + "\\MuSC_MutationTestLog");
        File[] files = file.listFiles();
        if(files.length==logNum*2)return true;
        return false;
    }
    private static String getMutantString(String mutantLine,int mutantLineNum,ArrayList<String>oriLine){
        String content="";
        for(int i=0;i<oriLine.size();i++){
            if(i!=mutantLineNum)
                content+=oriLine.get(i)+"\n";
            else
                content+=mutantLine+"\n";
        }
        return content;
    }
    public static void generateStarterBatFile(){
        File file = new File(ProjectPath + "\\MuSC_StartMutationTest.bat");
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
    public static void processMutantsJSON(String mutantsJSON){
        JSONArray mutantsArray= JSON.parseArray(mutantsJSON);
        JSONArray tMutantsArray=(JSONArray)mutantsArray.get(0);
        JSONArray eMutantsArray=(JSONArray)mutantsArray.get(1);
        if(tMutantsArray.size()>0){
            for(int i=0;i<tMutantsArray.size();i++) {
                MutantsJSON temp = JSON.parseObject(tMutantsArray.get(i).toString(), MutantsJSON.class);
                tMutants.add(temp);
            }
        }
        if(eMutantsArray.size()>0){
            for(int i=0;i<eMutantsArray.size();i++) {
                MutantsJSON temp = JSON.parseObject(eMutantsArray.get(i).toString(), MutantsJSON.class);
                eMutants.add(temp);
            }
        }
    }
    public static void generateMutationTest(int num,String type){
        try {
            File filePath = new File(ProjectPath + "\\MuSC_StartMutationTest.bat");
            Process proc = Runtime.getRuntime().exec(filePath.toString());
            CMDStreamManage errorStream = new CMDStreamManage(proc.getErrorStream(), "Error",ProjectPath,num,type);
            CMDStreamManage outputStream  = new CMDStreamManage(proc.getInputStream(), "Output",ProjectPath,num,type);
            errorStream.start();
            outputStream.start();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("Start Mutation Test");

    }

}
