package blacklinden.com.cannabisgrowthsimulator.ui.medium;

import android.animation.Animator;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.eszk.Mentés;
import blacklinden.com.cannabisgrowthsimulator.pojo.Nutri;

class FertilHolder extends RecyclerView.ViewHolder {
    private TextView txtName, txtDistance, txtGravity, txtDiameter, txtMennyi;
    private ImageView imageView;
    private ItemClickListener listener;


    FertilHolder(View itemView, ItemClickListener listener) {
        super(itemView);
        imageView = itemView.findViewById(R.id.cardImage);
        txtName = itemView.findViewById(R.id.txtName);
        txtDistance = itemView.findViewById(R.id.txtDistance);
        txtGravity = itemView.findViewById(R.id.txtGravity);
        txtDiameter = itemView.findViewById(R.id.txtDiameter);
        txtMennyi = itemView.findViewById(R.id.howmuch);
        this.listener = listener;
    }

    void setDetails(Fertilizer fertil) {
        imageView.setImageDrawable(fertil.getDrawable());
        txtName.setText(fertil.getName());
        txtDistance.setText(String.format(Locale.US, "Nitrogen : %d", fertil.getN()));
        txtGravity.setText(String.format(Locale.US, "Phosphorus : %d", fertil.getP()));
        txtDiameter.setText(String.format(Locale.US, "Potassium : %d", fertil.getK()));
        txtMennyi.setText(Integer.toString(fertil.getMennyi()));
        final Fertilizer elem = fertil;

        itemView.setOnClickListener(view -> {

            String s = Mentés.getInstance().gsonra(new Nutri(elem.getName() ,elem.getDrawCode(),elem.getN(),elem.getP(),elem.getK()));

            Mentés.getInstance().put(Mentés.Key.SAMPLE_NUT,s);


            view.animate()
                    .translationX(100)
                    .setDuration(200)
                    .setInterpolator(new LinearInterpolator())
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            view.animate()
                                    .translationX(0)
                                    .setDuration(200)
                                    .setInterpolator(new LinearInterpolator())
                                    .start();

                        }
                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    }).start();

            listener.onItemClick();

        });
    }
}