package blacklinden.com.cannabisgrowthsimulator.sql;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import androidx.annotation.NonNull;
import blacklinden.com.cannabisgrowthsimulator.pojo.AccessoryEntity;

public class AccVM extends AndroidViewModel {

    private AccRepo repo;
    private LiveData<List<AccessoryEntity>> data;

    public AccVM(@NonNull Application application) {
        super(application);
        repo = new AccRepo(application);
        data = repo.getAll();
    }

    public LiveData<List<AccessoryEntity>> getAll(){
        return data;
    }

    public void insert(AccessoryEntity adat){repo.insert(adat);}

    public void deleteAll(){repo.deleteAll();}


}

