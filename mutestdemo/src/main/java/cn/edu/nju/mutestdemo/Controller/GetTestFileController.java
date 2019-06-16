package cn.edu.nju.mutestdemo.Controller;

import cn.edu.nju.mutestdemo.FileUtils.CMDStreamManage;
import cn.edu.nju.mutestdemo.FileUtils.CopyDir;
import cn.edu.nju.mutestdemo.MutationTestUtil.MutationTestStater;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@RequestMapping
@Controller
public class GetTestFileController {
    static String ProjectPath="";
    @RequestMapping("getTestProfile")
    @ResponseBody
    public  static String getTestProfile(@RequestParam("path") String projectPath){
        ProjectPath=projectPath;
        MutationTestStater.ProjectPath=projectPath;
        File fileDir=new File(ProjectPath);
        if(!fileDir.exists()){//如果文件夹不存在
            return JSON.toJSONString("Fail: Folder directory doesn't exist.");//返回错误信息
        }else{
            MutationTestStater.generateStarterBatFile();
            File logFileDir=new File(ProjectPath+"\\MuSC_MutationTestLog");
            if(!logFileDir.exists()){//如果文件夹不存在
                logFileDir.mkdir();//创建文件夹
            }
            try {
                File filePath = new File(ProjectPath + "\\MuSC_StartMutationTest.bat");
                Process proc = Runtime.getRuntime().exec(filePath.toString());
                CMDStreamManage errorStream = new CMDStreamManage(proc.getErrorStream(), "Error",ProjectPath,0,"ori");
                CMDStreamManage outputStream  = new CMDStreamManage(proc.getInputStream(), "Output",ProjectPath,0,"ori");
                errorStream.start();
                outputStream.start();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            System.out.println("Start Mutation Test");

            boolean isEnd=false;
            while(!isEnd){
                File file = new File(ProjectPath + "\\MuSC_MutationTestLog");
                File[] files = file.listFiles();
                if(files.length==2)isEnd=true;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String temp=AnalyseResult();
            CopyDir.deleteDir(new File(ProjectPath+"\\MuSC_StartMutationTest.bat"));
            if(!temp.equals(""))return JSON.toJSONString("Fail: "+temp);
            ArrayList<String>cons=getContract();
            CopyDir.deleteDir(new File(ProjectPath+"\\MuSC_MutationTestLog"));
            return JSON.toJSONString(cons);
        }
    }
    private static String AnalyseResult(){
        File file = new File(ProjectPath + "\\MuSC_MutationTestLog\\MutationTestDebugInfo_0_ori.txt");
        if(!file.exists()) {//如果文件夹不存在
            return "Test unknown error, please check your project or change path or try again later.";//返回错误信息
        }else{
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));//
                String line  = null;
                int num=0;
                while ((line = br.readLine()) != null) {
                    num+=1;
                    if(line.contains("Could not find suitable configuration file."))
                        return "Not a truffle test project, please check your project or change path.";
                    if(line.contains(" failing\u001B[0m"))
                        return "Mutation test fail, please check your project.";
                    if(line.contains("Compilation failed."))
                        return "Compilation failed.";
                }
                if(num<=1)
                    return "Test unknown error, please check your project or change path or try again later.";
                br.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }
    private static ArrayList<String> getContract(){
        ArrayList<String> cons=new ArrayList<String>();
        String path=ProjectPath;
        File file = new File(path + "\\contracts");
        while(!file.exists()) {
            if(path.contains("/"))
            path=path.substring(0,path.lastIndexOf("/"));
            else if(path.contains("\\"))
                path=path.substring(0,path.lastIndexOf("\\"));
            file = new File(path + "\\contracts");
        }
        cons.add(path);
        File[] files = file.listFiles();
        for (File file2 : files) {
            String name=file2.getName();
            if(file2.isFile()&&name.length()>4) {
                if (name.substring(name.length() - 4, name.length()).equals(".sol"))
                    cons.add((file2.getName()));
            }
        }
        return cons;
    }
    public static void main(String[]args){

        System.out.println(getTestProfile("C:\\Users\\belikout\\Desktop\\metacoin-box-master"));
    }
}
