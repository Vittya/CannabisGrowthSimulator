package blacklinden.com.cannabisgrowthsimulator.ui.grind;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.eszk.Teknos;
import blacklinden.com.cannabisgrowthsimulator.ui.recre.drag.DragElement;

public class Bong extends View implements Runnable, DragElement {

    private Paint paint;
    private VectorDrawableCompat kd;
    private float top;
    private BongListener listener;
    private boolean filled;
    private int counter;
    private String fajta,mnsg="";
    private float thc,cbd;
    private Handler handler;



    public Bong(Context context) {
        super(context);
        init();
    }

    public Bong(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){

        listener = null;
        paint = new Paint();
        Bitmap bitmap = Teknos.flowerStrain(getContext(),"weed1");
        paint.setShader(new BitmapShader(bitmap,Shader.TileMode.CLAMP,Shader.TileMode.REPEAT));
        kd = VectorDrawableCompat.create(getResources(),R.drawable.ic_bong,null);
        top=1000;
        handler = new Handler();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN&&filled) {
            this.run();
            if(listener!=null)
                listener.lightUp();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        kd.setBounds(0,0,getWidth(),getHeight());
        kd.draw(canvas);
        if(top<=getHeight()/2)
        canvas.drawRect(0,top,(float)getWidth()/10,(float)getHeight()/2,paint);
    }

    public void fillUp(String fajta,float thc,float cbd,String mnsg){
       filled=true;
       top = 0;
       this.mnsg=mnsg;
       this.fajta=fajta;
       this.thc=thc;
       this.cbd=cbd;
       if(listener!=null)listener.inhale();
       invalidate();
    }

    @Override
    public void run() {

        if (filled&&top < getHeight()/2) {

            if(listener!=null){
                listener.exhale(counter*10);
            }

            counter++;
            top+=(float)(getHeight()/2)/5;
            invalidate();
            handler.postDelayed(this,3000);

        }else if(filled&&top>=getHeight()/2){
            filled=false;
            top=(float)getHeight()/2;
            handler.removeCallbacks(this);
            if(listener!=null) listener.end(calculateXp(),fajta);

        }
    }

    private int calculateXp(){

        float f = XPutil.f(fajta);
        int q = XPutil.q(mnsg);
        float t = XPutil.thc(thc);
        return (int)(f*q+t);
    }

    @Override
    public void hiLite(boolean isLit) {
        if(isLit)
            kd.setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
        else
            kd.setColorFilter(null);
    }

    public interface BongListener{

        void lightUp();

        void inhale();

        void exhale(int counter);

        void end(int xp,String fajta);

    }

    public void setListener(BongListener listener){this.listener=listener;}

    public boolean isFilled(){
        return filled;
    }

}

class XPutil {
    static int q(String mnsg) {
        int i;
        switch (mnsg) {
            case "wet":
                i = -1;
                break;
            case "smelly":
                i = 2;
                break;
            case "good":
                i = 5;
                break;
            case "goldilocks":
                i = 10;
                break;
            default:
                i = 0;
        }

        return i;
    }


    static float thc(float thc) {
        if (thc >= 1 && thc < 6)
            return 1;
        else if (thc >= 6 && thc < 11)
            return 2;
        else if (thc >= 11 && thc < 16)
            return 3;
        else if (thc >= 16 && thc < 21)
            return 4;
        else if (thc >= 21 && thc <= 25)
            return 5;
        else
            return 1;
    }

    static int f(String fajta) {
        switch(fajta){
            case "Skunk":
            case "Afghani":
            case "Balkan Rose":
            return 5;

            case "AK 47":
            case "Grape Ape":
            case "BlueBerry":
            case "Northern Light":
            return 10;

            case "Cheese":
            case "Amnesia":
            case "S.Lemon Haze":
            return 15;

            case "White Widow":
            case "Gelato":
            case "Ghost OG":
            return 20;

            case "Cherry Diesel":
            case "Permafrost":
            return 25;

            case "Pink Mango":
            case "Pineapple":
            case "G.White Shark":
            return 30;

            default:return 3;
        }

    }
}

