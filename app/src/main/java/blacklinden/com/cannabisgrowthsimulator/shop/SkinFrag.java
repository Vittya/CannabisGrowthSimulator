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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import blacklinden.com.cannabisgrowthsimulator.R;

import blacklinden.com.cannabisgrowthsimulator.ShopActivity;
import blacklinden.com.cannabisgrowthsimulator.databinding.SknFragShopBinding;
import blacklinden.com.cannabisgrowthsimulator.pojo.SkinEntity;
import blacklinden.com.cannabisgrowthsimulator.sql.ScoreVM;
import blacklinden.com.cannabisgrowthsimulator.sql.SkinVM;

public class SkinFrag extends Fragment {

    private String rank;
    private ScoreVM scoreVM;
    private final ArrayList<String> permanentItems = new ArrayList<>();
    private static final List<String> rawRankLst = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        scoreVM = ViewModelProviders.of(SkinFrag.this).get(ScoreVM.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SknFragShopBinding binding =
                DataBindingUtil.inflate(
                        inflater, R.layout.skn_frag_shop, container, false);




        SkinVM skinVM = ViewModelProviders.of(SkinFrag.this).get(SkinVM.class);



        ShopItemAdapter recyclerViewAdapter2 =
                new ShopItemAdapter(new ArrayList<>(),item -> {
                    skinVM.insert(new SkinEntity(item.getSrName()));
                    scoreVM.updateScore(item.getPrice());
                    ((ShopActivity)Objects.requireNonNull(getActivity())).playSound();
                });

        binding.frag2recy.setLayoutManager(
                new LinearLayoutManager(getActivity()));

        binding.frag2recy.setAdapter(recyclerViewAdapter2);



        scoreVM.get().observe(SkinFrag.this, scoreEntity -> {
            if (scoreEntity != null) {
                rank = scoreEntity.getRank();
                recyclerViewAdapter2.setScore(scoreEntity.getScore());
            }
        });



        skinVM.getAll().observe(
                SkinFrag.this, skinEntities -> {
                    if (skinEntities != null) {
                        List<ShopItem> temp = new ArrayList<>();
                        List<String> entityNames = new ArrayList<>();
                        for(SkinEntity s:skinEntities) entityNames.add(s.getFajta());
                        List<String> result = permanentItems.stream()
                                .filter(not(new HashSet<>(entityNames)::contains))
                                .collect(Collectors.toList());
                        for(String ms:result) {
                            switch (ms) {
                                case "a":
                                    temp.add(new ShopItem(ms,"Marleys", 3000, R.drawable.jamaicaindex, false));
                                    break;
                                case "b": temp.add(new ShopItem(ms,"Goa", 2000, R.drawable.goaindex, false));
                                    break;
                                case "c": temp.add(new ShopItem(ms,"Hermit", 1000, R.drawable.caveindex, false));
                                    break;
                                case "d": temp.add(new ShopItem(ms,"HiTech", 2000, R.drawable.boxindex, false));
                                    break;
                            }

                        }
                        recyclerViewAdapter2.setShopItems(temp);
                        recyclerViewAdapter2.notifyDataSetChanged();
                    }
                });



        return binding.getRoot();
    }

    private static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }

}
