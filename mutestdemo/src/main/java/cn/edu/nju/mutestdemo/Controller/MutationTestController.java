package cn.edu.nju.mutestdemo.Controller;

import cn.edu.nju.mutestdemo.Model.MutationTestResult;
import cn.edu.nju.mutestdemo.MutationTestUtil.MutationTestStater;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;

@RequestMapping
@Controller
public class MutationTestController {
    @RequestMapping("/generateMutationTest")
    @ResponseBody
    public String generateMutationTest(@RequestParam("projectPath")String projctPath,@RequestParam("mutants")String mutantsJSON) throws IOException {
        ArrayList<MutationTestResult> res=MutationTestStater.start0(projctPath,mutantsJSON);
        return JSON.toJSONString(res);
    }
}
