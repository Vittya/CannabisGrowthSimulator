package blacklinden.com.cannabisgrowthsimulator.ui.tutorial;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import blacklinden.com.cannabisgrowthsimulator.R;

public class DialogFrag extends DialogFragment {



    @Override
    public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {
        return super.show(transaction, tag);
    }

    @Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

}

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


            return inflater.inflate(R.layout.dialog_frag, container, false);
        }





}
