package blacklinden.com.cannabisgrowthsimulator.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity (tableName = "vgtrmk_table")
public class Vegtermek {


    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "fajta")
    private String fajta;

    @ColumnInfo(name="mennyi")
    private float mennyi;

    @ColumnInfo(name="mnsg")
    private String mnsg;

    public Vegtermek(@NonNull String fajta,String mnsg,float mennyi) {this.fajta = fajta; this.mnsg=mnsg; this.mennyi=mennyi;}

    public String getFajta(){return this.fajta;}

    public float getMennyi(){return this.mennyi;}

    public String getMnsg(){return this.mnsg;}
}
