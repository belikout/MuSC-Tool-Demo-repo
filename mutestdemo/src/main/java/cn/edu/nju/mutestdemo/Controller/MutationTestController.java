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
    public String generateMutationTest() throws IOException {
        MutationTestResult res=MutationTestStater.start("");
        ArrayList<Integer>resList=new ArrayList<Integer>();
        resList.add(res.getTkill());
        resList.add(res.gettLive());
        resList.add(res.gettTotal());
        resList.add(res.gettScore());
        resList.add(res.getEkill());
        resList.add(res.geteLive());
        resList.add(res.geteTotal());
        resList.add(res.geteScore());
        return JSON.toJSONString(resList);
    }
}
