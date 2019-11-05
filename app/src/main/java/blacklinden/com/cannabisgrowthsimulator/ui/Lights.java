package blacklinden.com.cannabisgrowthsimulator.ui;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import blacklinden.com.cannabisgrowthsimulator.InventoryActivity;
import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.pojo.Lampa;
import blacklinden.com.cannabisgrowthsimulator.sql.LampaVM;
import blacklinden.com.cannabisgrowthsimulator.ui.medium.Elem;
import blacklinden.com.cannabisgrowthsimulator.ui.medium.ElemAdapter;

public class Lights extends Fragment {


    private ElemAdapter adapter;
    private ArrayList<Elem> lampArrayList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.lampa_frag, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recy);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(),1));

        lampArrayList = new ArrayList<>();
        lampArrayList.add(new Elem("Desk Bulb","CFL",60,4700,3000,
                R.drawable.cflkek,R.drawable.feher_csova,Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_ggg)
        ));
        adapter = new ElemAdapter(root.getContext(), lampArrayList, () -> ((InventoryActivity)Objects.requireNonNull(getActivity())).playSound());
        recyclerView.setAdapter(adapter);

        LampaVM lampaVM = ViewModelProviders.of(this).get(LampaVM.class);
        lampaVM.getAll().observe(this, this::createLiveData);

        return root;
    }

    private void createLiveData(List<Lampa> lampas){
        for(Lampa l:lampas){

            switch (l.getFajta()){
                case "a":
                    lampArrayList.add(new Elem("Electric Blue", "LED", 250, 4500, 13500,
                            R.drawable.blue_led,R.drawable.feher_csova, Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_jjj)
                    ));
                    break;

                case "b":
                    lampArrayList.add(new Elem("PRO STAR","LED", 200, 6600, 11200,
                            R.drawable.fullspec_led,R.drawable.lila_csova,Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_rrr)
                   ));

                    break;

                case "c":
                    lampArrayList.add(new Elem("CFL Bloom","CFL", 150, 2700, 7850,
                            R.drawable.cfl_yellow,R.drawable.httr_vill001,Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_light_bulb_technology_svgrepo_com)
                    ));
                    break;

                case "d":

                    lampArrayList.add(new Elem("Bio Glow","LED",170,6600,9000,
                            R.drawable.bio_glow,R.drawable.lila_csova,Objects.requireNonNull(getContext()).getDrawable(R.drawable.bio_glow)
                    ));
                    break;

                case "f":
                    lampArrayList.add(new Elem("Sodium 1000","HPS",1000,2700,12000,
                            R.drawable.hps_1000w_avd,R.drawable.narancs_csova,Objects.requireNonNull(getContext()).getDrawable(R.drawable.hps_1000w_avd)
                    ));
                    break;

                case "g":
                    lampArrayList.add(new Elem("Volatile HPS","HPS",600,2100,15000,
                            R.drawable.volatile_hps,R.drawable.narancs_csova,Objects.requireNonNull(getContext()).getDrawable(R.drawable.volatile_hps)
                    ));
                    break;

                case "h":
                    lampArrayList.add(new Elem("Metal Halide Bulb","HALOGEN",1750,2500,6000,
                            R.drawable.metal_halide_175w,R.drawable.narancs_csova,Objects.requireNonNull(getContext()).getDrawable(R.drawable.metal_halide_175w)
                    ));
                    break;

            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


    }

}