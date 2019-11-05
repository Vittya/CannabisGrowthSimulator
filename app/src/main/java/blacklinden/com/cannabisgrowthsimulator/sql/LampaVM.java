package blacklinden.com.cannabisgrowthsimulator.sql;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import androidx.annotation.NonNull;
import blacklinden.com.cannabisgrowthsimulator.pojo.Lampa;


public class LampaVM extends AndroidViewModel {

    private LampaRepo repo;
    private LiveData<List<Lampa>> data;

    public LampaVM(@NonNull Application application) {
        super(application);
        repo = new LampaRepo(application);
        data = repo.getAll();
    }

    public LiveData<List<Lampa>> getAll(){
        return data;
    }

    public void insert(Lampa adat){repo.insert(adat);}

    public void deleteAll(){repo.deleteAll();}



}