package cn.edu.nju.mutestdemo.FileUtils;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class CMDStreamManage extends Thread {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    InputStream inputStream;
    String type;
    String ProjectPath;
    String muType;
    int num;
    public CMDStreamManage(InputStream inputStream, String type, String ProjectPath, int num,String muType) {
        this.inputStream = inputStream;
        this.type = type;
        this.ProjectPath=ProjectPath;
        this.num=num;
        this.muType=muType;
    }
    public void run () {

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = null;
        String ErrorInfo="";
        String DebugInfo="";
        try {
            while((line = bufferedReader.readLine()) !=null ) {
                if(type.equals("Error")) {
                    //logger.error(line);
                    ErrorInfo+=line+"\r\n";
                }else {
                    logger.debug(line);
                    DebugInfo+=line+"\r\n";
                }
            }
            FileWriter writer=new FileWriter(new File(ProjectPath+"\\MuSC_MutationTestLog\\MutationTestErrorInfo_"+num+"_"+muType+".txt"));
            writer.write(ErrorInfo);
            writer.close();
            writer=new FileWriter(new File(ProjectPath+"\\MuSC_MutationTestLog\\MutationTestDebugInfo_"+num+"_"+muType+".txt"));
            writer.write(DebugInfo);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}