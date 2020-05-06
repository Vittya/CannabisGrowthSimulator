package blacklinden.com.cannabisgrowthsimulator.ui.recy;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.pojo.SelectableStashItem;

public class SelectableViewHolder  extends RecyclerView.ViewHolder {

        static final int MULTI_SELECTION = 2;
        static final int SINGLE_SELECTION = 1;
        CheckedTextView textView;
        ImageView imageView;
        LinearLayout fl;
        SelectableStashItem stashItem;
        OnItemSelectedListener itemSelectedListener;


        SelectableViewHolder(View view, OnItemSelectedListener listener) {
            super(view);
            itemSelectedListener = listener;
            textView = view.findViewById(R.id.checked_text_item);
            imageView = view.findViewById(R.id.ivCI);
            fl = view.findViewById(R.id.fl);
            textView.setOnClickListener(view1 -> {


                if (stashItem.isSelected() ) {
                    setChecked(false);
                } else {
                    setChecked(true);
                }
                itemSelectedListener.onItemSelected(stashItem);

            });
        }

        void setChecked(boolean value) {

            stashItem.setSelected(value);
            textView.setChecked(value);
        }

        public interface OnItemSelectedListener {

            void onItemSelected(SelectableStashItem stash);
        }

    }

