package cn.edu.nju.mutestdemo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;

@org.springframework.stereotype.Controller
public class MainController {
    @RequestMapping("/musc")
    public String musc(){
        return "main";
    }
    public static void main(String args[]){
        File directory = new File("");//设定为当前文件夹
        try{
            System.out.println(directory.getCanonicalPath()+"\\src\\main\\resources\\static");//获取标准的路径
            System.out.println(directory.getAbsolutePath());//获取绝对路径
        }catch(IOException e){}
    }
}
