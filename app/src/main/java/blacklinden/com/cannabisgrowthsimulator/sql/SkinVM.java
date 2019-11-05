package blacklinden.com.cannabisgrowthsimulator.sql;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import androidx.annotation.NonNull;
import blacklinden.com.cannabisgrowthsimulator.pojo.SkinEntity;

public class SkinVM extends AndroidViewModel {

    private SkinRepo repo;
    private LiveData<List<SkinEntity>> data;

    public SkinVM(@NonNull Application application) {
        super(application);
        repo = new SkinRepo(application);
        data = repo.getAll();
    }

    public LiveData<List<SkinEntity>> getAll() {
        return data;
    }

    public void insert(SkinEntity adat) {
        repo.insert(adat);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

}


