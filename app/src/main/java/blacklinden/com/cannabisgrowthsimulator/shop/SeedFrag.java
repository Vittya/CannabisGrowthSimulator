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
import blacklinden.com.cannabisgrowthsimulator.databinding.SdFragShopBinding;
import blacklinden.com.cannabisgrowthsimulator.pojo.MagEntity;
import blacklinden.com.cannabisgrowthsimulator.sql.MagVM;
import blacklinden.com.cannabisgrowthsimulator.sql.ScoreVM;

public class SeedFrag extends Fragment {
    int fragVal;
    private String rank;
    private ScoreVM scoreVM;
    private static List<String> rawRankLst;
    private final ArrayList<String> permanentItems = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
        rawRankLst = new ArrayList<>();
        rawRankLst.add("LEVEL 1");
        rawRankLst.add("LEVEL 2");
        rawRankLst.add("LEVEL 3");
        rawRankLst.add("LEVEL 4");
        rawRankLst.add("LEVEL 5");
        rawRankLst.add("LEVEL 6");

        permanentItems.add("a");
        permanentItems.add("b");
        permanentItems.add("c");
        permanentItems.add("d");
        permanentItems.add("e");
        permanentItems.add("f");
        permanentItems.add("g");
        permanentItems.add("h");
        permanentItems.add("i");
        permanentItems.add("j");
        permanentItems.add("k");
        permanentItems.add("l");
        permanentItems.add("m");
        permanentItems.add("n");
        permanentItems.add("o");
        permanentItems.add("p");
        permanentItems.add("q");
        permanentItems.add("r");
        permanentItems.add("s");
        permanentItems.add("t");


        scoreVM = ViewModelProviders.of(SeedFrag.this).get(ScoreVM.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SdFragShopBinding binding =
                DataBindingUtil.inflate(
                        inflater, R.layout.sd_frag_shop, container, false);


        HashMap<String,Integer> magvak = new HashMap<>();

        MagVM magVM =
                ViewModelProviders.of(SeedFrag.this).get(MagVM.class);
        magVM.getAll().observe(SeedFrag.this,magEntities -> {
            if(magEntities!=null){
                for(MagEntity m:magEntities)
                    magvak.put(m.getFajta(),m.getMennyi());
            }
        });

        ShopItemAdapter recyclerViewAdapter =
                new ShopItemAdapter(new ArrayList<>(), item -> {
                    ((ShopActivity)Objects.requireNonNull(getActivity())).playSound();
                    if(magvak.containsKey(item.getSrName())) {
                        magVM.update(item.getSrName(), magvak.get(item.getSrName()) + 3);

                    }else
                    magVM.insert(new MagEntity(item.getSrName(),3));
                    scoreVM.updateScore(item.getPrice());
                });

        binding.frag1recy.setLayoutManager(
                new LinearLayoutManager(getActivity()));

        binding.frag1recy.setAdapter(recyclerViewAdapter);


        scoreVM.get().observe(SeedFrag.this,scoreEntity -> {
            if (scoreEntity != null) {
                rank=scoreEntity.getRank();
                recyclerViewAdapter.setScore(scoreEntity.getScore());

                        List<ShopItem> temp = new ArrayList<>();


                        for(String ms:permanentItems){
                            switch (ms) {
                                case "a":
                                        temp.add(new ShopItem(ms,"Skunk", 5, R.drawable.ic_skunk_n1, false));
                                    break;
                                case "b":
                                        temp.add(new ShopItem(ms,"Afghani", 5, R.drawable.ic_afghan, false));
                                    break;
                                case "c":
                                        temp.add(new ShopItem(ms,"Balkan Rose", 3, R.drawable.ic_balkan_rose,false));
                                    break;
                                case "d":
                                        temp.add(new ShopItem(ms,"BlueBerry", 15, R.drawable.ic_blueberry_icon,!(rawRankLst.indexOf(rank)>=1)));
                                    break;
                                case "e":
                                        temp.add(new ShopItem(ms,"Northern Light", 10, R.drawable.ic_northern_light_icon,!(rawRankLst.indexOf(rank)>=1)));
                                    break;
                                case "f":
                                        temp.add(new ShopItem(ms,"Grape Ape", 22, R.drawable.ic_grape_ape_icon,!(rawRankLst.indexOf(rank)>=1)));
                                    break;
                                case "g":
                                        temp.add(new ShopItem(ms,"AK 47", 25, R.drawable.ic_ak47,!(rawRankLst.indexOf(rank)>=2)));
                                    break;
                                case "h":
                                        temp.add(new ShopItem(ms,"Cheese", 20, R.drawable.ic_cheese_icon,!(rawRankLst.indexOf(rank)>=2)));
                                    break;
                                case "i":
                                        temp.add(new ShopItem(ms,"Amnesia", 17, R.drawable.ic_amnesia_icon,!(rawRankLst.indexOf(rank)>=2)));
                                    break;
                                case "j":
                                        temp.add(new ShopItem(ms,"Super Lemon Haze", 30, R.drawable.ic_super_lemon_haze,!(rawRankLst.indexOf(rank)>=3)));
                                    break;
                                case "k":
                                        temp.add(new ShopItem(ms,"White Widow", 25, R.drawable.ic_white_widow_icon,!(rawRankLst.indexOf(rank)>=3)));
                                    break;
                                case "l":
                                        temp.add(new ShopItem(ms,"Gelato", 33, R.drawable.ic_gelato_icon,!(rawRankLst.indexOf(rank)>=3)));
                                    break;
                                case "m":
                                        temp.add(new ShopItem(ms,"Ghost OG", 45, R.drawable.ic_ghost_og_icon,!(rawRankLst.indexOf(rank)>=4)));
                                    break;
                                case "n":
                                        temp.add(new ShopItem(ms,"Cherry Diesel", 35, R.drawable.ic_cherry_diesel_icon,!(rawRankLst.indexOf(rank)>=4)));
                                    break;
                                case "o":
                                        temp.add(new ShopItem(ms,"Permafrost", 45, R.drawable.ic_permafrost_icon,!(rawRankLst.indexOf(rank)>=4)));
                                    break;
                                case "p":
                                        temp.add(new ShopItem(ms,"Pink Mango", 50, R.drawable.ic_pink_mango_icon,!(rawRankLst.indexOf(rank)>=5)));
                                    break;
                                case "q":
                                        temp.add(new ShopItem(ms,"Pineapple", 50, R.drawable.ic_pineapple_express,!(rawRankLst.indexOf(rank)>=5)));
                                    break;
                                case "r":
                                        temp.add(new ShopItem(ms,"Great White Shark", 65, R.drawable.ic_gws_icon,!(rawRankLst.indexOf(rank)>=5)));
                                    break;
                                case "s":
                                    temp.add(new ShopItem(ms,"Nurse Ratchet", 50, R.drawable.ic_nrs_icon,false));
                                    break;
                                case "t":
                                    temp.add(new ShopItem(ms,"Durban Poison", 65, R.drawable.ic_durban_poison_icon,false));
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