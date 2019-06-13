package cn.edu.nju.mutestdemo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class MainController {
    @RequestMapping("/musc")
    public String musc(){
        return "main";
    }
}
