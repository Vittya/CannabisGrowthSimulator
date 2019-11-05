package blacklinden.com.cannabisgrowthsimulator.sql;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import blacklinden.com.cannabisgrowthsimulator.pojo.ComboEntity;

@Dao
public interface ComboDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insert(ComboEntity comboEntity);

    @Query("SELECT * from cmb_table ORDER BY combo ASC")
    LiveData<List<ComboEntity>> getAll();

}

