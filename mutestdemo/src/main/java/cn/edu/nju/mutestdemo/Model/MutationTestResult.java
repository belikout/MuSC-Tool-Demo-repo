package cn.edu.nju.mutestdemo.Model;

public class MutationTestResult {
    private int tLive=0;
    private int tkill=0;
    private int tTotal=0;
    private int tScore=0;
    private int eLive=0;
    private int ekill=0;
    private int eTotal=0;
    private int eScore=0;

    public int gettLive() {
        return tLive;
    }

    public void settLive(int tLive) {
        this.tLive = tLive;
    }

    public int getTkill() {
        return tkill;
    }

    public void setTkill(int tkill) {
        this.tkill = tkill;
    }

    public int gettTotal() {
        return tTotal;
    }

    public void settTotal(int tTotal) {
        this.tTotal = tTotal;
    }

    public int gettScore() {
        return tScore;
    }

    public void settScore(int tScore) {
        this.tScore = tScore;
    }

    public int geteLive() {
        return eLive;
    }

    public void seteLive(int eLive) {
        this.eLive = eLive;
    }

    public int getEkill() {
        return ekill;
    }

    public void setEkill(int ekill) {
        this.ekill = ekill;
    }

    public int geteTotal() {
        return eTotal;
    }

    public void seteTotal(int eTotal) {
        this.eTotal = eTotal;
    }

    public int geteScore() {
        return eScore;
    }

    public void seteScore(int eScore) {
        this.eScore = eScore;
    }
}
