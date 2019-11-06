package blacklinden.com.cannabisgrowthsimulator.sql;

        import androidx.sqlite.db.SupportSQLiteDatabase;
        import androidx.room.Database;
        import androidx.room.Room;
        import androidx.room.RoomDatabase;
        import android.content.Context;
        import android.os.AsyncTask;
        import androidx.annotation.NonNull;

        import blacklinden.com.cannabisgrowthsimulator.pojo.AccessoryEntity;
        import blacklinden.com.cannabisgrowthsimulator.pojo.ComboEntity;
        import blacklinden.com.cannabisgrowthsimulator.pojo.Lampa;
        import blacklinden.com.cannabisgrowthsimulator.pojo.MagEntity;
        import blacklinden.com.cannabisgrowthsimulator.pojo.NutriEntity;
        import blacklinden.com.cannabisgrowthsimulator.pojo.ScoreEntity;
        import blacklinden.com.cannabisgrowthsimulator.pojo.SkinEntity;
        import blacklinden.com.cannabisgrowthsimulator.pojo.SoilEntity;
        import blacklinden.com.cannabisgrowthsimulator.pojo.Vegtermek;

@Database(entities = {Vegtermek.class,Lampa.class,NutriEntity.class,AccessoryEntity.class,MagEntity.class,SkinEntity.class,ScoreEntity.class,SoilEntity.class, ComboEntity.class}, version = 14,exportSchema = false)
abstract class VegtermekRoomDatabase extends RoomDatabase {
     abstract VegtermekDao adatDao();
     abstract LampaDao lampaDao();
     abstract NutriDao nutriDao();
     abstract AccessoryDao accessoryDao();
     abstract MagDao magDao();
     abstract ScoreDao scrDao();
     abstract SkinDao skinDao();
     abstract SoilDao soilDao();
     abstract ComboDao comboDao();

    private static volatile VegtermekRoomDatabase INSTANCE;

    static VegtermekRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VegtermekRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VegtermekRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();


                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onCreate (@NonNull SupportSQLiteDatabase db){
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final VegtermekDao mDao;
        private final LampaDao lDao;
        private final NutriDao nDao;
        private final AccessoryDao aDao;
        private final MagDao mgDao;
        private final ScoreDao scrDao;
        private final SkinDao skinDao;
        private final SoilDao soilDao;
        private final ComboDao comboDao;

        PopulateDbAsync(VegtermekRoomDatabase db) {
            mDao = db.adatDao();
            lDao = db.lampaDao();
            nDao = db.nutriDao();
            aDao = db.accessoryDao();
            mgDao = db.magDao();
            scrDao = db.scrDao();
            skinDao = db.skinDao();
            soilDao = db.soilDao();
            comboDao = db.comboDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            nDao.insert(new NutriEntity("BioGrow",15));
            soilDao.insert(new SoilEntity("dirt",30));
            mgDao.insert(new MagEntity("a",3));
            mgDao.insert(new MagEntity("b",3));
            mgDao.insert(new MagEntity("c",3));
            scrDao.insert(new ScoreEntity("LEVEL 1",1500));
            mDao.insert(new Vegtermek("Skunk","good",15));

            return null;
        }
    }
}

