package blacklinden.com.cannabisgrowthsimulator.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "mg_table")
public class MagEntity {

    @PrimaryKey
    @NonNull
    private String fajta;

    @ColumnInfo(name="mennyi")
    private int mennyi;

    public MagEntity(@NonNull String fajta, int mennyi) {
        this.mennyi = mennyi;
        this.fajta = fajta;
    }

    @NonNull
    public String getFajta(){return this.fajta;}

    public int getMennyi(){return this.mennyi;}


}
