package blacklinden.com.cannabisgrowthsimulator.serv;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import blacklinden.com.cannabisgrowthsimulator.MainActivity;
import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.eszk.Mentés;
import blacklinden.com.cannabisgrowthsimulator.nov.C;
import blacklinden.com.cannabisgrowthsimulator.nov.F;
import blacklinden.com.cannabisgrowthsimulator.nov.Gy;
import blacklinden.com.cannabisgrowthsimulator.nov.H;
import blacklinden.com.cannabisgrowthsimulator.nov.Kender;
import blacklinden.com.cannabisgrowthsimulator.nov.L;
import blacklinden.com.cannabisgrowthsimulator.nov.Növény;
import blacklinden.com.cannabisgrowthsimulator.nov.menttolt.M;
import blacklinden.com.cannabisgrowthsimulator.nov.menttolt.T;
import blacklinden.com.cannabisgrowthsimulator.pojo.Termény;

@SuppressWarnings("ALL")
public class LService extends Service {


    public int ism = 6;
    private static final int vég =800;
    public boolean stopIt = false;
    public volatile ArrayList<Növény> al = new ArrayList<>();
    private IBinder binderem = new Binderem();
    public static volatile boolean IS_SERVICE_RUNNING = false;
    private Notif notif;
    private Notification notification;
    private String üzenet;
    public volatile boolean szüretelve=false;
    public volatile boolean halott;
    private Thread lthread;
    private C bC,jC;
    private ArrayList<Növény> a;
    private PowerManager.WakeLock wakeLock;



    public LService() {

        if (!IS_SERVICE_RUNNING) {
            Kender.getInstance();
            Kender.getInstance().initFény();
            Kender.getInstance().initRost();
            Kender.getInstance().initVíz();
            Kender.getInstance().initCukor();
            Kender.getInstance().initCO2();
            a = new ArrayList<>();

        }
    }


    @Override
    public void onCreate() {

        notificationForO("In Progress",false);

            al.add(new Gy());
            al.add(new F(Kender.getInstance().getFajta()).init(0));
            al.add(new M());
            al.add(new H());
            al.add(new T());

        bC = new C(true);
        jC = new C(false);

        lthread = new Thread(oo);
        lthread.setPriority(Thread.MAX_PRIORITY);
        //lthread.setDaemon(true);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M && Build.MANUFACTURER.equals("Huawei")) {
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "LocationManagerService"); }
        else {

            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "MyApp::MyWakelockTag");

        }
        wakeLock.acquire();


    }

    private void notificationForO(String uzenet,boolean autoCancel){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notif = new Notif(this);

            Notification.Builder nb = notif.
                    getAndroidChannelNotification("GROWBOX", uzenet);
            nb.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.cgs_logo02));
            nb.setSmallIcon(R.drawable.ic_indicasativakslg);
            nb.setAutoCancel(autoCancel);
            if(!uzenet.equalsIgnoreCase("harvested")&&!uzenet.equalsIgnoreCase("your plant died")) {

                Intent notificationIntent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = TaskStackBuilder.create(this)
                        .addNextIntentWithParentStack(notificationIntent)
                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                nb.setContentIntent(pendingIntent);
            }
            nb.setOnlyAlertOnce(!uzenet.equalsIgnoreCase("Your plant is thirsty!")||uzenet.equalsIgnoreCase("your plant died"));

            notif.getManager().notify(101, nb.build());

            startForeground(101,
                    nb.build());

        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Mentés.getInstance(this);




        if (intent != null) {
            switch ((Objects.requireNonNull(intent.getAction()))) {
                case Constants.ACTION.STARTFOREGROUND_ACTION:
                    showNotification("In Progress",IS_SERVICE_RUNNING);


                    break;
                case Constants.ACTION.STOPFOREGROUND_ACTION:
                    stopForeground(true);

                    break;

            }
        }

        return START_STICKY;
    }

    public void startThread(){
        IS_SERVICE_RUNNING = true;
        lthread.start();

    }

    private void showNotification(String uzenet,boolean autoCancel) {

        üzenet=uzenet;

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {

            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);

            PendingIntent pendingIntent = TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(notificationIntent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.cgs_logo02);
            if(!uzenet.equalsIgnoreCase("harvested")&&!uzenet.equalsIgnoreCase("your plant died")&&!uzenet.equalsIgnoreCase("finished")) {
                notification = new Notification.Builder(this)
                        .setContentTitle("GROWBOX")
                        .setOnlyAlertOnce(!uzenet.equalsIgnoreCase("Your plant is thirsty!")||uzenet.equalsIgnoreCase("your plant died"))
                        .setTicker("Grow Operation")
                        .setContentText(uzenet)
                        .setSmallIcon(R.drawable.ic_indicasativakslg)
                        .setLargeIcon(icon)
                        .setAutoCancel(autoCancel)
                        .setContentIntent(pendingIntent)
                        .setOngoing(true)
                        .setPriority(Notification.PRIORITY_MAX)
                        .build();
            }else{
                notification = new Notification.Builder(this)
                        .setContentTitle("GROWBOX")
                        .setOnlyAlertOnce(!uzenet.equalsIgnoreCase("Your plant is thirsty!"))
                        .setTicker("Grow Operation")
                        .setContentText(uzenet)
                        .setSmallIcon(R.drawable.ic_indicasativakslg)
                        .setLargeIcon(icon)
                        .setAutoCancel(autoCancel)
                        .setOngoing(true)
                        .setPriority(Notification.PRIORITY_MAX)
                        .build();
            }
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                    notification);
        }else
            notificationForO(uzenet,autoCancel);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(oo);
        ism=6;
        al.clear();
        Kender.getInstance().clear();
        IS_SERVICE_RUNNING=false;
        wakeLock.release();
    }


    public Handler handler = new Handler(Looper.myLooper());
    public Runnable oo = new Runnable() {
        @Override
        public void run() {
            try {
                ism();
                A();
                handler.postDelayed(oo, 625);
            }catch (Exception e){
               halott=true;
               vége();

            }
        }
    };

    private void ism() {
        System.out.println("ISM " + ism);
        ism++;

    }

    private void shakeItBaby() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(150);
        }
    }


    private void A() {
       a.clear();
        Kender.getInstance().update(ism);

        if (!Kender.getInstance().halott_e&&!szüretelve&&ism<vég) {

            if(Kender.getInstance().VV.getVÍZ_Mennyiség()==0&&!üzenet.equals("Your Plant is Thirsty!"))
                showNotification("Your Plant is Thirsty!",true);
            else if(!üzenet.equals("In Progress")&&!üzenet.equals("Harvested")&&!üzenet.equals("Your Plant Died"))
                showNotification("In Progress",!IS_SERVICE_RUNNING);

            for(Növény x:al) {


                        //oldalhajtás
                if(Objects.equals(x.n,"X")&&x.fejl()==20&&x.szint()==3
                        &&!Kender.getInstance().verem.f.empty()&&!Kender.getInstance().verem.üreseMT()){

                        a.add(Kender.getInstance().verem.m.pop());
                        a.add(Kender.getInstance().verem.f.pop().init(x.szint(),x.szög()));
                        a.add(Kender.getInstance().verem.t.pop());

                    a.add(x);


                    }

                    else if (Objects.equals(x.n,"H")&&x.fejl()==10){
                           a.add(Kender.getInstance().verem.m.pop());
                           a.add(bC);
                           a.add(Kender.getInstance().verem.t.pop());
                           a.add(Kender.getInstance().verem.m.pop());
                           a.add(jC);
                           a.add(Kender.getInstance().verem.t.pop());
                           a.add(Kender.getInstance().verem.a.pop().init(0));
                        }

                    else if (Objects.equals(x.n, "A")&& x.fejl()==1&&
                        !Kender.getInstance().flowering&&

                                !Kender.getInstance().verem.üreseValami()) {

                        //oldalhajtás-e
                    if(x.hossz()==1) {
                        a.add(Kender.getInstance().verem.m.pop());
                        a.add(Kender.getInstance().verem.f.pop().init(x.szint()));
                        a.add(Kender.getInstance().verem.t.pop());

                        a.add(Kender.getInstance().verem.m.pop());
                        a.add(Kender.getInstance().verem.x.pop().init(true,x.szint()));
                        a.add(Kender.getInstance().verem.l.pop().init(true, x.szint()));
                        a.add(Kender.getInstance().verem.t.pop());
                        a.add(Kender.getInstance().verem.m.pop());
                        a.add(Kender.getInstance().verem.x.pop().init(false,x.szint()));
                        a.add(Kender.getInstance().verem.l.pop().init(false, x.szint()));
                        a.add(Kender.getInstance().verem.t.pop());
                    }else{
                        a.add(Kender.getInstance().verem.m.pop());
                        a.add(Kender.getInstance().verem.f.pop().init(x.szint(),x.szög()));
                        a.add(Kender.getInstance().verem.t.pop());

                        a.add(Kender.getInstance().verem.m.pop());
                        a.add(Kender.getInstance().verem.l.pop().init(true, x.szint(),1));
                        a.add(Kender.getInstance().verem.t.pop());
                        a.add(Kender.getInstance().verem.m.pop());
                        a.add(Kender.getInstance().verem.l.pop().init(false, x.szint(), 1));
                        a.add(Kender.getInstance().verem.t.pop());

                    }

                }

                    else if (Objects.equals(x.n, "F") && Kender.getInstance().Szintet()-x.szint()<9 &&
                        x.fejl()>=(Kender.getInstance().Szintet()-x.szint())*10 &&
                        Kender.getInstance().verem.av.size()>100-Kender.getInstance().Szintet()&&
                         Kender.getInstance().flowering
                                &&!Kender.getInstance().verem.av.empty()) {

                            a.add(x);
                            a.add(Kender.getInstance().verem.av.pop().init(x.szint()));

                        }


                        else if (Objects.equals(x.n, "F") && x.fejl() == 30 && x.szint()>0&&!Kender.getInstance().flowering) {

                            if(Kender.getInstance().getRost()>=x.szint()&&
                                    Kender.getInstance().verem.a.size()>=1) {
                                //légz() az oldalhajtást mutatja
                                a.add(x);
                                x.vízigény();
                                if (x.légz() == 0)
                                    a.add(Kender.getInstance().verem.a.pop().init(x.szint()));
                                else if (x.légz() == 1 && x.szint() < 8)
                                    a.add(Kender.getInstance().verem.a.pop().init(x.szint(), x.szög()));
                                Kender.getInstance().levonas(x.szint());
                            }else x.vízigény();
                }
                        else if (Objects.equals(x.n, "AV") && x.vastagság() > (Kender.getInstance().Szintet()-x.szint())*10&&
                                !Kender.getInstance().verem.v.empty()) {
                            a.add(Kender.getInstance().verem.v.pop());
                        } else if (Objects.equals(x.n, "V") && x.fejl() >= (Kender.getInstance().Szintet()-x.szint())*80
                                &&!Kender.getInstance().verem.av.empty()) {
                            a.add(x);
                            a.add(Kender.getInstance().verem.av.pop());
                        } else {
                            a.add(x);
                        }

                x.élet();

            }
            if(!a.isEmpty()) {
                al.clear();
                al.addAll(a);
            }
        }

        else{

             vége();

            }

        }

        public void vége(){
            handler.removeCallbacks(oo);

            halott=Kender.getInstance().halott_e;


            if(halott){
                showNotification("Your Plant Died",false);
                //notificationForO("Your Plant Died");

            }else if(!stopIt){
                showNotification("Finished",false);
                //notificationForO("Harvested");
            }
            stopIt=true;
            stopForeground(false);
            stopSelf();

        }

        public boolean saveWeed(){
            if(hányGrammLett()>=1) {
                Gson gson = new GsonBuilder().create();
                Type tList = new TypeToken<ArrayList<Termény>>() {
                }.getType();
                List<Termény> termenyList = gson.fromJson(Mentés.getInstance().getString(Mentés.Key.TRMS_LST), tList);

                termenyList.add(new Termény(darabraMennyi(), hányGrammLett(), Kender.getInstance().getFajta(),mennyireÉrett(), 1));
                String ment = Mentés.getInstance().gsonra(termenyList);
                Mentés.getInstance().put(Mentés.Key.TRMS_LST, ment);
                return true;
            }else
            return false;
        }


        public int hányGrammLett () {

            float gramm=0;
            for (Növény x : al) {
                if (Objects.equals(x.n, "V")) {
                    //gramm += x.vastagság();
                    gramm += x.fejl();
                }

            }
            if(gramm==0) return 0;
            else if(gramm/100>300)
                return (int)gramm/300;
            else if(gramm<300)
                return 3;
            else
            return (int)(gramm/100);///1000;
        }

        public int mennyireÉrett () {
        int érettDb=1;
        for (Növény x:al){
            if(Objects.equals(x.n,"V")){
                if(x.szín()== Color.rgb(205, 133, 63))
                érettDb++;
            }
        }

        if(darabraMennyi()!=0) {
            int thc = Kender.getInstance().getThc() - (darabraMennyi() / érettDb);
            if(thc>8)
            return thc;
            else
            return 8;
        }
        else
        return 5;
        }

        public int darabraMennyi(){
        int i=1;
        for(Növény x:al){
            if (Objects.equals(x.n, "V")) {
                i++;
            }
        }
        if(i>100)
        return 100;
        else
        return i;
        }

        public int reward(){
        return Kender.getInstance().getReward()*hányGrammLett();
        }

        @Nullable
        @Override
        public IBinder onBind (Intent intent){

        return binderem;
        }

        @Override
        public boolean onUnbind (Intent intent){



        return true;
        }

        @Override
        public void onRebind (Intent intent){
            super.onRebind(intent);
        }



        public class Binderem extends Binder {
            public LService getService() {
                return LService.this;
            }
        }

        public int hullám () {
            return (int) Kender.getInstance().VV.getVÍZ_Mennyiség();
        }

        public void harvest(){
        szüretelve=true;
        handler.post(oo);

        }

        public void trim(){
        handler.post(oo);
            al.remove(L.class);
        }



    }
