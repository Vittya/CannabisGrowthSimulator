package blacklinden.com.cannabisgrowthsimulator.ui;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.pojo.Combo;
import blacklinden.com.cannabisgrowthsimulator.pojo.ComboEntity;
import blacklinden.com.cannabisgrowthsimulator.sql.ComboVM;

public class ComboFragment extends Fragment {


    private ComboAdapter adapter;
    private ComboPagerAdapter pagerAdapter;
    private ArrayList<Combo> comboArrayList;
    private ViewPager vp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.combo_frag, container, false);

        vp = root.findViewById(R.id.comboVP);
        List<Combo> comboList = new ArrayList<>();
        //for(int i=0;i<18;i++) comboList.add(new Combo("S_S_S",0));
        pagerAdapter = new ComboPagerAdapter(comboList);
        vp.setAdapter(pagerAdapter);
        vp.setOffscreenPageLimit(6);
        TextView tierTv = root.findViewById(R.id.textView2);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tierTv.setText(String.format(Locale.ENGLISH,"Tier %d", position + 1));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        RecyclerView recyclerView = root.findViewById(R.id.recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(3);
        recyclerView.setDrawingCacheEnabled(true);

        comboArrayList = new ArrayList<>();

        adapter = new ComboAdapter(comboArrayList);
        recyclerView.setAdapter(adapter);

        ComboVM comboVM = ViewModelProviders.of(this).get(ComboVM.class);
        comboVM.getAll().observe(this,comboEntities -> {
            if(comboEntities!=null) {


                createLiveData(comboEntities);
                createLiveDataForPagerAdapter(comboEntities);

            }
        });

        return root;
    }

    private void createLiveData(List<ComboEntity> combos){
        List<Combo> comboList = new ArrayList<>();
        for(ComboEntity combo:combos){
            comboList.add(new Combo(combo.getCombo(),0));
        }

        adapter.setCombos(comboList);
        adapter.notifyDataSetChanged();
    }

    private void createLiveDataForPagerAdapter(List<ComboEntity> combos){
        List<Combo> comboList = new ArrayList<>();
        for(int i=0;i<18;i++)
            comboList.add(new Combo("S_S_S",0));
        combos.forEach(comboEntity -> {
            Combo combo = new Combo(comboEntity.getCombo(),0);
            //pagerAdapter.setEachAdat(combo);

            comboList.set((combo.getPlacmentCode()[1]-1)+combo.getPlacmentCode()[0],combo);

        });

        pagerAdapter.setAdat(comboList);

        pagerAdapter.notifyDataSetChanged();

    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


    }


}

