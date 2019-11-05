package blacklinden.com.cannabisgrowthsimulator.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;

import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.pojo.Combo;

public class ComboAdapter extends RecyclerView.Adapter
        <ComboAdapter
                .ComboHolder> {


    private List<Combo> combos;
    private static ImageView[] c;

    ComboAdapter(List<Combo> combos) {

        this.combos = combos;
    }


    @NonNull
    @Override
    public ComboHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.combo_row,parent, false);
        return new ComboHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ComboHolder holder, int position) {
        Combo combo = combos.get(position);
        holder.bind(combo);
    }

    @Override
    public int getItemCount() {
        return combos.size();
    }

    void setCombos(List<Combo> combos) {
        this.combos = combos;

    }


    void setEachItem(Combo item) {
        combos.add(item);
    }

    static class ComboHolder extends RecyclerView.ViewHolder {



        ComboHolder(View comboview) {
            super(comboview);
            c = new ImageView[3];
            c[0] = comboview.findViewById(R.id.c1);
            c[1] = comboview.findViewById(R.id.c2);
            c[2] = comboview.findViewById(R.id.c3);
        }

        void bind(Combo combo) {
            for(int i=0;i<combo.getCombo().length;i++){
                switch (combo.getCombo()[i]){
                    case "Skunk":
                      c[i].setImageResource(R.drawable.ic_skunk_n1);
                        break;
                    case "Afghan":
                    c[i].setImageResource(R.drawable.ic_afghan);
                        break;
                    case "Balkan Rose":
                     c[i].setImageResource(R.drawable.ic_balkan_rose);
                        break;
                    case "BlueBerry":
                      c[i].setImageResource(R.drawable.ic_blueberry_icon);
                        break;
                    case "Northern Light":
                        c[i].setImageResource(R.drawable.ic_northern_light_icon);
                        break;
                    case "Grape Ape":
                       c[i].setImageResource(R.drawable.ic_grape_ape_icon);
                        break;
                    case "AK47":
                       c[i].setImageResource(R.drawable.ic_ak47);
                        break;
                    case "Cheese":
                       c[i].setImageResource(R.drawable.ic_cheese_icon);
                        break;
                    case "Amnesia":
                        c[i].setImageResource(R.drawable.ic_amnesia_icon);
                        break;
                    case "S.Lemon Haze":
                        c[i].setImageResource(R.drawable.ic_super_lemon_haze);
                        break;
                    case "White Widow":
                        c[i].setImageResource(R.drawable.ic_white_widow_icon);
                        break;
                    case "Gelato":
                       c[i].setImageResource(R.drawable.ic_gelato_icon);
                        break;
                    case "Ghost OG":
                       c[i].setImageResource(R.drawable.ic_ghost_og_icon);
                        break;
                    case "Cherry Diesel":
                        c[i].setImageResource(R.drawable.ic_cherry_diesel_icon);
                        break;
                    case "Permafrost":
                        c[i].setImageResource(R.drawable.ic_permafrost_icon);
                        break;
                    case "Pink Mango":
                        c[i].setImageResource(R.drawable.ic_pink_mango_icon);
                        break;
                    case "Pineapple":
                        c[i].setImageResource(R.drawable.ic_pineapple_express);
                       break;
                    case "G.White Shark":
                       c[i].setImageResource(R.drawable.ic_gws_icon);
                       break;
               default:
                   c[i].setImageResource(R.drawable.ic_emptycombo);

                }
            }

        }


    }
}
