package blacklinden.com.cannabisgrowthsimulator.ui;


public class CardItem {

    private String thc,yield;
    private String strainType;
    private int mTitleResource;
    private int mPic;
    private int db,reward;
    private String cd;

    public CardItem(String cd,int title, int db, String thc, String yield, String strainType, int reward, int pic) {
        mTitleResource = title;
        this.db=db;
        this.thc = thc;
        this.yield = yield;

        this.strainType = strainType;
        this.reward = reward;
        mPic = pic;
        this.cd=cd;
    }

    public String getThc() {
        return thc;
    }

    public String getYield(){ return yield;}

    public String getStrainType(){return strainType;}

    public int getReward(){ return reward;}

    public int getTitle() {
        return mTitleResource;
    }

    public int getPic() { return mPic; }

    public int getDb(){return db;}

    public String getCd(){return cd;}
}
