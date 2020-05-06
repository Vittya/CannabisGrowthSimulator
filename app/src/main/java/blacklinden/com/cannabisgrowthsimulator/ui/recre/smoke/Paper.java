package blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

public class Paper implements JointElement{

    private final Drawable paperDrawable;
    private final String name;
    private final boolean isRear;

    public Paper(Drawable paperDrawable, String name, boolean isRear){
        this.name=name;
        this.paperDrawable=paperDrawable;
        paperDrawable.setBounds(0,0,paperDrawable.getIntrinsicWidth(),paperDrawable.getIntrinsicHeight());
        this.isRear=isRear;
    }


    @Override
    public void draw(@Nullable Rect rect, Canvas c) {
        if(rect!=null)
        paperDrawable.setBounds(rect);
        else
            paperDrawable.setBounds(0,0,paperDrawable.getIntrinsicWidth(),paperDrawable.getIntrinsicHeight());
        paperDrawable.draw(c);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean getState() {
        return isRear;
    }

    @Override
    public Rect getRect() {
        return paperDrawable.getBounds();
    }

    @Override
    public Drawable getDrawable() {
        return paperDrawable;
    }
}
