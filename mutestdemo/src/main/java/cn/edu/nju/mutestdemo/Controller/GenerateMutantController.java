package cn.edu.nju.mutestdemo.Controller;

import cn.edu.nju.mutestdemo.ASTMutation.Mutant;
import cn.edu.nju.mutestdemo.EnumType.MuType;
import cn.edu.nju.mutestdemo.FileUtils.CopyDir;
import cn.edu.nju.mutestdemo.Model.MutantsJSON;
import cn.edu.nju.mutestdemo.Sol2AST.GenASTServiceClient;
import cn.edu.nju.mutestdemo.Model.SourceUnit;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;
@RequestMapping
@Controller
public class GenerateMutantController {
    static String ProjectPath="C:\\Users\\belikout\\Desktop\\metacoin-box-master";
    @RequestMapping("/generateMutant")
    @ResponseBody
    public String generateMutant(@RequestParam("contracts") String contracts, @RequestParam("types") String types,@RequestParam("names") String names) throws IOException {

        File fileDir=new File(ProjectPath+"\\Mutants");
        if(!fileDir.exists()){//如果文件夹不存在
            fileDir.mkdir();//创建文件夹
        }
        JSONArray conArr=JSONArray.parseArray(contracts);
        String con=conArr.getString(0);
        JSONArray conNameArr=JSONArray.parseArray(names);
        String conName=conNameArr.getString(0);
        FileWriter writer=new FileWriter(new File(fileDir+"\\ori_"+conName));
        writer.write(con);
        writer.close();

        JSONArray typeArr=JSONArray.parseArray(types);
        ArrayList<MuType>typesList=new ArrayList<MuType>();
        for(int i=0;i<typeArr.size();i++) {
            MuType mutype = MuType.valueOf(typeArr.getString(i));
            typesList.add(mutype);
        }
        //后面写处理多个文件
        String json= GenASTServiceClient.genAST(fileDir+"\\ori_"+conName);
        SourceUnit su= JSON.parseObject(json, SourceUnit.class);
        ArrayList<MuType>type=new ArrayList<MuType>();
        type.add(MuType.AOR);
        type.add(MuType.ASR);
        su.addToMutant(type);
        System.out.println(Mutant.lines.size());
        Mutant.Repair();
        writer=new FileWriter(new File(fileDir+"\\ori_"+conName));
        String content="";
        for(int i=0;i<Mutant.lines.size();i++)
            content+=Mutant.lines.get(i).getContent()+"\n";
        writer.write(content);
        writer.close();
        for(int i=0;i<Mutant.mutateLine.size();i++){
            writer=new FileWriter(new File(fileDir+"\\mut_"+MuType.class.getEnumConstants()[Mutant.mutateLineTypeNums.get(i)]+"_"+Mutant.mutateLineNums.get(i)+"_"+conName));
            content="";
            int muLine=Mutant.mutateLineNums.get(i);
            for(int j=0;j<muLine;j++)
                content+=Mutant.lines.get(j).getContent()+"\n";
            content+=Mutant.mutateLine.get(i)+"\n";
            if(muLine<Mutant.lines.size()-1)
            for(int j=muLine+1;j<Mutant.lines.size();j++)
                content+=Mutant.lines.get(j).getContent()+"\n";
            writer.write(content);
            writer.close();
        }
        JSONArray res=new JSONArray();
        JSONArray oriLine=new JSONArray();
        for(int i=0;i<Mutant.lines.size();i++)
            oriLine.add(Mutant.lines.get(i).getContent());
        JSONArray mutLine=new JSONArray();
        for(int i=0;i<Mutant.mutateLine.size();i++)
            mutLine.add(Mutant.mutateLine.get(i));
        JSONArray mutLineNum=new JSONArray();
        for(int i=0;i<Mutant.mutateLineNums.size();i++)
            mutLineNum.add(Mutant.mutateLineNums.get(i));
        JSONArray mutLinetype=new JSONArray();
        for(int i=0;i<Mutant.mutateLineTypeNums.size();i++)
            mutLinetype.add(MuType.class.getEnumConstants()[Mutant.mutateLineTypeNums.get(i)]);
        res.add(oriLine);
        res.add(mutLine);
        res.add(mutLineNum);
        res.add(mutLinetype);

        return res.toJSONString();
    }
    @RequestMapping("/generateMutant0")
    @ResponseBody
    public static String generateMutant0(@RequestParam("projectPath") String path,@RequestParam("contracts") String contracts, @RequestParam("types") String types){
        CopyDir.makeMutationDir(path);

        JSONArray conArrJson=JSONArray.parseArray(contracts);
        ArrayList<String>conArr=new ArrayList<String>();
        for(int i=0;i<conArrJson.size();i++)
            conArr.add(conArrJson.getString(i));

        JSONArray typeArr=JSONArray.parseArray(types);
        ArrayList<MuType>typesArr=new ArrayList<MuType>();
        for(int i=0;i<typeArr.size();i++) {
            MuType mutype = MuType.valueOf(typeArr.getString(i));
            typesArr.add(mutype);
        }
        ArrayList<MutantsJSON>res=new ArrayList<MutantsJSON>();
        File file = new File(path + "\\MuSC_dup\\contracts");
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                //如果该文件需变异则开始变异体生成
                if(conArr.contains(file2.getName())){
                    try {
                        String content="";
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(file2));// 读取文件
                            String line  = null;
                            while ((line = br.readLine()) != null) {
                                content+=line;
                            }
                            br.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        MutantsJSON temp=genMutant(path+"\\MuSC_dup",content,file2.getName(),new ArrayList<MuType>());
                        if(temp.mutateLine.size()>0)
                            res.add(temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return JSON.toJSONString(res);
    }
    public static MutantsJSON genMutant(String projectPath,String contract,String name,ArrayList<MuType>types) throws IOException {
        File fileDir=new File(projectPath+"\\Mutants");
        if(!fileDir.exists()){//如果文件夹不存在
            fileDir.mkdir();//创建文件夹
        }

        FileWriter writer=new FileWriter(new File(fileDir+"\\ori_"+name));
        writer.write(contract);
        writer.close();
        //处理一下type

        //后面写处理多个文件
        String json= GenASTServiceClient.genAST(fileDir+"\\ori_"+name);
        SourceUnit su= JSON.parseObject(json, SourceUnit.class);
        ArrayList<MuType>type=new ArrayList<MuType>();
        type.add(MuType.AOR);
        type.add(MuType.ASR);
        su.addToMutant(type);
        System.out.println(Mutant.lines.size());
        Mutant.Repair();
        writer=new FileWriter(new File(fileDir+"\\ori_"+name));
        String content="";
        for(int i=0;i<Mutant.lines.size();i++)
            content+=Mutant.lines.get(i).getContent()+"\n";
        writer.write(content);
        writer.close();
        for(int i=0;i<Mutant.mutateLine.size();i++){
            writer=new FileWriter(new File(fileDir+"\\mut_"+MuType.class.getEnumConstants()[Mutant.mutateLineTypeNums.get(i)]+"_"+Mutant.mutateLineNums.get(i)+"_"+name));
            content="";
            int muLine=Mutant.mutateLineNums.get(i);
            for(int j=0;j<muLine;j++)
                content+=Mutant.lines.get(j).getContent()+"\n";
            content+=Mutant.mutateLine.get(i)+"\n";
            if(muLine<Mutant.lines.size()-1)
                for(int j=muLine+1;j<Mutant.lines.size();j++)
                    content+=Mutant.lines.get(j).getContent()+"\n";
            writer.write(content);
            writer.close();
        }
        MutantsJSON res=new MutantsJSON();
        res.conName=name;
        for(int i=0;i<Mutant.lines.size();i++)
            res.oriLine.add(Mutant.lines.get(i).getContent());
        for(int i=0;i<Mutant.mutateLine.size();i++)
            res.mutateLine.add(Mutant.mutateLine.get(i));
        for(int i=0;i<Mutant.mutateLineNums.size();i++)
            res.mutateLineNums.add(Mutant.mutateLineNums.get(i));
        for(int i=0;i<Mutant.mutateLineTypeNums.size();i++)
            res.mutateLineType.add(MuType.class.getEnumConstants()[Mutant.mutateLineTypeNums.get(i)].toString());

        return res;
    }
    public static void main(String[]args){
        System.out.println(generateMutant0("C:\\Users\\belikout\\Desktop\\metacoin-box-master","[\"MetaCoin.sol\"]","[]"));

    }
}
