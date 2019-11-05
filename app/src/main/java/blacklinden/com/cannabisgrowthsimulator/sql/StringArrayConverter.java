package blacklinden.com.cannabisgrowthsimulator.sql;

import androidx.room.TypeConverter;

import java.io.Serializable;


public class StringArrayConverter implements Serializable {
    @TypeConverter
    public String fromStringArrayToString(String[] combos) {
        if (combos == null) {
            return (null);
        }
        StringBuilder sb = new StringBuilder();
        for (String c:combos)
        sb.append(c).append(" ");

        return sb.toString();
    }

    @TypeConverter
    public String[] toStringArray (String combostr) {
        if (combostr == null) {
            return (null);
        }

        return combostr.split(" ");
    }

}
