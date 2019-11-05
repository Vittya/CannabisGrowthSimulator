package blacklinden.com.cannabisgrowthsimulator.ui.kolibri;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import java.util.concurrent.ThreadLocalRandom;

public class Fogocska implements KolibriState {

    private static Fogocska instance = null;
    private Kolibri kolibri;
    private Path path;
    private OvershootInterpolator tul;


    private Fogocska(Kolibri kolibri) {
        this.kolibri = kolibri;
        path = new Path();
        tul = new OvershootInterpolator();

    }

    public static Fogocska getInstance(@Nullable Kolibri kolibri) {
        if (instance == null) {
            synchronized (Tutorial.class) {
                if (instance == null) {
                    instance = new Fogocska(kolibri);
                }
            }
        }
        return instance;
    }

    @Override
    public void flyTo(View view) {
        if(view==null){
            csirip(null);
            int[] hely = new int[2];
            kolibri.getKolibri().getLocationOnScreen(hely);

            int x1 = hely[0];
            int y1 = hely[1];
            int x0 = ThreadLocalRandom.current().nextInt((int) kolibri.getScreenWidth() / 4 - kolibri.getKolibri().getWidth(), (int) kolibri.getScreenWidth() / 2 + kolibri.getKolibri().getWidth());
            int y0 = ThreadLocalRandom.current().nextInt((int) kolibri.getScreenWidth() / 4 - kolibri.getKolibri().getWidth(), (int) kolibri.getScreenWidth() + kolibri.getKolibri().getWidth());

            float X = (float) (x0 + x1) / 3;
            float Y = (float)(y0 + y1) / 3;

            path.moveTo(x1, y1);
            path.quadTo(X, Y, x0, y0);
            ObjectAnimator animator = ObjectAnimator.ofFloat(kolibri.getKolibri(), View.X, View.Y, path);
            animator.setDuration(2000);
            animator.setInterpolator(tul);
            animator.start();
            path.reset();
            if (kolibri.getKolibri().getScaleX()==-1f)
                kolibri.getKolibri().setScaleX(1f);
        }else
            kolibri.setState("tutorial",view);
    }

    @Override
    public void csirip(View view) {
        //if(kolibri.getKolibri()!=null)

    }

    @Override
    public void dispose() {
        instance=null;
    }


}
