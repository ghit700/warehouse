package com.xmrbi.warehouse.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.util.Xml;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.base.Config;
import com.xmrbi.warehouse.component.http.IOTransformer;
import com.xmrbi.warehouse.component.http.OkHttpHepler;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.Request;
import okhttp3.Response;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;
import static com.xmrbi.warehouse.base.Config.Http.UPDATE_APK_ADDRESS;
import static com.xmrbi.warehouse.base.Config.Http.UPDATE_APK_FILE_ADDRESS;
import static com.xmrbi.warehouse.base.Config.Http.UPDATE_APK_UPDATE_FILE;

/**
 * 更新app工具类
 * Created by wzn on 2018/5/7.
 */

public class UpdateUtils {
    /**
     * 进度条
     */
    private MaterialDialog mProgressDialog;
    /**
     * 提示更新框
     */
    private MaterialDialog mAlertDialog;
    /**
     *
     */
    private BaseActivity mActivity;
    private Disposable mDisposable;
    /**
     * 更新地址
     */
    private UpdateInfo mInfo;

    public UpdateUtils(Context context) {
        mActivity = (BaseActivity) context;
        mProgressDialog = new MaterialDialog
                .Builder(context)
                .title(R.string.update_progress_title)
                .content(R.string.update_progress_content)
                .progress(false, 0, true)
                .canceledOnTouchOutside(false)
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // 如果中断对话框，取消下载订阅
                        mDisposable.dispose();
                    }
                })
                .build();
        mAlertDialog = new MaterialDialog
                .Builder(context)
                .title(R.string.update_alert_title)
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        updating();
                    }
                })
                .negativeText("取消")
                .build();
    }

    // 获取当前程序的版本号
    private String getVersionName() {
        String versionname = "";
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = Utils.getApp().getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    Utils.getApp().getPackageName(), 0);
            versionname = packInfo.versionName;
        } catch (Exception e) {

        }
        return versionname;
    }

    // 安装apk
    protected void installApk() {
        mAlertDialog.setTitle(mActivity.getString(R.string.update_alert_install_title));
        mAlertDialog.setContent(mActivity.getString(R.string.update_alert_install_content));
        mAlertDialog.getBuilder().onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                if (FileUtils.isFileExists(Config.Http.UPDATE_APK_FILE_ADDRESS)) {
                    File file = new File(Config.Http.UPDATE_APK_FILE_ADDRESS);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    // 由于没有在Activity环境下启动Activity,设置下面的标签
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= 24) { // 判读版本是否在7.0以上
                        // 参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致 参数3 共享的文件
                        Uri apkUri = FileProvider.getUriForFile(Utils.getApp(),
                                "com.a520wcf.chapter11.fileprovider", file);
                        // 添加这一句表示对目标应用临时授权该Uri所代表的文件
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(apkUri,
                                "application/vnd.android.package-archive");
                    } else {
                        intent.setDataAndType(Uri.fromFile(file),
                                "application/vnd.android.package-archive");
                    }
                    startActivity(intent);
                } else {
                    ToastUtils.showLong("安装文件不存在！");
                }
            }
        });
        mAlertDialog.show();


    }


    /**
     * 更新下载apk
     *
     * @return
     */
    public void updateAPK() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<Boolean> event) throws Exception {
                try {
                    //判断是否需要升级
                    String mUrl = UPDATE_APK_UPDATE_FILE;
                    URL url = new URL(mUrl);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(12000);
                    InputStream is = conn.getInputStream();
                    UpdateInfo info = getUpdataInfo(is);
                    String versionName = getVersionName();
                    if (versionName.compareTo(info.getVersion()) == 0) {
                        //不用升级
                        event.onNext(false);
                    } else if (versionName.compareTo(info.getVersion()) < 0) {
                        event.onNext(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    event.onComplete();
                }
            }
        })
                .compose(new IOTransformer<Boolean>(mActivity))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isUpdate) throws Exception {
                        if (isUpdate) {
                            mAlertDialog.setContent(mInfo.getDescription());
                            mAlertDialog.show();
                        }
                    }
                });


    }

    /**
     * 更新中
     */
    private void updating() {
        mProgressDialog.show();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<String> event) throws Exception {
                InputStream is = null;
                OutputStream os = null;
                try {
                    Request request = new Request.Builder()
                            .url(UPDATE_APK_ADDRESS)
                            .build();
                    Response response = OkHttpHepler.getClient().newCall(request).execute();
                    if (response.isSuccessful()) {
                        is = response.body().byteStream();
                        long length = response.body().contentLength();
                        mProgressDialog.setMaxProgress((int) length);
                        event.onNext("show");
                        FileUtils.createOrExistsDir(Config.Http.UPDATE_APK_FILE_ADDRESS_DIR);
                        os = new FileOutputStream(UPDATE_APK_FILE_ADDRESS);
                        byte[] bytes = new byte[1024];
                        int total = 0;
                        int count;
                        while ((count = is.read(bytes)) != -1) {
                            total += count;
                            // 返回当前实时进度
                            mProgressDialog.setProgress(total);
//                            event.onNext(String.valueOf(total));
                            os.write(bytes, 0, count);
                        }
                    }
                    event.onNext("finish");
                } catch (Exception e) {
                    e.printStackTrace();
                    event.onError(e);
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                event.onComplete();

            }
        })
                .compose(new IOTransformer<String>(mActivity))
                .subscribe(new Observer<String>() {

                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull String s) {
                        if (s.equals("finish")) {
                            installApk();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        if (!mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                        ToastUtils.showLong(R.string.update_download_fail);
                    }

                    @Override
                    public void onComplete() {
                        mProgressDialog.dismiss();

                    }
                });


    }

    /*
     * 用pull解析器解析服务器返回的xml文件 (xml封装了版本号)
	 */
    private UpdateInfo getUpdataInfo(InputStream is) throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "utf-8");// 设置解析的数据源
        int type = parser.getEventType();
        mInfo = new UpdateInfo();// 实体
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if ("version".equals(parser.getName())) {
                        mInfo.setVersion(parser.nextText()); // 获取版本号
                    } else if ("url".equals(parser.getName())) {
                        mInfo.setUrl(parser.nextText()); // 获取要升级的APK文件
                    } else if ("description".equals(parser.getName())) {
                        mInfo.setDescription(parser.nextText()); // 获取该文件的信息
                    }
                    break;
            }
            type = parser.next();
        }
        return mInfo;
    }

    /**
     * 更新信息实体类
     * UpdateInfo.java
     */
    class UpdateInfo {
        private String version;    //版本号
        private String url;    //新版本存放url路径
        private String description;   //更新说明信息，比如新增什么功能特性等

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
