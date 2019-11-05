package blacklinden.com.cannabisgrowthsimulator.ui.kolibri;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Build;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class Smoke implements KolibriState {

    private static Smoke instance =null;
    private Kolibri kolibri;
    private Path path;
    private OvershootInterpolator tul;

    private Smoke(Kolibri kolibri){
        this.kolibri=kolibri;
        path = new Path();
        tul = new OvershootInterpolator();
    }

    public static Smoke getInstance(@Nullable Kolibri kolibri){
        if(instance ==null){
            synchronized (Smoke.class){
                if(instance==null){
                    instance = new Smoke(kolibri);
                }
            }
        }
        return instance;
    }

    @Override
    public void flyTo(@Nullable View v) {

        if(kolibri!=null) {
            int[] hely = new int[2];
            int[] helyv = new int[2];
            kolibri.getKolibri().getLocationOnScreen(hely);
            if (v != null)
            v.getLocationOnScreen(helyv);

            int x1 = hely[0];
            int y1 = hely[1];

            TextView ktv =(TextView) kolibri.getKolibri();
            int x0;
            int y0;
            if (Build.VERSION.SDK_INT <28) {
                x0 = helyv[0] - kolibri.getKolibri().getWidth();
                y0 = helyv[1];//-kolibri.getKolibri().getHeight()/2;
            }else{
                x0 = helyv[0] - kolibri.getKolibri().getWidth();
                y0 = helyv[1]-kolibri.getKolibri().getHeight()/2;
            }
            float X = (x0 + (float)(x1 / 3));
            float Y = (float)(y0 + y1) / 3;

            path.moveTo(x1, y1);
            path.quadTo(X, Y, x0, y0);
            Animator animator = ObjectAnimator.ofFloat(kolibri.getKolibri(), View.X, View.Y, path);
            animator.setDuration(2000);
            animator.setInterpolator(tul);
            animator.start();

            if (x0 < kolibri.getScreenWidth() / 2)
                kolibri.getKolibri().setScaleX(-1f);
            else
                kolibri.getKolibri().setScaleX(1f);

            path.reset();

            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    csirip(v);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }

    }

    @Override
    public void csirip(View view) {
        kolibri.setText("");
    }

    @Override
    public void dispose() {
        instance = null;
    }
}
