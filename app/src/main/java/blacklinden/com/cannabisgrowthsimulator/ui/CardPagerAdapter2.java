package blacklinden.com.cannabisgrowthsimulator.ui;

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
import blacklinden.com.cannabisgrowthsimulator.pojo.SoilEntity;

public class CardPagerAdapter2 extends PagerAdapter implements CardAdapter {

        private List<CardView> mViews;
        private List<CardItem2> mData;
        private float mBaseElevation;

        public CardPagerAdapter2() {
            mData = new ArrayList<>();
            mViews = new ArrayList<>();
        }

        private void addCardItem(CardItem2 item) {
            mViews.add(null);
            mData.add(item);
        }

    public void addLiveData(List<SoilEntity> soilEntities){
        mData.clear();
        for(SoilEntity soilEntity:soilEntities) {
            switch (soilEntity.getFajta()){
                case "dirt":
                    addCardItem(new CardItem2(1,R.string.dirt_soil,soilEntity.getFajta(),R.drawable.ic_dirtfromoutside));
                    break;

                case "worm":
                    addCardItem(new CardItem2(soilEntity.getMennyi(),R.string.soil01,soilEntity.getFajta(),R.drawable.ic_soil01));
                    break;

                case "super":
                    addCardItem(new CardItem2(soilEntity.getMennyi(),R.string.soil02,soilEntity.getFajta(),R.drawable.ic_soil02));
                    break;

                case "coco":
                    addCardItem(new CardItem2(soilEntity.getMennyi(),R.string.coco,soilEntity.getFajta(),R.drawable.ic_coco_yo));
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
                    .inflate(R.layout.adapter2, container, false);
            container.addView(view);
            bind(mData.get(position), view);
            CardView cardView = view.findViewById(R.id.cardView);

            if (mBaseElevation == 0) {
                mBaseElevation = cardView.getCardElevation();
            }

            cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
            mViews.set(position, cardView);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            mViews.set(position, null);
        }

        private void bind(CardItem2 item, View view) {
            TextView titleTextView =  view.findViewById(R.id.titleTextView);

            TextView type = view.findViewById(R.id.typeTV);
            ImageView imageView = view.findViewById(R.id.titleImage);
            TextView soilLeft = view.findViewById(R.id.soilLeft);
            soilLeft.setText(Integer.toString(item.getMennyi()));
            type.setTag(item.getMennyi());
            titleTextView.setText(item.getTitleRes());
            titleTextView.setTag(item.getType());
            type.setText(item.getType());
            imageView.setImageResource(item.getPic());
        }



    }
