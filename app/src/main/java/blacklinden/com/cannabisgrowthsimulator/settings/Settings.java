package blacklinden.com.cannabisgrowthsimulator.settings;

public class Settings {

    private static Settings instance = null;
    private boolean tutorialSequence;
    private boolean isKolibriOn;
    private int languageSettings;

    private Settings(){
       isKolibriOn=true;
       tutorialSequence=false;
    }

    public static Settings getInstance() {
        if (instance == null) {
            synchronized (Settings.class) {
                if (instance == null) {
                    instance = new Settings();
                }
            }
        }
        return instance;
    }

    public boolean getTutorialSequence(){

        return tutorialSequence;
    }

    public void setTutorialSequence(boolean onoff){
        tutorialSequence=onoff;
    }

    public void setKolibriOn(boolean on){
        isKolibriOn=on;
    }

    public boolean getKolibriOn(){
        return isKolibriOn;
    }



}
