package blacklinden.com.cannabisgrowthsimulator.sql;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import blacklinden.com.cannabisgrowthsimulator.pojo.SoilEntity;

public class SoilRepo {

    private SoilDao soilDao;
    private LiveData<List<SoilEntity>> data;



    SoilRepo(Application application){
        VegtermekRoomDatabase db = VegtermekRoomDatabase.getDatabase(application);

        soilDao = db.soilDao();
        data = soilDao.getAll();

    }

    LiveData<List<SoilEntity>> getAll(){
        return data;
    }

    void insert (SoilEntity adat){
        new SoilRepo.insertAsyncTask(soilDao).execute(adat);
    }

    void deleteAll(){
        new SoilRepo.deleteAllAsync(soilDao).execute();
    }

    void update(String fajta,int mennyi){new SoilRepo.updateAsync(soilDao,fajta,mennyi).execute();}

    private static class insertAsyncTask extends AsyncTask<SoilEntity, Void, Void> {

        private SoilDao mAsyncTaskDao;

        insertAsyncTask(SoilDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SoilEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllAsync extends AsyncTask<SoilEntity, Void, Void> {

        private SoilDao mAsyncTaskDao;

        deleteAllAsync(SoilDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SoilEntity... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class updateAsync extends AsyncTask<SoilEntity, Void, Void> {

        private SoilDao mAsyncTaskDao;
        private String fajta;
        private int mennyi;

        updateAsync(SoilDao dao,String fajta,int mennyi) {
            mAsyncTaskDao = dao;
            this.fajta=fajta;
            this.mennyi=mennyi;
        }

        @Override
        protected Void doInBackground(final SoilEntity... params) {

            if(mennyi>0)
                mAsyncTaskDao.updateSoil(fajta,mennyi);
            else
                mAsyncTaskDao.deleteIt(fajta);

            return null;
        }
    }
}
