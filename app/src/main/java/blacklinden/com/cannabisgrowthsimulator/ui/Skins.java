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
import blacklinden.com.cannabisgrowthsimulator.pojo.SkinEntity;
import blacklinden.com.cannabisgrowthsimulator.sql.SkinVM;
import blacklinden.com.cannabisgrowthsimulator.ui.medium.Skin;
import blacklinden.com.cannabisgrowthsimulator.ui.medium.SkinAdapter;

public class Skins extends Fragment {


    private SkinAdapter adapter;
    private ArrayList<Skin> skinArrayList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.skin_frag, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recy);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(),1));

        skinArrayList = new ArrayList<>();
        skinArrayList.add(new Skin("No Skin",R.drawable.defaultindex));
        adapter = new SkinAdapter(root.getContext(), skinArrayList, () -> ((InventoryActivity)Objects.requireNonNull(getActivity())).playSound());
        recyclerView.setAdapter(adapter);

        //observer interface helyett
        SkinVM skinVM = ViewModelProviders.of(this).get(SkinVM.class);
        skinVM.getAll().observe(this, this::createLiveData);


        //createListData();

        return root;
    }

    private void createLiveData(List<SkinEntity> skinEntities){
        for(SkinEntity s:skinEntities){

            switch (s.getFajta()){
                case "a":
                    skinArrayList.add(new Skin("a",R.drawable.jamaicaindex ));
                    break;

                case "b":
                    skinArrayList.add(new Skin("b",R.drawable.goaindex ));

                    break;

                case "c":
                    skinArrayList.add(new Skin("c",R.drawable.caveindex ));

                    break;


                case "d":
                    skinArrayList.add(new Skin("d",R.drawable.boxindex ));

                    break;


            }
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {


    }

}