package blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;


public interface JointElement {

    void draw(@Nullable Rect rect, Canvas c);
    String getName();
    boolean getState();
    Rect getRect();
    Drawable getDrawable();
}
