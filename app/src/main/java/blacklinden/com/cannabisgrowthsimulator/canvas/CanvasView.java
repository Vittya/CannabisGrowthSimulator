package blacklinden.com.cannabisgrowthsimulator.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.VectorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.eszk.Teknős;
import blacklinden.com.cannabisgrowthsimulator.nov.A;
import blacklinden.com.cannabisgrowthsimulator.nov.X;
import blacklinden.com.cannabisgrowthsimulator.nov.Av;
import blacklinden.com.cannabisgrowthsimulator.nov.F;
import blacklinden.com.cannabisgrowthsimulator.nov.Kender;
import blacklinden.com.cannabisgrowthsimulator.nov.L;
import blacklinden.com.cannabisgrowthsimulator.nov.Növény;
import blacklinden.com.cannabisgrowthsimulator.nov.V;
import blacklinden.com.cannabisgrowthsimulator.nov.menttolt.T;
import blacklinden.com.cannabisgrowthsimulator.nov.menttolt.M;



public class CanvasView extends TextureView implements TextureView.SurfaceTextureListener, Runnable {


        private int ism;
        Teknős t;


        float delta_theta=0f;
        private float metrix;
        private Thread thread;
        private volatile boolean mRunning = false;

        private Surface mSurface;

        private ArrayList<Növény> AL;

        public CanvasView(Context c, AttributeSet attrs) {
            super(c, attrs);

            AL = new ArrayList<>();
            t = new Teknős(c);
            //this.setLayerType(LAYER_TYPE_HARDWARE,null);

            thread = new Thread(this, "CANVAS_TAG");
            setSurfaceTextureListener(this);
            this.setOpaque(false);


        }



        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);


        }


        public void T(Canvas c,ArrayList<Növény> a){

            //this.setLayerType(LAYER_TYPE_HARDWARE,null);

            t.orient((float)c.getWidth()/2,c.getHeight()-metrix,90);


            for (Növény ch : a) {


                switch (ch.n) {
                    case "F":
                    case "X":
                        c.rotate(ch.szög(), (float) t.x, (float) t.y);
                        t.előreRajz(ch.vastagság()*(metrix/52),-ch.hossz()*(metrix/52), c,ch.szín(), ism);
                        t.levElRajz((ch.vastagság()*2)*(metrix/52),2*(metrix/52),c);
                        break;


                    case "L":
                      if(ch.szint()==1) {

                            c.rotate(ch.szög(), (float) t.x, (float) t.y);
                            t.levElRajz(ch.vastagság()*(metrix/52),-ch.hossz()*(metrix/42),c,ch.szín());

                        }else if(ch.szint()<=3&&ch.szint()>1){

                            t.mentés(c, (int)t.x,(int)t.y,(int)delta_theta);
                            c.rotate(ch.szög()-65, (float) t.x, (float) t.y);
                            t.levElRajz(ch.vastagság()*(metrix/52),(-ch.hossz()/2)*(metrix/42),c,ch.szín());
                            t.betöltés(c);

                            t.mentés(c, (int)t.x,(int)t.y,(int)delta_theta);
                            c.rotate(ch.szög()+65, (float) t.x, (float) t.y);
                            t.levElRajz(ch.vastagság()*(metrix/52),(-ch.hossz()/2)*(metrix/42), c, ch.szín());
                            t.betöltés(c);

                            t.mentés(c, (int)t.x,(int)t.y,(int)delta_theta);
                            c.rotate(ch.szög(), (float) t.x, (float) t.y);
                            t.levElRajz(ch.vastagság()*(metrix/52),-ch.hossz()*(metrix/42), c,ch.szín());
                            t.betöltés(c);

                        }else {
                            t.mentés(c, (int)t.x,(int)t.y,(int)delta_theta);
                            c.rotate(ch.szög()-130, (float) t.x, (float) t.y);
                            t.levElRajz(ch.vastagság()*(metrix/52),(-ch.hossz()/4)*(metrix/42),c,ch.szín());
                            t.betöltés(c);

                            t.mentés(c, (int)t.x,(int)t.y,(int)delta_theta);
                            c.rotate(ch.szög()+130, (float) t.x, (float) t.y);
                            t.levElRajz(ch.vastagság()*(metrix/52),(-ch.hossz()/4)*(metrix/42), c, ch.szín());
                            t.betöltés(c);

                            t.mentés(c, (int)t.x,(int)t.y,(int)delta_theta);
                            c.rotate(ch.szög()-65, (float) t.x, (float) t.y);
                            t.levElRajz(ch.vastagság()*(metrix/52),(-ch.hossz()/2)*(metrix/42), c, ch.szín());
                            t.betöltés(c);

                            t.mentés(c, (int)t.x,(int)t.y,(int)delta_theta);
                            c.rotate(ch.szög()+65, (float) t.x, (float) t.y);
                            t.levElRajz(ch.vastagság()*(metrix/52),(-ch.hossz()/2)*(metrix/42),c,ch.szín());
                            t.betöltés(c);

                            t.mentés(c, (int)t.x,(int)t.y,(int)delta_theta);
                            c.rotate(ch.szög(), (float) t.x, (float) t.y);
                            t.levElRajz(ch.vastagság()*(metrix/52),-ch.hossz()*(metrix/42), c, ch.szín());
                            t.betöltés(c);
                        }
                        break;



                    case "V":

                        t.mentés(c, (int)t.x,(int)t.y,(int)delta_theta);
                        t.előreRajz(1f,-10*(metrix/52),c,ch.szín(),0);
                        t.virágzás(ch.vastagság()*(metrix/52),c,ch.szín());
                        t.betöltés(c);

                        break;

                    case "AV":
                        t.mentés(c, (int)t.x,(int)t.y,(int)delta_theta);
                        t.előreRajz(0.5f,20,c,ch.szín(),0);
                        t.előVirágzás(c,ch.szín());
                        t.betöltés(c);
                        break;


                    case "[":
                        t.mentés(c, (int) t.x, (int) t.y, (int) delta_theta);
                        break;
                    case "]":
                        t.betöltés(c);
                        break;
                }

            }


        }
    //adat toldó
    public void told(ArrayList<Növény> yyy, int ism){

        this.ism=ism;
        AL= yyy;
        this.post(thread);

    }

        public void metrix(float m){
            metrix=m;
        }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        mSurface = new Surface(surface);
        mRunning = true;
        thread.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mRunning = false;
        try{
            thread.join();
        }
        catch(InterruptedException e){
            Log.e("CANVAS_TAG", "onSurfaceTextureDestroyed", e);
        }
        mSurface.release();
        mSurface = null;
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
       mSurface.release();
       mSurface = new Surface(surface);
    }

    @Override
    public void run() {
        if (mSurface != null) {
            Canvas canvas = mSurface.lockHardwareCanvas();
            T(canvas, AL);
            mSurface.unlockCanvasAndPost(canvas);
        }
    }
}
