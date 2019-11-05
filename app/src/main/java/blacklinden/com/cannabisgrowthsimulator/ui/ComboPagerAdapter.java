package blacklinden.com.cannabisgrowthsimulator.ui;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.pojo.Combo;

public class ComboPagerAdapter extends PagerAdapter {
    private List<Combo> adat;
    ComboPagerAdapter(List<Combo> comboList){
        adat = comboList;

    }
    @Override
    public int getCount() {
        return 6;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.combo_pager,container,false);
        container.addView(view);
       for(Combo combo:adat) {
            if(combo.getPlacmentCode()[0]==position)
            bind(combo, view, position);
        }

        return view;
    }

    void setAdat(List<Combo> combos){
        adat=combos;
        notifyDataSetChanged();

    }

    void setEachAdat(Combo combo){
        if(adat.size()>(combo.getPlacmentCode()[0]+combo.getPlacmentCode()[1]))
        adat.set(combo.getPlacmentCode()[0]+combo.getPlacmentCode()[1],combo);
        else
        adat.add(combo);
    }

    private void bind(Combo combo, View view, int position) {

        ImageView[] fels = new ImageView[]{view.findViewById(R.id.f1),view.findViewById(R.id.f2),view.findViewById(R.id.f3)};
        ImageView[] kzps = new ImageView[]{view.findViewById(R.id.k1),view.findViewById(R.id.k2),view.findViewById(R.id.k3)};
        ImageView[] als = new ImageView[]{view.findViewById(R.id.a1),view.findViewById(R.id.a2),view.findViewById(R.id.a3)};


            switch (combo.getPlacmentCode()[1]){
                case 1:
                    iconPlacer(combo,fels);
                    break;
                case 2:
                    iconPlacer(combo,kzps);
                    break;
                case 3:
                    iconPlacer(combo,als);
                    break;
            }

    }

    private void iconPlacer(Combo combo,ImageView[] c){
        for(int i=0;i<combo.getCombo().length;i++) {
            switch (combo.getCombo()[i]) {
                case "Skunk":
                    c[i].setImageResource(R.drawable.ic_skunk_n1);
                    break;
                case "Afghan":
                    c[i].setImageResource(R.drawable.ic_afghan);
                    break;
                case "Balkan Rose":
                    c[i].setImageResource(R.drawable.ic_balkan_rose);
                    break;
                case "BlueBerry":
                    c[i].setImageResource(R.drawable.ic_blueberry_icon);
                    break;
                case "Northern Light":
                    c[i].setImageResource(R.drawable.ic_northern_light_icon);
                    break;
                case "Grape Ape":
                    c[i].setImageResource(R.drawable.ic_grape_ape_icon);
                    break;
                case "AK47":
                    c[i].setImageResource(R.drawable.ic_ak47);
                    break;
                case "Cheese":
                    c[i].setImageResource(R.drawable.ic_cheese_icon);
                    break;
                case "Amnesia":
                    c[i].setImageResource(R.drawable.ic_amnesia_icon);
                    break;
                case "S.Lemon Haze":
                    c[i].setImageResource(R.drawable.ic_super_lemon_haze);
                    break;
                case "White Widow":
                    c[i].setImageResource(R.drawable.ic_white_widow_icon);
                    break;
                case "Gelato":
                    c[i].setImageResource(R.drawable.ic_gelato_icon);
                    break;
                case "Ghost OG":
                    c[i].setImageResource(R.drawable.ic_ghost_og_icon);
                    break;
                case "Cherry Diesel":
                    c[i].setImageResource(R.drawable.ic_cherry_diesel_icon);
                    break;
                case "Permafrost":
                    c[i].setImageResource(R.drawable.ic_permafrost_icon);
                    break;
                case "Pink Mango":
                    c[i].setImageResource(R.drawable.ic_pink_mango_icon);
                    break;
                case "Pineapple":
                    c[i].setImageResource(R.drawable.ic_pineapple_express);
                    break;
                case "G.White Shark":
                    c[i].setImageResource(R.drawable.ic_gws_icon);
                    break;
            }
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
