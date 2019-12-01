package blacklinden.com.cannabisgrowthsimulator;



import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;


import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;


import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;

import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chartboost.sdk.Model.CBError;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.plattysoft.leonids.ParticleSystem;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import blacklinden.com.cannabisgrowthsimulator.eszk.Mentés;
import blacklinden.com.cannabisgrowthsimulator.eszk.Teknős;
import blacklinden.com.cannabisgrowthsimulator.nov.Kender;
import blacklinden.com.cannabisgrowthsimulator.pojo.ComboEntity;

import blacklinden.com.cannabisgrowthsimulator.pojo.Lamps;
import blacklinden.com.cannabisgrowthsimulator.pojo.SelectableStashItem;
import blacklinden.com.cannabisgrowthsimulator.pojo.Stash;
import blacklinden.com.cannabisgrowthsimulator.pojo.Termény;
import blacklinden.com.cannabisgrowthsimulator.pojo.Vegtermek;
import blacklinden.com.cannabisgrowthsimulator.serv.Constants;
import blacklinden.com.cannabisgrowthsimulator.serv.LService;
import blacklinden.com.cannabisgrowthsimulator.settings.Settings;
import blacklinden.com.cannabisgrowthsimulator.sql.ComboVM;

import blacklinden.com.cannabisgrowthsimulator.sql.MagVM;
import blacklinden.com.cannabisgrowthsimulator.sql.ScoreVM;
import blacklinden.com.cannabisgrowthsimulator.sql.SoilVM;
import blacklinden.com.cannabisgrowthsimulator.sql.VegtermekViewModel;


import blacklinden.com.cannabisgrowthsimulator.ui.CardPagerAdapter;
import blacklinden.com.cannabisgrowthsimulator.ui.CardPagerAdapter2;
import blacklinden.com.cannabisgrowthsimulator.ui.ShadowTransformer;
import blacklinden.com.cannabisgrowthsimulator.ui.grind.Bong;
import blacklinden.com.cannabisgrowthsimulator.ui.grind.ComboEvent;
import blacklinden.com.cannabisgrowthsimulator.ui.grind.GrinderTartalomCV;
import blacklinden.com.cannabisgrowthsimulator.ui.kolibri.Kolibri;

import blacklinden.com.cannabisgrowthsimulator.ui.kolibri.Smoke;
import blacklinden.com.cannabisgrowthsimulator.ui.recy.SelectableAdapter;
import blacklinden.com.cannabisgrowthsimulator.ui.recy.SelectableViewHolder;
import blacklinden.com.cannabisgrowthsimulator.ui.tutorial.DialogFrag;


import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.ChartboostDelegate;


public class Main2Activity extends FragmentActivity implements View.OnClickListener, SelectableViewHolder.OnItemSelectedListener,
        CompoundButton.OnCheckedChangeListener {


    Intent service;
    private Button magv;
    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private CardPagerAdapter2 mCardAdapter2;
    private ShadowTransformer mCardShadowTransformer;
    private Kolibri kolibriAnimator;
    private boolean pakkokNyitva=false;
    private CardView score;
    private TextView achievTv,stashedTv,rackTV,jarTv,kolibriTV,reward;
    private RecyclerView recyclerView;
    private SelectableAdapter adapter;
    private VegtermekViewModel viewModel;
    private Gson gson;
    private GrinderTartalomCV grinderTartalomCV;
    private ImageView grinder,logo;
    private FrameLayout frameGrinder;
    private Bong bong;
    private RelativeLayout fiok;
    private ShimmerFrameLayout shimmer;
    private Button fab;
    private int currentXp,currentCoin;
    private MediaPlayer csirip,csirip2,coinHang;
    private ImageButton start;
    private ImageView[] comboViews;
    private View bongpeg;
    private ImageButton comboMenuGomb;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Mentés.getInstance(this);
        Settings.getInstance();


        Chartboost.getPIDataUseConsent().getValue();

        Chartboost.setPIDataUseConsent(this, Chartboost.CBPIDataUseConsent.YES_BEHAVIORAL);

        Chartboost.startWithAppId(this, "5d17f91fb0f86c0b177698e8", "427051160c4400408650134364a2a4cb8c97165c");



        if(Mentés.getInstance().getString(Mentés.Key.TRMS_LST,"o").equals("o")) {
            List<Termény> trmny = new ArrayList<>();
            String trm = Mentés.getInstance().gsonra(trmny);
            Mentés.getInstance().put(Mentés.Key.TRMS_LST,trm);
        }

        if(Mentés.getInstance().getString(Mentés.Key.ERllT_LST,"o").equals("o")) {
            List<Termény> trmny = new ArrayList<>();
            String trm = Mentés.getInstance().gsonra(trmny);
            Mentés.getInstance().put(Mentés.Key.ERllT_LST,trm);
        }

        if(Mentés.getInstance().getString(Mentés.Key.VGTRMK_LST,"o").equals("o")) {
            List<Stash> trmny = new ArrayList<>();
            String trm = Mentés.getInstance().gsonra(trmny);
            Mentés.getInstance().put(Mentés.Key.VGTRMK_LST,trm);
        }

       if(Mentés.getInstance().getString(Mentés.Key.TESZT_OBJ,"o").equals("o")){
            Lamps lamps = new Lamps("Desk Bulb","CFL",60,4700,3000,
                    R.drawable.cflkek,R.drawable.feher_csova);

            String lampsString = Mentés.getInstance().gsonra(lamps);

            Mentés.getInstance().put(Mentés.Key.TESZT_OBJ,lampsString);

       }

        gson = new GsonBuilder().create();

        Mentés.getInstance().put(Mentés.Key.SAMPLE_ZSETON,100);



        fab = findViewById(R.id.fab);



        start = findViewById(R.id.start);
        kolibriTV = findViewById(R.id.kolibri);
        AnimatedVectorDrawable kolibri = (AnimatedVectorDrawable) kolibriTV.getCompoundDrawables()[3];//(AnimatedVectorDrawable)getResources().getDrawable(R.drawable.kolibriavd,null);//

        kolibri.start();

            kolibri.registerAnimationCallback(new Animatable2.AnimationCallback(){
                @Override
                public void onAnimationEnd(Drawable drawable) {


                    kolibriTV.post(kolibri::start);
                }
            });

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        final float w = point.x;
        kolibriAnimator = new Kolibri(w, kolibriTV);
        kolibriAnimator.setState("repdes",null);
        kolibriAnimator.run();



        mViewPager = findViewById(R.id.viewPager);

        mCardAdapter = new CardPagerAdapter();

        MagVM magVM = ViewModelProviders.of(this).get(MagVM.class);
        magVM.getAll().observe(this, magEntities -> {
            mCardAdapter.addLiveData(magEntities);
            mCardAdapter.notifyDataSetChanged();
        });

        mCardAdapter2 = new CardPagerAdapter2();

        SoilVM soilVM = ViewModelProviders.of(this).get(SoilVM.class);
        soilVM.getAll().observe(this,soilEntities -> {
            mCardAdapter2.addLiveData(soilEntities);
            mCardAdapter2.notifyDataSetChanged();
        });

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);

        mViewPager.setOffscreenPageLimit(3);
        magv = findViewById(R.id.magv);

        mViewPager.setPageTransformer(false, mCardShadowTransformer);

        mViewPager.setCurrentItem(3);

        logo = findViewById(R.id.imageView);
        ViewTreeObserver vto = logo.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                logo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                new ParticleSystem(Main2Activity.this, 10, R.drawable.kenderii, 6000)
                        .setFadeOut(5000)
                        .setRotationSpeed(50)
                        .setScaleRange(2,10)
                        .setSpeedModuleAndAngleRange(0.1f,0.5f,0,365)
                        .oneShot(logo,50);

            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(this,1,GridLayoutManager.HORIZONTAL,false);
        recyclerView = findViewById(R.id.selection_list);
        recyclerView.setLayoutManager(layoutManager);


        final TextView xp = findViewById(R.id.xp);
        coinHang = MediaPlayer.create(this,R.raw.coin);
        score = findViewById(R.id.scoreGomb);
        achievTv = findViewById(R.id.achiev);
        TextView coins = findViewById(R.id.coins);
        ScoreVM scoreVM = ViewModelProviders.of(this).get(ScoreVM.class);
        scoreVM.get().observe(this, scoreEntity -> {

            if (scoreEntity != null) {
                currentXp = scoreEntity.getXp();
                currentCoin = scoreEntity.getScore();
                xp.setText(Integer.toString(scoreEntity.getXp()));
                coins.setText(Integer.toString(scoreEntity.getScore()));
                achievTv.setText(scoreEntity.getRank());
            }

        });
        comboMenuGomb = findViewById(R.id.tutor);
        kolibriAnimator.setListener(new Kolibri.CoinListener() {
            @Override
            public void score() {
                scoreVM.updateScore(currentCoin+=10);
                coinHang.start();
                new ParticleSystem(Main2Activity.this,20,R.drawable.ic_coin,6000L)
                        .setFadeOut(4500)
                        .setSpeedModuleAndAngleRange(0.02f,0.1f,75,105)
                        .oneShot(coins,10);
            }

            @Override
            public void ended() {

                findViewById(R.id.cardTypeBtn).setEnabled(true);
                comboMenuGomb.setEnabled(true);
                findViewById(R.id.dryer).setEnabled(true);
                findViewById(R.id.shop).setEnabled(true);
                start.setEnabled(true);
                findViewById(R.id.box).setEnabled(true);
                findViewById(R.id.magv).setEnabled(true);
                findViewById(R.id.video).setEnabled(true);
                fab.setEnabled(true);
            }


        });


        stashedTv = findViewById(R.id.stashed);
        rackTV = findViewById(R.id.rack);
        jarTv = findViewById(R.id.jars);

        comboViews = new ImageView[]{findViewById(R.id.combo1),findViewById(R.id.combo2),findViewById(R.id.combo3)};
         csirip = MediaPlayer.create(this,R.raw.kolinyert);
         csirip2 = MediaPlayer.create(this,R.raw.kolikinevet);


        ComboEvent comboEvent = new ComboEvent(new ComboEvent.ComboListener() {
            @Override
            public void comboFound(String[] combo) {
                findViewById(R.id.cardTypeBtn).setEnabled(false);
                comboMenuGomb.setEnabled(false);
                findViewById(R.id.dryer).setEnabled(false);
                findViewById(R.id.shop).setEnabled(false);
                start.setEnabled(false);
                findViewById(R.id.box).setEnabled(false);
                findViewById(R.id.magv).setEnabled(false);
                findViewById(R.id.video).setEnabled(false);
                fab.setEnabled(false);
                int szin1 = Color.WHITE;
                int szin2 = Color.RED;

                switch (combo[2]) {
                    case "Skunk":
                    comboViews[2].setImageResource(R.drawable.ic_skunk_n1);
                    break;
                    case "Afghan":
                    comboViews[2].setImageResource(R.drawable.ic_afghan);
                    break;
                    case "Balkan Rose":
                    comboViews[2].setImageResource(R.drawable.ic_balkan_rose);
                    break;
                    case "BlueBerry":
                    comboViews[2].setImageResource(R.drawable.ic_blueberry_icon);
                    break;
                    case "Northern Light":
                    comboViews[2].setImageResource(R.drawable.ic_northern_light_icon);
                    break;
                    case "Grape Ape":
                    comboViews[2].setImageResource(R.drawable.ic_grape_ape_icon);
                    break;
                    case "AK47":
                    comboViews[2].setImageResource(R.drawable.ic_ak47);
                    break;
                    case "Cheese":
                    comboViews[2].setImageResource(R.drawable.ic_cheese_icon);
                    break;
                    case "Amnesia":
                    comboViews[2].setImageResource(R.drawable.ic_amnesia_icon);
                    break;
                    case "S.Lemon Haze":
                    comboViews[2].setImageResource(R.drawable.ic_super_lemon_haze);
                    break;
                    case "White Widow":
                    comboViews[2].setImageResource(R.drawable.ic_white_widow_icon);
                    break;
                    case "Gelato":
                    comboViews[2].setImageResource(R.drawable.ic_gelato_icon);
                    break;
                    case "Ghost OG":
                    comboViews[2].setImageResource(R.drawable.ic_ghost_og_icon);
                    break;
                    case "Cherry Diesel":
                    comboViews[2].setImageResource(R.drawable.ic_cherry_diesel_icon);
                    break;
                    case "Permafrost":
                    comboViews[2].setImageResource(R.drawable.ic_permafrost_icon);
                    break;
                    case "Pink Mango":
                    comboViews[2].setImageResource(R.drawable.ic_pink_mango_icon);
                    break;
                    case "Pineapple":
                    comboViews[2].setImageResource(R.drawable.ic_pineapple_express);
                    break;
                    case "G.White Shark":
                    comboViews[2].setImageResource(R.drawable.ic_gws_icon);
                    break;
                    case "Nurse R.":
                    comboViews[2].setImageResource(R.drawable.ic_nrs_icon);
                    break;
                    case "Durban":
                    comboViews[2].setImageResource(R.drawable.ic_durban_poison_icon);
                    break;

                }

                villog(comboMenuGomb,szin1,szin2,20);

                if(!isFinishing())
                comboDialog();

                csirip.start();
                ComboVM comboVM = ViewModelProviders.of(Main2Activity.this).get(ComboVM.class);
                StringBuilder sb = new StringBuilder();

                for(int i=0;i<combo.length;i++) {
                    if(i==2)
                    sb.append(combo[i]);
                    else
                    sb.append(combo[i]).append("_");
                }
                comboVM.insert(new ComboEntity(sb.toString()));
            }

            @Override
            public void notFound() {

                resetComboViews();

                csirip2.start();
            }
        });


        grinderTartalomCV = findViewById(R.id.grinderTartalom);
        grinder = findViewById(R.id.grinder2);
        frameGrinder =findViewById(R.id.frameGrinder);
        shimmer = findViewById(R.id.csilivili);


        fiok = findViewById(R.id.fiokajto);
        final ImageButton lighterButton = findViewById(R.id.gyjt);
        Drawable lighter = lighterButton.getDrawable();

        bong = findViewById(R.id.bong);
        bongpeg = findViewById(R.id.bongpeg);
        bong.setListener(new Bong.BongListener() {
            @Override
            public void lightUp() {

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
                new ParticleSystem(Main2Activity.this,50,R.drawable.fust_particle,5000L)
                        .setFadeOut(2500)
                        .setRotationSpeed(100)
                        .setScaleRange(2,10 )
                        .setSpeedModuleAndAngleRange(0.01f,0.05f,10,80)
                        .emitWithGravity(kolibriTV,Gravity.CENTER_VERTICAL,counter,1000);
            }

            @Override
            public void end(int i, String fajta) {
                kolibriAnimator.setState("repdes",null);
                scoreVM.updateXP(currentXp+=i);
                if(i>0)
                new ParticleSystem(Main2Activity.this,20,R.drawable.ic_bobi_coin_iconv2,5000L)
                        .setFadeOut(4500)
                        .setSpeedModuleAndAngleRange(0.02f,0.1f,75,105)
                        .oneShot(xp,i);

                bong.animate().rotation(0).start();
                grinder.setEnabled(true);
            if(comboEvent.getSmokeCounter()<=1) {
                switch (fajta) {
                    case "Skunk":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_skunk_n1);
                        break;
                    case "Afghan":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_afghan);
                        break;
                    case "Balkan Rose":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_balkan_rose);
                        break;
                    case "BlueBerry":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_blueberry_icon);
                        break;
                    case "Northern Light":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_northern_light_icon);
                        break;
                    case "Grape Ape":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_grape_ape_icon);
                        break;
                    case "AK47":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_ak47);
                        break;
                    case "Cheese":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_cheese_icon);
                        break;
                    case "Amnesia":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_amnesia_icon);
                        break;
                    case "S.Lemon Haze":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_super_lemon_haze);
                        break;
                    case "White Widow":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_white_widow_icon);
                        break;
                    case "Gelato":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_gelato_icon);
                        break;
                    case "Ghost OG":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_ghost_og_icon);
                        break;
                    case "Cherry Diesel":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_cherry_diesel_icon);
                        break;
                    case "Permafrost":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_permafrost_icon);
                        break;
                    case "Pink Mango":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_pink_mango_icon);
                        break;
                    case "Pineapple":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_pineapple_express);
                        break;
                    case "G.White Shark":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_gws_icon);
                        break;
                    case "Nurse R.":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_nrs_icon);
                        break;
                    case "Durban":
                        comboViews[comboEvent.fillUpComboArray(fajta)].setImageResource(R.drawable.ic_durban_poison_icon);
                        break;

                }
            }else
                comboEvent.fillUpComboArray(fajta);
            }

        });

        grinderTartalomCV.setGrinderListener(new GrinderTartalomCV.GrinderListener() {

            @Override
            public void onEmpty() {
                if(shimmer.isShimmerStarted())
                shimmer.stopShimmer();

                if(!pakkokNyitva) {

                    bong.animate()
                            .translationX(0)
                            .translationZ(0)
                            .rotation(-20)
                            .setDuration(500)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    grinder.animate()
                                            .translationY(0)
                                            .setStartDelay(200)
                                            .setDuration(500)
                                            .setListener(new AnimatorListenerAdapter() {
                                                @Override
                                                public void onAnimationEnd(Animator animation) {
                                                    super.onAnimationEnd(animation);
                                                    frameGrinder.setVisibility(View.GONE);
                                                }
                                            })
                                            .start();
                                }
                            })
                            .start();
                }


            }

            @Override
            public void onFilled() {
                if(!shimmer.isShimmerStarted())
                 shimmer.startShimmer();


            }

            @Override
            public void onGrinded() {
                if(shimmer.isShimmerStarted()) {

                    shimmer.stopShimmer();
                    kolibriAnimator.setState("smoke",bongpeg);
                }
            }
        });

       MyApp.addStaticListener(this::updateStaticTV);

        adapter = new SelectableAdapter(this,false);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(VegtermekViewModel.class);

        viewModel.getAll().observe(this, vegtermekek -> {


            if (vegtermekek != null) {
                adapter.setLiveValues(vegtermekek);
                stashedTv.setText(Integer.toString(vegtermekek.size()));
            }
        });

        service = new Intent(Main2Activity.this, LService.class);



        reward = findViewById(R.id.reward);
        reward.setVisibility(View.GONE);

        ChartboostDelegate chartboostDelegate = new ChartboostDelegate() {


            @Override
            public boolean shouldDisplayRewardedVideo(String location) {
                return super.shouldDisplayRewardedVideo(location);
            }

            @Override
            public void didCacheRewardedVideo(String location) {
                super.didCacheRewardedVideo(location);
                reward.setVisibility(View.VISIBLE);

            }

            @Override
            public void didFailToLoadRewardedVideo(String location, CBError.CBImpressionError error) {
                super.didFailToLoadRewardedVideo(location, error);

            }

            @Override
            public void didCloseRewardedVideo(String location) {
                super.didCloseRewardedVideo(location);
                new ParticleSystem(Main2Activity.this,20,R.drawable.ic_coin,3000L)
                        .setFadeOut(2000)
                        .setSpeedModuleAndAngleRange(0.02f,0.1f,75,105)
                        .oneShot(coins,100);

            }

            @Override
            public void didCompleteRewardedVideo(String location, int reward) {
                super.didCompleteRewardedVideo(location, reward);
                scoreVM.updateScore(currentCoin+reward*100);

            }

            @Override
            public void didDisplayRewardedVideo(String location) {
                super.didDisplayRewardedVideo(location);
                if(Chartboost.hasRewardedVideo(CBLocation.LOCATION_DEFAULT))
                reward.setVisibility(View.VISIBLE);
                else
                reward.setVisibility(View.GONE);

            }
        };

        Chartboost.setDelegate(chartboostDelegate);
        Chartboost.onCreate(this);
        Chartboost.setAutoCacheAds(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Chartboost.onResume(this);
        insert();
        updateStaticTV();
        kolibriAnimator.run();
        kolibriAnimator.setState("repdes",null);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Chartboost.onStart(this);
        if(Settings.getInstance().getTutorialSequence()) {
            //kolibriAnimator.setTutorial_e(tutorialTmb);
            kolibriAnimator.run();
        }else {
            kolibriAnimator.setState("repdes", null);
            kolibriAnimator.run();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Chartboost.onStop(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        releaseMedia();
        Chartboost.onDestroy(this);
        kolibriAnimator.dispose();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Chartboost.onPause(this);
        kolibriAnimator.dispose();
    }

    private int[] updateStashConsumption(int mennyi){
        int[] levont = new int[2];
       if(mennyi<6f) {
           //viewModelnek
           //levont[0] = 0;
           //grinderFillupnak
           levont[1] = mennyi;
       }
       else {
           //viewModelnek
           levont[0] = mennyi - 6;
           //grinderFillupnak
           levont[1] = 6;
       }
           return levont;
    }


    private void insert(){
        String rawVgktrmk = Mentés.getInstance().getString(Mentés.Key.VGTRMK_LST,"o");
        if(!rawVgktrmk.equalsIgnoreCase("o")) {
            Type vList = new TypeToken<ArrayList<Stash>>() {
            }.getType();
            List<Stash> vgtrmkList = gson.fromJson(rawVgktrmk, vList);
                if (!vgtrmkList.isEmpty()) {

                 for (Stash s : vgtrmkList) {

                    viewModel.insert(new Vegtermek(s.getFajta(), s.getMinőség(), s.getMennyi()));

                    Toast.makeText(
                    getApplicationContext(),
                    s.getFajta() + " Added",
                    Toast.LENGTH_LONG).show();
                }

                    List<Stash> trmny = new ArrayList<>();
                    String trm = Mentés.getInstance().gsonra(trmny);
                    Mentés.getInstance().put(Mentés.Key.VGTRMK_LST, trm);

                }
            }
    }

    @SuppressLint("SetTextI18n")
    private void updateStaticTV(){
        String rawTermes = Mentés.getInstance().getString(Mentés.Key.TRMS_LST,"o");
        Type tList = new TypeToken<ArrayList<Termény>>() {
        }.getType();
        List<Termény> trmkList = gson.fromJson(rawTermes, tList);

        String rawErlelmeny = Mentés.getInstance().getString(Mentés.Key.ERllT_LST,"o");
        Type eList = new TypeToken<ArrayList<Termény>>() {
        }.getType();
        List<Termény> erlltList = gson.fromJson(rawErlelmeny, eList);


        if(!trmkList.isEmpty()){
            int tempR=0;
            for(Termény t:trmkList)
                tempR+=t.getSuly();

                rackTV.setText(Integer.toString(tempR));
        }else  rackTV.setText("0");

        if(!erlltList.isEmpty()){
            int tempJ=0;
            for(Termény t:erlltList)
                tempJ+=t.getSuly();
                jarTv.setText(Integer.toString(tempJ));
        }else jarTv.setText("0");

    }

    public void szarito(View v)
    {
        Intent i = new Intent(this, StashActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.zoom_out,R.anim.zoom_in);
    }


    public void mag(View v){

            mViewPager.setAdapter(mCardAdapter);
            score.setVisibility(View.GONE);
            start.setVisibility(View.GONE);
            mViewPager.setVisibility(View.VISIBLE);
            magv.setVisibility(View.GONE);
            findViewById(R.id.box).setVisibility(View.GONE);
            if(!LService.IS_SERVICE_RUNNING)
            kolibriAnimator.setState("tutorial",mViewPager);


    }

    public void closeMag(View v){
        mViewPager.setVisibility(View.GONE);
        score.setVisibility(View.VISIBLE);
        magv.setVisibility(View.VISIBLE);
        findViewById(R.id.box).setVisibility(View.VISIBLE);
        start.setVisibility(View.VISIBLE);
        mViewPager.setAdapter(null);
        if(Kender.getInstance().getFajta()>0)
            kolibriAnimator.setState("tutorial", findViewById(R.id.start));
        else
            kolibriAnimator.setState("tutorial",magv);
    }



    public void startSim(View v){

        Intent i = new Intent(this,MainActivity.class);
        //i.putExtra("tutorial_e",kolibriAnimator.isTutorial());

        if(Kender.getInstance().getFajta()>0) {
            if (!LService.IS_SERVICE_RUNNING) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    service.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                    startForegroundService(service);
                    startActivity(i);
                    overridePendingTransition(R.anim.zoom_out,R.anim.zoom_in);
                } else {
                    service.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                    //LService.IS_SERVICE_RUNNING = true;
                    startService(service);
                    startActivity(i);
                    overridePendingTransition(R.anim.zoom_out,R.anim.zoom_in);
                }

            }else if(isTaskRoot()){
                //if(Kender.getInstance().getFöld().equals("0"))
                //Kender.getInstance().setFöld("coco");
                startActivity(i);
                overridePendingTransition(R.anim.zoom_out,R.anim.zoom_in);
            }else finish();

                //kolibriAnimator.dispose();
        }else {
            kolibriAnimator.setState("tutorial",findViewById(R.id.magv));

        }
    }



    public void setStrain(View v) {
        int i = Integer.parseInt((String) mCardAdapter.getCardViewAt(mViewPager.getCurrentItem()).findViewById(R.id.seedsLeft).getTag());
        if(!LService.IS_SERVICE_RUNNING) {
            if (i > 0) {
                Kender.getInstance();
                Kender.getInstance().fajta(mCardAdapter.getCardViewAt(mViewPager.getCurrentItem()).findViewById(R.id.flower).getTag().toString(), i);
                Kender.getInstance().fajtaDrawCode = mCardAdapter.getDrawCode(mViewPager.getCurrentItem());
                Kender.getInstance().setReward(Integer.parseInt(mCardAdapter.getCardViewAt(mViewPager.getCurrentItem()).findViewById(R.id.reward).getTag().toString()));
                kolibriAnimator.setState("repdes", null);

                mViewPager.setVisibility(View.GONE);
                score.setVisibility(View.VISIBLE);
                magv.setVisibility(View.VISIBLE);
                findViewById(R.id.box).setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);
                mViewPager.setAdapter(null);

                kolibriAnimator.setState("tutorial", start);


            } else kolibriAnimator.setState("tutorial", findViewById(R.id.shop));

        }else{
            mViewPager.setAdapter(null);
            mViewPager.setVisibility(View.GONE);
            score.setVisibility(View.VISIBLE);
            magv.setVisibility(View.VISIBLE);
            findViewById(R.id.box).setVisibility(View.VISIBLE);
            start.setVisibility(View.VISIBLE);
            Toast.makeText(this,"Your Plant is Still Growing",Toast.LENGTH_SHORT).show();
            kolibriAnimator.setState("tutorial",start);
        }


    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCardShadowTransformer.enableScaling(b);

    }

    public void menuActivity(View v){
        Intent ii = new Intent(Main2Activity.this,InventoryActivity.class);
        startActivity(ii);
        overridePendingTransition(R.anim.zoom_out,R.anim.zoom_in);
    }
    public void openShop(View v){
        if(mViewPager.getAdapter()!=null) closeAllPager();
        startActivity(new Intent(this,ShopActivity.class));
        overridePendingTransition(R.anim.zoom_out,R.anim.zoom_in);
    }

    public void nyissBox(View v){

            mViewPager.setAdapter(mCardAdapter2);
            start.setVisibility(View.GONE);
            score.setVisibility(View.GONE);
            mViewPager.setVisibility(View.VISIBLE);
            magv.setVisibility(View.GONE);
            findViewById(R.id.box).setVisibility(View.GONE);
            if(!LService.IS_SERVICE_RUNNING)
            kolibriAnimator.setState("tutorial",mViewPager);

    }

    public void setBox(View v){
        int i = ((int) mCardAdapter2.getCardViewAt(mViewPager.getCurrentItem()).findViewById(R.id.typeTV).getTag());
        String fld = mCardAdapter2.getCardViewAt(mViewPager.getCurrentItem()).findViewById(R.id.titleTextView).getTag().toString();
        kolibriAnimator.setState("repdes",null);
        if(!LService.IS_SERVICE_RUNNING) {
            if (i > 0 && !fld.equals("dirt")) {
                Kender.getInstance().setFöld(fld);
                mViewPager.setAdapter(null);
                mViewPager.setVisibility(View.GONE);
                score.setVisibility(View.VISIBLE);
                magv.setVisibility(View.VISIBLE);
                findViewById(R.id.box).setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);
            } else {
                mViewPager.setAdapter(null);
                mViewPager.setVisibility(View.GONE);
                score.setVisibility(View.VISIBLE);
                magv.setVisibility(View.VISIBLE);
                findViewById(R.id.box).setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);
            }
        }else{
            mViewPager.setAdapter(null);
            mViewPager.setVisibility(View.GONE);
            score.setVisibility(View.VISIBLE);
            magv.setVisibility(View.VISIBLE);
            findViewById(R.id.box).setVisibility(View.VISIBLE);
            start.setVisibility(View.VISIBLE);
            Toast.makeText(this,"Your Plant is Still Growing",Toast.LENGTH_SHORT).show();
            kolibriAnimator.setState("tutorial",start);
        }



    }

    private void closeAllPager(){
        mViewPager.setAdapter(null);
        mViewPager.setVisibility(View.GONE);
        score.setVisibility(View.VISIBLE);
        magv.setVisibility(View.VISIBLE);
        findViewById(R.id.box).setVisibility(View.VISIBLE);
        start.setVisibility(View.VISIBLE);
    }

    public void fiokajtoNyitas(View v) {
        if (!pakkokNyitva) {
            recyclerView.setVisibility(View.VISIBLE);
            pakkokNyitva = true;
            float d = fiok.getWidth();
            fiok.animate().translationX(-d).setDuration(500).setInterpolator(new BounceInterpolator()).start();
        } else {
            recyclerView.setVisibility(View.GONE);
            pakkokNyitva=false;
            fab.setText(R.string.selectableStash);
            fiok.animate().translationX(0).setDuration(500).setInterpolator(new BounceInterpolator()).start();
        }

        logoGrinderOpener(pakkokNyitva);
        if(!(kolibriAnimator.getState() instanceof Smoke))
        kolibriAnimator.setState("repdes",null);

    }




    @Override
    public void onItemSelected(SelectableStashItem selectableItem) {

        List<Stash> selectedItems = adapter.getSelectedItems();
        Snackbar.make(recyclerView,"Selected: "+selectableItem.getFajta()+" "+String.format(java.util.Locale.US,"%.2f",selectableItem.getMennyi())+"Gr."+
                ", Quality: "+selectableItem.getMinőség(),Snackbar.LENGTH_LONG).show();
        fab.setText(R.string.selectableConfirm);
        villog(fab,Color.BLACK,Color.RED,1);
        if(selectedItems.isEmpty()) {
            grinderTartalomCV.dispose();
            grinderTartalomCV.invalidate();
        }else if(!bong.isFilled()&&grinderTartalomCV.getIsEmpty()){

            findViewById(R.id.frameGrinder).setVisibility(View.VISIBLE);
            grinderTartalomCV.fillUp(Teknős.flowerStrain(
                    this, selectableItem.getFajta()), updateStashConsumption((int)selectableItem.getMennyi()),
                    selectableItem.getId(),
                    selectableItem.getFajta(),
                    selectableItem.getMinőség(),
                    selectableItem.getThc(),
                    selectableItem.getCbd()
            );


        }

    }


    private void logoGrinderOpener(boolean csukva){
        if(!bong.isFilled()) {
            if (csukva) {

                if (grinderTartalomCV.getIsEmpty())
                    openTopFromNothing();
                else
                    openTop();


            } else closeTop();
        }
    }

    private void openTop() {

        FrameLayout frame = findViewById(R.id.frameGrinder);
        if(frame.getVisibility()==View.GONE)
        frame.setVisibility(View.VISIBLE);

        grinder.animate()
                .translationZ(20)
                .rotation(20)
                .setInterpolator(new LinearInterpolator())
                .setDuration(1000)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        fab.setEnabled(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                        grinder.animate()
                                .translationX(grinder.getWidth())
                                .setDuration(500)
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
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).start();
    }
    private void closeTop(){
        grinder.animate()
                .translationX(0)
                .translationZ(0)
                .rotation(0)
                .setInterpolator(new DecelerateInterpolator())
                .setDuration(500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        fab.setEnabled(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if(grinderTartalomCV.getIsEmpty()){
                            findViewById(R.id.frameGrinder).setVisibility(View.GONE);
                        }
                        fab.setEnabled(true);
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

    public void grindIt(View view){

        if(shimmer.isShimmerStarted()) {
            view.setEnabled(false);
            fab.setEnabled(false);
            adapter.resetSelectedItems();

            grinder.animate()
                    .setStartDelay(200)
                    .rotation(360)
                    .setDuration(1000)
                    .setInterpolator(new DecelerateInterpolator())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            grinderTartalomCV.setGrinded(true);
                            grinder.animate()
                                    .setStartDelay(200)
                                    .translationY(-grinder.getHeight())
                                    .setDuration(2000)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            fillBong();
                                        }
                                    })
                                    .start();

                        }

                    })
                    .start();

        }

    }

    private void fillBong() {

        bong.animate()
                .translationX(bong.getHeight())
                .setStartDelay(200)
                .setDuration(1000)
                .setInterpolator(new BounceInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        viewModel.update(grinderTartalomCV.getStashID(),grinderTartalomCV.getLevonando());
                        bong.fillUp(grinderTartalomCV.getFajta(),grinderTartalomCV.getThc(),grinderTartalomCV.getCbd(),grinderTartalomCV.getMnsg());
                        grinderTartalomCV.dispose();
                        grinderTartalomCV.invalidate();
                        fab.setEnabled(true);
                        }

                }).start();

    }


    private void openTopFromNothing(){

        FrameLayout frame = findViewById(R.id.frameGrinder);
                frame.setVisibility(View.VISIBLE);

        int cx = logo.getRight() / 2;
        int cy = logo.getBottom()/2;
        int finalRadius = (int) Math.hypot(logo.getWidth()/2,
                logo.getHeight()/2);

        Animator a = ViewAnimationUtils.createCircularReveal(frame, cx,
                cy, 0, finalRadius);
        a.setDuration(300);
        a.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                fab.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

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
                        }).start();

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        a.start();
    }

    private void villog(View view,int szin1,int szin2, int ism) {
        Drawable drawable = view.getBackground();
        ObjectAnimator anim = ObjectAnimator.ofInt(view, "backgroundColor", szin1, szin2,
                Color.TRANSPARENT);

        anim.setDuration(1500);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(ValueAnimator.REVERSE);
        if(ism!=-1)
            anim.setRepeatCount(ism);
        else
            anim.setRepeatCount(Animation.INFINITE);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setBackground(drawable);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        anim.start();
    }



    public void playVideo(View view) {

        if(!Chartboost.hasRewardedVideo(CBLocation.LOCATION_DEFAULT))
        Chartboost.cacheRewardedVideo(CBLocation.LOCATION_DEFAULT);
        Handler h = new Handler();

        Runnable runnable= ()->{
            if(Chartboost.hasRewardedVideo(CBLocation.LOCATION_DEFAULT))
            Chartboost.showRewardedVideo(CBLocation.LOCATION_DEFAULT);
            else
            Toast.makeText(getBaseContext(),"Try Again Later",Toast.LENGTH_SHORT).show();
        };

        h.postDelayed(runnable,1000);
    }

    public void comboMenu(View view) {
        startActivity(new Intent(Main2Activity.this,ComboActivity.class));
        overridePendingTransition(R.anim.zoom_out,R.anim.zoom_in);
    }

    private void comboDialog(){
        resetComboViews();
        new AlertDialog.Builder(this,R.style.CustomDialogTheme)
                .setTitle("Combo Event!")
                .setMessage("Catch the bird, before the time runs out.")
                .setPositiveButton("GO", (dialog, which) -> kolibriAnimator.setState("fogocska",null))
                .setNegativeButton("NOT NOW", (dialog, which) -> {
                    findViewById(R.id.cardTypeBtn).setEnabled(true);
                    comboMenuGomb.setEnabled(true);
                    findViewById(R.id.dryer).setEnabled(true);
                    findViewById(R.id.shop).setEnabled(true);
                    start.setEnabled(true);
                    findViewById(R.id.box).setEnabled(true);
                    findViewById(R.id.magv).setEnabled(true);
                    findViewById(R.id.video).setEnabled(true);
                    fab.setEnabled(true);
                    dialog.dismiss();

                })
                .setCancelable(false)

                .show();
    }

    private void resetComboViews(){
        for(int i=0;i<comboViews.length-1;i++)
            comboViews[i].setImageResource(R.drawable.ic_emptycombo);

        comboViews[2].setImageResource(R.drawable.ic_emptycombo);
    }

    @Override
    public void onBackPressed() {
        // If an interstitial is on screen, close it.
        if (Chartboost.onBackPressed())
            return;
        else
            super.onBackPressed();
    }

    private void releaseMedia(){
       if(csirip!=null) {
           csirip.stop();
           csirip.reset();
           csirip.release();
       }
       if(csirip2!=null) {
           csirip2.stop();
           csirip2.reset();
           csirip2.release();
       }
       if(coinHang!=null) {
           coinHang.stop();
           coinHang.reset();
           coinHang.release();
       }

    }

    public void levelUps(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialogFrag = new DialogFrag();
        dialogFrag.show(ft,"dialog");

    }
}
