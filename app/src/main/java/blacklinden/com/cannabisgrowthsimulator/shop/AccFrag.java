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
import blacklinden.com.cannabisgrowthsimulator.databinding.AxFragShopBinding;
import blacklinden.com.cannabisgrowthsimulator.pojo.AccessoryEntity;

import blacklinden.com.cannabisgrowthsimulator.sql.AccVM;
import blacklinden.com.cannabisgrowthsimulator.sql.ScoreVM;

public class AccFrag extends Fragment {

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
        permanentItems.add("e");
        permanentItems.add("f");
        permanentItems.add("g");
        permanentItems.add("h");
        permanentItems.add("i");
        permanentItems.add("j");
        permanentItems.add("k");
        permanentItems.add("l");


        scoreVM = ViewModelProviders.of(AccFrag.this).get(ScoreVM.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AxFragShopBinding binding =
                DataBindingUtil.inflate(
                        inflater, R.layout.ax_frag_shop, container, false);


        AccVM accVM =
                ViewModelProviders.of(AccFrag.this).get(AccVM.class);




        ShopItemAdapter recyclerViewAdapter =
                new ShopItemAdapter(new ArrayList<>(), item -> {
                    accVM.insert(new AccessoryEntity(item.getSrName()));
                    scoreVM.updateScore(item.getPrice());
                    ((ShopActivity)Objects.requireNonNull(getActivity())).playSound();
                });




        scoreVM.get().observe(AccFrag.this, scoreEntity -> {
            if (scoreEntity != null) {
                rank = scoreEntity.getRank();
                recyclerViewAdapter.setScore(scoreEntity.getScore());

            }
        });

        binding.frag2recy.setLayoutManager(
                new LinearLayoutManager(getActivity()));

        binding.frag2recy.setAdapter(recyclerViewAdapter);


        accVM.getAll().observe(
                AccFrag.this, accessoryEntities -> {
                    if (accessoryEntities != null) {
                        List<ShopItem> temp = new ArrayList<>();

                        List<String> entityNames = new ArrayList<>();
                        for(AccessoryEntity a:accessoryEntities) entityNames.add(a.getFajta());
                        List<String> result = permanentItems.stream()
                                .filter(not(new HashSet<>(entityNames)::contains))
                                .collect(Collectors.toList());
                        for(String ms:result) {
                            switch (ms) {
                                case "a":
                                    temp.add(new ShopItem(ms,"Cauldron", 1500, R.drawable.ic_cserep1, !(rawRankLst.indexOf(rank)>=4)));
                                    break;
                                case "b": temp.add(new ShopItem(ms,"PotHead Bucket", 250, R.drawable.ic_pothead_pot, !(rawRankLst.indexOf(rank)>=2)));
                                    break;
                                case "c":
                                    temp.add(new ShopItem(ms,"Flower Pot", 20, R.drawable.ic_cserep4,false));
                                    break;
                                case "d":
                                    temp.add(new ShopItem(ms,"Red Vase", 50, R.drawable.ic_cserep5,false));
                                    break;
                                case "e":
                                    temp.add(new ShopItem(ms,"Holy Grail", 1000, R.drawable.ic_cserep6,!(rawRankLst.indexOf(rank)>=3)));
                                    break;
                                case "f":
                                    temp.add(new ShopItem(ms,"Air Raid Siren", 300, R.drawable.ic_cserep7,!(rawRankLst.indexOf(rank)>=3)));
                                    break;
                                case "g":
                                    temp.add(new ShopItem(ms,"Polygon", 150, R.drawable.ic_cserep8,!(rawRankLst.indexOf(rank)>=3)));
                                    break;
                                case "h":
                                    temp.add(new ShopItem(ms,"Unknown Error", 600, R.drawable.ic_cserep9,!(rawRankLst.indexOf(rank)>=2)));
                                    break;
                                case "i":
                                    temp.add(new ShopItem(ms,"Rusty", 50, R.drawable.ic_rusty_kanna,!(rawRankLst.indexOf(rank)>=3)));
                                    break;
                                case "j":
                                    temp.add(new ShopItem(ms,"TinyTin Can", 20, R.drawable.ic_kanna_n1,!(rawRankLst.indexOf(rank)>=2)));
                                    break;
                                case "k":
                                    temp.add(new ShopItem(ms,"Marley's TeaPot", 150, R.drawable.ic_kanna004,!(rawRankLst.indexOf(rank)>=1)));
                                    break;
                                case "l":
                                    temp.add(new ShopItem(ms,"Coffee Dispenser", 30, R.drawable.ic_kanna006,!(rawRankLst.indexOf(rank)>=3)));
                                    break;
                            }

                        }
                        recyclerViewAdapter.setShopItems(temp);
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                });




        return binding.getRoot();
    }

    private static <T> Predicate<T> not(Predicate<T> predicate) {
        return predicate.negate();
    }
}