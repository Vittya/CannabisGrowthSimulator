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
import blacklinden.com.cannabisgrowthsimulator.pojo.AccessoryEntity;
import blacklinden.com.cannabisgrowthsimulator.sql.AccVM;
import blacklinden.com.cannabisgrowthsimulator.ui.medium.Elem;
import blacklinden.com.cannabisgrowthsimulator.ui.medium.PotAdapter;

public class Accessories extends Fragment {

    private PotAdapter adapter;
    private ArrayList<Elem> arrayList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.kieg_frag, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(),1));

        arrayList = new ArrayList<>();
        arrayList.add(new Elem("Standard Blue", 600,"Fast","Common",R.drawable.ic_kanna001,
                Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_kanna001)));
        arrayList.add(new Elem("Brown Plastic", 800,"Slow","Epic",R.drawable.ic_cserep2,
                Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_cserep2)));
        adapter = new PotAdapter(root.getContext(), arrayList, () -> ((InventoryActivity)Objects.requireNonNull(getActivity())).playSound());
        recyclerView.setAdapter(adapter);
        AccVM accVM = ViewModelProviders.of(this).get(AccVM.class);
        accVM.getAll().observe(getViewLifecycleOwner(),this::createLiveData);



        return root;
    }

    private void createLiveData(List<AccessoryEntity> list){
        for(AccessoryEntity l:list){

            switch (l.getFajta()){
                case "a":
                    arrayList.add(new Elem("Cauldron", 800,"Slow","Specialized",R.drawable.ic_cserep1,
                            Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_cserep1)));
                    break;

                case "b":
                    arrayList.add(new Elem("PotHead Bucket", 800,"Slow","Epic",R.drawable.ic_pothead_pot,
                            Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_pothead_pot)));
                    break;

                case "c":
                    arrayList.add(new Elem("Flower Pot", 800,"Fast","Rare",R.drawable.ic_cserep4,
                            Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_cserep4)));
                    break;

                case "d":
                    arrayList.add(new Elem("Red Vase", 800,"Fast","Rare",R.drawable.ic_cserep5,
                            Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_cserep5)));
                    break;

                case "e":
                    arrayList.add(new Elem("Holy Grail", 800,"Fast","Rare",R.drawable.ic_cserep6,
                            Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_cserep6)));
                    break;

                case "f":
                    arrayList.add(new Elem("Air Raid Siren", 800,"Fast","Rare",R.drawable.ic_cserep7,
                            Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_cserep7)));
                    break;

                case "g":
                    arrayList.add(new Elem("Polygon", 800,"Fast","Rare",R.drawable.ic_cserep8,
                            Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_cserep8)));
                    break;

                case "h":
                    arrayList.add(new Elem("Unknown Error", 800,"Fast","Rare",R.drawable.ic_cserep9,
                            Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_cserep9)));
                    break;
                case "i":
                    arrayList.add(new Elem("Rusty", 600,"Fast","Rare",R.drawable.ic_rusty_kanna,
                            Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_rusty_kanna)));
                    break;
                case "j":
                    arrayList.add(new Elem("TinyTin Can", 600,"Fast","Rare",R.drawable.ic_kanna_n1,
                            Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_kanna_n1)));
                    break;
                case "k":
                    arrayList.add(new Elem("Marley's TeaPot", 600,"Fast","Rare",R.drawable.ic_kanna004,
                            Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_kanna004)));
                    break;
                case "l":
                    arrayList.add(new Elem("Coffee Dispenser", 600,"Fast","Rare",R.drawable.ic_kanna008,
                            Objects.requireNonNull(getContext()).getDrawable(R.drawable.ic_kanna008)));
                    break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    }

}
