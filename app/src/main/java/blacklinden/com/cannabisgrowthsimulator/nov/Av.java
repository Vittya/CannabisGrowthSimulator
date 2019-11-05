package blacklinden.com.cannabisgrowthsimulator.nov;


import java.io.Serializable;

public class Av extends Növény implements Serializable {
    private int szint;
    private float ép;
    private int x;
    public Av() {
        super("AV");
        vízigény();
        x=1;
    }

    public Av init(int szint){
        this.szint=szint+1;
        return this;
    }

    @Override
    public void élet() {
        ép+=Kender.getInstance().cukrozó(1);
        ép+=tápigény();
        ép+=fényigény();
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
        return 0;
    }

    @Override
    public int szín() {
        return 0;
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
        if(Kender.getInstance().FF.getKelvin()<3000)
            return Kender.getInstance().FF.getLux()/100;
        else
            return 1.5f;
    }

    @Override
    public float hőigény() {
        return 0;
    }

    @Override
    public float tápigény() {
        if(Kender.getInstance().nutes.P>0)
            return Kender.getInstance().nutes.P*Kender.getInstance().nutes.P;
        else
        return 0;
    }

    @Override
    public float légz() {
        return 0;
    }

    @Override
    public int szint() {
        return szint;
    }

}
