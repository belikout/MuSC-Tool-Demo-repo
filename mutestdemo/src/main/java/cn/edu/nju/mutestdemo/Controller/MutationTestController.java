package cn.edu.nju.mutestdemo.Controller;

import cn.edu.nju.mutestdemo.FileUtils.CopyDir;
import cn.edu.nju.mutestdemo.FileUtils.ReplaceFileUtil;
import cn.edu.nju.mutestdemo.Model.MutationTestResult;
import cn.edu.nju.mutestdemo.MutationTestUtil.MutationTestStater;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

@RequestMapping
@Controller
public class MutationTestController {
    public static boolean isTesting=false;
    @RequestMapping("/generateMutationTest")
    @ResponseBody
    public String generateMutationTest(@RequestParam("chainCode")String chainCode,@RequestParam("projectPath")String projectPath,@RequestParam("mutants")String mutantsJSON) throws IOException {
        boolean istestFail=false;
        boolean istestfinish=false;
        Process proc=null;
        if(chainCode!=null&&chainCode!=""){
            generateStartChainFile(projectPath,chainCode);
            /*String cmd = "cmd /k start "+projectPath+"\\MuSC_dup\\MuSC_StartTestChain.bat";
            try {
                Process ps = Runtime.getRuntime().exec(cmd);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            try {
                File filePath = new File(projectPath + "\\MuSC_dup\\MuSC_StartTestChain.bat");
                proc = Runtime.getRuntime().exec(filePath.toString());
                InputStreamReader inputStreamReader = new InputStreamReader(proc.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = null;
                try {
                    while((line = bufferedReader.readLine()) !=null ) {
                        System.out.println(line);
                        if(line.contains("Listening on")) {
                            istestfinish=true;
                            break;
                        }
                        else if(line.contains("Error")){
                            istestFail=true;
                            break;
                        }
                    }
                    System.out.println("finish");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            if(istestFail||!istestfinish)
                return JSON.toJSONString("Fail: Start Test Chain Fail! Please check command or check if there is already running a test chain on the port!");
        }
        isTesting=true;
        ArrayList<MutationTestResult> res=MutationTestStater.start(projectPath,mutantsJSON);
        if(proc!=null)
            proc.destroy();
        if(res!=null) {
            isTesting = false;
            return JSON.toJSONString(res);
        }
        else
            return JSON.toJSONString("Fail: Tests have been interrupted!");
    }
    public void generateStartChainFile(String projectPath,String chainCode){
        File file = new File(projectPath + "\\MuSC_dup\\MuSC_StartTestChain.bat");
        String content="";
        content+="cd /d %~dp0\r\n";
        content+=chainCode;//+" > "+"E:\\blockchain\\MutationTest\\TC\\AirSwap\\test.txt";
        FileWriter writer= null;
        try {
            writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/getProgress")
    @ResponseBody
    public int getProgress(@RequestParam("projectPath")String projectPath) throws IOException {
        File logFileDir=new File(projectPath+"\\MuSC_dup\\MuSC_MutationTestLog");
        if(logFileDir.exists()){//如果文件夹存在
            File[] files = logFileDir.listFiles();
            System.out.println(files.length);
            return files.length;
        }
        return 0;
    }
    @RequestMapping("/stopTest")
    @ResponseBody
    public void stopTest()  {
        isTesting=false;
    }
    public static void main(String args[]){
        /*File file=new File("C:\\Users\\belikout\\Desktop\\1");
        if (file.exists()) {
            File[] files = file.listFiles();
            Date start=new Date();
            int count=0;
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    continue;
                } else {
                    if(file2.getName().contains("AddressArrayUtilsContract")){
                        ReplaceFileUtil.replaceFile("C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\contracts\\array-utils\\AddressArrayUtilsContract.sol",file2.getPath());
                        runTest(count);
                        ReplaceFileUtil.replaceFile("C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\contracts\\array-utils\\AddressArrayUtilsContract.sol","C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\contracts0\\array-utils\\AddressArrayUtilsContract.sol");
                    }
                    else if(file2.getName().contains("AddressArrayUtils")){
                        ReplaceFileUtil.replaceFile("C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\contracts\\array-utils\\AddressArrayUtils.sol",file2.getPath());
                        runTest(count);
                        ReplaceFileUtil.replaceFile("C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\contracts\\array-utils\\AddressArrayUtils.sol","C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\contracts0\\array-utils\\AddressArrayUtils.sol");
                    }
                    else if(file2.getName().contains("UIntArrayUtils")){
                        ReplaceFileUtil.replaceFile("C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\contracts\\array-utils\\UIntArrayUtils.sol",file2.getPath());
                        runTest(count);
                        ReplaceFileUtil.replaceFile("C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\contracts\\array-utils\\UIntArrayUtils.sol","C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\contracts0\\array-utils\\UIntArrayUtils.sol");
                    }
                    else if(file2.getName().contains("RationalMath")){
                        ReplaceFileUtil.replaceFile("C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\contracts\\rationals\\RationalMath.sol",file2.getPath());
                        runTest(count);
                        ReplaceFileUtil.replaceFile("C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\contracts\\rationals\\RationalMath.sol","C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\contracts0\\rationals\\RationalMath.sol");
                    }
                }
                count++;
            }
            Date end=new Date();
            System.out.print("Test take time:  ");
            System.out.println(end.getTime()-start.getTime());

        }*/
        getTestresult();
    }
    public static void runTest(int count){
        try {
            File filePath = new File( "C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\runTest.bat");
            Process proc = Runtime.getRuntime().exec(filePath.toString());
            InputStreamReader inputStreamReader = new InputStreamReader(proc.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            String DebugInfo="";
            try {
                while((line = bufferedReader.readLine()) !=null ) {
                    //System.out.println(line);
                    DebugInfo+=line+"\r\n";
                }
                FileWriter writer=new FileWriter(new File("C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\result\\MutationTestDebugInfo_"+count+".txt"));
                writer.write(DebugInfo);
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    public static MutationTestResult getTestresult(){
        MutationTestResult res=new MutationTestResult();
        File file = new File("C:\\Users\\belikout\\Desktop\\TC\\cryptofin-solidity\\result");
        ArrayList<String> mutFileName=new ArrayList<String>();
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    continue;
                } else {
                    if(file2.getName().substring(0,17).equals("MutationTestDebug")){
                        String temp=isKill(file2);
                        if(temp.equals("yes"))res.setKill(res.getKill()+1);
                        else if(temp.equals("fail"))res.setCompileFail(res.getCompileFail()+1);
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
        boolean isError=false;
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
                else if(line.contains("Error"))
                    isError=true;

            }
            if(isError)
                return "yes";
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "no";
    }
}
