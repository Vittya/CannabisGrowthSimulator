package blacklinden.com.cannabisgrowthsimulator;

import android.content.Intent;
import android.media.MediaPlayer;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import blacklinden.com.cannabisgrowthsimulator.ui.FragAdapter;

public class InventoryActivity extends FragmentActivity {

ViewPager vp;
TabLayout tl;
MediaPlayer mp;
private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        vp = findViewById(R.id.viewPager);
        vp.setAdapter(new FragAdapter(getSupportFragmentManager()));
        tl = findViewById(R.id.tablayout);
        tl.setupWithViewPager(vp);
        mp = MediaPlayer.create(this,R.raw.shophang);
        intent = new Intent(InventoryActivity.this,Main2Activity.class);
    }

    @Override
    public void onBackPressed() {
       if(isTaskRoot()) {
           startActivity(intent);
           finish();
       }else
       super.onBackPressed();

    }

    public void backBack(View v){
        if(isTaskRoot()) {
            startActivity(intent);
            finish();
        }else
            finish();
    }

    public void playSound(){ mp.start();}


}