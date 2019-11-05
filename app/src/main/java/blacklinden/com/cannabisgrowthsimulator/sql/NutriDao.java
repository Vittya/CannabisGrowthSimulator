package blacklinden.com.cannabisgrowthsimulator.sql;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import blacklinden.com.cannabisgrowthsimulator.pojo.NutriEntity;

@Dao
public interface NutriDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insert(NutriEntity nutriEntity);

    @Query("DELETE FROM ntr_table")
    void deleteAll();

    @Query("SELECT * from ntr_table ORDER BY fajta ASC")
    LiveData<List<NutriEntity>> getAll();

    @Query("UPDATE ntr_table SET mennyi = :mennyi_adat WHERE fajta = :nutriFajta")
    void updateNute(String nutriFajta,int mennyi_adat);

    @Query("DELETE FROM ntr_table WHERE fajta = :nutriFajta")
    void deleteIt(String nutriFajta);
}
