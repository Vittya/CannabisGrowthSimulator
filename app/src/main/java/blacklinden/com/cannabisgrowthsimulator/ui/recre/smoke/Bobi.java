package blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import java.util.concurrent.ThreadLocalRandom;

public class Bobi implements JointElement {
    private final Drawable bobiDrawable;
    private final String name;
    private final Paint shaderPaint;
    private final int mennyi;
    public Bobi(BitmapDrawable bobiDrawable, String name, int mennyi){
        this.mennyi=mennyi;
        this.bobiDrawable=bobiDrawable;
        this.bobiDrawable.setBounds(0,0,bobiDrawable.getIntrinsicWidth(),bobiDrawable.getIntrinsicHeight());
        this.name=name;
        shaderPaint = new Paint();
        shaderPaint.setShader(new BitmapShader(bobiDrawable.getBitmap(), Shader.TileMode.REPEAT,Shader.TileMode.REPEAT));
    }
    @Override
    public void draw(@Nullable Rect rect, Canvas c) {
        if(rect!=null)
        bobiDrawable.setBounds(rect);
        drawNuggets(c);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean getState() {
        return false;
    }

    @Override
    public Rect getRect() {
        return bobiDrawable.getBounds();
    }

    @Override
    public Drawable getDrawable() {
        return bobiDrawable;
    }

    private void drawNuggets(Canvas c){


        for(int i=0;i<50;i++) {
            int tempW=bobiDrawable.getBounds().width()/10;
            int tempH=bobiDrawable.getBounds().height()/4;
            int x = ThreadLocalRandom.current().nextInt(tempW, bobiDrawable.getBounds().width()-tempW);
            int y = ThreadLocalRandom.current().nextInt(tempH, bobiDrawable.getBounds().height()-tempH);
            int d = ThreadLocalRandom.current().nextInt(-tempW,tempW);


            c.drawOval(x,y,x+d,y+d,shaderPaint);
        }
    }
}
