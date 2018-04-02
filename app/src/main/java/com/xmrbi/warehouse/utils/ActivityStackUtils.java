package com.xmrbi.warehouse.utils;

import android.app.Activity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wzn on 2018/3/29.
 */

public class ActivityStackUtils {
    private static Set<Activity> allActivities;

    public static void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    public static void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    public static void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
