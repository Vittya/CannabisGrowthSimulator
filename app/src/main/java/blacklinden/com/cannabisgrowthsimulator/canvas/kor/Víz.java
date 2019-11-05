package blacklinden.com.cannabisgrowthsimulator.canvas.kor;

import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.eszk.Mentés;
import blacklinden.com.cannabisgrowthsimulator.pojo.Acc;

public class Víz {

    private float PH;

    public float getVÍZ_Mennyiség() {
        return VÍZ_Mennyiség;
    }

    private float VÍZ_Mennyiség;

    public Víz(){
        PH=5.5f;
        VÍZ_Mennyiség=0;
        //setDrawCode();

    }

    public int setDrawCode(){
        //Mentés.getInst Context

        String s = Mentés.getInstance().getString(Mentés.Key.SAMPLE_CAN,"0");
        int drawCode;
        if(s.equals("0")){
            drawCode =R.drawable.ic_kanna001;

        }else{
            Acc acc = (Acc)Mentés.getInstance().javara(s,Acc.class);
            drawCode =acc.getDrawCode();

        }

        return drawCode;
    }

    public float fogyaszt(int i){
        float er;
        if(VÍZ_Mennyiség>=i) {
            VÍZ_Mennyiség -= i;

           er=i;
        }else {
            er = VÍZ_Mennyiség;
            VÍZ_Mennyiség = 0;
        }
        return er;
    }

    public void locsol(){

        VÍZ_Mennyiség+=100;
    }
    public float getPH(){
        return PH;
    }

}