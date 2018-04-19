package com.xmrbi.warehouse.utils;

import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.xmrbi.warehouse.R;
import com.xmrbi.warehouse.application.WareHouseApplication;


/**
 * 在线语音合成工具
 * Created by wzn on 2018/4/18.
 */

public class TTSUtils {
    private static SpeechSynthesizer mSpeechSynthesizer;
    /**
     * 初始化语音合成回调接口（先不做任何操作）
     */
    private static SynthesizerListener mListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    /**
     * 获取语音合成对象
     *
     * @return
     */
    public static SpeechSynthesizer getTTS() {
        if (mSpeechSynthesizer == null) {
            mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(WareHouseApplication.getInstances(), new InitListener() {
                @Override
                public void onInit(int code) {
                    if (code != ErrorCode.SUCCESS) {
                        ToastUtils.showLong(R.string.tts_init_fail);
                    }
                }
            });

        }
        return mSpeechSynthesizer;
    }

    public static void speak(String text) {
        getTTS().startSpeaking(text, mListener);
    }

    public static void speak(int textId) {
        getTTS().startSpeaking(WareHouseApplication.getInstances().getString(textId), mListener);
    }
}
