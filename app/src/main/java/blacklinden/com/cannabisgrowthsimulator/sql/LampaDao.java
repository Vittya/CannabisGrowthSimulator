package blacklinden.com.cannabisgrowthsimulator.sql;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import blacklinden.com.cannabisgrowthsimulator.pojo.Lampa;


@Dao
public interface LampaDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insert(Lampa lampa);

    @Query("DELETE FROM lmpa_table")
    void deleteAll();

    @Query("SELECT * from lmpa_table ORDER BY fajta ASC")
    LiveData<List<Lampa>> getAll();

    @Query("DELETE FROM lmpa_table WHERE fajta = :lampaFajta")
    void deleteIt(String lampaFajta);
}
