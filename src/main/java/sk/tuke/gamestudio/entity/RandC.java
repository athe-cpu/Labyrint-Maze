package sk.tuke.gamestudio.entity;

public class RandC {
    private String comment;
    private int rate;
    public RandC(String comment, int rate) {
        this.comment = comment;
        this.rate = rate;
    }

    public String getComment(){
        return comment;
    }
    public int getRate(){
        return rate;
    }
    public void setRate(int rate){
        this.rate = rate;
    }
    @Override
    public String toString()
    {
        return "User [name=" + comment ;
    }
}
