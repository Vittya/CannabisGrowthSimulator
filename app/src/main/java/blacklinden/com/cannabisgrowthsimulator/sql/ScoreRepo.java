package blacklinden.com.cannabisgrowthsimulator.sql;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import blacklinden.com.cannabisgrowthsimulator.pojo.ScoreEntity;

public class ScoreRepo {
    private ScoreDao dao;
    private LiveData<ScoreEntity> scoreEntityLiveData;
    private static final String[] rawRankTmb = {"LEVEL 1","LEVEL 2","LEVEL 3","LEVEL 4","LEVEL 5","LEVEL 6"};


    ScoreRepo(Application application) {
        VegtermekRoomDatabase db = VegtermekRoomDatabase.getDatabase(application);
        dao = db.scrDao();
        scoreEntityLiveData = dao.get();
    }

    LiveData<ScoreEntity> get() {
        return scoreEntityLiveData;
    }

    void insert(ScoreEntity adat) {
        new ScoreRepo.insertAsyncTask(dao).execute(adat);
    }


    void updateScore(int score) {
        new ScoreRepo.updateScoreAsync(dao,score).execute();
    }

    void updateXp(int xp){
        new ScoreRepo.updateXpAsync(dao,xp).execute();
    }


    private static class insertAsyncTask extends AsyncTask<ScoreEntity, Void, Void> {

        private ScoreDao mAsyncTaskDao;

        insertAsyncTask(ScoreDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ScoreEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class updateScoreAsync extends AsyncTask<ScoreEntity, Void, Void> {

        private ScoreDao mAsyncTaskDao;
        private int score;

        updateScoreAsync(ScoreDao dao, int score) {
            mAsyncTaskDao = dao;
            this.score = score;
        }

        @Override
        protected Void doInBackground(final ScoreEntity... params) {
            if (score >= 0)
                mAsyncTaskDao.updateScore(score);
            return null;
        }
    }

    private static class updateXpAsync extends AsyncTask<ScoreEntity, Void, Void> {

        private ScoreDao mAsyncTaskDao;
        private int xp;

        updateXpAsync(ScoreDao dao, int xp) {
            mAsyncTaskDao = dao;
            this.xp = xp;
        }

        @Override
        protected Void doInBackground(final ScoreEntity... params) {
            if (xp>499&&xp<=1500)
                mAsyncTaskDao.updateRank(rawRankTmb[1]);
            else if(xp>1500&&xp<=3999)
                mAsyncTaskDao.updateRank(rawRankTmb[2]);
            else if(xp>3999&&xp<=8000)
                mAsyncTaskDao.updateRank(rawRankTmb[3]);
            else if(xp>8000&&xp<=15000)
                mAsyncTaskDao.updateRank(rawRankTmb[4]);
            else if(xp>15000&&xp<=16000)
                mAsyncTaskDao.updateRank(rawRankTmb[5]);

                mAsyncTaskDao.updateXp(xp);

            return null;
        }
    }

}

