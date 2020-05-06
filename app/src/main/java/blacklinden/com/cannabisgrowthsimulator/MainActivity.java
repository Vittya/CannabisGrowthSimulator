package blacklinden.com.cannabisgrowthsimulator;


import android.app.Dialog;

import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.github.alexjlockwood.kyrie.KyrieDrawable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.plattysoft.leonids.ParticleSystem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import blacklinden.com.cannabisgrowthsimulator.canvas.CanvasView;
import blacklinden.com.cannabisgrowthsimulator.eszk.AlsFiok;
import blacklinden.com.cannabisgrowthsimulator.eszk.FelsFiok;
import blacklinden.com.cannabisgrowthsimulator.eszk.Mentés;
import blacklinden.com.cannabisgrowthsimulator.eszk.ThermoView;
import blacklinden.com.cannabisgrowthsimulator.nov.Kender;
import blacklinden.com.cannabisgrowthsimulator.pojo.NutriEntity;
import blacklinden.com.cannabisgrowthsimulator.pojo.SoilEntity;
import blacklinden.com.cannabisgrowthsimulator.pojo.Termény;
import blacklinden.com.cannabisgrowthsimulator.serv.LService;
import blacklinden.com.cannabisgrowthsimulator.settings.Settings;
import blacklinden.com.cannabisgrowthsimulator.sql.MagVM;
import blacklinden.com.cannabisgrowthsimulator.sql.NutriVM;
import blacklinden.com.cannabisgrowthsimulator.sql.ScoreVM;
import blacklinden.com.cannabisgrowthsimulator.sql.SoilVM;
import blacklinden.com.cannabisgrowthsimulator.ui.kolibri.Kolibri;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener, Runnable {

    CanvasView canvasView;
    ThermoView thermoView;
    CardView polc;
    ImageButton bulb, seed, cserép, táp, kanna, ollo;
    ImageView fátyol;
    ImageView ventilView;
    private DrawerLayout drawerLayout;
    boolean ventilBE;
    private boolean toDryer;
    TextView napTV, indicator;
    private LService lService;
    Intent service, intent;
    private Dialog dialog;
    private MediaPlayer nutriGoo, loccs, gong;
    private PopupWindow quickAction;
    private ImageView tapeta;
    private TypedValue outValue;
    private KyrieDrawable vectorDrawable;
    private Kolibri kolibriAnimator;
    private ImageView ivLC;
    private TextView tvLC,soilIndicator;
    private LayoutInflater inflater;
    private View customView;
    private Gson gson;
    private Handler fieldHandler = new Handler();
    private NutriVM nutriVM;
    private SoilVM soilVM;
    private ScoreVM scoreVM;
    private HashMap<String, Integer> soilMennyi;
    private HashMap<String, Integer> nutriMennyi;
    private int lightCounter, score;
    private AnimatedVectorDrawable ventilAvd,olloAvd;
    private boolean isKilled=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gson = new GsonBuilder().create();
        String skin = Mentés.getInstance(this).getString(Mentés.Key.SKN, "");
        FelsFiok felsFiok = findViewById(R.id.felso);
        AlsFiok alsFiok = findViewById(R.id.also);
        tapeta = findViewById(R.id.httr_tapeta);





        switch (skin) {
            case "a":
                felsFiok.setBackgroundResource(R.drawable.jamanfelso);
                alsFiok.setBackgroundResource(R.drawable.jamanalso);
                tapeta.setBackgroundResource(R.drawable.jamankozepso);
                break;
            case "b":
                felsFiok.setBackgroundResource(R.drawable.goafelso);
                alsFiok.setBackgroundResource(R.drawable.goaalso);
                tapeta.setBackgroundResource(R.drawable.goakozepso);
                break;
            case "c":
                felsFiok.setBackgroundResource(R.drawable.cavefelso);
                alsFiok.setBackgroundResource(R.drawable.cavealso);
                tapeta.setBackgroundResource(R.drawable.cavekozepso);
                break;
            case "d":
                felsFiok.setBackgroundResource(R.drawable.boxfelso);
                alsFiok.setBackgroundResource(R.drawable.boxalso);
                tapeta.setBackgroundResource(R.drawable.boxkozepso);
                break;
            case "0":
                felsFiok.setBackgroundColor(Color.argb(255, 130, 82, 1));
                alsFiok.setBackgroundColor(Color.argb(255, 130, 82, 1));
                tapeta.setBackgroundResource(R.drawable.wooden_tiler);
                break;
        }
        intent = new Intent(this, Main2Activity.class);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (quickAction != null) {
                    quickAction.dismiss();
                    quickAction = null;
                }
            }
        });
        outValue = new TypedValue();
        this.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        ivLC = findViewById(R.id.ivLC);
        tvLC = findViewById(R.id.tvLC);
        soilIndicator = findViewById(R.id.soil_indicator);
        soilIndicator.setTextColor(Color.BLACK);
        soilIndicator.setText(Kender.getInstance().CC.föld.soilName);
        TextView kolibriTV = findViewById(R.id.kolibri);

        final AnimatedVectorDrawable kolibri = (AnimatedVectorDrawable) kolibriTV.getCompoundDrawables()[3];

        kolibri.start();
        if(kolibri!=null){
            kolibri.registerAnimationCallback(new Animatable2.AnimationCallback(){
                @Override
                public void onAnimationStart(Drawable drawable) {
                    super.onAnimationStart(drawable);
                    kolibriTV.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                }

                @Override
                public void onAnimationEnd(Drawable drawable) {
                    kolibriTV.setLayerType(View.LAYER_TYPE_NONE, null);
                    kolibriTV.post(kolibri::start);
                }
            });
        }

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        final float w = point.x;
        kolibriAnimator = new Kolibri(w, kolibriTV);

        ventilView = findViewById(R.id.ventilTest);

            ventilAvd = (AnimatedVectorDrawable) ventilView.getDrawable();

        //ventilAvd = AnimatedVectorDrawableCompat.create(this,R.drawable.ventilpro);
        if (ventilAvd != null) {
            ventilAvd.registerAnimationCallback(new Animatable2.AnimationCallback(){
                @Override
                public void onAnimationStart(Drawable drawable) {
                    super.onAnimationStart(drawable);
                    ventilView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
                }

                @Override
                public void onAnimationEnd(Drawable drawable) {
                    ventilView.setLayerType(View.LAYER_TYPE_NONE,null);
                    if(Kender.getInstance().FF.ventilKapcs)
                    ventilView.post(() -> ventilAvd.start());
                }
            });
        }

        nutriGoo = MediaPlayer.create(this, R.raw.nutri);
        loccs = MediaPlayer.create(this, R.raw.loccs);
        gong = MediaPlayer.create(this, R.raw.gong);

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    // set item as selected to persist highlight
                    // menuItem.setChecked(false);

                    drawerLayout.closeDrawers();

                    switch (menuItem.getItemId()) {
                        case R.id.killit:

                            new AlertDialog.Builder(this)
                                    .setTitle("Kill Plant")
                                    .setMessage("Are you sure?")
                                    .setIcon(R.drawable.koponyacsont)
                                    .setPositiveButton("Yes", (dialog, whichButton) -> {
                                        isKilled=true;
                                        lService.stopForeground(true);
                                        lService.stopSelf();
                                        lService.vége();
                                        Kender.getInstance().clear();
                                        if(isTaskRoot()){
                                            startActivity(intent);
                                            finish();
                                        }else
                                            finish();
                                    })
                                    .setNegativeButton("No", null).show();

                            break;

                        case R.id.back:
                            if (isTaskRoot())
                                startActivity(intent);

                            else
                                finish();

                            break;

                        case R.id.inventory:
                            Intent i = new Intent(MainActivity.this, InventoryActivity.class);
                            startActivity(i);
                            finish();
                            break;

                        case R.id.toShop:
                            startActivity(new Intent(MainActivity.this, ShopActivity.class));

                            break;

                        case R.id.colibri:
                            if (kolibriTV.getVisibility() == View.GONE) {
                                kolibriAnimator.run();
                                kolibriTV.setVisibility(View.VISIBLE);
                                Settings.getInstance().setKolibriOn(true);
                            } else {
                                //lehet h nem kell
                                kolibriAnimator.dispose();
                                kolibriTV.setVisibility(View.GONE);
                                Settings.getInstance().setKolibriOn(false);
                            }
                            break;

                        case R.id.todryer:

                            startActivity(new Intent(MainActivity.this, StashActivity.class));
                            finish();
                            break;
                    }

                    return true;
                });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.densityDpi;

        kanna = findViewById(R.id.locsol);
        seed = findViewById(R.id.daseed);
        cserép = findViewById(R.id.cserep);
        táp = findViewById(R.id.tap1);
        bulb = findViewById(R.id.gmb);
        ViewTreeObserver vto = bulb.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {


                bulb.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        kanna.setImageDrawable(getDrawable(Kender.getInstance().VV.setDrawCode()));
        cserép.setImageDrawable(getDrawable(Kender.getInstance().CC.setDrawableCode()));
        táp.setImageDrawable(getDrawable(Kender.getInstance().nutes.setDrawCode()));
        try {
            vectorDrawable = KyrieDrawable.create(this.getApplicationContext(), Kender.getInstance().FF.setDrawCode(""));
        } catch (Exception e) {
            vectorDrawable = KyrieDrawable.create(this.getApplicationContext(), R.drawable.yellow_hps);
        }
        bulb.setRotation(180);
        bulb.setImageDrawable(vectorDrawable);

        seed.setOnTouchListener(this);
        cserép.setOnDragListener(this);
        kanna.setOnTouchListener(this);
        táp.setOnTouchListener(this);
        ollo = findViewById(R.id.ollo);

        olloAvd = (AnimatedVectorDrawable)ollo.getDrawable();


        napTV = findViewById(R.id.nap);
        indicator = findViewById(R.id.seedNameIndicator);
        setIndicator();
        //ventilBE = false;
        canvasView = findViewById(R.id.canvas);

        final float cserepMagassag = 52;
        canvasView.metrix((density / 160) * cserepMagassag);
        Kender.getInstance().metrix(dm.heightPixels / density);
        thermoView = findViewById(R.id.thermo);

        fátyol = findViewById(R.id.fatyol);
        polc = findViewById(R.id.polc);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnDismissListener(dialogInterface -> {

                toDryer=saveWeed();
                fieldHandler.post(ii);

        });
        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(v -> {
            v.setVisibility(View.GONE);
            dialog.findViewById(R.id.pb).setVisibility(View.VISIBLE);

            if (saveWeed()) {
                toDryer=true;
                fieldHandler.postDelayed(ii, 2000);
            } else {
                toDryer=false;
                v.setClickable(false);
                v.setVisibility(View.VISIBLE);
                ((Button) v).setText("UNSAVED");
                fieldHandler.postDelayed(ii, 2000);
            }

        });


        if (Kender.getInstance().FF.beKapcs)
            fátyol.setVisibility(View.VISIBLE);
        else
            fátyol.setVisibility(View.GONE);


        service = new Intent(MainActivity.this, LService.class);
        bindService(service, kapcsolat, Context.BIND_AUTO_CREATE);


        runThermoView();

        nutriMennyi = new HashMap<>();


        nutriVM = ViewModelProviders.of(this).get(NutriVM.class);
        nutriVM.getAll().observe(this, nutriEntities -> {
            if (nutriEntities != null) {

                for (NutriEntity n : nutriEntities) {
                    if (n.getFajta().equals(Kender.getInstance().nutes.nuteName)) {

                        if (nutriMennyi.containsKey(n.getFajta()))
                            nutriMennyi.replace(n.getFajta(), n.getMennyi());
                        else
                            nutriMennyi.put(n.getFajta(), n.getMennyi());
                    }
                }

            } else {
                nutriMennyi.put("BioGrow", 0);

            }
        });
        soilMennyi = new HashMap<>();
        soilVM = ViewModelProviders.of(this).get(SoilVM.class);
        soilVM.getAll().observe(this, soilEntities -> {
            if (soilEntities != null) {

                for (SoilEntity s : soilEntities) {
                    if (s.getFajta().equals(Kender.getInstance().CC.föld.soilName)) {

                        if (soilMennyi.containsKey(s.getFajta()))
                            soilMennyi.replace(s.getFajta(), s.getMennyi());
                        else
                            soilMennyi.put(s.getFajta(), s.getMennyi());
                    }
                }

            } else
                soilMennyi.put("Dirt from outside", 1);
        });

        scoreVM = ViewModelProviders.of(this).get(ScoreVM.class);
        scoreVM.get().observe(this, scoreEntity -> score = scoreEntity != null ? scoreEntity.getScore() : 0);

        overridePendingTransition(R.anim.zoom_out,R.anim.zoom_out);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    Runnable ii = new Runnable() {
        @Override
        public void run() {
            if (toDryer) {
                lService.stopForeground(true);
                lService.stopSelf();
                lService.vége();
                Kender.getInstance().clear();
                Intent intent = new Intent(MainActivity.this, StashActivity.class);
                startActivity(intent);
                finish();
            } else {
                if (isTaskRoot()) {
                    lService.stopForeground(true);
                    lService.stopSelf();
                    lService.vége();
                    Kender.getInstance().clear();
                    startActivity(intent);
                    finish();
                } else {
                    lService.stopForeground(true);
                    lService.stopSelf();
                    lService.vége();
                    Kender.getInstance().clear();
                    finish();
                }
            }
        }
    };

    private void setIndicator() {
        indicator.setText(Kender.getInstance().getStrFajta());
        indicator.setCompoundDrawablesWithIntrinsicBounds(0,
                Kender.getInstance().fajtaDrawCode, 0, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();


    }


    @Override
    protected void onResume() {
        super.onResume();
        setIndicator();
        soilIndicator.setText(Kender.getInstance().CC.föld.soilName);
        fátyol.setImageDrawable(getDrawable(Kender.getInstance().FF.setDrawCode(1)));
        runThermoView();
        if(lService!=null)
            run();



        kanna.setImageDrawable(getDrawable(Kender.getInstance().VV.setDrawCode()));
        cserép.setImageDrawable(getDrawable(Kender.getInstance().CC.setDrawableCode()));
        táp.setImageDrawable(getDrawable(Kender.getInstance().nutes.setDrawCode()));
        try {
            vectorDrawable = KyrieDrawable.create(this.getApplicationContext(), Kender.getInstance().FF.setDrawCode(""));
        } catch (Exception e) {
            vectorDrawable = KyrieDrawable.create(this.getApplicationContext(), R.drawable.yellow_hps);
        }
        if (Kender.getInstance().FF.beKapcs) {
            vectorDrawable.start();
            tapeta.getBackground().clearColorFilter();
            if (tapeta.getDrawable() != null)
                tapeta.getDrawable().clearColorFilter();
            polc.setElevation(5);
            polc.setCardElevation(60);
        } else {
            tapeta.getBackground().setColorFilter(this.getColor(R.color.cardview_dark_background), PorterDuff.Mode.MULTIPLY);
            if (tapeta.getDrawable() != null)
                tapeta.getDrawable().setColorFilter(this.getColor(R.color.cardview_dark_background), PorterDuff.Mode.MULTIPLY);
            polc.setElevation(20);
            polc.setCardElevation(5);
        }
        bulb.setRotation(180);
        bulb.setImageDrawable(vectorDrawable);
        if(Kender.getInstance().FF.ventilKapcs)
            ventilAvd.start();
    }

    public void cserépGomb(View v) {
        if (quickAction == null) {

            if (inflater == null)
                inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
            if (inflater != null)
                customView = inflater.inflate(R.layout.quick_action, null);
            quickAction = new PopupWindow(
                    customView,
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            quickAction.setElevation(5.0f);
            quickAction.showAtLocation(cserép, Gravity.CENTER, 0, 0);
            kolibriAnimator.setState("repdes", null);
        } else {
            quickAction.dismiss();
            quickAction = null;
            kolibriAnimator.setState("repdes", null);
        }
        kwikkEksn();
    }

    private void kwikkEksn() {


        if (customView != null) {
            CircularProgressBar vizBar = (customView.findViewById(R.id.water));

            if (!(Kender.getInstance().VV.getVÍZ_Mennyiség() + Kender.getInstance().CC.waterRunoff < 100 * (Kender.getInstance().Szintet() / 2)))
                vizBar.setForegroundStrokeColor(Color.RED);
            else
                vizBar.setForegroundStrokeColor(getColor(R.color.progressKek));


            vizBar.setProgress(Kender.getInstance().VV.getVÍZ_Mennyiség());

            ((CircularProgressBar) customView.findViewById(R.id.sugar)).setProgress(Kender.getInstance().getCukor());

            ((CircularProgressBar) customView.findViewById(R.id.starch)).setProgress(Kender.getInstance().getRost());

            ((ProgressBar) customView.findViewById(R.id.nitrogen)).setProgress(Kender.getInstance().nutes.N);

            ((ProgressBar) customView.findViewById(R.id.foszfor)).setProgress(Kender.getInstance().nutes.P);

            ((ProgressBar) customView.findViewById(R.id.kalium)).setProgress(Kender.getInstance().nutes.K);

        }
    }

    private void runThermoView() {
        thermoView.oo.run();
    }

    public void cl(View v) {

        if (fátyol.getVisibility() == View.GONE) {

            fátyol.setVisibility(View.VISIBLE);
            vectorDrawable.start();
            tapeta.getBackground().clearColorFilter();
            if (tapeta.getDrawable() != null)
                tapeta.getDrawable().clearColorFilter();
            Kender.getInstance().FF.beKapcs = true;
            polc.setElevation(5);
            polc.setCardElevation(60);
            if (Integer.parseInt(napTV.getText().toString()) < 3)
                kolibriAnimator.setState("repdes", null);

        } else if (fátyol.getVisibility() == View.VISIBLE && !vectorDrawable.isRunning()) {

            fátyol.setVisibility(View.GONE);
            vectorDrawable.setCurrentPlayTime(0);
            tapeta.getBackground().setColorFilter(this.getColor(R.color.cardview_dark_background), PorterDuff.Mode.MULTIPLY);
            if (tapeta.getDrawable() != null)
                tapeta.getDrawable().setColorFilter(this.getColor(R.color.cardview_dark_background), PorterDuff.Mode.MULTIPLY);
            bulb.setBackgroundResource(outValue.resourceId);
            Kender.getInstance().FF.beKapcs = false;
            polc.setCardElevation(5);
            polc.setElevation(20);

        }

    }


    public void ventilator(View v) {
        if (!ventilBE) {
            ventilBE = true;
            ventilAvd.start();
            //if (Integer.parseInt(napTV.getText().toString()) < 5)
                kolibriAnimator.setState("repdes", null);
        } else {
            ventilBE = false;
            ventilAvd.stop();
        }

        Kender.getInstance().FF.ventilKapcs=ventilBE;
    }

    @Override
    public void onBackPressed() {
    if(LService.IS_SERVICE_RUNNING) {
        if (!isTaskRoot())
            super.onBackPressed();
        else {
            Intent i = new Intent(this, Main2Activity.class);
            finish();
            startActivity(i);

        }
    }else{

        new AlertDialog.Builder(this)
                .setTitle("Abandon Plant")
                .setMessage("Are you sure?")
                .setIcon(R.drawable.ic_sentiment_very_dissatisfied_black_24dp)
                .setPositiveButton("Yes", (dialog, whichButton) -> {
                    isKilled=true;
                    if (isTaskRoot()) {
                        lService.stopForeground(true);
                        lService.stopSelf();
                        lService.vége();
                        Kender.getInstance().clear();
                        startActivity(intent);
                        finish();
                    } else {
                        lService.stopForeground(true);
                        lService.stopSelf();
                        lService.vége();
                        Kender.getInstance().clear();
                        finish();
                    }
                })
                .setNegativeButton("No", (dialog,whichButton)->{
                    if (!isTaskRoot())
                        super.onBackPressed();
                    else {
                        Intent i = new Intent(this, Main2Activity.class);
                        finish();
                        startActivity(i);

                    }
                }).show();

    }
    }

    private void releaseMedia(){


        if(loccs!=null) {
            loccs.stop();
            loccs.reset();
            loccs.release();
        }
        if(nutriGoo!=null) {
            nutriGoo.stop();
            nutriGoo.reset();
            nutriGoo.release();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(kapcsolat);
        thermoView.handler.removeCallbacks(thermoView.oo);
        h.removeCallbacks(thread);
        kolibriHandler.removeCallbacks(this);

        if (dialog.isShowing()) {

            dialog.dismiss();
        }

        kolibriAnimator.dispose();
        if (quickAction != null) quickAction.dismiss();
        releaseMedia();
    }

    private ServiceConnection kapcsolat = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //h.removeCallbacks(thread);

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LService.Binderem myBinder = (LService.Binderem) service;
            lService = myBinder.getService();
            //r.run();
            thread.start();
            run();
            if (LService.IS_SERVICE_RUNNING)
                seed.setVisibility(View.GONE);


        }
    };

    Handler h = new Handler();
    Thread thread = new Thread(
            new Runnable() {
        @Override
        public void run() {


            if (ventilBE) Kender.getInstance().FF.setHő();

            canvasView.told(lService.al, lService.ism);



            if (lService.stopIt) {
                kolibriHandler.removeCallbacks(this);


            } else h.postDelayed(this, 625);


        }
    });


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        View.DragShadowBuilder mShadow = new View.DragShadowBuilder(view);
        ClipData.Item item = new ClipData.Item(view.getTag().toString());
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
        switch (view.getId()) {

            case R.id.daseed:

            case R.id.locsol:

            case R.id.tap1:
                view.startDragAndDrop(data, mShadow, null, 0);
                break;
        }

        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:

                ((ImageView) v).setColorFilter(Color.YELLOW);
                seed.setVisibility(View.GONE);
                kanna.setVisibility(View.GONE);
                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_ENTERED:

                ((ImageView) v).setColorFilter(
                        ContextCompat.getColor(MainActivity.this, R.color.colorAccent),
                        android.graphics.PorterDuff.Mode.MULTIPLY
                );

                if(event.getClipDescription().getLabel().toString().equals("Give some water")&&
                        Kender.getInstance().VV.getVÍZ_Mennyiség()<=Kender.getInstance().CC.potSize/2) {

                    Kender.getInstance().VV.locsol();
                    loccs.start();
                    kolibriAnimator.setState("tutorial",cserép);


            }

                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                return true;

            case DragEvent.ACTION_DRAG_EXITED:

                ((ImageView) v).clearColorFilter();
                ((ImageView) v).setColorFilter(Color.YELLOW);

                v.invalidate();
                return true;

            case DragEvent.ACTION_DROP:
                String clipData = event.getClipDescription().getLabel().toString();
                if(clipData.equals("Plant the seed")) {
                    lService.startThread();
                    gong.start();
                    gong.stop();
                    gong.release();
                    kolibriAnimator.setState("repdes",null);
                //itt volt magVm.update
                    MagVM magVM = ViewModelProviders.of(this).get(MagVM.class);
                    magVM.update(Kender.getInstance().getABC(), Kender.getInstance().getLevonasElottiMagSzam() - 1);

                    String soilName = Kender.getInstance().CC.föld.soilName;

                    if (soilMennyi.get(soilName) != null) {
                        if (soilMennyi.get(soilName) >= 1) {

                            if (!soilName.equals("Dirt from outside")) {
                                soilVM.update(soilName, soilMennyi.get(soilName) - 1);
                                if (soilMennyi.get(soilName) == 1) soilMennyi.clear();


                            }
                        }
                    }
                }
                else if(clipData.equals("tap1")) {
                    String name = Kender.getInstance().nutes.nuteName;
                    if (nutriMennyi.get(name) != null) {
                        if (nutriMennyi.get(name) >= 1) {
                            nutriGoo.start();
                            Kender.getInstance().CC.föld.Nitrogén += Kender.getInstance().nutes.iN;
                            Kender.getInstance().CC.föld.Foszfor += Kender.getInstance().nutes.iP;
                            Kender.getInstance().CC.föld.Kálium += Kender.getInstance().nutes.iK;
                            nutriVM.update(name, nutriMennyi.get(name) - 1);
                            if(nutriMennyi.get(name)==1) nutriMennyi.clear();
                            kolibriAnimator.setState("repdes",null);
                        }
                    }else
                        Toast.makeText(this,"It's Empty",Toast.LENGTH_SHORT).show();
                }
                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_ENDED:


                ((ImageView) v).clearColorFilter();
                if (event.getResult()) {
                    if(!LService.IS_SERVICE_RUNNING) seed.setVisibility(View.VISIBLE);
                    kanna.setVisibility(View.VISIBLE);
                } else {
                    if(!LService.IS_SERVICE_RUNNING)
                        seed.setVisibility(View.VISIBLE);
                    kanna.setVisibility(View.VISIBLE);

                }
                return true;

            default:
                return false;
        }
    }

    public void szüret(View view){
        //lService.szüretelve=true;
        //findViewById(R.id.ollo).setVisibility(View.GONE);

        olloAvd.start();
        lService.harvest();
    }

    public boolean saveWeed(){

        Type tList = new TypeToken<ArrayList<Termény>>(){}.getType();
        List<Termény> termenyList = gson.fromJson(Mentés.getInstance().getString(Mentés.Key.TRMS_LST),tList);

        return termenyList.size()<3&&lService.saveWeed();
    }

    public void nyissMenut(View v){
        if(quickAction!=null){
            quickAction.dismiss();
            quickAction=null;
        }
        drawerLayout.openDrawer(GravityCompat.START);
    }

    Handler kolibriHandler = new Handler();

    @Override
    public void run() {
        if(quickAction!=null)kwikkEksn();

        if(fátyol.getVisibility()==View.GONE)
            lightCounter++;
        else
            lightCounter=0;

        if(Kender.getInstance().VV.getVÍZ_Mennyiség()<3) {
            if(seed.getVisibility()==View.GONE) {
                kolibriAnimator.setState("tutorial", kanna);


            }
        }else if(fátyol.getVisibility()==View.GONE&&lightCounter>6){
            if(seed.getVisibility()==View.GONE)
                kolibriAnimator.setState("tutorial", bulb);
        }else if(Kender.getInstance().FF.hőmérséklet()>30&&!ventilBE)
            kolibriAnimator.setState("tutorial",ventilView);
        napTV.setText(String.valueOf(lService.ism / 6));

        if (!Kender.getInstance().flowering && lService.ism < 50){
            ivLC.setImageResource(R.drawable.ic_seedling);
            tvLC.setText("SEEDLING");
        }
        else if (!Kender.getInstance().flowering){
            ivLC.setImageResource(R.drawable.ic_vegetative);
        tvLC.setText("VEGETATIVE");}
        if(Kender.getInstance().flowering&&lService.ism<440){
            ivLC.setImageResource(R.drawable.ic_preflower);
        tvLC.setText("PRE-FLOWER");}
        else if(Kender.getInstance().flowering&&lService.ism<480&&lService.ism>=440){
            ivLC.setImageResource(R.drawable.ic_earlyflower);
        tvLC.setText("EARLY BLOOM");}
        else if(Kender.getInstance().flowering&&lService.ism>=480&&lService.ism<540){
            ivLC.setImageResource(R.drawable.ic_ripeflower);
        tvLC.setText("RIPENED");}
        else if(Kender.getInstance().flowering&&lService.ism>=540) {
            ivLC.setImageResource(R.drawable.ic_overdueflower);
            tvLC.setText("OVERDUE");
        }
        if (Kender.getInstance().flowering) ollo.setVisibility(View.VISIBLE);



        if(lService.stopIt&&!isKilled) {
            if (dialog != null) {
                if (lService.halott) {
                    TextView text = dialog.findViewById(R.id.text);
                    text.setText("Your Plant Died!\n" +"\n"+causeofdeathHelper(Kender.getInstance().causeofdeath) +
                            "\n Raw Yield Salvaged: " + lService.hányGrammLett() + " Gr.");
                    ImageView image = dialog.findViewById(R.id.image);
                    image.setImageResource(R.drawable.koponyacsont);
                    h.removeCallbacks(this);

                    dialog.show();

                } else {
                    scoreVM.updateScore(score + lService.reward());
                    TextView text = dialog.findViewById(R.id.text);
                    text.setText("Produce Harvested!\n Raw Yield Collected: " + lService.hányGrammLett() + " Gr."
                            + "\n Coins Earned: " + lService.reward());
                    ImageView image = dialog.findViewById(R.id.image);
                    image.setImageResource(R.drawable.koszoru);
                    h.removeCallbacks(this);
                    dialog.show();
                    new ParticleSystem(MainActivity.this, 10, R.drawable.ic_coin, 6000)
                            .setFadeOut(5000)
                            .setRotationSpeed(50)
                            .setSpeedModuleAndAngleRange(0.1f, 0.5f, 0, 365)
                            .oneShot(image, 50);
                }
            }
        }else

        kolibriHandler.postDelayed(this,1000);

    }

    private String causeofdeathHelper(String cause){
        String[] a = cause.split("!");
        String[] causes = {"NITROGEN BURN", "PHOSPHORUS BURN", "POTASSIUM BURN", "DEHYDRATION", "LIGHT DEPRIVATION", "ROOT ROT","SUGAR STARVATION"};
        // search for pattern in a
        int[] count = {0,0,0,0,0,0,0};
        for (String s : a) {
            if (causes[0].equalsIgnoreCase(s))
                count[0]+=1;
            if (causes[1].equalsIgnoreCase(s))
                count[1]+=1;
            if (causes[2].equalsIgnoreCase(s))
                count[2]+=1;
            if (causes[3].equalsIgnoreCase(s))
                count[3]+=1;
            if (causes[4].equalsIgnoreCase(s))
                count[4]+=1;
            if (causes[5].equalsIgnoreCase(s))
                count[5]+=1;
            if (causes[6].equalsIgnoreCase(s))
                count[6]+=1;
        }
        StringBuilder s= new StringBuilder();
        for(int i=0;i<count.length-1;i++){
            if(count[i]>1)
                s.append(count[i]).append("X ").append(causes[i]).append("\n");
            else if(count[i]>0)
                s.append(causes[i]).append("\n");
        }
        return s.toString();
    }
}


