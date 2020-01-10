package com.zero.greenlist.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hmmm...\nseems kinda empty in here\njust tap the blue button");
    }

    public LiveData<String> getText() {
        return mText;
    }
}