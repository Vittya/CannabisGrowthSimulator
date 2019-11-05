package blacklinden.com.cannabisgrowthsimulator.sql;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import androidx.annotation.NonNull;

import java.util.List;

import blacklinden.com.cannabisgrowthsimulator.pojo.ComboEntity;

public class ComboVM extends AndroidViewModel {

    private ComboRepo repo;
    private LiveData<List<ComboEntity>> data;

    public ComboVM(@NonNull Application application) {
        super(application);
        repo = new ComboRepo(application);
        data = repo.getAll();
    }

    public LiveData<List<ComboEntity>> getAll(){
        return data;
    }

    public void insert(ComboEntity adat){repo.insert(adat);}

}