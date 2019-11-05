package blacklinden.com.cannabisgrowthsimulator.ui;

import androidx.lifecycle.ViewModelProviders;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.pojo.MagEntity;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    private void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public void addLiveData(List<MagEntity> magvak){
        mData.clear();
        for(MagEntity mag:magvak) {
            switch (mag.getFajta()){
                case "a":
                    addCardItem(new CardItem(mag.getFajta(),R.string.title_1, mag.getMennyi(),"9%","Low yield","Sativa Hybrid",1,R.drawable.ic_skunk_n1));
                    break;

                case "b":
                    addCardItem(new CardItem(mag.getFajta(),(R.string.afgan), mag.getMennyi(),"12%","High yield","Indica",1,R.drawable.ic_afghan));
                    break;

                case "c":
                    addCardItem(new CardItem(mag.getFajta(),R.string.shit,mag.getMennyi(),"8%","Mid yield","Sativa",1,R.drawable.ic_balkan_rose));
                    break;

                case "d":
                    addCardItem(new CardItem(mag.getFajta(),R.string.blueb,mag.getMennyi(),"14%","Mid yield","Indica",2,R.drawable.ic_blueberry_icon));
                    break;

                case "e":
                    addCardItem(new CardItem(mag.getFajta(),R.string.title_northernlight,mag.getMennyi(),"11%","High yield","Indica",2,R.drawable.ic_northern_light_icon));
                    break;

                case "f":
                    addCardItem(new CardItem(mag.getFajta(),R.string.grapeape,mag.getMennyi(),"15%","Mid yield","Indica",2,R.drawable.ic_grape_ape_icon));
                    break;
                    
                case "g":
                    addCardItem(new CardItem(mag.getFajta(),R.string.ak,mag.getMennyi(),"16%","High yield","Sativa Hybrid",2,R.drawable.ic_ak47));
                    break;
                case "h":
                    addCardItem(new CardItem(mag.getFajta(),R.string.cheese,mag.getMennyi(),"17%","Mid yield","Indica Hybrid",3,R.drawable.ic_cheese_icon));
                    break;
                case "i":
                    addCardItem(new CardItem(mag.getFajta(),R.string.amnesia,mag.getMennyi(),"15%","Low yield","Sativa",3,R.drawable.ic_amnesia_icon));
                    break;
                case "j":
                    addCardItem(new CardItem(mag.getFajta(),R.string.lemon,mag.getMennyi(),"18%","High yield","Sativa",3,R.drawable.ic_super_lemon_haze));
                    break;
                case "k":
                    addCardItem(new CardItem(mag.getFajta(),R.string.widow,mag.getMennyi(),"13%","High yield","Hybrid",3,R.drawable.ic_white_widow_icon));
                    break;
                case "l":
                    addCardItem(new CardItem(mag.getFajta(),R.string.gelato,mag.getMennyi(),"16%","Low yield","Hybrid",4,R.drawable.ic_gelato_icon));
                    break;
                case "m":
                    addCardItem(new CardItem(mag.getFajta(),R.string.ghost,mag.getMennyi(),"20%","Mid yield","Hybrid",4,R.drawable.ic_ghost_og_icon));
                    break;
                case "n":
                    addCardItem(new CardItem(mag.getFajta(),R.string.cdiesel,mag.getMennyi(),"18%","High yield","Hybrid",4,R.drawable.ic_cherry_diesel_icon));
                    break;
                case "o":
                    addCardItem(new CardItem(mag.getFajta(),(R.string.permfrst),mag.getMennyi(),"21%","High yield","Sativa Hybrid",5,R.drawable.ic_permafrost_icon));
                    break;
                case "p":
                    addCardItem(new CardItem(mag.getFajta(),(R.string.pinkmng),mag.getMennyi(),"17%","High yield","Indica Hybrid",5,R.drawable.ic_pink_mango_icon));
                    break;
                case "q":
                    addCardItem(new CardItem(mag.getFajta(),(R.string.pineapple),mag.getMennyi(),"19%","Mid yield","Sativa Hybrid",5,R.drawable.ic_pineapple_express));
                    break;
                case "r":
                    addCardItem(new CardItem(mag.getFajta(),(R.string.gws),mag.getMennyi(),"20%","High yield","Sativa Hybrid",5,R.drawable.ic_gws_icon));
                    break;
                case "s":
                    addCardItem(new CardItem(mag.getFajta(),(R.string.nrs_ratchet),mag.getMennyi(),"22%","High yield","Indica",5,R.drawable.ic_nrs_icon));
                    break;
                case "t":
                    addCardItem(new CardItem(mag.getFajta(),(R.string.drbn),mag.getMennyi(),"24%","High yield","Sativa",5,R.drawable.ic_durban_poison_icon));
                    break;
            }
        }

    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view,position);
        CardView cardView = view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);

        view.setTag(position);



        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(CardItem item, View view,int position) {
        TextView titleTextView =  view.findViewById(R.id.titleTextView);
        TextView contentTextView =  view.findViewById(R.id.thc);
        TextView yield = view.findViewById(R.id.yield);
        TextView reward = view.findViewById(R.id.reward);
        TextView strainType = view.findViewById(R.id.strainType);
        TextView flower = view.findViewById(R.id.flower);
        ImageView imageView = view.findViewById(R.id.titleImage);
        TextView darab = view.findViewById(R.id.seedsLeft);
        titleTextView.setText(item.getTitle());
        contentTextView.setText(" "+item.getThc());
        yield.setText(item.getYield());
        reward.setText(" "+item.getReward()+"X");
        reward.setTag(item.getReward());
        strainType.setText(item.getStrainType());
        imageView.setImageResource(item.getPic());
        flower.setTag(item.getCd());
        darab.setText(Integer.toString(item.getDb()));
        darab.setTag(Integer.toString(item.getDb()));


    }

    public int getDrawCode(int pos){
        return mData.get(pos).getPic();
    }



}