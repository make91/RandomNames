package com.example.make91.randomnames;

import android.support.multidex.MultiDexApplication;

import com.example.make91.randomnames.network.APIService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RandomNamesApplication extends MultiDexApplication {
    private static RandomNamesApplication singleton;
    private APIService apiService;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        apiService = new APIService();
        initRealm();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static RandomNamesApplication getInstance() {
        return singleton;
    }

    public APIService getApiService() {
        return apiService;
    }

}