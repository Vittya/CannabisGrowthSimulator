package blacklinden.com.cannabisgrowthsimulator.eszk;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import java.util.Objects;

import blacklinden.com.cannabisgrowthsimulator.R;

public class LifeCycler extends RelativeLayout {


    public LifeCycler(Context context) {
        super(context);
        init(context);
    }

    public LifeCycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LifeCycler(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){

        setGravity(Gravity.CENTER);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Objects.requireNonNull(inflater).inflate(R.layout.lifecycle_view, this);

    }



}
