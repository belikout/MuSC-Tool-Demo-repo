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
    static ArrayList<String>tOps=new ArrayList<String>();
    static ArrayList<String>eOps=new ArrayList<String>();
    public GenerateMutantController(){
        tOps.add("AOR");
        tOps.add("AOI");
        tOps.add("ROR");
        tOps.add("COR");
        tOps.add("LOR");
        tOps.add("ASR");
        tOps.add("SDL");
        tOps.add("RVR");
        tOps.add("CSC");
        eOps.add("FSC");
        eOps.add("FVC");
        eOps.add("DLR");
        eOps.add("VTR");
        eOps.add("PKD");
        eOps.add("DKD");
        eOps.add("GVC");
        eOps.add("MFR");
        eOps.add("AVR");
        eOps.add("EUR");
        eOps.add("TUR");
        eOps.add("RSD");
        eOps.add("RSC");
        eOps.add("ASD");
        eOps.add("ASC");
    }
    @RequestMapping("/generateMutant")
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
        if(typeArr.contains(MuType.LOR)){
            if(!typeArr.contains(MuType.ROR))typeArr.add(MuType.ROR);
            if(!typeArr.contains(MuType.COR))typeArr.add(MuType.COR);
        }
        ArrayList<MutantsJSON>resTemp=new ArrayList<MutantsJSON>();
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
                                content+=line+"\n";
                            }
                            br.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        MutantsJSON temp=genMutant(path+"\\MuSC_dup",content,file2.getName(),typesArr);
                        if(temp.mutateLine.size()>0)
                            resTemp.add(temp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ArrayList<ArrayList<MutantsJSON>>res=new ArrayList<ArrayList<MutantsJSON>>();
        ArrayList<MutantsJSON>resTraditional=new ArrayList<MutantsJSON>();
        ArrayList<MutantsJSON>resESC=new ArrayList<MutantsJSON>();
        for(int i=0;i<resTemp.size();i++){
            MutantsJSON tTemp=new MutantsJSON();boolean hasT=false;
            MutantsJSON eTemp=new MutantsJSON();boolean hasE=false;
            for(int j=0;j<resTemp.get(i).mutateLineType.size();j++){
                if(tOps.contains(resTemp.get(i).mutateLineType.get(j))) {
                    if(!hasT) {
                        hasT=true;
                        tTemp.conName = resTemp.get(i).conName;
                        tTemp.oriLine = resTemp.get(i).oriLine;
                    }
                    tTemp.mutateLineType.add(resTemp.get(i).mutateLineType.get(j));
                    tTemp.mutateLine.add(resTemp.get(i).mutateLine.get(j));
                    tTemp.mutateLineNums.add(resTemp.get(i).mutateLineNums.get(j));
                }else{
                    if(!hasE) {
                        hasE=true;
                        eTemp.conName = resTemp.get(i).conName;
                        eTemp.oriLine = resTemp.get(i).oriLine;
                    }
                    eTemp.mutateLineType.add(resTemp.get(i).mutateLineType.get(j));
                    eTemp.mutateLine.add(resTemp.get(i).mutateLine.get(j));
                    eTemp.mutateLineNums.add(resTemp.get(i).mutateLineNums.get(j));
                }
            }
            if(hasT)
                resTraditional.add(tTemp);
            if(hasE)
                resESC.add(eTemp);
        }
        res.add(resTemp);
        res.add(resTraditional);
        res.add(resESC);
        return JSON.toJSONString(res);
    }
    public static MutantsJSON genMutant(String projectPath,String contract,String name,ArrayList<MuType>types) throws IOException {
        Mutant.clear();
        File fileDir=new File(projectPath+"\\Mutants");
        if(!fileDir.exists()){//如果文件夹不存在
            fileDir.mkdir();//创建文件夹
        }

        FileWriter writer=new FileWriter(new File(fileDir+"\\ori_"+name));
        writer.write(contract);
        writer.close();
        //后面写处理多个文件
        String json= GenASTServiceClient.genAST(fileDir+"\\ori_"+name);
        SourceUnit su= JSON.parseObject(json, SourceUnit.class);
        su.addToMutant(types);
        su.output();
        System.out.println(Mutant.lines.size());
        Mutant.Repair();
        writer=new FileWriter(new File(fileDir+"\\ori_"+name));
        String content="";
        for(int i=0;i<Mutant.lines.size();i++)
            content+=Mutant.lines.get(i).getContent()+"\n";
        writer.write(content);
        writer.close();
        writer=new FileWriter(new File(fileDir+"\\mut_"+name));
        writer.write("");
        for(int i=0;i<Mutant.mutateLine.size();i++){
            content="";
            content+=Mutant.mutateLineNums.get(i)+" "+MuType.class.getEnumConstants()[Mutant.mutateLineTypeNums.get(i)]+"\n";
            content+=Mutant.mutateLine.get(i)+"\n";
            writer.append(content);
        }
        writer.close();
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
        System.out.println(generateMutant0("C:\\Users\\belikout\\Desktop\\all_object\\cryptofin-solidity","[\"array-utils/AddressArrayUtils.sol\"]","[\"ASR\",\"AOR\",\"COR\",\"ROR\",\"FSC\",\"FVC\",\"PKD\",\"EUR\",\"RSD\",\"RSC\"]"));

    }
}
