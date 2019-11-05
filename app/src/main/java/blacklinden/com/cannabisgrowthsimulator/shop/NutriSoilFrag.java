package blacklinden.com.cannabisgrowthsimulator.shop;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import blacklinden.com.cannabisgrowthsimulator.R;

import blacklinden.com.cannabisgrowthsimulator.ShopActivity;
import blacklinden.com.cannabisgrowthsimulator.databinding.NutriFragShopBinding;
import blacklinden.com.cannabisgrowthsimulator.pojo.NutriEntity;
import blacklinden.com.cannabisgrowthsimulator.pojo.SoilEntity;
import blacklinden.com.cannabisgrowthsimulator.sql.NutriVM;
import blacklinden.com.cannabisgrowthsimulator.sql.ScoreVM;
import blacklinden.com.cannabisgrowthsimulator.sql.SoilVM;

public class NutriSoilFrag extends Fragment {

    private String rank;
    private ScoreVM scoreVM;
    private static List<String> rawRankLst;
    private final ArrayList<String> permanentItems = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rawRankLst = new ArrayList<>();
        rawRankLst.add("LEVEL 1");
        rawRankLst.add("LEVEL 2");
        rawRankLst.add("LEVEL 3");
        rawRankLst.add("LEVEL 4");
        rawRankLst.add("LEVEL 5");
        rawRankLst.add("LEVEL 6");

        permanentItems.add("BioGrow");
        permanentItems.add("Piranha");
        permanentItems.add("Root Juice");
        permanentItems.add("FloraQX");
        permanentItems.add("Nitro Frog");
        permanentItems.add("Coco Magic");
        permanentItems.add("coco");
        permanentItems.add("super");
        permanentItems.add("worm");

        scoreVM = ViewModelProviders.of(NutriSoilFrag.this).get(ScoreVM.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       NutriFragShopBinding binding =
                DataBindingUtil.inflate(
                        inflater, R.layout.nutri_frag_shop, container, false);


        HashMap<String,Integer> nutrik = new HashMap<>();
        HashMap<String,Integer> soils = new HashMap<>();

        NutriVM nutriVM =
                ViewModelProviders.of(NutriSoilFrag.this).get(NutriVM.class);
        nutriVM.getAll().observe(NutriSoilFrag.this,nutriEntities -> {
            if(nutriEntities!=null){
                for(NutriEntity m:nutriEntities) {

                    nutrik.computeIfAbsent(m.getFajta(),k-> m.getMennyi());
                    nutrik.computeIfPresent(m.getFajta(),(k,v)->v=m.getMennyi());
                }
            }
        });

        SoilVM soilVM = ViewModelProviders.of(this).get(SoilVM.class);
        soilVM.getAll().observe(NutriSoilFrag.this,soilEntities -> {
            if(soilEntities!=null){
                for(SoilEntity s:soilEntities){
                    soils.computeIfAbsent(s.getFajta(),k -> s.getMennyi());
                    soils.computeIfPresent(s.getFajta(), (k,v)->v=s.getMennyi());
                }
            }
        });



        ShopItemAdapter recyclerViewAdapter =
                new ShopItemAdapter(new ArrayList<>(), item -> {
                    ((ShopActivity)Objects.requireNonNull(getActivity())).playSound();
                    if(nutrik.containsKey(item.getName()))
                        nutriVM.update(item.getName(),nutrik.get(item.getName())+15);
                    else
                        nutriVM.insert(new NutriEntity(item.getName(),15));

                    if(!item.getName().equals("dirt")) {
                        if (soils.containsKey(item.getName()))
                            soilVM.update(item.getName(), soils.get(item.getName()) + 5);
                        else
                            soilVM.insert(new SoilEntity(item.getName(), 5));
                    }

                    scoreVM.updateScore(item.getPrice());
                });

        binding.reci1.setLayoutManager(
                new LinearLayoutManager(getActivity()));

        binding.reci1.setAdapter(recyclerViewAdapter);


        scoreVM.get().observe(NutriSoilFrag.this,scoreEntity -> {
            if (scoreEntity != null) {
                rank=scoreEntity.getRank();
                recyclerViewAdapter.setScore(scoreEntity.getScore());

                List<ShopItem> temp = new ArrayList<>();


                for(String ms:permanentItems){
                    switch (ms) {
                        case "BioGrow":
                            temp.add(new ShopItem("",ms, 10, R.drawable.ic_tapszer1, false));
                            break;
                        case "Piranha": temp.add(new ShopItem("",ms, 20, R.drawable.ic_tapszer2, !(rawRankLst.indexOf(rank)>=1)));
                            break;
                        case "Root Juice":
                            temp.add(new ShopItem("",ms, 20, R.drawable.ic_tapszer3,!(rawRankLst.indexOf(rank)>=2)));
                            break;
                        case "FloraQX":
                            temp.add(new ShopItem("",ms, 50, R.drawable.ic_tapszer4,!(rawRankLst.indexOf(rank)>=3)));
                            break;
                        case "Nitro Frog":
                            temp.add(new ShopItem("",ms,80,R.drawable.ic_nitrofrogtap,!(rawRankLst.indexOf(rank)>=3)));
                            break;
                        case "Coco Magic":
                            temp.add(new ShopItem("",ms,45,R.drawable.ic_cocohelper,!(rawRankLst.indexOf(rank)>=2)));
                            break;
                        case "coco":
                            temp.add(new ShopItem("",ms,25,R.drawable.ic_coco_yo,false));
                            break;
                        case "super":
                            temp.add(new ShopItem("",ms,45,R.drawable.ic_soil02,false));
                            break;
                        case "worm":
                            temp.add(new ShopItem("",ms,50,R.drawable.ic_soil01,false));
                            break;
                    }

                }


                recyclerViewAdapter.setShopItems(temp);
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });



        return binding.getRoot();
    }

}
