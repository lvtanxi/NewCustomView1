package com.lv.demo;

import android.app.Application;

import com.antfortune.freeline.FreelineCore;

/**
 * Date: 2017-07-11
 * Time: 17:26
 * Description:
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FreelineCore.init(this, this);
    }
}
