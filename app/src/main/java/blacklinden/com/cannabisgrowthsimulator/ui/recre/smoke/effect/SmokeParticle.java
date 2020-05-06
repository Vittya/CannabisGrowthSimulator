package blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke.effect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

import blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke.Spangli;

class SmokeParticle{
        private PVector loc;
        private PVector vel;
        private PVector acc;
        private int lifespan;
        private Random internalRandom;
        private Paint paint;



    SmokeParticle(PVector l) {
            acc = new PVector(0, 0);
            float vx = (float) (randomGaussian()*0.3);
            float vy = (float) (randomGaussian()*0.3 - 1.0);
            vel = new PVector(vx, vy);
            loc = l.copy();
            lifespan = 100;
            paint = new Paint();
            paint.setColor(Color.DKGRAY);


        }

        void run(Canvas c,int size) {
            update();
            render(c,size);
        }

        void applyForce(PVector f) {
            acc.add(f);
        }

        private void update() {
            vel.add(acc);
            loc.add(vel);
            lifespan -= 2.5;
            acc.mult(0);
        }


        private void render(Canvas c, int size) {

            paint.setAlpha(lifespan);

            paint.setColor(manipulateColor(Color.WHITE,lifespan));
            c.drawOval(loc.x-Spangli.EMBER_RAD,loc.y-size, loc.x+Spangli.EMBER_RAD,loc.y+size,paint);
            c.drawCircle(loc.x,loc.y,Spangli.EMBER_RAD,paint);

        }


        boolean isDead() {
            return (lifespan <= 0.0);
            }

    private float randomGaussian() {
        if (internalRandom == null) {
            internalRandom = new Random();
        }
        return (float) internalRandom.nextGaussian();
    }

    private static int manipulateColor(int color, float factor) {
        int a = Color.alpha((int) (color*factor));
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(
               Math.min(a,100),
                240,//Math.min(r,255),
                240,//Math.min(g,255),
                240);//Math.min(b,255));
    }


    }

