package com.xmrbi.warehouse.component.rfid;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.blankj.utilcode.util.ToastUtils;
import com.speedata.libuhf.IUHFService;
import com.speedata.libuhf.UHFManager;
import com.speedata.libuhf.bean.Tag_Data;
import com.speedata.libuhf.utils.SharedXmlUtil;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.application.WareHouseApplication;
import com.xmrbi.warehouse.event.RfidScanEvent;
import com.xmrbi.warehouse.utils.RxBus;

import java.util.List;

/**
 * Created by wzn on 2018/4/23.
 */

public class RfidUtils {
    private Context mContext;
    private static IUHFService mIuhfService;
    private static String mMode;
    /**
     * 处理扫描回调的handler
     */
    private static Handler mHandler;
    /**
     * handle的处理id
     */
    private static int HANDLE_WHAT = 1;

    public RfidUtils(Context mContext) {
        this.mContext = mContext;


    }

    public static IUHFService getIuhfService() {
        if (mIuhfService == null) {
            if(Looper.myLooper()==null){
                Looper.prepare();
            }
            try {
                mIuhfService = UHFManager.getUHFService(WareHouseApplication.getInstances());
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.showLong(R.string.rfid_init_fail);
                return null;
            }
            mMode = SharedXmlUtil.getInstance(WareHouseApplication.getInstances()).read("modle", "");
        }
        return mIuhfService;
    }


    /**
     * 打开设备(onresume)
     *
     * @return
     */
    public static boolean openDevice() {
        if (mIuhfService != null && mIuhfService.OpenDev() != 0) {
            return false;
        }
        return true;
    }

    /**
     * 关闭设备(onstop)
     *
     * @return
     */
    public static void closeDevice() {
        if (mIuhfService != null) {
            mIuhfService.CloseDev();
        }
    }

    /**
     * 注销广播、对象制空(ondestory)
     */
    public static void destoryService() {
        if(mIuhfService!=null){
            UHFManager.closeUHFService();
        }
    }

    public static void start(Handler handler) {
        mHandler.removeMessages(HANDLE_WHAT);
        mHandler = null;
        mHandler = handler;
        start();
    }

    /**
     * 开始扫描
     */
    public static void start() {
        if (mHandler == null) {
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == HANDLE_WHAT) {
                        if (msg.obj != null) {
                            RxBus.getDefault().post(new RfidScanEvent((List<Tag_Data>) msg.obj));
                        }
                    }
                }
            };
        }
        if (mIuhfService != null && mIuhfService.OpenDev() == 0) {
            mIuhfService.inventory_start(mHandler);
        }
    }

    /**
     * 结束扫描（返回-1是初始化失败，模块不存在，!=0是停止失败）
     *
     * @return
     */
    public static int stop() {
        if (mIuhfService != null) {
            return mIuhfService.inventory_stop();
        }
        return -1;
    }

    // 判断扫出来的rfid是否符合规范XXXXXXXAAACCCCCCCCCCCCCC
    public static Boolean isAccord(String s) {
        Boolean fl = false;
        if (s != null) {
            if (s.length() > 10) {
                if (s.subSequence(0, 10).equals("0000000AAA")) {
                    fl = true;
                } else {
                    fl = false;
                }
            } else {
                fl = false;
            }

        } else {
            fl = false;
        }
        return fl;
    }
}
