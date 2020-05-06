package blacklinden.com.cannabisgrowthsimulator.ui.recre;

import androidx.lifecycle.ViewModelProviders;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.plattysoft.leonids.ParticleSystem;

import java.util.List;
import java.util.Objects;

import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.RecreActivity;
import blacklinden.com.cannabisgrowthsimulator.eszk.Teknos;
import blacklinden.com.cannabisgrowthsimulator.pojo.SelectableStashItem;
import blacklinden.com.cannabisgrowthsimulator.pojo.Stash;
import blacklinden.com.cannabisgrowthsimulator.sql.ScoreVM;
import blacklinden.com.cannabisgrowthsimulator.sql.VegtermekViewModel;
import blacklinden.com.cannabisgrowthsimulator.ui.grind.Bong;
import blacklinden.com.cannabisgrowthsimulator.ui.grind.GrinderTartalomCV;
import blacklinden.com.cannabisgrowthsimulator.ui.kolibri.Kolibri;
import blacklinden.com.cannabisgrowthsimulator.ui.recre.dialog.CommandCtrlDialog;
import blacklinden.com.cannabisgrowthsimulator.ui.recre.drag.DragElement;
import blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke.Bobi;
import blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke.JointElement;
import blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke.Paper;
import blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke.PreviewBackup;
import blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke.RollTable;
import blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke.Spangli;
import blacklinden.com.cannabisgrowthsimulator.ui.recre.smoke.SpangliTypes;
import blacklinden.com.cannabisgrowthsimulator.ui.recy.SelectableAdapter;
import blacklinden.com.cannabisgrowthsimulator.ui.recy.SelectableViewHolder;

public class RecreFragment extends Fragment implements SelectableViewHolder.OnItemSelectedListener, View.OnDragListener, View.OnTouchListener, CommandCtrlDialog.CtrlListener{

    private RecreViewModel mViewModel;
    private int currentXp;
    private ScoreVM scoreVM;
    private int currentCoin;
    private Kolibri kolibriAnimator;
    private GrinderTartalomCV grinderTartalomCV;
    private SelectableAdapter adapter;
    private RecyclerView recyclerView;
    private VegtermekViewModel termekVM;
    private Bong bong;
    private Bundle weedToGrindBundle, paperToSmoke, nonWeedAdditiveBundle;
    private  ImageView grinderTeteje;
    private FloatingActionMenu cpMenu,dohanyMenu,tipMenu;
    private RollTable rollTable;
    private View bongpeg;


    public static RecreFragment newInstance() {
        return new RecreFragment();
    }

    private void createBundles(){
        weedToGrindBundle = new Bundle();
        weedToGrindBundle.putInt("posresId",R.string.afgan);
        weedToGrindBundle.putInt("layoutId",R.layout.weed_to_grind);

        nonWeedAdditiveBundle = new Bundle();
        nonWeedAdditiveBundle.putInt("posresId",R.string.paper_confirm);
        nonWeedAdditiveBundle.putInt("layoutId",R.layout.non_weed_to_add);

        paperToSmoke = new Bundle();
        paperToSmoke.putInt("posresId",(R.string.paper_confirm));
        paperToSmoke.putInt("layoutId",R.layout.paper_to_smoke);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        createBundles();
        //dialogVM = new ViewModelProvider(this).get(DialogVM.class);
        scoreVM = ViewModelProviders.of(this).get(ScoreVM.class);

        View root = inflater.inflate(R.layout.recre_fragment, container, false);
        observeScore();

        Button fab = root.findViewById(R.id.fab);


        TextView kolibriTV = root.findViewById(R.id.kolibri);
        AnimatedVectorDrawable kolibri = (AnimatedVectorDrawable) kolibriTV.getCompoundDrawables()[3];//(AnimatedVectorDrawable)getResources().getDrawable(R.drawable.kolibriavd,null);//

        kolibri.start();

        kolibri.registerAnimationCallback(new Animatable2.AnimationCallback(){
            @Override
            public void onAnimationEnd(Drawable drawable) {
                kolibriTV.post(kolibri::start);
            }
        });
        ImageView nixieTV = root.findViewById(R.id.nixi);
        AnimatedVectorDrawable nixie = (AnimatedVectorDrawable) (nixieTV.getDrawable());//(AnimatedVectorDrawable)getResources().getDrawable(R.drawable.kolibriavd,null);//

        nixie.start();

        nixie.registerAnimationCallback(new Animatable2.AnimationCallback(){
            @Override
            public void onAnimationEnd(Drawable drawable) {
                nixieTV.post(nixie::start);
            }
        });

        rollTable = root.findViewById(R.id.rolltable);
        rollTable.setOnDragListener(this);
        ImageView undo = root.findViewById(R.id.undo);
        ImageView redo = root.findViewById(R.id.redo);
        PreviewBackup previewBackup = root.findViewById(R.id.previewImgBakcup);
        //TODO --preview--
        rollTable.backupPreview(previewBackup::fillUp);

        undo.setOnClickListener(l->rollTable.deleteItem());
        redo.setOnClickListener(l->rollTable.undoDeleteItem());


        rollTable.setOnLongClickListener(view -> {
            rollTable.clearStack();
            return false;
        });

        Spangli spangli = root.findViewById(R.id.spangli);
        fab.setOnClickListener(l->spangli.init(rollTable.validateCombo(),6, SpangliTypes.SITTES_CIGI));
        Typeface plain = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(), "font/humnst777.ttf");
        fab.setTypeface(plain);
        spangli.setOnClickListener(l->spangli.run());
        adapter = new SelectableAdapter(this, false);
        recyclerView = root.findViewById(R.id.selection_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1,GridLayoutManager.HORIZONTAL,false));

        termekVM = ViewModelProviders.of(this).get(VegtermekViewModel.class);


        termekVM.getAll().observe(getViewLifecycleOwner(), vegtermekek -> {


            if (vegtermekek != null) {
                adapter.setLiveValues(vegtermekek);
                //stashedTv.setText(Integer.toString(vegtermekek.size()));
            }
        });



        final float w = ((RecreActivity)Objects.requireNonNull(getActivity())).getPoint().x;
        kolibriAnimator = new Kolibri(w, kolibriTV);
        kolibriAnimator.setState("repdes",null);
        kolibriAnimator.run();

        grinderTartalomCV = root.findViewById(R.id.grinderTartalom);
        grinderTartalomCV.setTag("grinder_tartalom");
        grinderTartalomCV.setOnTouchListener(this);

        ShimmerFrameLayout shimmer = root.findViewById(R.id.csilivili);
        grinderTeteje = root.findViewById(R.id.grinder2);
       // grinder.setOnClickListener(listener->grindIt());

        final ImageButton lighterButton = root.findViewById(R.id.gyjt);
        Drawable lighter = lighterButton.getDrawable();

        bong = root.findViewById(R.id.bong);
        bong.setOnDragListener(this);
        bongpeg = root.findViewById(R.id.bongpeg);
        bong.setListener(new Bong.BongListener() {
            @Override
            public void lightUp() {
                bong.animate()
                        .translationX(0)
                        .translationZ(0)
                        .rotation(-20)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);

                            }
                        })
                        .start();
            }

            @Override
            public void inhale() {
                lighterButton.setVisibility(View.VISIBLE);

                if (lighter instanceof Animatable) {
                    ((Animatable) lighter).start();
                }

            }

            @Override
            public void exhale(int counter) {
                lighterButton.setVisibility(View.GONE);
                if (lighter instanceof Animatable) {
                    ((Animatable) lighter).stop();
                }
                new ParticleSystem(Objects.requireNonNull(getActivity()), 50, R.drawable.fust_particle, 5000L)
                        .setFadeOut(2500)
                        .setRotationSpeed(100)
                        .setScaleRange(2, 10)
                        .setSpeedModuleAndAngleRange(0.01f, 0.05f, 10, 80)
                        .emitWithGravity(kolibriTV, Gravity.CENTER_VERTICAL, counter, 1000);
            }

            @Override
            public void end(int i, String fajta) {
                kolibriAnimator.setState("repdes", null);
                scoreVM.updateXP(currentXp += i);
                if (i > 0)
                    new ParticleSystem(Objects.requireNonNull(getActivity()), 20, R.drawable.ic_bobi_coin_iconv2, 5000L)
                            .setFadeOut(4500)
                            .setSpeedModuleAndAngleRange(0.02f, 0.1f, 75, 105)
                            .oneShot(bong, i);

                bong.animate().rotation(0).start();




            }
        });

        grinderTartalomCV.setGrinderListener(new GrinderTartalomCV.GrinderListener() {

            @Override
            public void onEmpty() {


//                bong.animate()
//                        .translationX(0)
//                        .translationZ(0)
//                        .rotation(-20)
//                        .setDuration(500)
//                        .setListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                super.onAnimationEnd(animation);
//
//                            }
//                        })
//                        .start();

                closeTop();
                Toast.makeText(getContext(),"closeTop",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFilled() {
                if (!shimmer.isShimmerStarted())
                    shimmer.startShimmer();


            }

            @Override
            public void onGrinded() {
                if (shimmer.isShimmerStarted()) {

                    shimmer.stopShimmer();
//                    kolibriAnimator.setState("smoke", bongpeg);
                }
            }
        });

        //menü gombok cigipapír dohány csiga
        ImageView cigiPapír = root.findViewById(R.id.cp);
        ImageView dohány = root.findViewById(R.id.dohany);


        FrameLayout.LayoutParams imageViewParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);


        ImageView a = new ImageView(getContext());
        a.setTag(R.drawable.ic_cigiszelveny1);
        ImageView b = new ImageView(getContext());
        b.setTag(R.drawable.ic_nrs_icon);
        ImageView c = new ImageView(getContext());
        c.setTag(R.drawable.ic_amnesia_icon);
        View[] papírok = new View[]{
        a, b, c
        };
        ImageView ad = new ImageView(getContext());
        a.setTag(R.drawable.ic_cigiszelveny1);
        ImageView bd = new ImageView(getContext());
        b.setTag(R.drawable.ic_nrs_icon);
        ImageView cd = new ImageView(getContext());
        c.setTag(R.drawable.ic_amnesia_icon);
        View[] dohanyok = new View[]{
                ad, bd, cd
        };
        ImageView at = new ImageView(getContext());
        a.setTag(R.drawable.ic_cigiszelveny1);
        ImageView bt = new ImageView(getContext());
        b.setTag(R.drawable.ic_nrs_icon);
        ImageView ct = new ImageView(getContext());
        c.setTag(R.drawable.ic_amnesia_icon);
        View[] tippek = new View[]{
                at, bt, ct
        };

Listener listener = new Listener();
        for(View view : papírok){
            view.setOnClickListener(listener);
            ((ImageView) view).setImageResource(R.drawable.ic_blueberry_icon);
            view.setContentDescription("cp");

            view.setLayoutParams(imageViewParams);
        }

        for(View view : dohanyok){
            view.setOnClickListener(listener);
            ((ImageView) view).setImageResource(R.drawable.ic_blueberry_icon);
            view.setContentDescription("doh");

            view.setLayoutParams(imageViewParams);
        }
        for(View view : tippek){
            view.setOnClickListener(listener);
            ((ImageView) view).setImageResource(R.drawable.ic_blueberry_icon);
            view.setContentDescription("tip");

            view.setLayoutParams(imageViewParams);
        }


        cpMenu = new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(a)
                .addSubActionView(b)
                .addSubActionView(c)
                .setStartAngle(270)
                .setEndAngle(360)

                .attachTo(cigiPapír)
                .build();

        dohanyMenu = new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(ad)
                .addSubActionView(bd)
                .addSubActionView(cd)
                .attachTo(dohány)
                .build();

        tipMenu = new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(at)
                .addSubActionView(bt)
                .addSubActionView(ct)
                .setStartAngle(225)
                .setEndAngle(315)
                .attachTo(root.findViewById(R.id.tip))
                .build();




        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RecreViewModel.class);
        // TODO: Use the ViewModel
    }

    private void observeScore() {
        scoreVM.get().observe(getViewLifecycleOwner(), scoreEntity -> {

            if (scoreEntity != null) {
                currentXp = scoreEntity.getXp();
                currentCoin = scoreEntity.getScore();
//                xp.setText(Integer.toString(scoreEntity.getXp()));
//                coins.setText(Integer.toString(scoreEntity.getScore()));
//                achievTv.setText(scoreEntity.getRank());
            }

        });
    }


    @Override
    public void onItemSelected(SelectableStashItem selectableItem) {
        List<Stash> selectedItems = adapter.getSelectedItems();
        Snackbar.make(recyclerView,"Selected: "+selectableItem.getFajta()+" "+String.format(java.util.Locale.US,"%.2f",selectableItem.getMennyi())+"Gr."+
                ", Quality: "+selectableItem.getMinőség(),Snackbar.LENGTH_LONG).show();
//        fab.setText(R.string.selectableConfirm);
//        villog(fab, Color.BLACK,Color.RED,1);
        if(selectedItems.isEmpty()&&!grinderTartalomCV.getIsEmpty()) {
            grinderTartalomCV.dispose();
            grinderTartalomCV.invalidate();
            //TODO -- rolltable.isFilled()
        }else if(!bong.isFilled()&&grinderTartalomCV.getIsEmpty()){


                    weedToGrindBundle.putInt("mennyi",(int)selectableItem.getMennyi());
                    weedToGrindBundle.putInt("id",selectableItem.getId());
                    weedToGrindBundle.putString("fajta",selectableItem.getFajta());
                    weedToGrindBundle.putString("quality",selectableItem.getMinőség());

            CommandCtrlDialog.newInstance(weedToGrindBundle).show(getChildFragmentManager(),"dialog");

//            grinderTartalomCV.fillUp(Teknős.flowerStrain(
//                    getContext(), selectableItem.getFajta()),
//                    updateStashConsumption((int)selectableItem.getMennyi()),
//                    selectableItem.getId(),
//                    selectableItem.getFajta(),
//                    selectableItem.getMinőség(),
//                    selectableItem.getThc(),
//                    selectableItem.getCbd()
//            );


        }
        selectableItem.setSelected(false);

    }


    private void grindIt(){



            grinderTeteje.animate()
                    .setStartDelay(200)
                    .rotation(360)
                    .setDuration(1000)
                    .setInterpolator(new DecelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            grinderTartalomCV.setGrinded(true);
                            grinderTeteje.animate()
                                    .setStartDelay(200)
                                    .translationY(-grinderTeteje.getHeight())
                                    .setDuration(2000)

                                    .start();

                        }

                    })
                    .start();

Toast.makeText(getContext(),"grindIt",Toast.LENGTH_SHORT).show();

    }

    private void fillBong() {
        bong.fillUp(grinderTartalomCV.getFajta(),grinderTartalomCV.getThc(),grinderTartalomCV.getCbd(),grinderTartalomCV.getMnsg());
        disposeGrinder();
    }

    private void disposeGrinder(){
        termekVM.update(grinderTartalomCV.getStashID(),grinderTartalomCV.getLevonando());
        grinderTartalomCV.dispose();
        grinderTartalomCV.invalidate();
    }


    private void openTopFromNothing(ImageView logo, ImageView grinder, Button fab) {


        int cx = logo.getRight() / 2;
        int cy = logo.getBottom() / 2;
        int finalRadius = (int) Math.hypot(logo.getWidth() / 2,
                logo.getHeight() / 2);


        grinder.animate()
                .translationZ(20)
                .rotation(20)

                .setInterpolator(new LinearInterpolator())
                .setDuration(1000)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                        grinder.animate()
                                .translationX(grinder.getWidth())
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        fab.setEnabled(true);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                })
                                .setDuration(500)
                                .start();


                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                })


        .start();
    }

    private void closeTop(){
        grinderTeteje.animate()

                .translationX(0)
                .translationY(0)
                .translationZ(0)
                .rotation(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        grinderTeteje.animate().cancel();
                    }
                })
                .setDuration(500)
                .start();

    }

    @Override
    public boolean onDrag(View view, DragEvent event) {

        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                String clipData2 = event.getClipDescription().getLabel().toString();
                ((DragElement) view).hiLite(true);

                return true;

            case DragEvent.ACTION_DROP:

                String clipData = event.getClipDescription().getLabel().toString();
                switch (view.getId()) {
                    case R.id.bong:fillBong();
                    //closeTop();
                    kolibriAnimator.setState("smoke", bongpeg);
                    break;
                    case R.id.rolltable:
                    rollTable.loadStack(new Bobi(new BitmapDrawable(getResources(),grinderTartalomCV.getWeakRef().get()),grinderTartalomCV.getFajta(),grinderTartalomCV.getLevonando()));

                    disposeGrinder();
                    kolibriAnimator.setState("smoke", rollTable);
                    break;
                }

                ((DragElement)view).hiLite(false);
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                ((DragElement)view).hiLite(false);

                return true;

            default:
                return false;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        View.DragShadowBuilder mShadow = new View.DragShadowBuilder(view);
        ClipData.Item item = new ClipData.Item(view.getTag().toString());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
        switch (view.getId()) {
            case R.id.grinderTartalom:

                if(!((GrinderTartalomCV)view).getIsEmpty())
                    view.startDragAndDrop(data, mShadow, null, 0);


                break;

            case R.id.pakk:
                view.startDragAndDrop(data,mShadow,null,0);
                break;
        }
view.performClick();
        return false;
    }

    @Override
    public void onResult(Bundle data, boolean isFinalAction) {
        switch (data.getInt("layoutId")) {

            case R.layout.weed_to_grind:
            grinderTartalomCV.fillUp(Teknos.flowerStrain(
                    getContext(), Objects.requireNonNull(data.getString("fajta"))),
                    (data.getInt("mennyi")),
                    data.getInt("levonando"),
                    data.getInt("id"),
                    data.getString("fajta"),
                    data.getString("quality"),
                    data.getInt("thc"),
                    data.getInt("cbd")
            );
            if(isFinalAction) grindIt();
            break;

            case R.layout.paper_to_smoke:

             rollTable.loadStack(new Paper(
                     getResources().getDrawable(Integer.parseInt(Objects.requireNonNull(data.getString("paperName"))),null),
                     data.getString("paperName"),
                     data.getBoolean("isRear")
                )
             );
             break;

            default:
                System.out.println("11111111111111111111111");

        }
    }

    class Listener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getContentDescription().toString()) {
                case "cp":
                paperToSmoke.putString("paperName",view.getTag().toString());
                paperToSmoke.putString("paperText",textForView(view));
                CommandCtrlDialog.newInstance(paperToSmoke).show(getChildFragmentManager(),"dialog");
                break;
                case "doh":
                nonWeedAdditiveBundle.putString("name",view.getTag().toString());
                nonWeedAdditiveBundle.putString("text",textForView(view));
                CommandCtrlDialog.newInstance(nonWeedAdditiveBundle).show(getChildFragmentManager(),"dialog");
                break;
            }
            cpMenu.close(true);
        }
    }

  private String textForView(View view) {
            switch ((int)view.getTag()) {

                case R.drawable.ic_cigiszelveny1:
                    return getResources().getString(R.string.ocb_papir);

                case R.drawable.ic_blueberry_icon:
                    return getResources().getString(R.string.dohany_sima);

                default: return "default";

            }

        }

}

