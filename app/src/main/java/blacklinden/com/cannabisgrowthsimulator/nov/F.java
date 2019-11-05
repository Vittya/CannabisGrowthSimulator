package blacklinden.com.cannabisgrowthsimulator.nov;

import android.graphics.Color;

import java.io.Serializable;


public class F extends Növény implements Serializable {


    public float x=1f;
    private float ép;
    private float szg;
    private boolean oldHajt;
    private int szint;
    private int szín;
    private int fajta;
    private float hosszSzabályzó,vastszab;



    public F(int fajta){
        super("F");
        this.fajta=fajta;
        switch (fajta){
            case 1: case 7: case 16:
                if(oldHajt)
                    hosszSzabályzó=15;
                else
                    hosszSzabályzó=20;

                vastszab=0.09f;
            break;

            case 18:case 19:
                if(oldHajt)
                    hosszSzabályzó=30;
                else
                    hosszSzabályzó=35;
                vastszab=0.065f;
            break;

            case 2: case 8: case 17:
                if(oldHajt)
                    hosszSzabályzó=12;
                else
                    hosszSzabályzó=25;
                vastszab=0.1f;
            break;

            case 3: case 9:
                if(oldHajt)
                    hosszSzabályzó=6;
                else
                    hosszSzabályzó=22;
                vastszab=0.09f;
            break;

            case 4: case 13: case 20:
                if(oldHajt)
                    hosszSzabályzó=8;
                else
                    hosszSzabályzó=23;
                vastszab=0.075f;
            break;

            case 10:
                if(oldHajt)
                    hosszSzabályzó=8;
                else
                    hosszSzabályzó=30;
                vastszab=0.075f;
            break;

            case 5: case 11: case 14:
                if(oldHajt)
                    hosszSzabályzó=17;
                else
                    hosszSzabályzó=34;
                vastszab=0.06f;
            break;
            case 6: case 12: case 15:
                if(oldHajt)
                    hosszSzabályzó=16;
                else
                    hosszSzabályzó=30;
                vastszab=0.06f;
            break;



        }
        System.out.println("szint");
    }

    public F init(int i,float szg){

        //this.szg=szg>Kender.getInstance().FF.irány?szg-1:szg+1;
        if(szg!=Kender.getInstance().FF.irány){
            switch (fajta) {
                case 1: case 7: case 13: case 19:
                    this.szg = szg / 2;
                break;
                case 2: case 8: case 14:
                    this.szg = szg/4;
                break;
                case 3: case 9: case 15: case 20:
                    this.szg = szg/3.4f;
                    break;
                case 4: case 10: case 16:
                    this.szg = szg/1.2f;
                    break;
                case 5: case 11: case 17:
                    this.szg =szg*1.2f;
                    break;
                case 6: case 12: case 18:
                    this.szg = szg/2.7f;
                    break;
            }


        }
        ép=1;
        szint=i;
        this.oldHajt=true;
        //hosszSzabályzó=15;
        //Kender.getInstance().Szint();
        return this;
    }

    public F init(int i){
        ép=i;
        //if(szg!=Kender.getInstance().FF.irány) szg=szg>0?szg-10:szg+10;
        this.szint=i;
        Kender.getInstance().Szint();
        //hosszSzabályzó=20;
        return this;
    }


    @Override
    public void élet() {

        if(ép<40) ép+=Kender.getInstance().cukrozó(0.5f);

        //if(Kender.getInstance().getRost()<=0&&Kender.getInstance().getCukor()<=0) ép--;
        //if(Kender.getInstance().nutrition.N>16&&Kender.getInstance().flowering) ép --;

        if(ép==0)
            Kender.getInstance().halottRészek++;
        hosszSzabályzó+=tápigény();
        xHossz();//


    }

    @Override
    public float vastagság() {
        if((x*vastszab)-szint/5>1)
        return (x*vastszab)-((float)szint/5);
        else
        return 1;
    }



    private void xHossz(){

        if (x < hosszSzabályzó - szint && ép > 20&&Kender.getInstance().getRost()>szint&&!Kender.getInstance().flowering)
            x += (ép + Kender.getInstance().nutes.N) * 0.01f;
    }

    @Override
    public float hossz() {

        return x;
    }

    @Override
    public float szög() {

        if(!oldHajt)
            return Kender.getInstance().FF.irány;
        else {

            return Kender.getInstance().FF.irány + szg ;
        }

    }

    @Override
    public int szín() {
            szín = Color.RED;
        return szín;
    }

    @Override
    public float fejl() {

        return ép;
    }

    @Override
    public float vízigény() {
        Kender.getInstance().levonH2o();

        return 0;
    }

    @Override
    public float fényigény() {
        return 0;
    }

    @Override
    public float hőigény() {

        return 0;
    }

    @Override
    public float tápigény() {

        if(Kender.getInstance().nutes.N==9)
        return 3;
        else
        return 0;
    }

    @Override
    public float légz() {
        if(oldHajt)
            return 1;
        else
            return 0;
    }

    @Override
    public int szint() {
        return szint;
    }

    private int lerp(int i, int ii){
        return i + (ii - i) * szint/100;
    }


}