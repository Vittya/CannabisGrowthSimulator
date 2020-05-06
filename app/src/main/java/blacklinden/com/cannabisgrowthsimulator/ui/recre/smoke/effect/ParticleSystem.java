package blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke.effect;


import android.graphics.Canvas;
import android.graphics.Point;

import java.util.ArrayList;


public class ParticleSystem {

    private ArrayList<SmokeParticle> particles;
    private PVector origin;


    public ParticleSystem(int num, PVector v) {
        particles = new ArrayList<>();
        origin = v.copy();
        for (int i = 0; i < num; i++) {
            particles.add(new SmokeParticle(origin));
        }
    }

    public void run(Canvas c, int size) {
        for (int i = particles.size()-1; i >= 0; i--) {
            SmokeParticle p = particles.get(i);
            p.run(c, size);
            if (p.isDead()) {
                particles.remove(i);
            }
        }
    }


   public void applyForce(PVector dir) {

        for (SmokeParticle p : particles) {
            p.applyForce(dir);
        }
    }

    public void addParticle(PVector point) {
        origin = point.copy();
        particles.add(new SmokeParticle(origin));
    }

    public void reset(){

    }
}