package com.xmrbi.warehouse.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
    public static void finishAllActivity(){
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
    }

    public static void exitApp() {
        finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public static void lauch(Context context,Class clazz ,Bundle bundle){
        Intent intent=new Intent(context,clazz);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
