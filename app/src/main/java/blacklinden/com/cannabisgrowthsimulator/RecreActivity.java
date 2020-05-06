package blacklinden.com.cannabisgrowthsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.widget.Toast;

import blacklinden.com.cannabisgrowthsimulator.ui.recre.RecreFragment;
import blacklinden.com.cannabisgrowthsimulator.ui.recre.dialog.CommandCtrlDialog;

public class RecreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recre_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecreFragment.newInstance())
                    .commitNow();
        }
    }

    public Point getPoint() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        return point;
    }

}