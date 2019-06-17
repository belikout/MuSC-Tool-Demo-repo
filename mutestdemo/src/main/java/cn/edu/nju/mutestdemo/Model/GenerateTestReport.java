package cn.edu.nju.mutestdemo.Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.temporal.Temporal;
import java.util.ArrayList;

/**
 * @创建人 徐介晖
 * @创建时间 2019/6/17
 * @描述
 */
public class GenerateTestReport {
    public static void main(String args[]){
        TestReport tr=new TestReport();
        tr.conName="Exchange.sol";
         tr.tTotal=8;
         tr.tkill=6;
         tr.compileFail=0;
         tr.Total=8;
         tr.kill=6;
         ArrayList<TestReport> list=new ArrayList<>();
         list.add(tr);
         tr=new TestReport();
         tr.conName="AirSwapToken.sol";
         tr.tkill=1;
         tr.tTotal=3;
         tr.Total=3;
         tr.kill=1;
         list.add(tr);
         GenerateTestReport g=new GenerateTestReport();
         g.generateIndex(list);
    }

    public void generateCell(TestReport tr){
        int mts=100;
        int ekr=100;
        int tkr=100;
        int cp=100;
        if(tr.Total!=0){
            mts=(int)(((double)(tr.kill))/((double)(tr.Total))*100);
            cp=(int)(((double)(tr.Total-tr.compileFail))/((double)(tr.Total))*100);
        }
        if(tr.eTotal!=0){
            ekr=(int)(((double)(tr.ekill))/((double)(tr.eTotal))*100);
        }
        if(tr.tTotal!=0){
            tkr=(int)(((double)(tr.tkill))/((double)(tr.tTotal))*100);
        }
        String fileName=tr.conName+".html";
        File f=new File(fileName);
        try {
            BufferedWriter bw=new BufferedWriter(new FileWriter(f));
            bw.append("<!doctype html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <title>Code coverage report for contracts\\BasicToken.sol</title>\n" +
                    "    <meta charset=\"utf-8\" />\n" +
                    "    <link rel=\"stylesheet\" href=\"../prettify.css\" />\n" +
                    "    <link rel=\"stylesheet\" href=\"../base.css\" />\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "    <style type='text/css'>\n" +
                    "        .coverage-summary .sorter {\n" +
                    "            background-image: url(../sort-arrow-sprite.png);\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div class='wrapper'>\n" +
                    "  <div class='pad1'>\n" +
                    "    <h1>\n" +
                    "      <a href=\"../index.html\">All contracts </a>/ Exchange.sol\n" +
                    "    </h1>\n" +
                    "    <div class=\"clearfix\">\n" +
                    "        <div class=\"fl pad1y space-right2\">\n" +
                    "          <span class=\"strong\">"+mts+"% </span>\n" +
                    "          <span class=\"quiet\">Mutation Test Score</span>\n" +
                    "          <span class=\"fraction\">"+tr.kill+"/"+tr.Total+"</span>\n" +
                    "        </div>\n" +
                    "        <div class=\"fl pad1y space-right2\">\n" +
                    "          <span class=\"strong\">"+tkr+"% </span>\n" +
                    "          <span class=\"quiet\">Traditional Mutants Killed Rate</span>\n" +
                    "          <span class=\"fraction\">"+tr.tkill+"/"+tr.tTotal+"</span>\n" +
                    "        </div>\n" +
                    "        <div class=\"fl pad1y space-right2\">\n" +
                    "          <span class=\"strong\">"+ekr+"% </span>\n" +
                    "          <span class=\"quiet\">ESC Mutants Killed Rate</span>\n" +
                    "          <span class=\"fraction\">"+tr.ekill+"/"+tr.eTotal+"</span>\n" +
                    "        </div>\n" +
                    "        <div class=\"fl pad1y space-right2\">\n" +
                    "          <span class=\"strong\">"+cp+"% </span>\n" +
                    "          <span class=\"quiet\">Compile Passed Rate</span>\n" +
                    "          <span class=\"fraction\">"+(tr.Total-tr.compileFail)+"/"+tr.Total+"</span>\n" +
                    "        </div>\n" +
                    "      </div>\n" +
                    "  </div>\n" +
                    "  <div class='status-line high'></div>\n" +
                    "  <div class='mutantsView'>\n" +
                    "    <div class=\"pad1\">\n" +
                    "        <table class=\"coverage-summary\">\n" +
                    "        <thead>\n" +
                    "        <tr>\n" +
                    "            <th data-col=\"file\" data-html=\"true\" class=\"file sorted\" style=\"\n" +
                    "            width: 100px;\n" +
                    "        \">Mutant No</th>\n" +
                    "        <th data-col=\"file\" data-html=\"true\" class=\"file sorted\" style=\"\n" +
                    "        width: 100px;\n" +
                    "        \">Mutant Status</th>\n" +
                    "            <th data-col=\"file\" data-html=\"true\" class=\"file sorted\" style=\"\n" +
                    "            width: 100px;\n" +
                    "        \">Mutant Type</th>      \n" +
                    "            <th data-col=\"file\"  data-html=\"true\" class=\"file sorted\" style=\"\n" +
                    "            width: 100px;\n" +
                    "        \">Line Num</th>\n" +
                    "            <th data-col=\"file\"  data-html=\"true\" class=\"file sorted\" style=\"\n" +
                    "            width: 400px;\n" +
                    "        \">Original Line</th>\n" +
                    "            <th data-col=\"file\" data-html=\"true\" class=\"file sorted\" style=\"\n" +
                    "            width: 400px;\n" +
                    "        \">Mutant Line</th>\n" +
                    "        </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody>");
                  for(int i=0;i<tr.mutateLine.size();i++){
                      bw.append("       <tr>\n" +
                              "            <td class=\"file high\" data-value=\"1\">"+(i+1)+"</td>\n" +
                              "            <td data-value=\"83.33\" class=\"file high\">"+tr.isKilled.get(i)+"</td>\n" +
                              "            <td data-value=\"83.33\" class=\"file high\">"+tr.mutateLineType.get(i)+"</td>\n" +
                              "            <td data-value=\"50\" class=\"file high\">"+tr.mutateLineNums.get(i)+"</td>\n" +
                              "            <td data-value=\"100\" class=\"file high\">"+tr.oriLine.get(tr.mutateLineNums.get(i))+"</td>\n" +
                              "            <td data-value=\"85.71\" class=\"file high\">"+tr.mutateLine.get(i)+"</td>\n" +
                              "            </tr>");
                  }
                  bw.append("</tbody>\n" +
                          "        </table>\n" +
                          "        </div>\n" +
                          "  </div>\n" +
                          "<pre>\n" +
                          "  <h2 style=\"margin-left: 10px;margin-bottom: -20px\">Original Solidity Code</h2>\n" +
                          "  <table class=\"coverage\">\n" +
                          "<tr><td class=\"line-count quiet\">");
                  for(int i=1;i<=tr.oriLine.size();i++){
                      bw.append(i+"");
                  }
                  bw.append("</td>\n" +
                          "<td class=\"line-coverage quiet\"></td>\n" +
                          "<td class=\"text\" style=\"width: 100%;float: left;overflow: auto;\"><pre class=\"prettyprint lang-js\">");

            for(int i=0;i<tr.oriLine.size();i++){
                bw.append(tr.oriLine.get(i));
            }
            bw.append("&nbsp;</pre></td></tr>\n" +
                    "</table></pre>\n" +
                    "<div class='push'></div><!-- for sticky footer -->\n" +
                    "</div><!-- /wrapper -->\n" +
                    "<div class=\"footer quiet pad2 space-top1 center small\">\n" +
                    "        Mutation Test Report\n" +
                    "        generated by <a href=\"https://github.com/belikout/MuSC-Tool-Demo-repo\" >MuSC Tool Demo</a>\n" +
                    "      </div>\n" +
                    "<script src=\"../prettify.js\"></script>\n" +
                    "<script>\n" +
                    "window.onload = function () {\n" +
                    "        if (typeof prettyPrint === 'function') {\n" +
                    "            prettyPrint();\n" +
                    "        }\n" +
                    "};\n" +
                    "</script>\n" +
                    "<script src=\"../sorter.js\"></script>\n" +
                    "</body>\n" +
                    "</html>");
           bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void generateIndex(ArrayList<TestReport> list){

        int allTotal=getAllTotal(list);
        int alleTotal=getAlleTotal(list);
        int alltTotal=getAlltTotal(list);
        int allKill=getAllKill(list);
        int alltKill=getAlltKill(list);
        int alleKill=getAlleKill(list);
        int allCompileFail=getAllCompileFail(list);
        int alleCompileFail=getAlleCompileFail(list);
        int alltCompileFail=getAlleCompileFail(list);
        int MTS=100;
        int TKR=100;
        int EKR=100;
        int PF=100;
        if(allTotal!=0){
            MTS=(int)(((double)(allKill))/((double)(allTotal))*100);
            PF=(int)(((double)(allTotal-allCompileFail))/((double)(allTotal))*100);
        }
        if(alleTotal!=0){
            EKR=(int)(((double)(alleKill))/((double)(alleTotal))*100);
        }
        if(alltTotal!=0){
            TKR=(int)(((double)(alltKill))/((double)(alltTotal))*100);
        }

        File f=new File("report.html");
        try {
            if(!f.exists()){

                f.createNewFile();
            }
            BufferedWriter bw=new BufferedWriter(new FileWriter(f));
            bw.append("<!doctype html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <title>Code coverage report for All files</title>\n" +
                    "    <meta charset=\"utf-8\" />\n" +
                    "    <link rel=\"stylesheet\" href=\"prettify.css\" />\n" +
                    "    <link rel=\"stylesheet\" href=\"base.css\" />\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "    <style type='text/css'>\n" +
                    "        .coverage-summary .sorter {\n" +
                    "            background-image: url(sort-arrow-sprite.png);\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"wrapper\">\n" +
                    "        <h1 style=\"margin: 10px;\">MuSC Test Report</h1>\n" +
                    "      <div class=\"pad1\">\n" +
                    "        \n" +
                    "        <div class=\"clearfix\">\n" +
                    "          <div class=\"fl pad1y space-right2\">\n" +
                    "            <span class=\"strong\" >"+MTS+"% </span>\n" +
                    "            <span class=\"quiet\">Mutation Test Score</span>\n" +
                    "            <span class=\"fraction\">"+allKill+"/"+allTotal+"</span>\n" +
                    "          </div>\n" +
                    "          <div class=\"fl pad1y space-right2\">\n" +
                    "            <span class=\"strong\">"+TKR+"% </span>\n" +
                    "            <span class=\"quiet\">Traditional Mutants Killed Rate</span>\n" +
                    "            <span class=\"fraction\">"+alltKill+"/"+alltTotal+"</span>\n" +
                    "          </div>\n" +
                    "          <div class=\"fl pad1y space-right2\">\n" +
                    "            <span class=\"strong\">"+EKR+"% </span>\n" +
                    "            <span class=\"quiet\">ESC Mutants Killed Rate</span>\n" +
                    "            <span class=\"fraction\">"+alleKill+"/"+alleTotal+"</span>\n" +
                    "          </div>\n" +
                    "          <div class=\"fl pad1y space-right2\">\n" +
                    "            <span class=\"strong\">"+PF+"% </span>\n" +
                    "            <span class=\"quiet\">Compile Passed Rate</span>\n" +
                    "            <span class=\"fraction\">"+(allTotal-allCompileFail)+"/"+allTotal+"</span>\n" +
                    "          </div>\n" +
                    "        </div>\n" +
                    "      </div>");
            bw.append("      <div class=\"status-line medium\"></div>\n" +
                    "    <div class=\"pad1\">\n" +
                    "    <table class=\"coverage-summary\">\n" +
                    "    <thead>\n" +
                    "    <tr>\n" +
                    "       <th data-col=\"file\" data-fmt=\"html\" data-html=\"true\" class=\"file sorted sorted\" style=\"\n" +
                    "        width: 180px;\n" +
                    "        max-width: 200px;\n" +
                    "    \">File</th>\n" +
                    "       <th data-col=\"pic\" data-type=\"number\" data-fmt=\"html\" data-html=\"true\" class=\"pic\" style=\"\n" +
                    "        width: 160px;\n" +
                    "    \"></th>\n" +
                    "       <th data-col=\"All\" data-type=\"number\" data-fmt=\"pct\" class=\"pct\" style=\"\n" +
                    "        width: 150px;\n" +
                    "    \">Mutation Test Score</span></th>\n" +
                    "       <th data-col=\"all_raw\" data-type=\"number\" data-fmt=\"html\" class=\"abs\" style=\"\n" +
                    "        width: 90px;\n" +
                    "    \"></th>\n" +
                    "       <th data-col=\"traditional\" data-type=\"number\" data-fmt=\"pct\" class=\"pct\" style=\"\n" +
                    "        width: 120px;\n" +
                    "    \">Mt Killed Rate</span></th>\n" +
                    "       <th data-col=\"traditional_raw\" data-type=\"number\" data-fmt=\"html\" class=\"abs\"></span></th>\n" +
                    "       <th data-col=\"esc\" data-type=\"number\" data-fmt=\"pct\" class=\"pct\" style=\"\n" +
                    "        width: 140px;\n" +
                    "    \">Mesc Killed Rate</span></th>\n" +
                    "       <th data-col=\"esc_raw\" data-type=\"number\" data-fmt=\"html\" class=\"abs\"></span></th>\n" +
                    "       <th data-col=\"pass\" data-type=\"number\" data-fmt=\"pct\" class=\"pct\" style=\"\n" +
                    "        width: 110px;\n" +
                    "    \">Compile Passed</span></th>\n" +
                    "       <th data-col=\"pass_raw\" data-type=\"number\" data-fmt=\"html\" class=\"abs\"></span></th>\n" +
                    "    </tr>\n" +
                    "    </thead>\n" +
                    "    <tbody><tr>\n" +
                    "      <td class=\"file high\" data-value=\"All File\" style=\"\n" +
                    "    \">All File</td>\n" +
                    "      <td data-value=\"83.33\" class=\"pic "+(MTS<=30 ? "low":(MTS>=70) ? "high": "medium")+"\" style=\"\n" +
                    "    \"><div class=\"chart\"><div class=\"cover-fill\" style=\"width: "+MTS+"%;\"></div><div class=\"cover-empty\" style=\"width:"+(100-MTS)+"%;\"></div></div></td>\n" +
                    "      <td data-value=\"83.33\" class=\"pct "+(MTS<=30 ? "low":(MTS>=70) ? "high": "medium")+"\">"+MTS+"%</td>\n" +
                    "      <td data-value=\"6\" class=\"abs "+(MTS<=30 ? "low":(MTS>=70) ? "high": "medium")+"\" style=\"\n" +
                    "    \">"+allKill+"/"+allTotal+"</td>\n" +
                    "      <td data-value=\"50\" class=\"pct "+(TKR<=30 ? "low":(TKR>=70) ? "high": "medium")+"\">"+TKR+"%</td>\n" +
                    "      <td data-value=\"2\" class=\"abs "+(TKR<=30 ? "low":(TKR>=70) ? "high": "medium")+"\">"+alltKill+"/"+alltTotal+"</td>\n" +
                    "      <td data-value=\"100\" class=\"pct "+(EKR<=30 ? "low":(EKR>=70) ? "high": "medium")+"\">"+EKR+"%</td>\n" +
                    "      <td data-value=\"3\" class=\"abs "+(EKR<=30 ? "low":(EKR>=70) ? "high": "medium")+"\">"+alleKill+"/"+alleTotal+"</td>\n" +
                    "      <td data-value=\"85.71\" class=\"pct "+(PF<=30 ? "low":(PF>=70) ? "high": "medium")+"\">"+PF+"%</td>\n" +
                    "      <td data-value=\"7\" class=\"abs "+(PF<=30 ? "low":(PF>=70) ? "high": "medium")+"\">"+(allTotal-allCompileFail)+"/"+allTotal+"</td>\n" +
                    "      </tr>\n" +
                    "    </tbody>\n" +
                    "    </table>\n" +
                    "    </div>");
            bw.append("        <div class=\"pad1\">\n" +
                    "    <table class=\"coverage-summary\">\n" +
                    "    <thead>\n" +
                    "    <tr>\n" +
                    "       <th data-col=\"file\" data-fmt=\"html\" data-html=\"true\" class=\"file sorted\" style=\"\n" +
                    "        width: 180px;\n" +
                    "        max-width: 200px;\n" +
                    "    \">File<span class=\"sorter\"></span></th>\n" +
                    "       <th data-col=\"pic\" data-type=\"number\" data-fmt=\"html\" data-html=\"true\" class=\"pic\" style=\"\n" +
                    "        width: 160px;\n" +
                    "    \"><span class=\"sorter\"></span></th>\n" +
                    "       <th data-col=\"All\" data-type=\"number\" data-fmt=\"pct\" class=\"pct\" style=\"\n" +
                    "        width: 150px;\n" +
                    "    \">Mutation Test Score<span class=\"sorter\"></span></th>\n" +
                    "       <th data-col=\"all_raw\" data-type=\"number\" data-fmt=\"html\" class=\"abs\" style=\"\n" +
                    "        width: 90px;\n" +
                    "    \"><span class=\"sorter\"></span></th>\n" +
                    "       <th data-col=\"traditional\" data-type=\"number\" data-fmt=\"pct\" class=\"pct\" style=\"\n" +
                    "        width: 120px;\n" +
                    "    \">Mt Killed Rate<span class=\"sorter\"></span></th>\n" +
                    "       <th data-col=\"traditional_raw\" data-type=\"number\" data-fmt=\"html\" class=\"abs\"><span class=\"sorter\"></span></th>\n" +
                    "       <th data-col=\"esc\" data-type=\"number\" data-fmt=\"pct\" class=\"pct\" style=\"\n" +
                    "        width: 140px;\n" +
                    "    \">Mesc Killed Rate<span class=\"sorter\"></span></th>\n" +
                    "       <th data-col=\"esc_raw\" data-type=\"number\" data-fmt=\"html\" class=\"abs\"><span class=\"sorter\"></span></th>\n" +
                    "       <th data-col=\"fail\" data-type=\"number\" data-fmt=\"pct\" class=\"pct\" style=\"\n" +
                    "        width: 110px;\n" +
                    "    \">Compile Passed<span class=\"sorter\"></span></th>\n" +
                    "       <th data-col=\"fail_raw\" data-type=\"number\" data-fmt=\"html\" class=\"abs\"><span class=\"sorter\"></span></th>\n" +
                    "    </tr>\n" +
                    "    </thead>\n" +
                    "    <tbody>");
            for(TestReport tr:list){
                    int mts=100;
                    int ekr=100;
                    int tkr=100;
                    int cp=100;
                    if(tr.Total!=0){
                        mts=(int)(((double)(tr.kill))/((double)(tr.Total))*100);
                        cp=(int)(((double)(tr.Total-tr.compileFail))/((double)(tr.Total))*100);
                    }
                    if(tr.eTotal!=0){
                        ekr=(int)(((double)(tr.ekill))/((double)(tr.eTotal))*100);
                    }
                    if(tr.tTotal!=0){
                        tkr=(int)(((double)(tr.tkill))/((double)(tr.tTotal))*100);
                    }



                 bw.append("      <tr>\n" +
                         "        <td class=\"file high\" data-value=\""+tr.conName+"\"><a href=\"contracts/"+tr.conName+".html\">"+tr.conName+"</a></td>\n" +
                         "        <td data-value=\"68.33\" class=\"pic high\"><div class=\"chart\"><div class=\"cover-fill\" style=\"width: "+mts+"%;\"></div><div class=\"cover-empty\" style=\"width:"+(100-mts)+"%;\"></div></div></td>\n" +
                         "        <td data-value=\"68.33\" class=\"pct "+(mts<=30 ? "low":(mts>=70) ? "high": "medium")+"\">"+mts+"%</td>\n" +
                         "        <td data-value=\"60\" class=\"abs "+(mts<=30 ? "low":(mts>=70) ? "high": "medium")+"\">"+tr.kill+"/"+tr.Total+"</td>\n" +
                         "        <td data-value=\"55.56\" class=\"pct "+(tkr<=30 ? "low":(tkr>=70) ? "high": "medium")+"\">"+tkr+"%</td>\n" +
                         "        <td data-value=\"36\" class=\"abs "+(tkr<=30 ? "low":(tkr>=70) ? "high": "medium")+"\">"+tr.tkill+"/"+tr.tTotal+"</td>\n" +
                         "        <td data-value=\"84.62\" class=\"pct "+(ekr<=30 ? "low":(ekr>=70) ? "high": "medium")+"\">"+ekr+"%</td>\n" +
                         "        <td data-value=\"13\" class=\"abs "+(ekr<=30 ? "low":(ekr>=70) ? "high": "medium")+"\">"+tr.ekill+"/"+tr.eTotal+"</td>\n" +
                         "        <td data-value=\"80.77\" class=\"pct "+(cp<=30 ? "low":(cp>=70) ? "high": "medium")+"\">"+cp+"%</td>\n" +
                         "        <td data-value=\"52\" class=\"abs "+(cp<=30 ? "low":(cp>=70) ? "high": "medium")+"\">"+(tr.Total-tr.compileFail)+"/"+tr.Total+"</td>\n" +
                         "        </tr>");
            }
            bw.append("    </tbody>\n" +
                    "    </table>\n" +
                    "    </div>\n" +
                    "    <div class=\"push\"></div><!-- for sticky footer -->\n" +
                    "    </div><!-- /wrapper -->\n" +
                    "    <div class=\"footer quiet pad2 space-top1 center small\">\n" +
                    "      Mutation Test Report\n" +
                    "      generated by <a href=\"https://github.com/belikout/MuSC-Tool-Demo-repo\" >MuSC Tool Demo</a>\n" +
                    "    </div>\n" +
                    "    \n" +
                    "    <script src=\"prettify.js\"></script>\n" +
                    "    <script>\n" +
                    "    window.onload = function () {\n" +
                    "            if (typeof prettyPrint === 'function') {\n" +
                    "                prettyPrint();\n" +
                    "            }\n" +
                    "    };\n" +
                    "    </script>\n" +
                    "    <script src=\"sorter.js\"></script>\n" +
                    "</body>\n" +
                    "</html>");
              bw.close();

        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        }
    }
    public int getAllTotal(ArrayList<TestReport> list){
        int result=0;
        for(TestReport tr:list){
            result+=tr.Total;
        }
        return result;
    }
    public int getAlltTotal(ArrayList<TestReport> list){
        int result=0;
        for(TestReport tr:list){
            result+=tr.tTotal;
        }
        return result;
    }

    public int getAlleTotal(ArrayList<TestReport> list){
        int result=0;
        for(TestReport tr:list){
            result+=tr.eTotal;
        }
        return result;
    }

    public int getAllKill(ArrayList<TestReport> list){
        int result=0;
        for(TestReport tr:list){
            result+=tr.kill;
        }
        return result;
    }
    public int getAlltKill(ArrayList<TestReport> list){
        int result=0;
        for(TestReport tr:list){
            result+=tr.tkill;
        }
        return result;
    }

    public int getAlleKill(ArrayList<TestReport> list){
        int result=0;
        for(TestReport tr:list){
            result+=tr.ekill;
        }
        return result;
    }
    public int getAllCompileFail(ArrayList<TestReport> list){
        int result=0;
        for(TestReport tr:list){
            result+=tr.compileFail;
        }
        return result;
    }
    public int getAlltCompileFail(ArrayList<TestReport> list){
        int result=0;
        for(TestReport tr:list){
            result+=tr.tcompileFail;
        }
        return result;
    }

    public int getAlleCompileFail(ArrayList<TestReport> list){
        int result=0;
        for(TestReport tr:list){
            result+=tr.ecompileFail;
        }
        return result;
    }


}
