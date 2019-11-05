package blacklinden.com.cannabisgrowthsimulator.sql;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import blacklinden.com.cannabisgrowthsimulator.pojo.AccessoryEntity;


@Dao
public interface AccessoryDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insert(AccessoryEntity entity);

    @Query("DELETE FROM acc_table")
    void deleteAll();

    @Query("SELECT * from acc_table ORDER BY fajta ASC")
    LiveData<List<AccessoryEntity>> getAll();

    @Query("DELETE FROM acc_table WHERE fajta = :accFajta")
    void deleteIt(String accFajta);
}
