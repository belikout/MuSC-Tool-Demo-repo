var isTesting=false;
var tOpNum=9;
var eOpNum=15;
var projectPath=""
var contractsName=new Array()
var mutants=null;
var mutantsNum=0;
var testResult=null;
function tAll() {
    var ops=document.getElementsByClassName("typeCheck");
    if(document.getElementById("toggle-t-all").checked){
        for(var i=0;i<tOpNum;i++)
            ops[i].checked=true;
    }else
        for(var i=0;i<tOpNum;i++)
            ops[i].checked=false;
}
function eAll() {
    var ops=document.getElementsByClassName("typeCheck");
    if(document.getElementById("toggle-e-all").checked){
        for(var i=tOpNum;i<tOpNum+eOpNum;i++)
            ops[i].checked=true;
    }else
        for(var i=tOpNum;i<tOpNum+eOpNum;i++)
            ops[i].checked=false;
}
function chooseCon(type){
    var conNum;
    if(type==0) {
        conNum=document.getElementById("select-con-t").selectedIndex;
        showChooseMutantSpan(0,conNum);
        showMuText(0,conNum,0);
    }else{
        conNum=document.getElementById("select-con-e").selectedIndex;
        showChooseMutantSpan(1,conNum);
        showMuText(1,conNum,0);
    }
}
function clearCount(type){
    var items=document.getElementsByClassName("typeCount");
    if(type==0)
        for(var i=0;i<9;i++)
            items[i].innerHTML="0";
    else if(type==1)
        for(var i=9;i<24;i++)
            items[i].innerHTML="0";
    else if(type==-1)
        for(var i=0;i<items.length;i++)
            items[i].innerHTML="0";
}
function showChooseMutantSpan(type,conNum){
    var spanText=""
    if(type==0) {
        clearCount(0)
        for(var i=0;i<mutants[type][conNum].mutateLine.length;i++){
            spanText+="<div>\n" +
                "          <input id=\"chooseRadio-t-"+i+"\" class=\"chooseRadio-t\" type=\"radio\" onchange='changeMuText(0,"+conNum+","+i+")' name=\"item\" value=\"\""
            if(i==0)spanText+= "checked";
            spanText+=">\n" +"          <label for=\"chooseRadio-t-"+i+"\"></label>\n" +
                "          <span style=\"margin-left: 10px\">"+mutants[type][conNum]["mutateLineType"][i]+"_"+(mutants[type][conNum]["mutateLineNums"][i]+1)+"</span>\n" +
                "        </div>"
            var num= parseInt(document.getElementById(mutants[type][conNum]["mutateLineType"][i]+"MuCount").innerText);
            num+=1;
            $("#"+mutants[type][conNum]["mutateLineType"][i]+"MuCount").html(""+num);
        }
        $("#chooseMutant-t").html(spanText);
    }else{
        clearCount(1);
        for(var i=0;i<mutants[type][conNum].mutateLine.length;i++){
            spanText+="<div>\n" +
                "          <input id=\"chooseRadio-e-"+i+"\" class=\"chooseRadio-t\" type=\"radio\" onchange='changeMuText(1,"+conNum+","+i+")' name=\"item\" value=\"\""
            if(i==0)spanText+= "checked";
            spanText+=">\n" +"          <label for=\"chooseRadio-e-"+i+"\"></label>\n" +
                "          <span style=\"margin-left: 10px\">"+mutants[type][conNum]["mutateLineType"][i]+"_"+mutants[type][conNum]["mutateLineNums"][i]+"</span>\n" +
                "        </div>"
            var num= parseInt(document.getElementById(mutants[type][conNum]["mutateLineType"][i]+"MuCount").innerText);
            num+=1;
            $("#"+mutants[type][conNum]["mutateLineType"][i]+"MuCount").html(""+num);
        }
        $("#chooseMutant-e").html(spanText);
    }
}
function changeMuText(type,conNum,muNum){
    showMuText(type,conNum,muNum);
}
function showMuText(type,conNum,muNum){
    var muLine=mutants[type][conNum]["mutateLine"][muNum];
    var muLineNum=mutants[type][conNum]["mutateLineNums"][muNum];
    var muLineType=mutants[type][conNum]["mutateLineType"][muNum];
    var oriText="<p>";
    var muText="<p>";
    var no=1;
    for(var i=0;i<muLineNum;i++){
        oriText+="<div class=\"lineNum\">"+no+"</div>"
        oriText+=mutants[type][conNum]["oriLine"][i]+"<br>";
        muText+="<div class=\"lineNum\">"+no+"</div>"
        muText+=mutants[type][conNum]["oriLine"][i]+"<br>";
        no+=1;
    }
    oriText+="<div id='oriLine-"+type+"' class=\"lineNum\">"+no+"</div>"
    oriText+=" <font color=\"red\">"+mutants[type][conNum]["oriLine"][muLineNum]+"</font><br>";
    muText+="<div id='muLine-"+type+"' class=\"lineNum\">"+no+"</div>"
    muText+="<font color=\"red\">"+muLine+"</font><br>";
    no+=1;
    for(var i=muLineNum+1;i<mutants[type][conNum]["oriLine"].length;i++){
        oriText+="<div class=\"lineNum\">"+no+"</div>"
        oriText+=mutants[type][conNum]["oriLine"][i]+"<br>";
        muText+="<div class=\"lineNum\">"+no+"</div>"
        muText+=mutants[type][conNum]["oriLine"][i]+"<br>";
        no+=1;
    }
    if(type==0) {
        $("#oriText-t").html(oriText+"</p>");
        $("#mutText-t").html(muText+"</p>");
        $("#oriText-t").scrollTop($('#oriLine-0').offset().top);
        $("#mutText-t").scrollTop($('#muLine-0').offset().top);
    }else{
        $("#oriText-e").html(oriText+"</p>");
        $("#mutText-e").html(muText+"</p>");
        $("#oriText-e").scrollTop($('#oriLine-1').offset().top);
        $("#mutText-e").scrollTop($('#muLine-1').offset().top);
    }

}
function changeProjectPath(){
    projectPath="";
    $("#project-path-input").removeAttr("disabled");
    var instruction="<h2>Instruction</h2>\n" +
        "                                                <p>Enter the path of Truffle Project to be tested in the input box above, and click \"Test Project\" to check the project of the path. Only when the path is correct and the test cases are all passed, can the mutants be generated by clicking \"Generate\" button below.</p>\n" +
        "                                                <p>If you need to change the path of the project, click the \"Change Path\" button above, and then test the changed path again.</p>";
    $("#contractChoosePanel").html(instruction);
}
function getTestProjectFolder(){
    document.getElementById("project-path-input").setAttribute("disabled","disabled");
    document.getElementById("loadTestProject").removeAttribute("hidden");
    $.ajax({
        type: 'GET',
        url: '/getTestProfile',
        cache: 'false',
        data: {
            "path": document.getElementById("project-path-input").value
        },
        dataType: 'json',
        //contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
        headers : {
            'Content-Type' : 'application/json;charset=utf-8'
        },
        success: function (result) {
            document.getElementById("loadTestProject").setAttribute("hidden","hidden");
            if(result.toString().length>4&&result.toString().substr(0,4)=="Fail"){
                projectPath="";
                $("#project-path-input").removeAttr("disabled");
                $(this).openWindow('Fail',result.substr(5,result.length),'["OK"]');
            }
            else {
                $("#contractChoosePanel").html("");
                projectPath=result[0];
                document.getElementById("project-path-input").value=result[0];
                for (var i = 1; i < result.length; i++) {
                    contractsName[contractsName.length]=result[i]
                    var text = "<div class=\"col-md-3\"class=\"contractFile\"style='margin-bottom: 15px;max-width:90%;margin-top: 15px'>\n" +
                        "                                        <div class=\"toggle-switch\" data-ts-color=\"info\">\n" +
                        "                                            <label for=\"ts" + contractsName.length + "\" class=\"contract-label\">" + result[i] + "</label>\n" +
                        "                                            <input class=\"contractFileChoose\"id=\"ts" + contractsName.length + "\" type=\"checkbox\" hidden=\"hidden\">\n" +
                        "                                            <label for=\"ts" + contractsName.length + "\" class=\"ts-helper\"></label>\n" +
                        "                                        </div>\n" +
                        "                                    </div>"
                    $("#contractChoosePanel").append(text);
                }
            }
        }
    })
}
function generateMutant() {
    var checkboxs=document.getElementsByClassName("contractFileChoose");
    var testContractsName=[];
    for (var i=0;i<checkboxs.length;i++){
        if(checkboxs.item(i).checked) {
            testContractsName[testContractsName.length]=contractsName[i];
        }
    }
    var ops=document.getElementsByClassName("typeCheck");
    var types=[]
    for (var i=0;i<ops.length;i++){
        if(ops.item(i).checked) {
            types[types.length] = ops.item(i).id;
        }
    }
    $.ajax({
        type: 'GET',
        url: '/generateMutant',
        cache: 'false',
        data: {
            "projectPath": projectPath,
            "contracts": JSON.stringify(testContractsName),
            "types": JSON.stringify(types)
        },
        dataType: 'json',
        //contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
        headers : {
            'Content-Type' : 'application/json;charset=utf-8'
        },
        success: function (result) {
            mutants=result;
            mutantsNum=0;
            showMutants0();
        }
    })
}
function showMutants(){
    showTotalOpsSummary();
    if(mutants[0].length>0){
        var selectCon="";
        for(var i=0;i<mutants[0].length;i++){
            selectCon+='<option>'+mutants[0][i].conName+'</option>'
            mutantsNum+=mutants[0][i].mutateLine.length;
        }
        $("#select-con-t").html(selectCon);
        showChooseMutantSpan(0,0);
        showMuText(0,0,0);
        $("#tViewer").click();
    }
    if(mutants[1].length>0){
        var selectCon="";
        for(var i=0;i<mutants[1].length;i++){
            selectCon+='<option>'+mutants[1][i].conName+'</option>'
            mutantsNum+=mutants[1][i].mutateLine.length;
        }
        $("#select-con-e").html(selectCon);
        showChooseMutantSpan(1,0);
        showMuText(1,0,0)
        $("#eViewer").click();
    }
    if(mutants[0].length<=0&&mutants[1].length<=0){
        //提示一下未生成变异体
        $(this).openWindow('Attention','No mutant generated!','["OK"]');
    }
}
function configureTestChain(){
    if(document.getElementById("toggle_chain-code").checked){
        $("#chainCodeInput").removeAttr("disabled");
    }else{
        document.getElementById("chainCodeInput").setAttribute("disabled","disabled");
    }

}
function showMutants0(){
    showTotalOpsSummary0();
    if(mutants[0].length>0){
        var selectCon="";
        for(var i=0;i<mutants[0].length;i++){
            selectCon+='<option>'+mutants[0][i].conName+'</option>'
            mutantsNum+=mutants[0][i].mutateLine.length;
        }
        $("#select-con").html(selectCon);
        showChooseMutantSpan0(0);
        showMuText0(0,0)
        $("#Viewer").click();
    }else{
        $(this).openWindow('Attention','No mutant generated!','["OK"]');
    }
}
function showTotalOpsSummary0(){
    clearCount(-1);
    for(var j=0;j<mutants[0].length;j++){
        for(var m=0;m<mutants[0][j]["mutateLineType"].length;m++){
            var num= parseInt(document.getElementById(mutants[0][j]["mutateLineType"][m]+"-MuCount").innerText)+1;
            $("#"+mutants[0][j]["mutateLineType"][m]+"-MuCount").html(""+num);
        }
    }
}
function showTotalOpsSummary(){
    clearCount(-1);
    for(var i=0;i<mutants.length;i++){
        if(mutants[i].length>0){
            for(var j=0;j<mutants[i].length;j++){
                for(var m=0;m<mutants[i][j]["mutateLineType"].length;m++){
                    var num= parseInt(document.getElementById(mutants[i][j]["mutateLineType"][m]+"-MuCount").innerText)+1;
                    $("#"+mutants[i][j]["mutateLineType"][m]+"-MuCount").html(""+num);
                }
            }
        }
    }
}
function showChooseMutantSpan0(conNum){
    var spanText=""
        clearCount(0)
        for(var i=0;i<mutants[0][conNum].mutateLine.length;i++){
            spanText+="<div>\n" +
                "          <input id=\"chooseRadio-"+i+"\" class=\"chooseRadio\" type=\"radio\" onchange='changeMuText0("+conNum+","+i+")' name=\"item\" value=\"\""
            if(i==0)spanText+= "checked";
            spanText+=">\n" +"          <label for=\"chooseRadio-"+i+"\"></label>\n" +
                "          <span style=\"margin-left: 10px\">"+mutants[0][conNum]["mutateLineType"][i]+"_"+(mutants[0][conNum]["mutateLineNums"][i]+1)+"</span>\n" +
                "        </div>"
            var num= parseInt(document.getElementById(mutants[0][conNum]["mutateLineType"][i]+"MuCount").innerText);
            num+=1;
            $("#"+mutants[0][conNum]["mutateLineType"][i]+"MuCount").html(""+num);
        }
        $("#chooseMutant").html(spanText);
}
function changeMuText0(conNum,muNum){
    showMuText0(conNum,muNum);
}
function showMuText0(conNum,muNum){
    var muLine=mutants[0][conNum]["mutateLine"][muNum];
    var muLineNum=mutants[0][conNum]["mutateLineNums"][muNum];
    var muLineType=mutants[0][conNum]["mutateLineType"][muNum];
    var oriText="<p>";
    var muText="<p>";
    var no=1;
    for(var i=0;i<muLineNum;i++){
        oriText+="<div class=\"lineNum\">"+no+"</div>"
        oriText+=mutants[0][conNum]["oriLine"][i]+"<br>";
        muText+="<div class=\"lineNum\">"+no+"</div>"
        muText+=mutants[0][conNum]["oriLine"][i]+"<br>";
        no+=1;
    }
    oriText+="<div id='oriLine' class=\"lineNum\">"+no+"</div>"
    oriText+=" <font color=\"red\">"+mutants[0][conNum]["oriLine"][muLineNum]+"</font><br>";
    muText+="<div id='muLine' class=\"lineNum\">"+no+"</div>"
    muText+="<font color=\"red\">"+muLine+"</font><br>";
    no+=1;
    for(var i=muLineNum+1;i<mutants[0][conNum]["oriLine"].length;i++){
        oriText+="<div class=\"lineNum\">"+no+"</div>"
        oriText+=mutants[0][conNum]["oriLine"][i]+"<br>";
        muText+="<div class=\"lineNum\">"+no+"</div>"
        muText+=mutants[0][conNum]["oriLine"][i]+"<br>";
        no+=1;
    }
        $("#oriText").html(oriText+"</p>");
        $("#mutText").html(muText+"</p>");
        $("#oriText").scrollTop($('#oriLine').offset().top);
        $("#mutText").scrollTop($('#muLine').offset().top);
}
function chooseCon0(){
    var conNum;
        conNum=document.getElementById("select-con").selectedIndex;
        showChooseMutantSpan0(conNum);
        showMuText0(conNum,0);
}
function stopTest(){
    //isTesting=false;
    $.ajax({
        type: 'GET',
        url: '/stopTest',
        cache: 'false',
        data: {
        },
        dataType: 'json',
        //contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
        headers : {
            'Content-Type' : 'application/json;charset=utf-8'
        }
    })
}
function getTestedNum(){
    if(isTesting){
        $.ajax({
            type: 'GET',
            url: '/getProgress',
            cache: 'false',
            data: {
                "projectPath": projectPath,
            },
            dataType: 'json',
            headers : {
                'Content-Type' : 'application/json;charset=utf-8'
            },
            success: function (result) {
                console.log("already "+result);
                $("#loadMuTestText").html("Test the mutants now...("+result+"/"+mutantsNum+")");
                window.setTimeout(getTestedNum,2000);
            }
        })

    }
}
function startMutationTest(){
    if(mutants!=null&&mutants[0].length>0) {
        document.getElementById("exportButton").setAttribute("disabled", "disabled");
        document.getElementById("loadMutationTest").removeAttribute("hidden");
        isTesting = true;
        var chainCode = "";
        var testedMutants = new Array();
        testedMutants[0] = mutants[1]
        testedMutants[1] = mutants[2]
        if (document.getElementById("toggle_chain-code").checked)
            chainCode = document.getElementById("chainCodeInput").value;
        $.ajax({
            type: 'GET',
            url: '/generateMutationTest',
            cache: 'false',
            data: {
                "chainCode": chainCode,
                "projectPath": projectPath,
                "mutants": JSON.stringify(testedMutants)
            },
            dataType: 'json',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            success: function (result) {
                isTesting = false;
                document.getElementById("loadMutationTest").setAttribute("hidden", "hidden");
                if (result.toString().length > 4 && result.toString().substr(0, 4) == "Fail") {
                    $(this).openWindow('Attention', result, '["OK"]');
                } else {
                    testResult=result;
                    $(this).openWindow('Attention', 'Mutation test finished!', '["OK"]');
                    $("#tKill").html(result[0]["kill"])
                    $("#tLive").html(result[0]["live"])
                    $("#tTotal").html(result[0]["total"])
                    $("#tScore").html(result[0]["score"])
                    $("#eKill").html(result[1]["kill"])
                    $("#eLive").html(result[1]["live"])
                    $("#eTotal").html(result[1]["total"])
                    $("#eScore").html(result[1]["score"])
                    $("#teKill").html(result[0]["kill"] + result[1]["kill"])
                    $("#teLive").html(result[0]["live"] + result[1]["live"])
                    $("#teTotal").html(result[0]["total"] + result[1]["total"])
                    $("#teScore").html(parseInt((result[0]["kill"] + result[1]["kill"]) * 100 / (result[0]["total"] + result[1]["total"])))
                    document.getElementById("exportButton").removeAttribute("disabled");
                }
            }
        })
        $("#loadMuTestText").html("Test the mutants now...(0" + "/" + mutantsNum + ")");
        getTestedNum();
    }else{
        $(this).openWindow('Attention', "No mutant has been generated!", '["OK"]');
    }
}
function exportReport(){
    $(this).openWindow('Attention', "Report has been export to \<MuSC_Report\> folder under your truffle project folder! ", '["OK"]');
}