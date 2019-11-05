package blacklinden.com.cannabisgrowthsimulator.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

@Entity(tableName = "cmb_table")
public class ComboEntity {

        @PrimaryKey(autoGenerate = true)
        public int comboId;

        @ColumnInfo(name="combo")
        private String combo;

        public ComboEntity(@NonNull String combo) {this.combo=combo;}

        @NonNull
        public String getCombo(){return this.combo;}
}
