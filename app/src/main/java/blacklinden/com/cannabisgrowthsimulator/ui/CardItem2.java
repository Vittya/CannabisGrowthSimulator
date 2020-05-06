package blacklinden.com.cannabisgrowthsimulator.ui;

public class CardItem2 {
    private int titleRes;
    private String type;
    private int pic;
    private int mennyi;

    CardItem2(int mennyi,int titleRes,String type,int pic){
        this.mennyi=mennyi;
        this.titleRes=titleRes;
        this.type=type;
        this.pic=pic;
    }

    int getTitleRes() {
        return titleRes;
    }

    public String getType() {
        return type;
    }

    int getPic() {
        return pic;
    }

    public int getMennyi(){return mennyi;}
}
