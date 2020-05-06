package blacklinden.com.cannabisgrowthsimulator.ui.recre.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import java.util.Objects;

import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.eszk.Teknos;

public class CommandCtrlDialog extends DialogFragment {
    private CtrlListener listener;
    private DialogVM model;
    private boolean weedIsSet=false;
    public CommandCtrlDialog(){

    }

    public static CommandCtrlDialog newInstance(Bundle args) {

        CommandCtrlDialog frag = new CommandCtrlDialog();


        frag.setArguments(args);
        return frag;
    }

    public interface CtrlListener{
        void onResult(Bundle data,boolean finalAction);

    }

    private void yield(Bundle data,boolean isFinal) {

            listener.onResult(data,isFinal);



    }


        @Override
        public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {
            return super.show(transaction, tag);
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {


        Bundle args = getArguments();
        int posresId = Objects.requireNonNull(args).getInt("posresId");
        int layoutId = Objects.requireNonNull(args.getInt("layoutId"));
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

            try {
                this.listener = (CtrlListener)getParentFragment();
            } catch (ClassCastException e) {
                assert getParentFragment() != null;
                throw new ClassCastException(getParentFragment().toString()
                        + " must implement CtrlListener interface");
            }

            assert getParentFragment() != null;
            LayoutInflater inflater = getParentFragment().getLayoutInflater();
            View view = inflater.inflate(layoutId, null);
            setLayoutElements(view,args);
            dialog.setView(view)
                    .setPositiveButton(getResources().getString(posresId), (dialog1, id) -> {
                        if(layoutId==R.layout.weed_to_grind){
                            if(weedIsSet)yield(args,true);
                        }else yield(args,true);


                    })
                    .setNegativeButton(R.string.dismiss, (dialog12, id) -> Objects.requireNonNull(CommandCtrlDialog.this.getDialog()).cancel());

            return dialog.create();
        }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @SuppressLint("SetTextI18n")
    private void setLayoutElements(View view, Bundle args){
        switch (args.getInt("layoutId")){

            case R.layout.weed_to_grind:
                SeekBar seekBar =view.findViewById(R.id.seek_weed_amount);
                ImageView wImg = view.findViewById(R.id.weedPic);
                TextView wTxt = view.findViewById(R.id.weedTxt);
                int availableWeed = Objects.requireNonNull(args).getInt("mennyi");

                if(availableWeed<=1){
                    seekBar.setEnabled(false);
                    wTxt.setText(1+" "+DialogUtil.qualitytext(
                            Objects.requireNonNull(args.getString("quality"))) +"\n" + args.getString("fajta"));
                    weedIsSet=true;

                }else
                wTxt.setText(DialogUtil.qualitytext(
                        Objects.requireNonNull(args.getString("quality"))) +"\n" + args.getString("fajta"));
                int i = DialogUtil.setIcon((Objects.requireNonNull(args.getString("fajta"))));
                Toast.makeText(getContext(),i,Toast.LENGTH_SHORT).show();
                wImg.setImageResource(i);


                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int res=1;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int ii, boolean b) {
                        res = ((Math.min(availableWeed, 6))*(ii))/100;

                        wTxt.setText(res+" "+DialogUtil.qualitytext(
                                Objects.requireNonNull(args.getString("quality"))) +"\n" + args.getString("fajta"));


                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        Toast.makeText(getContext(),""+res,Toast.LENGTH_SHORT).show();
                        if(res>0) {
                            weedIsSet = true;
                            args.putInt("levonando", res);
                        }
                    }
                });
                break;

            case R.layout.paper_to_smoke:
                ImageView iv = view.findViewById(R.id.big_paper);
                iv.setImageResource(Integer.parseInt(Objects.requireNonNull(args.getString("paperName"))));
                TextView pprTv = view.findViewById(R.id.paperName);
                pprTv.setText(Objects.requireNonNull(args.getString("paperText")));
                Switch sw = view.findViewById(R.id.paperSwitch);
                sw.setOnCheckedChangeListener((compoundButton, b) -> {
                            args.putBoolean("isRear", b);
                });


                break;
        }
    }





}
