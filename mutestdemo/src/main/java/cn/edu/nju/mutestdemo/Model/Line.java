package cn.edu.nju.mutestdemo.Model;

import cn.edu.nju.mutestdemo.EnumType.MuType;

import java.util.ArrayList;

public class Line {
    private String content;
    private ArrayList<MuType> types;
    private int space=0;
    public Line(String content,ArrayList<MuType> types,int space){
        this.content=content;
        this.types=types;
        this.space=space;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<MuType> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<MuType> types) {
        this.types = types;
    }


    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }
}
