package blacklinden.com.cannabisgrowthsimulator.sql;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import androidx.annotation.NonNull;
import blacklinden.com.cannabisgrowthsimulator.pojo.SoilEntity;

public class SoilVM extends AndroidViewModel {

    private SoilRepo repo;
    private LiveData<List<SoilEntity>> data;

    public SoilVM(@NonNull Application application) {
        super(application);
        repo = new SoilRepo(application);
        data = repo.getAll();
    }

    public LiveData<List<SoilEntity>> getAll(){
        return data;
    }

    public void insert(SoilEntity adat){repo.insert(adat);}

    public void deleteAll(){repo.deleteAll();}

    public void update(String fajta,int mennyi){repo.update(fajta,mennyi);}



}
