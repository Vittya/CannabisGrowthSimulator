package blacklinden.com.cannabisgrowthsimulator.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "skn_table")
public class SkinEntity {


    @PrimaryKey
    @NonNull
    private String fajta;

    public SkinEntity(@NonNull String fajta) {this.fajta = fajta;}

    @NonNull
    public String getFajta(){return this.fajta;}

}
