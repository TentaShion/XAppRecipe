package work.shion.javarecipe;

import android.content.Context;

import androidx.annotation.NonNull;

import work.shion.baser.nativeui.webview.IWebViewJavaAttacher;
import work.shion.baser.stetho.IStethoAttacher;
import work.shion.strictmode.IStrictModeAttacher;

/**
 * デバッグ用アプリ状態の管理
 */
public class DebugApplication extends MainApplication
        implements IStethoAttacher, IStrictModeAttacher, IWebViewJavaAttacher {

    @Override
    public void onCreate() {
        super.onCreate();

        // Stetho 設定
        attachStetho(getApplicationContext());

        // StrictMode の有効化
        setupThreadPolicy();
        setupVmPolicy();

        // WebView 設定
        IWebViewJavaAttacher.super.attachDebugger();
    }


    /**
     * Stetho の組み込み
     *
     * @param appContext アプリケーションコンテキスト
     */
    @Override
    public void attachStetho(@NonNull Context appContext) {
        IStethoAttacher.DefaultImpls.attachStetho(this, appContext);
    }

    /**
     * ThreadPolicy のセットアップ
     */
    @Override
    public void setupThreadPolicy() {
        IStrictModeAttacher.DefaultImpls.setupThreadPolicy(this);
    }

    /**
     * VmPolicy のセットアップ
     */
    @Override
    public void setupVmPolicy() {
        IStrictModeAttacher.DefaultImpls.setupVmPolicy(this);
    }
}
