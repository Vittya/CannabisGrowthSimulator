package blacklinden.com.cannabisgrowthsimulator.pojo;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "lmpa_table")
public class Lampa {


    @PrimaryKey
    @NonNull
    private String fajta;

    public Lampa(@NonNull String fajta) {this.fajta = fajta;}

    @NonNull
    public String getFajta(){return this.fajta;}

}
