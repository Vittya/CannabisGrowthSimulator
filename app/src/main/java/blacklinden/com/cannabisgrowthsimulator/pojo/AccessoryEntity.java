package blacklinden.com.cannabisgrowthsimulator.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "acc_table")
public class AccessoryEntity {

    @PrimaryKey
    @NonNull
    private String fajta;

    public AccessoryEntity(@NonNull String fajta) {this.fajta = fajta;}

    @NonNull
    public String getFajta(){return this.fajta;}
}
