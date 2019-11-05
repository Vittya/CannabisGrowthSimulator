package blacklinden.com.cannabisgrowthsimulator.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "fld_table")
public class SoilEntity {

    @PrimaryKey
    @NonNull
    private String fajta;

    @ColumnInfo(name="mennyi")
    private int mennyi;

    public SoilEntity(@NonNull String fajta,int mennyi) { this.fajta = fajta; this.mennyi=mennyi; }

    @NonNull
    public String getFajta(){return this.fajta;}

    public int getMennyi(){ return this.mennyi;}
}
