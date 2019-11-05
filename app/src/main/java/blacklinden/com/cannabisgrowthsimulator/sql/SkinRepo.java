package blacklinden.com.cannabisgrowthsimulator.sql;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import blacklinden.com.cannabisgrowthsimulator.pojo.SkinEntity;

public class SkinRepo {

    private SkinDao skinDao;
    private LiveData<List<SkinEntity>> skinek;



    SkinRepo(Application application){
        VegtermekRoomDatabase db = VegtermekRoomDatabase.getDatabase(application);

        skinDao = db.skinDao();
        skinek = skinDao.getAll();

    }

    LiveData<List<SkinEntity>> getAll(){
        return skinek;
    }

    void insert (SkinEntity adat){
        new SkinRepo.insertAsyncTask(skinDao).execute(adat);
    }

    void deleteAll(){
        new SkinRepo.deleteAllAsync(skinDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<SkinEntity, Void, Void> {

        private SkinDao mAsyncTaskDao;

        insertAsyncTask(SkinDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SkinEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllAsync extends AsyncTask<SkinEntity, Void, Void> {

        private SkinDao mAsyncTaskDao;

        deleteAllAsync(SkinDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final SkinEntity... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

}
