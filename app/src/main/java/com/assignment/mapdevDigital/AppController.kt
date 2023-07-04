package com.assignment.mapdevDigital

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle

class AppController : Application(), Application.ActivityLifecycleCallbacks {

    val TAG = AppController::class.java.simpleName

    override fun onCreate() {
        super.onCreate()
//        HyperSnapSDK.init(this, "45ac9b", "7143ad80089cd1dfba9e", HyperSnapParams.Region.India)
        registerActivityLifecycleCallbacks(this)
//        FirebaseApp.initializeApp(applicationContext)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        try {
//            MultiDex.install(this)
            // TrustKit.getInstance();
        } catch (multiDexException: RuntimeException) {
            multiDexException.printStackTrace()
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {
//        ISUInAppUpdateSDK.begin((Context) activity);
//        FirebaseApp.initializeApp(this)
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}