package blacklinden.com.cannabisgrowthsimulator.nov;

import android.graphics.Color;

import java.io.Serializable;
import java.util.Random;

public class V extends Növény implements Serializable {

    private int szint;
    private int p;
    private float ép;
    private float szög;
    private float x;
    private int fajta;

    public V(int fajta) {
        super("V");
        //p=Color.WHITE;
        this.fajta=fajta;
        vízigény();
        x=1f;

        Random rndm = new Random();
        szög=rndm.nextInt(20-(-20))+(-20);
    }


    @Override
    public void élet() {
        szög();
        szín();
        xVast();
        if(ép<=(Kender.getInstance().Szintet()-szint)*100&&x<100) {
            ép += Kender.getInstance().cukrozó(1);
            ép += tápigény();
            ép += fényigény();
            ép += hőigény();
            ép += légz();
        }
    }

    private void xVast(){
        x++;

    }

    @Override
    public float vastagság() {

        return x;
    }

    @Override
    public float hossz() {
        return 0;
    }

    @Override
    public float szög() {
        return szög;
    }

    @Override
    public int szín() {

            if (x >= 18)
                p = Color.rgb(205, 133, 63);
            else
                p = Color.WHITE;

        return p;
    }

    @Override
    public float fejl() {
        return ép;
    }

    @Override
    public float vízigény() {
        return 0;
    }

    @Override
    public float fényigény() {
        if(Kender.getInstance().FF.getKelvin()<3000||Kender.getInstance().FF.isFullSpec)
        return (float)Kender.getInstance().FF.getLux()/100;
        else
        return 1.5f;
    }

    @Override
    public float hőigény() {
        float temp=Kender.getInstance().FF.hőmérséklet();
        if(temp>28)
            return 0;
        else
        return 1;
    }

    @Override
    public float tápigény() {
        return (Kender.getInstance().nutes.P+Kender.getInstance().nutes.K);
    }

    @Override
    public float légz() {

        return Kender.getInstance().LL.getCO2();
    }

    @Override
    public int szint() {
        return szint;
    }
}
