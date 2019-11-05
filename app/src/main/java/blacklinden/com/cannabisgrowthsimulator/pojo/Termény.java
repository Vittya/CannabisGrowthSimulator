package blacklinden.com.cannabisgrowthsimulator.pojo;

public class Termény {
    private float mennyi;
    private float suly,eredetiSuly;
    private int fajta;
    private int thc,cbd;
    private float t,c;
    private int napok;
    private int burp,gas;
    private boolean isCuring;
    private String status ="wet",fajtaString;
    private float vapor;
    private String sorszám;


    public Termény(int db,float mennyi,int fajta,int thc,int cbd){
        this.mennyi=db;
        this.fajta=fajta;
        switch (fajta) {
            case 1:
                this.fajtaString ="Skunk";
                break;
            case 2:
                this.fajtaString="Afghan";
                break;
            case 3:
                this.fajtaString="Balkan Rose";
                break;
            case 4:
                this.fajtaString="BlueBerry";
                break;
            case 5:
                this.fajtaString="Northern Light";
                break;
            case 6:
                this.fajtaString="Grape Ape";
                break;
            case 7:
                this.fajtaString ="AK47";
                break;
            case 8:
                this.fajtaString="Cheese";
                break;
            case 9:
                this.fajtaString="Amnesia";
                break;
            case 10:
                this.fajtaString="S.Lemon Haze";
                break;
            case 11:
                this.fajtaString="White Widow";
                break;
            case 12:
                this.fajtaString="Gelato";
                break;
            case 13:
                this.fajtaString ="Ghost OG";
                break;
            case 14:
                this.fajtaString="Cherry Diesel";
                break;
            case 15:
                this.fajtaString="Permafrost";
                break;
            case 16:
                this.fajtaString="Pink Mango";
                break;
            case 17:
                this.fajtaString="Pineapple";
                break;
            case 18:
                this.fajtaString="G.White Shark";
                break;
            case 19:
                this.fajtaString="Nurse R.";
                break;
            case 20:
                this.fajtaString="Durban";
                break;

        }
        this.cbd=cbd;
        this.thc=thc;
        t=thc;
        suly=mennyi;
        eredetiSuly=suly;
        napok=1;

    }

    public void update(){
        if(suly>eredetiSuly/3) {
            if (vapor >= 40 && vapor < 60) {
                if (napok < 3)
                    suly -= suly / 3;
                else
                    suly -= suly / 5;

                if(t<thc+3)
                    t+=0.1f;
            } else if (vapor > 60) {
                if (napok < 3)
                    suly -= suly / 4;
                else
                    suly -= suly / 8;
            } else if(vapor<37){

                if (napok < 3)
                    suly -= suly / 3;
                else
                    suly -= suly / 5;
            }else {
                if (napok < 3)
                    suly -= suly / 3;
                else
                    suly -= suly / 3;

            }

        }
        if(isCuring){
            if(gas<10) {
                if (t < thc + 5)
                    t += 0.5f;
            }

            switch (status) {
                case "wet":
                    if (napok > 2)
                        status = "smelly";
                    else
                        status = "molded";
                    break;
                case "good":
                    if (napok > 60)
                        status = "goldilocks";
                    else
                        status = "good";
                    break;
                case "dry":
                    status = "good";
                    break;
                case "goldilocks":
                    if(gas<10)
                    status = "goldilocks";
                    else
                    status = "good";
                    break;
                default:
                    status = "smelly";
                    break;
            }
            if(suly>eredetiSuly/3)
            gas++;
        }else{
            status=goldilocks();
        }
        napok++;
    }

   public float getSuly(){
        return suly;
    }

    public float getThc(){return (t);}

    public float getCbd(){return (c);}

    public int getFajta(){return fajta;}

    public String getFajtaString(){return fajtaString;}

    public float getDarab(){return mennyi;}

    public int getNapok(){
        return napok;
    }
    private String goldilocks(){
        if(suly<eredetiSuly/2&&napok==10&&!status.equals("smelly"))
            return "goldilocks";
        else if(suly<(eredetiSuly/1.5f)&&!status.equals("smelly"))
            return "good";
        else if(vapor>60&&suly<eredetiSuly)
            return "smelly";
        else if(napok<2)
            return "wet";
        else
            return "dry";
    }
    public void burpJar(){
        burp++;
        gas=0;
    }

    public boolean isCuring() {
        return isCuring;
    }

    public String getStatus(){return status;}

    public void setCuring(boolean curing) {
        isCuring = curing;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setVapor(float vapor){
        this.vapor=vapor;
    }

    public void setSorszám(String i){
        sorszám=i;
    }

    public String getSorszám(){
        return sorszám;
    }

    public int getGas(){return gas;}
}
