package blacklinden.com.cannabisgrowthsimulator.ui;

public class CardItem2 {
    private int titleRes;
    private String type;
    private int pic;
    private int mennyi;

    public CardItem2(int mennyi,int titleRes,String type,int pic){
        this.mennyi=mennyi;
        this.titleRes=titleRes;
        this.type=type;
        this.pic=pic;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public String getType() {
        return type;
    }

    public int getPic() {
        return pic;
    }

    public int getMennyi(){return mennyi;}
}
