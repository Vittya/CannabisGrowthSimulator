package blacklinden.com.cannabisgrowthsimulator.ui;

import android.app.Fragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import blacklinden.com.cannabisgrowthsimulator.R;

public class WalkThruFrag extends Fragment {
    private CarouselView carouselView;
    private int[] drawCodes;
    private Visible visible;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.walkthru_frag,container,false);
        carouselView = view.findViewById(R.id.carouselView);

        carouselView.setCurrentItem(0,false);
        Button button = view.findViewById(R.id.fragOutButton);
        button.setOnClickListener(view1 -> {
            visible.invisibility();
        });
        return view;
    }

    public void setImageForCarousel(int[] drawCodes){
        this.drawCodes=drawCodes;
        carouselView.setPageCount(drawCodes.length);
        carouselView.setImageListener(imageListener);
    }

    public void setVisibilityListener(Visible visible){
        this.visible=visible;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setAdjustViewBounds(true);
            imageView.setImageResource(drawCodes[position]);
        }
    };

   public interface Visible{
        void invisibility();
    }
}
