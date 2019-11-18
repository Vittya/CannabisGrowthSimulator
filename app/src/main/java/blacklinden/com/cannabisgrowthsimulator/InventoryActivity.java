package blacklinden.com.cannabisgrowthsimulator;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.ImageView;

import blacklinden.com.cannabisgrowthsimulator.ui.FragAdapter;

public class InventoryActivity extends FragmentActivity {

ViewPager vp;
MediaPlayer mp;
private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        vp = findViewById(R.id.viewPager);
        vp.setAdapter(new FragAdapter(getSupportFragmentManager()));
        mp = MediaPlayer.create(this,R.raw.shophang);
        intent = new Intent(InventoryActivity.this,Main2Activity.class);

        final ImageView[] vpTmb = {
                findViewById(R.id.vp1),
                findViewById(R.id.vp2),
                findViewById(R.id.vp3),
                findViewById(R.id.vp4),
                findViewById(R.id.vp5)
        };
        vpTmb[0].setColorFilter(Color.RED, PorterDuff.Mode.XOR);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<vpTmb.length;i++) {
                    if (i!=position) vpTmb[i].clearColorFilter();
                    else vpTmb[position].setColorFilter(Color.BLUE,PorterDuff.Mode.XOR);;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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


    public void vpScroll(View view) {
        vp.setCurrentItem(Integer.parseInt((String)view.getTag()),true);
    }
}