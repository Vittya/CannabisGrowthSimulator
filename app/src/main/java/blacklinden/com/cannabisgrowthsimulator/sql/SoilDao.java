package blacklinden.com.cannabisgrowthsimulator.sql;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


import blacklinden.com.cannabisgrowthsimulator.pojo.SoilEntity;

@Dao
public interface SoilDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insert(SoilEntity soilEntity);

    @Query("DELETE FROM fld_table")
    void deleteAll();

    @Query("SELECT * from fld_table ORDER BY fajta ASC")
    LiveData<List<SoilEntity>> getAll();

    @Query("UPDATE fld_table SET mennyi = :mennyi_adat WHERE fajta = :fldFajta")
    void updateSoil(String fldFajta,int mennyi_adat);

    @Query("DELETE FROM fld_table WHERE fajta = :fldFajta")
    void deleteIt(String fldFajta);
}
