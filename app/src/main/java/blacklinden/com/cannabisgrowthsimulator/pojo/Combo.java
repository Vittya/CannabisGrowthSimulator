package blacklinden.com.cannabisgrowthsimulator.pojo;

public class Combo {

    private String[] combo;
    private int hnyszr;
    private int placmentCode;

    public Combo(String combo,int hnyszr){
        this.hnyszr=hnyszr;
        this.combo=combo.split("_");
        placer(combo);
    }

    private void placer(String combo){
        switch (combo){
            case "Skunk_Skunk_Skunk":
                placmentCode=11;
                break;
            case "Skunk_Afghan_Balkan Rose":
                placmentCode=12;
                break;
            case "Afghan_Balkan Rose_Afghan":
                placmentCode=13;
                break;
            case "BlueBerry_Northern Light_Northern Light":
                placmentCode=21;
                break;
            case "Grape Ape_Grape Ape_Northern Light":
                placmentCode=22;
                break;
            case "BlueBerry_Grape Ape_Northern Light":
                placmentCode=23;
                break;
            case "AK47_Cheese_BlueBerry":
                placmentCode=31;
                break;
            case "Amnesia_Balkan Rose_Cheese":
                placmentCode=32;
                break;
            case "Cheese_Grape Ape_Northern Light":
                placmentCode=33;
                break;
            case "White Widow_Amnesia_White Widow":
                placmentCode=41;
                break;
            case "S.Lemon Haze_Gelato_White Widow":
                placmentCode=42;
                break;
            case "AK47_Gelato_AK47":
                placmentCode=43;
                break;
            case "Cherry Diesel_Cherry Diesel_AK47":
                placmentCode=51;
                break;
            case "Ghost OG_Ghost OG_Cherry Diesel":
                placmentCode=52;
                break;
            case "Grape Ape_Permafrost_AK47":
                placmentCode=53;
                break;
            case "Pink Mango_BlueBerry_Pineapple":
                placmentCode=61;
                break;
            case "Pineapple_G.White Shark_Amnesia":
                placmentCode=62;
                break;
            case "G.White Shark_Gelato_Ghost OG":
                placmentCode=63;
                break;
           // default:placmentCode=10;

        }
    }

    public String[] getCombo(){
        return combo;
    }

    public int getHnyszr(){
        return hnyszr;
    }

    public int[] getPlacmentCode(){
        //if(placmentCode!=0)
        return new int[]{(placmentCode/10)-1,placmentCode%10};
        //else
        //return new int[]{-1,-1};
    }
}
