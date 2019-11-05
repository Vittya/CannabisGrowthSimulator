package blacklinden.com.cannabisgrowthsimulator.ui.recy;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.pojo.SelectableStashItem;
import blacklinden.com.cannabisgrowthsimulator.pojo.Stash;
import blacklinden.com.cannabisgrowthsimulator.pojo.Vegtermek;

public class SelectableAdapter extends RecyclerView.Adapter implements SelectableViewHolder.OnItemSelectedListener {

private List<SelectableStashItem> mValues;

private boolean isMultiSelectionEnabled;
private SelectableViewHolder.OnItemSelectedListener listener;


public SelectableAdapter(SelectableViewHolder.OnItemSelectedListener listener, boolean isMultiSelectionEnabled) {
        this.listener = listener;
        this.isMultiSelectionEnabled = isMultiSelectionEnabled;

        }

 public void setLiveValues(List<Vegtermek> vegtermekek){
    List<SelectableStashItem> tempVal = new ArrayList<>();
        for(Vegtermek v:vegtermekek){
                tempVal.add(new SelectableStashItem(new Stash(v.id,v.getFajta(),v.getMnsg(),v.getMennyi()),false));
        }
        mValues=tempVal;
        notifyDataSetChanged();
 }

@NonNull
@Override
public SelectableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.checked_item, parent, false);

        return new SelectableViewHolder(itemView, this);
        }

@SuppressLint("SetTextI18n")
@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        SelectableViewHolder holder = (SelectableViewHolder) viewHolder;
        SelectableStashItem selectableItem = mValues.get(position);
        String name = selectableItem.getFajta();
        holder.textView.setText(qualitytext(selectableItem.getMinőség()));
        setIcon(holder.textView,name);
        if (isMultiSelectionEnabled) {
        TypedValue value = new TypedValue();
        holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
        int checkMarkDrawableResId = value.resourceId;
        holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        } else {
        TypedValue value = new TypedValue();
        holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorSingle, value, true);
        int checkMarkDrawableResId = value.resourceId;
        holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        }

        holder.stashItem = selectableItem;
        holder.setChecked(holder.stashItem.isSelected());
        }

@Override
public int getItemCount() {
        if(mValues!=null)return mValues.size();
        else return 0;
        }

public List<Stash> getSelectedItems() {

        List<Stash> selectedItems = new ArrayList<>();
        for (SelectableStashItem item : mValues) {
        if (item.isSelected()) {
        selectedItems.add(item);
        }
        }
        return selectedItems;
        }

public void resetSelectedItems(){
        for(SelectableStashItem item:mValues) {
         if(item.isSelected())
            item.setSelected(false);
        }
        notifyDataSetChanged();
}

@Override
public int getItemViewType(int position) {
        if(isMultiSelectionEnabled){
        return SelectableViewHolder.MULTI_SELECTION;
        }
        else{
        return SelectableViewHolder.SINGLE_SELECTION;
        }
        }

@Override
public void onItemSelected(SelectableStashItem item) {
        if (!isMultiSelectionEnabled) {

        for (SelectableStashItem selectableItem : mValues) {
        if (selectableItem.getId()!=(item.getId())
        && selectableItem.isSelected()) {
                resetSelectedItems();

        } else if (selectableItem.getId()==(item.getId())
        && item.isSelected()) {

        selectableItem.setSelected(true);
        }
        }
        notifyDataSetChanged();
        }

        listener.onItemSelected(item);
        }

private void setIcon(View v,String icon){
        switch (icon) {
                case "Skunk":
                        v.setBackgroundResource(R.drawable.ic_skunk_n1);
                        break;
                case "Afghan":
                        v.setBackgroundResource(R.drawable.ic_afghan);
                        break;
                case "Balkan Rose":
                        v.setBackgroundResource(R.drawable.ic_balkan_rose);
                        break;
                case "BlueBerry":
                        v.setBackgroundResource(R.drawable.ic_blueberry_icon);
                        break;
                case "Northern Light":
                        v.setBackgroundResource(R.drawable.ic_northern_light_icon);
                        break;
                case "Grape Ape":
                        v.setBackgroundResource(R.drawable.ic_grape_ape_icon);
                        break;
                case "AK47":
                        v.setBackgroundResource(R.drawable.ic_ak47);
                        break;
                case "Cheese":
                        v.setBackgroundResource(R.drawable.ic_cheese_icon);
                        break;
                case "Amnesia":
                        v.setBackgroundResource(R.drawable.ic_amnesia_icon);
                        break;
                case "S.Lemon Haze":
                        v.setBackgroundResource(R.drawable.ic_super_lemon_haze);
                        break;
                case "White Widow":
                        v.setBackgroundResource(R.drawable.ic_white_widow_icon);
                        break;
                case "Gelato":
                        v.setBackgroundResource(R.drawable.ic_gelato_icon);
                        break;
                case "Ghost OG":
                        v.setBackgroundResource(R.drawable.ic_ghost_og_icon);
                        break;
                case "Cherry Diesel":
                        v.setBackgroundResource(R.drawable.ic_cherry_diesel_icon);
                        break;
                case "Permafrost":
                        v.setBackgroundResource(R.drawable.ic_permafrost_icon);
                        break;
                case "Pink Mango":
                        v.setBackgroundResource(R.drawable.ic_pink_mango_icon);
                        break;
                case "Pineapple":
                        v.setBackgroundResource(R.drawable.ic_pineapple_express);
                        break;
                case "G.White Shark":
                        v.setBackgroundResource(R.drawable.ic_gws_icon);
                        break;
                case "Nurse R.":
                        v.setBackgroundResource(R.drawable.ic_nrs_icon);
                        break;
                case "Durban":
                        v.setBackgroundResource(R.drawable.ic_durban_poison_icon);
                        break;
        }
}

private String qualitytext(String status){
        switch (status){
                case "molded":
                        return "Rotten Schwag";
                case "wet":
                        return "Raw";
                case "smelly":
                        return "Reggie Weed";
                case "good":
                        return "Mid Grade";
                case "goldilocks":
                        return "Premium Dank";
                default:return "Unknown";
        }
}


}