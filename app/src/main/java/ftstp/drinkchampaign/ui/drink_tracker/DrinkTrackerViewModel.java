package ftstp.drinkchampaign.ui.drink_tracker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DrinkTrackerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DrinkTrackerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}