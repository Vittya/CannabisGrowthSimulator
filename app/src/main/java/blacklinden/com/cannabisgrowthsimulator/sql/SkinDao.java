package blacklinden.com.cannabisgrowthsimulator.sql;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import blacklinden.com.cannabisgrowthsimulator.pojo.SkinEntity;
@Dao
public interface SkinDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insert(SkinEntity skinEntity);

    @Query("DELETE FROM skn_table")
    void deleteAll();

    @Query("SELECT * from skn_table ORDER BY fajta ASC")
    LiveData<List<SkinEntity>> getAll();

    @Query("DELETE FROM skn_table WHERE fajta = :skinFajta")
    void deleteIt(String skinFajta);
}
