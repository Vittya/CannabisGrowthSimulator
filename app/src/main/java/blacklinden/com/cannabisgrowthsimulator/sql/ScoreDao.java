package blacklinden.com.cannabisgrowthsimulator.sql;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import blacklinden.com.cannabisgrowthsimulator.pojo.ScoreEntity;

@Dao
public interface ScoreDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insert(ScoreEntity scoreEntity);

    @Query("SELECT * from scr_table")
    LiveData<ScoreEntity> get();

    @Query("UPDATE scr_table SET score = :score")
    void updateScore(int score);

    @Query("UPDATE scr_table SET rank = :rank")
    void updateRank(String rank);

    @Query("UPDATE scr_table SET xp = :xp")
    void updateXp(int xp);

}
