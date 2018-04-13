package com.xmrbi.warehouse.module.san.activity;


import android.Manifest;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.base.BaseActivity;
import com.xmrbi.warehouse.base.Config;
import com.xmrbi.warehouse.component.zxing.camera.CameraManager;
import com.xmrbi.warehouse.component.zxing.decoding.CaptureActivityHandler;
import com.xmrbi.warehouse.component.zxing.decoding.InactivityTimer;
import com.xmrbi.warehouse.component.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Created by wzn on 2018/4/9.
 */

public class ScanActivity extends BaseActivity implements SurfaceHolder.Callback {
    @BindView(R.id.preview_view)
    SurfaceView surfaceView;
    @BindView(R.id.viewfinder_view)
    ViewfinderView viewfinderView;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private boolean playBeep;
    private boolean vibrate;
    private MediaPlayer mediaPlayer;
    private final long VIBRATE_DURATION = 200L;
    private final float BEEP_VOLUME = 0.10f;

    @Override
    protected int getLayout() {
        return R.layout.scan_activity;
    }

    @Override
    protected void onViewCreated() {
        setToolBar(mBundle.getString("title"));
    }

    @Override
    protected void initEventAndData() {
        CameraManager.init(getApplication());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        getRxPermissionsInstance().request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    CameraManager.get().closeDriver();
                } else {
                    ToastUtils.showLong(R.string.permissions_fail);
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     *
     * @param result
     */
    public void handleDecode(Result result) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String sequenceCode = result.getText();
        LogUtils.w("scan-content:" + sequenceCode);
        Intent intent = new Intent();
        if (sequenceCode == null || sequenceCode.equals("")) {
            ToastUtils.showLong(R.string.scan_fail);
        } else {
            if (sequenceCode.length()>=6&&sequenceCode.subSequence(0, 5).equals("'rfid")) {
                ToastUtils.showLong(R.string.scan_success);
//                mTextToSpeech.speak("扫描成功", TextToSpeech.QUEUE_FLUSH, null);
                //if (type.equals("领料扫码")) {
//                intent.setClass(BarcodeActivity.this,
//                        RfidIndexPostScanActivity.class);
//				}else {
//					intent.setClass(RfidIndexPostScanGIFActivity.this,
//							RfidPackListDetailItemActivity.class);
//				}
                String[] arr = sequenceCode.split("\\'");
                if (arr.length > 1) {
                    intent.putExtra("PickListid",
                            arr[1].substring(4, arr[1].length()));
                }
                startActivity(intent);
                
            } else if (sequenceCode.length()>=4&&sequenceCode.subSequence(0, 3).equals("'pd")) {
                String[] arr = sequenceCode.split("\\'");
                if (arr.length > 1) {
                    intent.putExtra("PickListid",
                            arr[1].substring(2, arr[1].length()));
                }
//                intent.setClass(BarcodeActivity.this,
//                        RfidNewInventoryActivity.class);
                startActivity(intent);
                ToastUtils.showLong(R.string.scan_success);
                finish();
//                mTextToSpeech.speak("扫描成功", TextToSpeech.QUEUE_FLUSH, null);
            } else if (sequenceCode.length()>=10&&sequenceCode.subSequence(0, 9).equals("PICKGOOD-")) {
                //安卓上位机领料
                String[] arr = sequenceCode.split("-");
                if (arr.length > 1) {
                    intent.putExtra("PickListid", arr[1]);
                }
//                intent.setClass(BarcodeActivity.this,
//                        RfidNewPickListActivity.class);
                startActivity(intent);
                ToastUtils.showLong(R.string.scan_success);
                finish();
            } else if (sequenceCode.length()>=13&&sequenceCode.subSequence(0, 12).equals("DELIVERGOOD,")) {
                //安卓上位机送货
                String[] arr = sequenceCode.split(",");
                if (arr.length > 1) {
                    intent.putExtra("delivergoodId", arr[1]);
                }
//                intent.setClass(BarcodeActivity.this,
//                        RfidIPCpostCardListActivity.class);
                startActivity(intent);
                ToastUtils.showLong(R.string.scan_success);
                finish();
            } else {
                ToastUtils.showLong(R.string.scan_recognition_fail);
            }
        }
        finish();

    }


    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }
}
