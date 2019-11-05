package blacklinden.com.cannabisgrowthsimulator.shop;



import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.Html;
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
import blacklinden.com.cannabisgrowthsimulator.databinding.LmpFragShopBinding;
import blacklinden.com.cannabisgrowthsimulator.pojo.Lampa;
import blacklinden.com.cannabisgrowthsimulator.sql.LampaVM;
import blacklinden.com.cannabisgrowthsimulator.sql.ScoreVM;

public class LampFrag extends Fragment {

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
        //permanentItems.add("e");
        permanentItems.add("f");
        permanentItems.add("g");
        permanentItems.add("h");

        scoreVM = ViewModelProviders.of(LampFrag.this).get(ScoreVM.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LmpFragShopBinding binding =
                DataBindingUtil.inflate(
                        inflater, R.layout.lmp_frag_shop, container, false);


        LampaVM lampaVM =
                ViewModelProviders.of(LampFrag.this).get(LampaVM.class);


        ShopItemAdapter recyclerViewAdapter =
                new ShopItemAdapter(new ArrayList<>(), item -> {
                    lampaVM.insert(new Lampa(item.getSrName()));
                    scoreVM.updateScore(item.getPrice());
                    ((ShopActivity)Objects.requireNonNull(getActivity())).playSound();
                });

        binding.frag2recy.setLayoutManager(
                new LinearLayoutManager(getActivity()));

        binding.frag2recy.setAdapter(recyclerViewAdapter);


        scoreVM.get().observe(LampFrag.this,scoreEntity -> {
            if (scoreEntity != null) {
                rank = scoreEntity.getRank();
                recyclerViewAdapter.setScore(scoreEntity.getScore());

            }
            });

        lampaVM.getAll().observe(
                LampFrag.this, lampas -> {
                    if (lampas != null) {
                        List<ShopItem> temp = new ArrayList<>();

                        List<String> entityNames = new ArrayList<>();
                        for(Lampa l:lampas) entityNames.add(l.getFajta());
                        List<String> result = permanentItems.stream()
                                .filter(not(new HashSet<>(entityNames)::contains))
                                .collect(Collectors.toList());
                        for(String ms:result) {
                            String s;
                            switch (ms) {
                                case "a":
                                    temp.add(new ShopItem(ms,Html.fromHtml(getString(R.string.a), Html.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH).toString(), 2300, R.drawable.blue_led, !(rawRankLst.indexOf(rank)>=2)));
                                break;
                                case "b": temp.add(new ShopItem(ms,Html.fromHtml(getString(R.string.b), Html.FROM_HTML_MODE_LEGACY).toString(), 2000, R.drawable.ic_rrr, !(rawRankLst.indexOf(rank)>=3)));
                                break;
                                case "c":
                                    temp.add(new ShopItem(ms,Html.fromHtml(getString(R.string.c), Html.FROM_HTML_MODE_LEGACY).toString(), 500, R.drawable.ic_light_bulb_technology_svgrepo_com,!(rawRankLst.indexOf(rank)>=1)));
                                break;
                                case "d":
                                    temp.add(new ShopItem(ms,Html.fromHtml(getString(R.string.d), Html.FROM_HTML_MODE_LEGACY).toString(), 1600, R.drawable.bio_glow,!(rawRankLst.indexOf(rank)>=2)));
                                break;
                                case "f":
                                    temp.add(new ShopItem(ms,Html.fromHtml(getString(R.string.f), Html.FROM_HTML_MODE_LEGACY).toString(), 1000, R.drawable.hps_1000w_avd,!(rawRankLst.indexOf(rank)>=2)));
                                    break;
                                case "g":
                                    temp.add(new ShopItem(ms,Html.fromHtml(getString(R.string.g), Html.FROM_HTML_MODE_LEGACY).toString(),900,R.drawable.volatile_hps,!(rawRankLst.indexOf(rank)>=5)));
                                    break;
                                case "h":
                                    temp.add(new ShopItem(ms,Html.fromHtml(getString(R.string.h), Html.FROM_HTML_MODE_LEGACY).toString(),1500,R.drawable.metal_halide_175w,!(rawRankLst.indexOf(rank)>=1)));
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

