package com.gopher.medicalwaste;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.Stack;

/**
 * Created by Administrator on 2017/10/13.
 */

public class App extends Application {
    public static final String TAG = App.class.getSimpleName();
    private static App mApp;
    private Stack<Activity> activityStack;

    public static App getInstance() {
        if (mApp == null) {
            synchronized (App.class) {
                if (mApp == null) {
                    mApp = new App();
                }
            }
        }
        return mApp;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initNet();
        //Stetho
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(
//                                Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(
//                                Stetho.defaultInspectorModulesProvider(this))
//                        .build());
    }

    private void initNet() {
        try {
            String ip = PreferenceUtil.getString(Constans.IP_KEY, null);
            String port = PreferenceUtil.getString(Constans.PORT_KEY, null);
            Log.e("ip", ip + ":" + port);
            if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(port)) {
                PreferenceUtil.put(Constans.IP_KEY, Constans.IP_VALUE);
                PreferenceUtil.put(Constans.PORT_KEY, Constans.PORT_VALUE);
            } else {
                Log.e("ip", ip + ":" + port);
                PreferenceUtil.put(Constans.IP_KEY, ip);
                PreferenceUtil.put(Constans.PORT_KEY, port);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加指定Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定Class的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 结束全部的Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void exitApp(Context context) {
        try {
            finishAllActivity();
            //杀死后台进程需要在AndroidManifest中声明android.permission.KILL_BACKGROUND_PROCESSES；
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            Log.e(TAG, "app exit" + e.getMessage());
        }
    }
}
