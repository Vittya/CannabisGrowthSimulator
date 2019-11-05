package blacklinden.com.cannabisgrowthsimulator.canvas.kor;


import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.eszk.Mentés;
import blacklinden.com.cannabisgrowthsimulator.pojo.Lamps;
import blacklinden.com.cannabisgrowthsimulator.ui.medium.Elem;

@SuppressWarnings("ALL")
public class Fény {
    public float irány;
    private int lux;
    public int watt;
    private float hőSötétben=25,hőmérséklet;
    private int kelvin;
    private float táv;


    private Égő égő;
    public int fatyolCode;
    public boolean beKapcs = false;
    public boolean ventilKapcs = false;
    public boolean isFullSpec = false;
    public int drawCode;
    private Lamps defT;

    public Fény(){



     this.irány=0;



        defT =new Lamps( "Desk Bulb","CFL",60,4700,3000, R.drawable.cflkek,R.drawable.feher_csova);
     setDrawCode("");


    }


    public int setDrawCode(String teszt){
        String s = Mentés.getInstance().getString(Mentés.Key.TESZT_OBJ,"o");
        if(!s.equals("o")) {
            Lamps T = (Lamps) Mentés.getInstance().javara(s, Lamps.class);

            égő = Égő.valueOf(T.getType());
            watt = T.getConsumption();
            kelvin = T.getSpectrum();
            lux = T.getLumen();
            drawCode = T.getAnimDrawCode();
            fatyolCode = T.getFatyolDrawCode();
            if(fatyolCode==R.drawable.lila_csova) isFullSpec=true;
            return drawCode;
        }else{

            Mentés.getInstance().put(Mentés.Key.TESZT_OBJ,Mentés.getInstance().gsonra(
                    new Lamps( "Desk Bulb","CFL",60,4700,3000, R.drawable.cflkek,R.drawable.feher_csova)
            ));

            return setDrawCode("");
        }

    }

    public int setDrawCode(int i){
        //alapérték ide jön
        Lamps T;
        String s = Mentés.getInstance().getString(Mentés.Key.TESZT_OBJ,"0");
        if(s.equals("0"))
         T = defT;
        else
         T = (Lamps)Mentés.getInstance().javara(s,Lamps.class);

        égő=Égő.valueOf(T.getType());
        watt = T.getConsumption();
        kelvin = T.getSpectrum();
        lux = T.getLumen();
        drawCode = T.getAnimDrawCode();
        fatyolCode = T.getFatyolDrawCode();

        if(fatyolCode==R.drawable.lila_csova) isFullSpec=true;
        return fatyolCode;
    }

    public void setWatt(int watt){
        this.watt=watt;
    }

    public void setKelvin(int kelvin){
        this.kelvin=kelvin;
    }

    public float hőmérséklet(){
        if(beKapcs) {
            if(!ventilKapcs)
            hőmérséklet = (hőSötétben + (watt * égő.getHőszor_()));
        }
        else
            hőmérséklet= hőSötétben;

        return hőmérséklet;
    }



    public void setIrány(){
        if(Math.abs(irány)<5)irány++;
    }

    public void resetIrány(){if(irány>0)irány--;}

    public void setIrány(int i){
        irány=i;
    }
    public int getLux(){
        return lux;
    }

    public int getKelvin() { return kelvin;}
    public void setHő(){
        hőmérséklet=24;
    }


}