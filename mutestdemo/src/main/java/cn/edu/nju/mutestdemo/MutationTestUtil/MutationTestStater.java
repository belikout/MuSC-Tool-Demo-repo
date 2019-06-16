package cn.edu.nju.mutestdemo.MutationTestUtil;

import cn.edu.nju.mutestdemo.Controller.MutationTestController;
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
                    if(MutationTestController.isTesting) {
                        generateMutationTest(index, tMutants.get(i).mutateLineType.get(j));
                        index += 1;
                    }else{
                        return null;
                    }
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
                    if(MutationTestController.isTesting) {
                        generateMutationTest(index, eMutants.get(i).mutateLineType.get(j));
                        index += 1;
                    }else{
                        return null;
                    }
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
        return res;
    }
    public static void main(String[] args) {
        start("","");
    }
    private static boolean isLogEnd(int logNum){
        File file = new File(ProjectPath + "\\MuSC_MutationTestLog");
        File[] files = file.listFiles();
        //if(files.length==logNum*2)return true;
        if(files.length==logNum)return true;
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
            runTest(proc.getInputStream(),num,type);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("Start Mutation Test");

    }
    public static void runTest(InputStream inputStream,int num,String muType){
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = null;
        String DebugInfo="";
        try {
            while((line = bufferedReader.readLine()) !=null ) {
                    //System.out.println(line);
                    DebugInfo+=line+"\r\n";
            }
            FileWriter writer=new FileWriter(new File(ProjectPath+"\\MuSC_MutationTestLog\\MutationTestDebugInfo_"+num+"_"+muType+".txt"));
            writer.write(DebugInfo);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
