package blacklinden.com.cannabisgrowthsimulator.sql;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;


import blacklinden.com.cannabisgrowthsimulator.pojo.ComboEntity;

public class ComboRepo {

    private ComboDao comboDao;
    private LiveData<List<ComboEntity>> liveData;



    ComboRepo(Application application){
        VegtermekRoomDatabase db = VegtermekRoomDatabase.getDatabase(application);

        comboDao = db.comboDao();
        liveData = comboDao.getAll();

    }

    LiveData<List<ComboEntity>> getAll(){
        return liveData;
    }

    void insert (ComboEntity adat){
        new ComboRepo.insertAsyncTask(comboDao).execute(adat);
    }



    private static class insertAsyncTask extends AsyncTask<ComboEntity, Void, Void> {

        private ComboDao mAsyncTaskDao;

        insertAsyncTask(ComboDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ComboEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


}
