package cn.edu.nju.mutestdemo.Model;

public class MutationTestResult {
    private int Live=0;
    private int kill=0;
    private int Total=0;
    private int compileFail=0;
    private int Score=0;


    public int getLive() {
        return Live;
    }

    public void setLive(int live) {
        Live = live;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public int getCompileFail() {
        return compileFail;
    }

    public void setCompileFail(int compileFail) {
        this.compileFail = compileFail;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
}
