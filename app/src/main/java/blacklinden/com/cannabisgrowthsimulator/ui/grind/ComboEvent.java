package blacklinden.com.cannabisgrowthsimulator.ui.grind;
import java.util.Arrays;

public class ComboEvent {
    private String[] currentCombo;
    private ComboListener listener;
    private int smokeCounter =0;

    private String[] a1 = {"Skunk","Skunk","Skunk"};
    private String[] a2 = {"Skunk","Afghan","Balkan Rose"};
    private String[] a3 = {"Afghan","Balkan Rose","Afghan"};
    private String[] b1 = {"BlueBerry","Northern Light","Northern Light"};
    private String[] b2 = {"BlueBerry","Grape Ape","Northern Light"};
    private String[] b3 = {"Grape Ape","Grape Ape","Northern Light"};
    private String[] c1 = {"AK47","Cheese","BlueBerry"};
    private String[] c2 = {"Amnesia","Balkan Rose","Cheese"};
    private String[] c3 = {"Cheese","Grape Ape","Northerh Light"};
    private String[] d1 = {"White Widow","Amnesia","White Widow"};
    private String[] d2 = {"S.Lemon Haze","Gelato","White Widow"};
    private String[] d3 = {"AK47","Gelato","AK47"};
    private String[] e1 = {"Cherry Diesel","Cherry Diesel","AK47"};
    private String[] e2 = {"Ghost OG","Ghost OG","Cherry Diesel"};
    private String[] e3 = {"Grape Ape","Permafrost","AK47"};
    private String[] f1 = {"Pink Mango","BlueBerry","Pineapple"};
    private String[] f2 = {"Pineapple","G.White Shark","Amnesia"};
    private String[] f3 = {"G.White Shark","Gelato","Ghost OG"};

    public ComboEvent(ComboListener listener){
        currentCombo = new String[]{"", "", ""};
        this.listener=listener;
    }

    public int fillUpComboArray(String fajta){
        if(smokeCounter<=1) {
            currentCombo[smokeCounter] = fajta;
            smokeCounter++;
        }else {
            currentCombo[smokeCounter] = fajta;
            combinator();
        }return smokeCounter-1;
    }

    public int getSmokeCounter(){
        return smokeCounter;
    }

    private void combinator(){
        if(Arrays.equals(currentCombo,a1)||
                Arrays.equals(currentCombo,a2)||
                Arrays.equals(currentCombo,a3)||
                Arrays.equals(currentCombo,b1)||
                Arrays.equals(currentCombo,b2)||
                Arrays.equals(currentCombo,b3)||
                Arrays.equals(currentCombo,c1)||
                Arrays.equals(currentCombo,c2)||
                Arrays.equals(currentCombo,c3)||
                Arrays.equals(currentCombo,d1)||
                Arrays.equals(currentCombo,d2)||
                Arrays.equals(currentCombo,d3)||
                Arrays.equals(currentCombo,e1)||
                Arrays.equals(currentCombo,e2)||
                Arrays.equals(currentCombo,e3)||
                Arrays.equals(currentCombo,f1)||
                Arrays.equals(currentCombo,f2)||
                Arrays.equals(currentCombo,f3)){
            listener.comboFound(currentCombo);
            smokeCounter=0;
            currentCombo = new String[3];
        }else{
            //currentCombo = new String[]{"", "", ""};
            listener.notFound();
            smokeCounter=0;
            currentCombo = new String[3];
        }
    }

    public interface ComboListener{
        void comboFound(String[] combo);
        void notFound();
    }



}
