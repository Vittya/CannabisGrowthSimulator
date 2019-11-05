package blacklinden.com.cannabisgrowthsimulator.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "scr_table")
public class ScoreEntity {


    @PrimaryKey
    @NonNull
    public String name="scr";


    @ColumnInfo
    @NonNull
    private String rank;

    @ColumnInfo(name = "xp")
    private int xp=0;

    @ColumnInfo(name="score")
    private int score;

    public ScoreEntity(@NonNull String rank, int score) {this.rank = rank; this.score=score;}

    @NonNull
    public String getRank(){return this.rank;}

    public int getScore(){return this.score;}

    public int getXp(){return this.xp;}


    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
