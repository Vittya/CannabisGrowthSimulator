package blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PreviewBackup extends View {
    private Rect bounds;
    private JointElement element;
    private Paint paint;

    public PreviewBackup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bounds = new Rect();
        paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
    }

    public void fillUp(JointElement element){
        this.element= element;

        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.rotate(10);

        bounds.set(0,0,getWidth(),getHeight());
        if(element!=null)
        element.draw(bounds,canvas);
        else
        canvas.drawRect(bounds,paint);
    }
}
