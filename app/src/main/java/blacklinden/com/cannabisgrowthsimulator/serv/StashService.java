package blacklinden.com.cannabisgrowthsimulator.serv;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


import blacklinden.com.cannabisgrowthsimulator.R;
import blacklinden.com.cannabisgrowthsimulator.StashActivity;
import blacklinden.com.cannabisgrowthsimulator.eszk.Mentés;
import blacklinden.com.cannabisgrowthsimulator.pojo.Termény;

public class StashService extends JobService {
    private Random random;
    private String t="";
    private int t_napok;
    private Gson gson;


    public StashService() {
        random = new Random();
        gson =  new GsonBuilder().create();
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Mentés.getInstance(this);
        String erlRaw = Mentés.getInstance().getString(Mentés.Key.ERllT_LST,"0");
        String trmRaw = Mentés.getInstance().getString(Mentés.Key.TRMS_LST,"0");

        Type tList = new TypeToken<ArrayList<Termény>>(){}.getType();
        List<Termény> erlList = gson.fromJson(erlRaw,tList);
        List<Termény> trmList = gson.fromJson(trmRaw,tList);


        if(erlList.size()!=0){

            ArrayList<Termény> tt = new ArrayList<>();

            for(Termény t : erlList){
                t.update();
                if(t.getStatus().equals("smelly")&&random.nextInt(10)+1==8) t.setStatus("molded");
                tt.add(t);
                t_napok=t.getNapok();
                this.t=t.getFajtaString()+" days: "+(t.getNapok())+" thc: "+t.getThc()+" status: "+t.getStatus();
            }


            erlRaw = Mentés.getInstance().gsonra(tt);
            System.out.println(erlRaw);
            Mentés.getInstance().put(Mentés.Key.ERllT_LST,erlRaw);


        }

        if(trmList.size()!=0){

            ArrayList<Termény> tt = new ArrayList<>();

            for(Termény t : trmList){
                t.update();
                if(t.getStatus().equals("smelly")&&random.nextInt(10)+1==8) t.setStatus("molded");
                tt.add(t);
                this.t=t.getFajtaString()+" days: "+(t.getNapok())+" thc: "+t.getThc()+" status: "+t.getStatus();

            }


            trmRaw = Mentés.getInstance().gsonra(tt);
            System.out.println(trmRaw);
            Mentés.getInstance().put(Mentés.Key.TRMS_LST,trmRaw);



        }

        if(trmList.size()==0&&erlList.size()==0)
            jobFinished(jobParameters,false);




        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }



}
