package blacklinden.com.cannabisgrowthsimulator.ui.kolibri;



import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;

import java.util.Locale;

import blacklinden.com.cannabisgrowthsimulator.R;

public class Kolibri implements KolibriState, Runnable, View.OnClickListener {
    private float screenWidth;

    private Handler handler;
    private View kolibri;
    private int tmbIndicator=0;
    private KolibriState state;
    private View[] tutorialTmb=null;
    private boolean tutorial_e=false;
    private int gameCounter=0;
    private CoinListener listener;


    public Kolibri(float screenWidth, View kolibri){

        handler = new Handler(Looper.myLooper());
        this.screenWidth=screenWidth;
        this.kolibri=kolibri;
        setState("repdes",null);
        kolibri.setOnClickListener(this);
    }

    public void dispose(){
        tutorialTmb=null;
        tutorial_e=false;
        tmbIndicator=0;
        Tutorial.getInstance(this).dispose();
        Idle.getInstance(this).dispose();
        Repdes.getInstance(this).dispose();
        Smoke.getInstance(this).dispose();
        Fogocska.getInstance(this).dispose();
        state.dispose();
    }

    public void setListener(CoinListener listener){
        this.listener = listener;
    }


    @Override
    public void flyTo(View view) {
        if(state instanceof Repdes&&view!=null||state instanceof Idle)
            setState("tutorial",view);
        else
            state.flyTo(view);
    }

    @Override
    public void csirip(View view) {
        if(state instanceof Fogocska)
            ((TextView)kolibri).setText(String.format(Locale.ENGLISH,"%ds", 30 - gameCounter));
    }

    @Override
    public void run() {

        if(state instanceof Repdes) {
            gameCounter=0;
            tmbIndicator=0;
            tutorialTmb=null;
            state.flyTo(null);
            handler.postDelayed(this, 12000);

        }else if(tutorialTmb!=null&&state instanceof Tutorial){

            if(tmbIndicator<tutorialTmb.length-1) {
                state.flyTo(tutorialTmb[tmbIndicator]);
                tmbIndicator++;
            }
            else{
                state = Repdes.getInstance(this);
                tutorialTmb = null;
            }

            //handler.postDelayed(this, 5000);
        }else if(state instanceof Fogocska) {
            csirip(null);
            gameCounter++;
            if (gameCounter < 30) {
                state.flyTo(null);
                handler.postDelayed(this, 1000);
            }
            else {
                listener.ended();
                state = Repdes.getInstance(this);
                gameCounter=0;
            }
        }
    }

    public void setTutorial_e(View[] tutorialTmb) {
        tmbIndicator=0;
        this.tutorialTmb = tutorialTmb;
        state=Tutorial.getInstance(this);
        tutorial_e=true;
    }

    @Override
    public void onClick(View view) {
        if(state instanceof Repdes)
        state.flyTo(null);
        else if(state instanceof Idle)
        setState("repdes",null);
        else if(state instanceof Fogocska){
            listener.score();
            state.flyTo(null);
        }
    }

    private void stopIt(){

    }

    public View getKolibri(){
        return kolibri;
    }

    float getScreenWidth() {
        return screenWidth;
    }

    void setText(String text){
        ((TextView)kolibri).setText(text);
    }

    public KolibriState getState() {
        return state;
    }

    public boolean isTutorial(){return tutorial_e;}

    public void setState(String nextState, @Nullable View view){
        if(state!=null)
            state.dispose();
        switch (nextState) {
            case "tutorial":
            if(!(state instanceof Smoke)) {
                state = Tutorial.getInstance(this);
                state.flyTo(view);
                kolibri.setClickable(false);
            }
            break;
            case "idle":
            state = Idle.getInstance(this);
            state.flyTo(view);
            kolibri.setClickable(true);
            break;
            case "repdes":
            state = Repdes.getInstance(this);
            handler.post(this);
            kolibri.setClickable(true);
            break;
            case "smoke":
            state = Smoke.getInstance(this);
            if(view!=null)state.flyTo(view);
            kolibri.setClickable(true);
            break;
            case "fogocska":
            state = Fogocska.getInstance(this);
            kolibri.setClickable(true);
            handler.post(this);
            break;
        }
    }

    public int getGameCounter() {
        return gameCounter;
    }

    public interface CoinListener{
        void score();
        void ended();
    }

}
