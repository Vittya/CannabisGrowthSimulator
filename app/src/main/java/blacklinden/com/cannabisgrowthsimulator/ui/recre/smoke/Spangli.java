package blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

import blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke.effect.PVector;
import blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke.effect.ParticleSystem;

public class Spangli extends View implements Runnable{

    private Bitmap bitmap;
    private int sizeInGrams;
    private String name;
    private Paint shader;
    private Paint paint;
    private Point flamePosition;
    private Stack<JointElement> elements;
    private static int MAX_WIDTH;
    public static final int EMBER_RAD=5;
    private RectF csigaBounds;
    private Path bounds;
    private boolean IS_SMOKING=false;
    private int timer;
    private PVector wind;
    private ParticleSystem ps;
    private float slope;
    private SpangliListener listener;


    private SpangliTypes type = SpangliTypes.SITTES_CIGI;
    private Paint contour;

    public Spangli(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        shader = new Paint();
        shader.setStyle(Paint.Style.FILL);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        bounds = new Path();

        csigaBounds = new RectF(0,getHeight()/3f,getWidth(),getHeight()-getHeight()/4f);
        elements = new Stack<>();
        flamePosition = new Point(getWidth(),getHeight()/2);
        wind = new PVector(0, 0);
        ps = new ParticleSystem(20,wind);
        contour = new Paint();
        contour.setColor(Color.BLACK);
        contour.setAlpha(50);
        contour.setStrokeWidth(0);
        contour.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0,0,getWidth(),getHeight(),paint);

        elements.forEach(e->{
            if(e instanceof Bobi)
                drawBobi(canvas, (Bobi) e);
            else if(e instanceof Paper)
                drawPpr(canvas,(Paper)e);
        });

        if(IS_SMOKING) drawSmoke(canvas);
        //canvas.drawRect(bounds,shader);
        //drawPpr(canvas,null);

    }

    public void setListener(SpangliListener l){
        if(listener==null)
        listener = l;
    }

    //visszafelé kell feltöltenie a vermet
    public void init(Stack<JointElement> elements, int sizeInGrams, SpangliTypes type){
        //this.elements=elements;
        MAX_WIDTH = getWidth()-EMBER_RAD;
        while(!elements.empty()) this.elements.push(elements.pop());
        this.sizeInGrams=sizeInGrams*2;//sizeInGramsNormalizer(sizeInGrams);

        this.type = type;
        flamePosition.set(MAX_WIDTH,getHeight()/2);
        csigaBounds.set(0,flamePosition.y-(getHeight()/100f*6),getWidth(),flamePosition.y+(getHeight()/100f*6));
        slope=(float)this.sizeInGrams/flamePosition.x;
        invalidate();
    }

    private void drawBobi(Canvas c, Bobi bobi){

           shader.setShader(new BitmapShader(getBitmapFromVectorDrawable(bobi.getDrawable()), Shader.TileMode.MIRROR, Shader.TileMode.CLAMP));
           shader.setStyle(Paint.Style.FILL);

           csigaBounds.set(0,flamePosition.y-(getHeight()/100f*6),flamePosition.x,flamePosition.y+(getHeight()/100f*6));
           c.drawOval(csigaBounds,shader);

    }

    private void drawPpr(Canvas c,Paper paper){
       shader.setShader(new BitmapShader(getBitmapFromVectorDrawable(paper.getDrawable()), Shader.TileMode.REPEAT, Shader.TileMode.CLAMP));
       shader.setStyle(Paint.Style.FILL_AND_STROKE);
       shader.setStrokeWidth(3);
       shader.setStrokeCap(Paint.Cap.BUTT);
       shader.setStrokeJoin(Paint.Join.ROUND);
       shader.setAntiAlias(true);
        bounds.reset();
        bounds.moveTo(0,flamePosition.y-(flamePosition.y-csigaBounds.top));
        bounds.lineTo(flamePosition.x,flamePosition.y-sizeInGrams);
        bounds.lineTo(flamePosition.x,flamePosition.y+sizeInGrams);
        bounds.lineTo(0,csigaBounds.bottom);
        bounds.close();
        c.drawPath(bounds,shader);
        c.drawLine(0,flamePosition.y-(flamePosition.y-csigaBounds.top),flamePosition.x,flamePosition.y-sizeInGrams,contour);
        c.drawLine(flamePosition.x,flamePosition.y+sizeInGrams,0,csigaBounds.bottom,contour);
        //c.drawOval(new RectF(0f,0f,getWidth(),getHeight()),shader);
    }

    private void drawSmoke(Canvas c){
        wind.set(0,0);
        ps.applyForce(wind);
        ps.run(c,sizeInGrams);
        wind.set(flamePosition.x,flamePosition.y);
        for (int i = 0; i < 2; i++) {
            ps.addParticle(wind);
        }
    }

    private Bitmap getBitmapFromVectorDrawable(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(Objects.requireNonNull(drawable).getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


    @Override
    public void run() {
        IS_SMOKING=true;
        timer++;
        if(timer%100==0) {
            slope=(float)(this.sizeInGrams/flamePosition.x);
            if(sizeInGrams>slope*20)
            sizeInGrams-=slope*20;//(float)(this.sizeInGrams/flamePosition.x)*20;
            flamePosition.set(flamePosition.x - getWidth() / 20, flamePosition.y);

            if(listener!=null)listener.exhale();

        }
        invalidate();
        postDelayed(this,33);
        if(flamePosition.y<=getWidth()/20){
            IS_SMOKING=false;
            removeCallbacks(this);
            dispose();
        }
    }

    private int sizeInGramsNormalizer(int sizeInGrams){
        return (flamePosition.y+EMBER_RAD)+ (getHeight()/4 - (flamePosition.y+EMBER_RAD)) * ((sizeInGrams - 1) / (18 - 1));
    }
    private float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }

    private void dispose(){
        elements.clear();
        invalidate();
        if(listener!=null) listener.finish();
    }

    interface SpangliListener{
        void exhale();
        void finish();
    }
}
