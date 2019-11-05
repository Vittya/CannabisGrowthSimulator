package blacklinden.com.cannabisgrowthsimulator;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.text.format.DateUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import blacklinden.com.cannabisgrowthsimulator.eszk.Mentés;
import blacklinden.com.cannabisgrowthsimulator.pojo.Termény;
import blacklinden.com.cannabisgrowthsimulator.serv.StashService;
import blacklinden.com.cannabisgrowthsimulator.ui.typef.TypefaceUtil;

public class MyApp extends Application {

    private static Application app;
    private static JobListener listener;

    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "font/quikhand.otf");
        TypefaceUtil.overrideFont(getApplicationContext(),"DEFAULT","font/quikhand.otf");
        TypefaceUtil.overrideFont(getApplicationContext(),"MONOSPACE","font/quikhand.otf");
        TypefaceUtil.overrideFont(getApplicationContext(),"SANS_SERIF","font/quikhand.otf");
        MyApp.app = this;

        Mentés.getInstance(this);
        if(Mentés.getInstance().getInt(Mentés.Key.BELEP,0)==0)
        Mentés.getInstance().put(Mentés.Key.BELEP,BuildConfig.VERSION_CODE);
        else if(Mentés.getInstance().getInt(Mentés.Key.BELEP)!=BuildConfig.VERSION_CODE){
            Mentés.Key[] keys = new Mentés.Key[]{Mentés.Key.SAMPLE_CAN,Mentés.Key.SAMPLE_POT,Mentés.Key.TESZT_OBJ,Mentés.Key.SKN,Mentés.Key.SAMPLE_INT};
            Mentés.getInstance().remove(keys);
        }
        String rawList = Mentés.getInstance().getString(Mentés.Key.TRMS_LST,"0");

        String rawListErllt = Mentés.getInstance().getString(Mentés.Key.ERllT_LST,"0");




        JobScheduler jobScheduler = (JobScheduler)getApplicationContext()
                .getSystemService(JOB_SCHEDULER_SERVICE);
        if(Objects.requireNonNull(jobScheduler).getPendingJob(0) != null&&!rawList.equals("0")||!rawListErllt.equals("0")) {
            ComponentName componentName = new ComponentName(this,
                    StashService.class);
            //long scheduler_Interval = 5 * DateUtils.MINUTE_IN_MILLIS;
            //long flexTime = (long) (scheduler_Interval*0.05);
            JobInfo jobInfo = new JobInfo.Builder(0, componentName)
                    .setPeriodic(JobInfo.getMinPeriodMillis(),JobInfo.getMinFlexMillis())
                    .setPersisted(true).build();
            Objects.requireNonNull(jobScheduler).schedule(jobInfo);
            if(listener!=null)
                listener.update();
        }

    }

    public static Application getAppContext() {
        return MyApp.app;
    }

    public static void addStaticListener(JobListener listener){
        MyApp.listener=listener;
    }

    public interface JobListener{
        void update();
    }
}