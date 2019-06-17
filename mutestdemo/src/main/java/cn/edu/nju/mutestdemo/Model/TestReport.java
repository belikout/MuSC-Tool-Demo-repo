package cn.edu.nju.mutestdemo.Model;

import java.util.ArrayList;

public class TestReport {
    public String conName;
    public ArrayList<String>oriLine=new ArrayList<String>();
    public ArrayList<String>mutateLine=new ArrayList<String>();
    public ArrayList<Integer>mutateLineNums=new ArrayList<Integer>();
    public ArrayList<String>mutateLineType=new ArrayList<String>();
    public ArrayList<String>isKilled=new ArrayList<String>();
    public int tLive=0;
    public int tkill=0;
    public int tTotal=0;
    public int tcompileFail=0;
    public int tScore=0;
    public int eLive=0;
    public int ekill=0;
    public int eTotal=0;
    public int ecompileFail=0;
    public int eScore=0;
    public int Live=0;
    public int kill=0;
    public int Total=0;
    public int compileFail=0;
    public int Score=0;
}
