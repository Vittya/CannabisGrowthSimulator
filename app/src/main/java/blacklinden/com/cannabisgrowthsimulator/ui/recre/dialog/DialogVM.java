package blacklinden.com.cannabisgrowthsimulator.ui.recre.dialog;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class DialogVM extends AndroidViewModel {

    private MutableLiveData<Bundle> bundleLiveData;
    public DialogVM(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Bundle> getBundleLiveData(){
        if(bundleLiveData == null){
            bundleLiveData = new MutableLiveData<Bundle>();
        }
        return bundleLiveData;
    }


}
