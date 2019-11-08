package blacklinden.com.cannabisgrowthsimulator;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;

import androidx.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import androidx.dynamicanimation.animation.DynamicAnimation;

import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.plattysoft.leonids.ParticleSystem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.UUID;

import blacklinden.com.cannabisgrowthsimulator.canvas.CsuporCanvas;
import blacklinden.com.cannabisgrowthsimulator.canvas.KicsiCanvas;
import blacklinden.com.cannabisgrowthsimulator.eszk.Mentés;
import blacklinden.com.cannabisgrowthsimulator.eszk.Ventil;
import blacklinden.com.cannabisgrowthsimulator.pojo.Stash;
import blacklinden.com.cannabisgrowthsimulator.pojo.Termény;
import blacklinden.com.cannabisgrowthsimulator.sql.ScoreVM;
import blacklinden.com.cannabisgrowthsimulator.ui.WalkThruFrag;
import blacklinden.com.cannabisgrowthsimulator.ui.kolibri.Kolibri;
import blacklinden.com.cannabisgrowthsimulator.ui.kolibri.Tutorial;

public class StashActivity extends FragmentActivity implements View.OnTouchListener, View.OnDragListener {


    private float dX;
    private float dY;
    private CsuporCanvas csupi,cs1,cs2,cs3,kuka;
    private  KicsiCanvas kicsiCanvas, kc2, kc3;
    private float humidity;
    private TextView paramerő;
    private CircularProgressBar cpb;
    private boolean ventilBE;
    private Ventil ventilObj;
    private ImageView pakk,humidifier;
    private LinearLayout ll;
    private TextView ll2tv,ll3tv,ll4tv,ll5tv,
    cstv,cstv1,cstv2,cstv3,
    llrtv,llr2tv,llr3tv;
    private Dialog dialog;
    private boolean isThereAnything;
    private Kolibri kolibriAnimator;
    private int xp;
    private ScoreVM scoreVM;
    private View fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stash);
        paramerő = findViewById(R.id.para);
        cpb = findViewById(R.id.progress_bar);
        cpb.setProgressAnimationInterpolator(new DecelerateInterpolator());
        ll = findViewById(R.id.csupisor);
        Typeface plain = Typeface.createFromAsset(getAssets(), "font/digitaldream.ttf");
        paramerő.setTypeface(plain);

        humidity = Mentés.getInstance().getFloat(Mentés.Key.PARATART,0.3f);
        humidifier = findViewById(R.id.humid);
        ventilObj = findViewById(R.id.ventil);
        ventilObj.setLayerType(View.LAYER_TYPE_HARDWARE,null);

        fragment = findViewById(R.id.wtFrag);
        fragment.setVisibility(View.GONE);
        fragment.setElevation(500);
        WalkThruFrag walkThruFrag = (WalkThruFrag) getFragmentManager().findFragmentById(R.id.wtFrag);
        walkThruFrag.setImageForCarousel(new int[]{R.drawable.szarito11,R.drawable.szarito12,R.drawable.szarito21,R.drawable.szarito22});
        walkThruFrag.setVisibilityListener(() -> {
            fragment.setVisibility(View.GONE);
        });

        TextView kolibriTV = findViewById(R.id.kolibriTv);
        kolibriTV.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        final AnimatedVectorDrawable kolibri = (AnimatedVectorDrawable) kolibriTV.getCompoundDrawables()[3];
        kolibri.start();
        if(kolibri!=null){
            kolibri.registerAnimationCallback(new Animatable2.AnimationCallback(){
                @Override
                public void onAnimationEnd(Drawable drawable) {
                    kolibriTV.post(kolibri::start);
                }
            });
        }
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        final float w = point.x;
        kolibriAnimator = new Kolibri(w, kolibriTV);


        kicsiCanvas = findViewById(R.id.stashCanvas);
        kc2 = findViewById(R.id.stashCanvas2);
        kc3 = findViewById(R.id.stashCanvas3);
        final ImageView iv = findViewById(R.id.h1);
        kuka = findViewById(R.id.kukaCnvs);
        pakk = findViewById(R.id.pakk);

        kicsiCanvas.setOnTouchListener(this);
        kc2.setOnTouchListener(this);
        kc3.setOnTouchListener(this);
        pakk.setOnTouchListener(this);

        chainedSpringAnimation(iv,kicsiCanvas,kc2,kc3);

        Stack<KicsiCanvas> kicsiCanvasStack = new Stack<>();
        kicsiCanvasStack.push(kicsiCanvas);
        kicsiCanvasStack.push(kc2);
        kicsiCanvasStack.push(kc3);

        csupi = findViewById(R.id.csupi);
        cs1 = findViewById(R.id.csupi1);
        cs2 = findViewById(R.id.csupi2);
        cs3 = findViewById(R.id.csupi3);

        csupi.setOnDragListener(this);
        cs1.setOnDragListener(this);
        cs2.setOnDragListener(this);
        cs3.setOnDragListener(this);
        kuka.setOnDragListener(this);

        Stack<CsuporCanvas> csuporCanvasStack = new Stack<>();

        csuporCanvasStack.add(csupi);
        csuporCanvasStack.add(cs1);
        csuporCanvasStack.add(cs2);
        csuporCanvasStack.add(cs3);

        llrtv = findViewById(R.id.llrtv);
        llr2tv = findViewById(R.id.llr2tv);
        llr3tv = findViewById(R.id.llr3tv);




        ll2tv = findViewById(R.id.ll2tv);
        ll2tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ll3tv = findViewById(R.id.ll3tv);
        ll3tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ll4tv = findViewById(R.id.ll4tv);
        ll4tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ll5tv = findViewById(R.id.ll5tv);
        ll5tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        cstv = findViewById(R.id.cstv);
        cstv1 = findViewById(R.id.cstv1);
        cstv2 = findViewById(R.id.cstv2);
        cstv3 = findViewById(R.id.cstv3);


        scoreVM = ViewModelProviders.of(this).get(ScoreVM.class);
        scoreVM.get().observe(this, scoreEntity->{
            if(scoreEntity!=null)
            xp=scoreEntity.getXp();
        });


        String rawList = Mentés.getInstance().getString(Mentés.Key.TRMS_LST,"0");
        if(!rawList.equals("0")) {
            Gson gson = new Gson();
            Type tList = new TypeToken<ArrayList<Termény>>() {
            }.getType();
            List<Termény> termenyList = gson.fromJson(rawList, tList);
            if(!termenyList.isEmpty()) {
                for (Termény t : termenyList) {

                    if (kicsiCanvasStack.peek().isEmpty()) {
                        t.setSorszám(createSalt());
                        kicsiCanvasStack.pop().init(t);

                    }
                }

                Mentés.getInstance().put(Mentés.Key.TRMS_LST,gson.toJson(termenyList));

                isThereAnything=true;
            }else
                isThereAnything=false;


        }

        String rawListErllt = Mentés.getInstance().getString(Mentés.Key.ERllT_LST,"0");
        if(!rawList.equals("0")) {
            Gson gson = new Gson();
            Type tList = new TypeToken<ArrayList<Termény>>() {
            }.getType();
            List<Termény> erleltlist = gson.fromJson(rawListErllt, tList);
            if(!erleltlist.isEmpty()) {
                for (Termény t : erleltlist) {

                    if (csuporCanvasStack.peek().isEmpty()) {

                        csuporCanvasStack.pop().fillUp(t);

                    }
                }
            }
        }



        paramerő.setText(Integer.toString((int) (humidity*100)));
        cpb.setProgress(humidity*100);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setCanceledOnTouchOutside(true);

        dialog.findViewById(R.id.dialogButtonOK).setOnClickListener(view -> {
            dialog.dismiss();
        });
        oo.run();
        aa.run();



    }

    private String createSalt() {

        return (UUID.randomUUID().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Mentés.getInstance().put(Mentés.Key.PARATART,humidity);
        if(dialog.isShowing())dialog.dismiss();
        handler.removeCallbacks(oo);
        kolibriAnimator.dispose();
    }


    public SpringAnimation createSpringAnimation(View view,
                                                 DynamicAnimation.ViewProperty property,
                                                 float stiffness,
                                                 float dampingRatio) {
        SpringAnimation animation = new SpringAnimation(view, property);
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(stiffness);
        springForce.setDampingRatio(dampingRatio);
        animation.setSpring(springForce);
        return animation;
    }



    @SuppressLint("ClickableViewAccessibility")
    private void chainedSpringAnimation(final View c1, final View c2, final View c3, final View c4) {


        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);


        View bKtl = findViewById(R.id.balKtl);
        View jKtl = findViewById(R.id.jbKtl);

        final SpringAnimation ktlXAnim = createSpringAnimation(bKtl, DynamicAnimation.X, SpringForce.STIFFNESS_HIGH, SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        final SpringAnimation ktlYAnim = createSpringAnimation(bKtl, DynamicAnimation.Y, SpringForce.STIFFNESS_HIGH, SpringForce.DAMPING_RATIO_LOW_BOUNCY);

        final SpringAnimation jktlXAnim = createSpringAnimation(jKtl, DynamicAnimation.X, SpringForce.STIFFNESS_HIGH, SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        final SpringAnimation jktlYAnim = createSpringAnimation(jKtl, DynamicAnimation.Y, SpringForce.STIFFNESS_HIGH, SpringForce.DAMPING_RATIO_LOW_BOUNCY);

        final SpringAnimation firstXAnim = createSpringAnimation(c2, DynamicAnimation.X, SpringForce.STIFFNESS_HIGH, SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        final SpringAnimation firstYAnim = createSpringAnimation(c2, DynamicAnimation.Y, SpringForce.STIFFNESS_HIGH, SpringForce.DAMPING_RATIO_LOW_BOUNCY);

        final SpringAnimation secondXAnim = createSpringAnimation(c3, DynamicAnimation.X, SpringForce.STIFFNESS_HIGH, SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        final SpringAnimation secondYAnim = createSpringAnimation(c3, DynamicAnimation.Y, SpringForce.STIFFNESS_HIGH, SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        final SpringAnimation thirdXAnim = createSpringAnimation(c4, DynamicAnimation.X, SpringForce.STIFFNESS_HIGH, SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        final SpringAnimation thirdYAnim = createSpringAnimation(c4, DynamicAnimation.Y, SpringForce.STIFFNESS_HIGH, SpringForce.DAMPING_RATIO_LOW_BOUNCY);

        final ViewGroup.MarginLayoutParams fab5Params = (ViewGroup.MarginLayoutParams) c2.getLayoutParams();
        final ViewGroup.MarginLayoutParams fab6Params = (ViewGroup.MarginLayoutParams) c3.getLayoutParams();
        final ViewGroup.MarginLayoutParams fab7Params = (ViewGroup.MarginLayoutParams) c4.getLayoutParams();
        final ViewGroup.MarginLayoutParams bParams = (ViewGroup.MarginLayoutParams) bKtl.getLayoutParams();
        final ViewGroup.MarginLayoutParams jParams = (ViewGroup.MarginLayoutParams) jKtl.getLayoutParams();

        firstXAnim.addUpdateListener((animation, v, vl) -> {
            secondXAnim.animateToFinalPosition(v + ((c2.getWidth() -
                    c3.getWidth()) / 2));
            ktlXAnim.animateToFinalPosition(v + ((
                    bKtl.getWidth()) / 2)+bParams.leftMargin);
            jktlXAnim.animateToFinalPosition(v + (c2.getWidth() -
                   jParams.rightMargin-jKtl.getWidth()));

        });

        firstYAnim.addUpdateListener((animation, v, vl) -> {
            secondYAnim.animateToFinalPosition(v + c2.getHeight() +
                    fab6Params.topMargin);
            ktlYAnim.animateToFinalPosition(v + c2.getHeight() -
                    bParams.topMargin/2);
            jktlYAnim.animateToFinalPosition(v + c2.getHeight() -
                    jParams.topMargin/2);
        });

        secondYAnim.addUpdateListener((animation, value, velocity) -> thirdYAnim.animateToFinalPosition(value + c2.getHeight() +
                fab7Params.topMargin));

        secondXAnim.addUpdateListener((animation, value, velocity) -> thirdXAnim.animateToFinalPosition(value + ((c2.getWidth() -
                c3.getWidth()) / 2)));



        c1.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - motionEvent.getRawX();
                    dY = view.getY() - motionEvent.getRawY();


                    break;
                case MotionEvent.ACTION_MOVE:

                    float newX = motionEvent.getRawX() + dX;
                    float newY = motionEvent.getRawY() + dY;

                    view.animate().x(newX).y(newY).setDuration(0).start();

                    firstXAnim.animateToFinalPosition(newX + ((c1.getWidth() -
                            c2.getWidth()) / 2));
                    firstYAnim.animateToFinalPosition(newY + c1.getHeight() +
                            fab5Params.topMargin);

                    break;

                case MotionEvent.ACTION_UP:
                    view.animate().translationX(0).translationY(0).setDuration(100).start();
                    float x=view.getX()-view.getTranslationX();
                    float y=view.getY()-view.getTranslationY();


                    firstXAnim.animateToFinalPosition(x + ((c1.getWidth() -
                            c2.getWidth()) / 2));
                    firstYAnim.animateToFinalPosition(y + c1.getHeight() +
                            fab5Params.topMargin);




                   break;
            }
            return true;
        });

    }

    public void kcMenu(View view) {
        switch (view.getId()){
            case R.id.stashCanvas:

                break;
            case R.id.stashCanvas2:

                break;
            case R.id.stashCanvas3:

                break;
        }
    }

    public void nyissCsupi(View view) {
        ((CsuporCanvas) view).setNyitva();
        if(!((CsuporCanvas) view).isEmpty()) {
            ((CsuporCanvas) view).getTermény().burpJar();

            if(((CsuporCanvas) view).getTermény().getGas()>0) {
                new ParticleSystem(StashActivity.this, 5, R.drawable.fust_particle, 2000)
                        .setFadeOut(1500)
                        .setRotationSpeed(50)
                        .setSpeedModuleAndAngleRange(0.1f, 0.5f, 15, 335)
                        .oneShot(view, ((CsuporCanvas) view).getTermény().getGas()*5);
            }
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {

        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                String clipData2 = event.getClipDescription().getLabel().toString();
                    if (((CsuporCanvas) v).isEmpty()&&v.getId()!=R.id.kukaCnvs&&!clipData2.equalsIgnoreCase("pakk"))
                        ((CsuporCanvas) v).hilite();
                    else if(clipData2.equalsIgnoreCase("pakk")&&!((CsuporCanvas)v).isEmpty())
                        ((CsuporCanvas)v).hilite();


                return true;

            case DragEvent.ACTION_DRAG_ENTERED:

            case DragEvent.ACTION_DRAG_EXITED:

                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                return true;

            case DragEvent.ACTION_DROP:
                if(v.getId()!=R.id.kukaCnvs)
                ((CsuporCanvas) v).hilite();
                String clipData = event.getClipDescription().getLabel().toString();
                switch (clipData) {
                    case "kc1":
                            if (!kicsiCanvas.isEmpty() && ((CsuporCanvas) v).isEmpty()&&!v.getTag().toString().equals("kukaCnvs")) {
                                ((CsuporCanvas) v).fillUp(kicsiCanvas.getTermény());
                                dryToCure(kicsiCanvas.getTermény().getSorszám());
                                kicsiCanvas.setEmpty();
                            }else if(!kicsiCanvas.isEmpty()&&v.getTag().toString().equals("kukaCnvs")) {
                                delete(kicsiCanvas.getTermény().getSorszám());
                                kicsiCanvas.setEmpty();
                            }

                  break;

                    case "kc2":

                            if (!kc2.isEmpty() && ((CsuporCanvas) v).isEmpty()&&!v.getTag().toString().equals("kukaCnvs")) {
                                ((CsuporCanvas) v).fillUp(kc2.getTermény());
                                dryToCure(kc2.getTermény().getSorszám());
                                kc2.setEmpty();
                            }else if(!kc2.isEmpty()&&v.getTag().toString().equals("kukaCnvs")) {
                                delete(kc2.getTermény().getSorszám());
                                kc2.setEmpty();
                            }

                        break;

                    case "kc3":

                            if (!kc3.isEmpty() && ((CsuporCanvas) v).isEmpty()&&!v.getTag().toString().equals("kukaCnvs")) {
                                ((CsuporCanvas) v).fillUp(kc3.getTermény());
                                dryToCure(kc3.getTermény().getSorszám());
                                kc3.setEmpty();
                            }else if(!kc3.isEmpty()&&v.getTag().toString().equals("kukaCnvs")) {
                                delete(kc3.getTermény().getSorszám());
                                kc3.setEmpty();
                            }

                    break;
                    case "pakk":
                        if(!((CsuporCanvas)v).isEmpty()){

                            cureToStash(v);
                        }


                }

                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                //if(((CsuporCanvas) v).isEmpty()&&v.getId()!=R.id.kukaCnvs) ((CsuporCanvas) v).hilite();

                ((CsuporCanvas)v).unliteAll();

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
            case R.id.stashCanvas:
                if(!((KicsiCanvas)view).isEmpty())
                    view.startDragAndDrop(data, mShadow, null, 0);


                break;

            case R.id.stashCanvas2:

                if(!((KicsiCanvas)view).isEmpty())
                    view.startDragAndDrop(data, mShadow, null, 0);

                break;

            case R.id.stashCanvas3:
                if(!((KicsiCanvas)view).isEmpty())
                    view.startDragAndDrop(data, mShadow, null, 0);

                break;

            case R.id.pakk:
                view.startDragAndDrop(data,mShadow,null,0);
                break;
        }

        return false;
    }

    private void delete(String sorszám){
        Gson gson = new GsonBuilder().create();
        Type tList = new TypeToken<ArrayList<Termény>>(){}.getType();
        List<Termény> termenyList = gson.fromJson(Mentés.getInstance().getString(Mentés.Key.TRMS_LST),tList);
        termenyList.removeIf(i -> i.getSorszám().equals(sorszám));
        Toast.makeText(this,"DELETED",Toast.LENGTH_SHORT).show();
        String ment = Mentés.getInstance().gsonra(termenyList);
        Mentés.getInstance().put(Mentés.Key.TRMS_LST,ment);

    }
    private void dryToCure(String sorszám){
        Gson gson = new GsonBuilder().create();
        Type tList = new TypeToken<ArrayList<Termény>>(){}.getType();
        List<Termény> termenyList = gson.fromJson(Mentés.getInstance().getString(Mentés.Key.TRMS_LST),tList);
        List<Termény> erlltList = gson.fromJson(Mentés.getInstance().getString(Mentés.Key.ERllT_LST),tList);
        for(Termény item:termenyList) {

            if (sorszám.equals(item.getSorszám())) {
                Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show();
                item.setCuring(true);
                erlltList.add(item);

                String ment2 = Mentés.getInstance().gsonra(erlltList);
                Mentés.getInstance().put(Mentés.Key.ERllT_LST,ment2);
                break;
            }
        }

        termenyList.removeIf(i -> i.getSorszám().equals(sorszám));
        String ment = Mentés.getInstance().gsonra(termenyList);
        Mentés.getInstance().put(Mentés.Key.TRMS_LST,ment);



    }

    @SuppressLint("SetTextI18n")
    private void cureToStash(View v){
        Gson gson = new GsonBuilder().create();
        Type tList = new TypeToken<ArrayList<Termény>>(){}.getType();
        Type vList = new TypeToken<ArrayList<Stash>>(){}.getType();
        List<Termény> erlltList = gson.fromJson(Mentés.getInstance().getString(Mentés.Key.ERllT_LST),tList);
        List<Stash> vgtrmkList = gson.fromJson(Mentés.getInstance().getString(Mentés.Key.VGTRMK_LST),vList);

        Termény t=((CsuporCanvas)v).getTermény();
        vgtrmkList.add(new Stash((int)t.getDarab(),t.getFajtaString(),t.getStatus(),t.getSuly()));

        erlltList.removeIf(i -> i.getSorszám().equals(t.getSorszám()));
        ((CsuporCanvas)v).empty();
        String ment = Mentés.getInstance().gsonra(erlltList);
        Mentés.getInstance().put(Mentés.Key.ERllT_LST,ment);
        String ment2 = Mentés.getInstance().gsonra(vgtrmkList);
        Mentés.getInstance().put(Mentés.Key.VGTRMK_LST,ment2);
        ((ImageView)dialog.findViewById(R.id.image)).setImageResource(R.drawable.ic_pakk_ikon);
        ((TextView)dialog.findViewById(R.id.text)).setText("Stashed!\n"+t.getFajtaString()+"\n\n Final Weight: "+String.format(java.util.Locale.US,"%.2f",t.getSuly())+"Gr.\n THC: "+(int)t.getThc()+"%\n Status: "+t.getStatus()+"\n Processed For: "+t.getNapok()+" Days");
        dialog.show();
        new ParticleSystem(StashActivity.this, 5, R.drawable.ic_bobi_coin_iconv2, 6000)
                .setFadeOut(5000)
                .setRotationSpeed(50)
                .setSpeedModuleAndAngleRange(0.1f,0.5f,0,365)
                .oneShot(paramerő,10);

        scoreVM.updateXP(xp+50);

        kolibriAnimator.setState("repdes",null);

    }


    public void humidify(View v){
        if(humidity<0.7f)
        humidity+=0.03f;

                new ParticleSystem(StashActivity.this, 10, R.drawable.smokepart, 600)
                        .setFadeOut(500)
                        .setRotationSpeed(15)
                        .setSpeedModuleAndAngleRange(0.09f,0.1f,225,320)
                        .oneShot(v,50);

            }

    Handler handler = new Handler(Objects.requireNonNull(Looper.myLooper()));
    Runnable oo = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {

            paramerő.setText(Integer.toString((int) (humidity*100)));
            cpb.setProgress(humidity*100);
            if(humidity*100>40&&humidity*100<=60) cpb.setForegroundStrokeColor(Color.GREEN);
            else if(humidity*100>60) cpb.setForegroundStrokeColor(Color.RED);
            else if(humidity*100<40) cpb.setForegroundStrokeColor(Color.YELLOW);
            if(ventilBE&&humidity>0.28f)humidity-=0.01f;

            if(!ventilBE){
                if(humidity>0.35f)
                    humidity-=0.01f;
                else if(humidity<0.35f)
                    humidity+=0.01f;
            }

            if(!csupi.isEmpty())csupi.getTermény().setVapor(humidity);
            if(!cs1.isEmpty())cs1.getTermény().setVapor(humidity);
            if(!cs2.isEmpty())cs2.getTermény().setVapor(humidity);
            if(!cs3.isEmpty())cs3.getTermény().setVapor(humidity);

            if(humidity<0.4f&&isThereAnything)
                kolibriAnimator.setState("tutorial",humidifier);
            else if(humidity>0.6f&&isThereAnything)
                kolibriAnimator.setState("tutorial",ventilObj);
            else{
                if(kolibriAnimator.getState() instanceof Tutorial)
                    kolibriAnimator.setState("repdes",null);
            }

            handler.postDelayed(this,10000);

        }
    };


    Runnable aa = new Runnable() {
        @Override
        public void run() {
            if(findViewById(R.id.ll2tv).getVisibility()==View.VISIBLE) {

                if (csupi.isEmpty()) ll2tv.setText("EMPTY");

                else ll2tv.setText(csupi.getTermény().getStatus()+"\n" +
                        csupi.getTermény().getNapok()+"Days\n" +
                        (int)csupi.getTermény().getThc()+"% THC\n" +
                        String.format(java.util.Locale.US,"%.2f",csupi.getTermény().getSuly())+"Gr.");


                if (cs1.isEmpty()) ll3tv.setText("EMPTY");

                else ll3tv.setText(cs1.getTermény().getStatus()+"\n" +
                        cs1.getTermény().getNapok()+"Days\n" +
                        (int)cs1.getTermény().getThc()+"% THC\n" +
                        String.format(java.util.Locale.US,"%.2f",cs1.getTermény().getSuly())+"Gr.");


                if (cs2.isEmpty()) ll4tv.setText("EMPTY");

                else ll4tv.setText(cs2.getTermény().getStatus()+"\n" +
                        cs2.getTermény().getNapok()+"Days\n" +
                        (int)cs2.getTermény().getThc()+"% THC\n" +
                        String.format(java.util.Locale.US,"%.2f",cs2.getTermény().getSuly())+"Gr.");


                if (cs3.isEmpty()) ll5tv.setText("EMPTY");

                else ll5tv.setText(cs3.getTermény().getStatus()+"\n" +
                        cs3.getTermény().getNapok()+"Days\n" +
                        (int)cs3.getTermény().getThc()+"% THC\n" +
                        String.format(java.util.Locale.US,"%.2f",cs3.getTermény().getSuly())+"Gr.");
            }



            if(findViewById(R.id.llrtv).getVisibility()==View.VISIBLE) {

                llrtv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                llr2tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                llr3tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                if (kicsiCanvas.isEmpty()) llrtv.setText("EMPTY");

                else llrtv.setText(kicsiCanvas.getTermény().getFajtaString()+" " +kicsiCanvas.getTermény().getStatus() + "\nDay" +
                        kicsiCanvas.getTermény().getNapok() +" "+
                        (int) kicsiCanvas.getTermény().getThc() + "% THC " +
                        String.format(java.util.Locale.US, "%.2f", kicsiCanvas.getTermény().getSuly()) + "Gr.");


                if (kc2.isEmpty()) llr2tv.setText("EMPTY");

                else llr2tv.setText(kc2.getTermény().getFajtaString()+" "+kc2.getTermény().getStatus() + "\nDay" +
                        kc2.getTermény().getNapok()+" "+
                        (int) kc2.getTermény().getThc() + "% THC " +
                        String.format(java.util.Locale.US, "%.2f", kc2.getTermény().getSuly()) + "Gr.");


                if (kc3.isEmpty()) llr3tv.setText("EMPTY");

                else llr3tv.setText(kc3.getTermény().getFajtaString()+" "+kc3.getTermény().getStatus() + "\nDay" +
                        kc3.getTermény().getNapok() + " " +
                        (int) kc3.getTermény().getThc() + "% THC " +
                        String.format(java.util.Locale.US, "%.2f", kc3.getTermény().getSuly()) + "Gr.");

            }





                if (csupi.isEmpty()) cstv.setVisibility(View.GONE);

            else {
                cstv.setVisibility(View.VISIBLE);
                cstv.setText(csupi.getTermény().getFajtaString());
            }

            if (cs1.isEmpty()) cstv1.setVisibility(View.GONE);

            else {
                cstv1.setVisibility(View.VISIBLE);
                cstv1.setText(cs1.getTermény().getFajtaString());
            }

            if (cs2.isEmpty()) cstv2.setVisibility(View.GONE);

            else {
                cstv2.setVisibility(View.VISIBLE);
                cstv2.setText(cs2.getTermény().getFajtaString());
            }

            if (cs3.isEmpty()) cstv3.setVisibility(View.GONE);

            else {
                cstv3.setVisibility(View.VISIBLE);
                cstv3.setText(cs3.getTermény().getFajtaString());
            }



            handler.postDelayed(aa,500);
            }

    };


    public void ventil(View view) {
        if (!ventilBE) {
            ventilObj.indit();
            ventilBE = true;

        } else {

            ventilObj.stop();
            ventilBE = false;
        }
    }

    public void csuporMenu(View view) {

        if(findViewById(R.id.ll2).getVisibility()==View.GONE) {


            int cx = csupi.getRight();
            int cy = csupi.getBottom();
            int finalRadius = (int) Math.hypot(ll.getWidth(),
                    ll.getHeight());
            Animator a = ViewAnimationUtils.createCircularReveal(findViewById(R.id.ll2), cx,
                    cy, 0, finalRadius);
            a.setDuration(500);
            a.setInterpolator(new DecelerateInterpolator());
            findViewById(R.id.ll2).setVisibility(View.VISIBLE);
            a.start();
        }else
            findViewById(R.id.ll2).setVisibility(View.GONE);

    }

    public void rackMenu(View view){
        if(findViewById(R.id.llr).getVisibility()==View.GONE) {
            View v = findViewById(R.id.balKtl);

            int cx = kicsiCanvas.getRight();
            int cy = kicsiCanvas.getBottom();
            int finalRadius = (int) Math.hypot(kicsiCanvas.getWidth(),
                    v.getHeight());
            Animator a = ViewAnimationUtils.createCircularReveal(findViewById(R.id.llr), cx,
                    cy, 10, finalRadius);
            a.setDuration(100);
            a.setInterpolator(new DecelerateInterpolator());
            findViewById(R.id.llr).setVisibility(View.VISIBLE);
            a.start();
        }else
            findViewById(R.id.llr).setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed(){
        if(isTaskRoot()){
            Intent intent = new Intent(this,Main2Activity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoom_out,R.anim.zoom_in);
            finish();
        }else
            finish();
    }


    public void openwtFrag(View view) {
        if(fragment.getVisibility()==View.VISIBLE)
            fragment.setVisibility(View.GONE);
        else
            fragment.setVisibility(View.VISIBLE);
    }
}
