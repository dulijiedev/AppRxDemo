package com.dol.appupdate.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dol.appupdate.R;
import com.dol.appupdate.base.BaseHttpDownloadManager;
import com.dol.appupdate.listener.OnDownloadListener;
import com.dol.appupdate.manager.DownloadManager;
import com.dol.appupdate.manager.HttpDownloadManager;
import com.dol.appupdate.utils.ApkUtil;
import com.dol.appupdate.utils.Constant;
import com.dol.appupdate.utils.FileUtil;
import com.dol.appupdate.utils.LogUtil;
import com.dol.appupdate.utils.NotificationUtil;

import java.io.File;

public final class DownloadService extends Service implements OnDownloadListener {

    private static final String TAG = Constant.TAG + "DownloadService";
    private int smallIcon;
    private String apkUrl;
    private String apkName;
    private String downloadPath;
    private String authorities;
    private OnDownloadListener listener;
    private boolean showNotification;
    private boolean showBgdToast;
    private boolean jumpInstallPage;
    private int lastProgress;
    /**
     * 是否正在下载，防止重复点击
     */
    private boolean downloading;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == intent) {
            return START_STICKY;
        }
        init();
        return super.onStartCommand(intent, flags, startId);
    }


    private void init() {
        apkUrl = DownloadManager.getInstance().getApkUrl();
        apkName = DownloadManager.getInstance().getApkName();
        downloadPath = DownloadManager.getInstance().getDownloadPath();
        smallIcon = DownloadManager.getInstance().getSmallIcon();
        authorities = DownloadManager.getInstance().getAuthorities();
        //如果没有设置则为包名
        if (TextUtils.isEmpty(authorities)) {
            authorities = getPackageName();
        }
        //创建apk文件存储文件夹
        FileUtil.createDirDirectory(downloadPath);

        listener = DownloadManager.getInstance().getConfiguration().getOnDownloadListener();
        showNotification = DownloadManager.getInstance().getConfiguration().isShowNotification();
        showBgdToast = DownloadManager.getInstance().getConfiguration().isShowBgdToast();
        jumpInstallPage = DownloadManager.getInstance().getConfiguration().isJumpInstallPage();
        //获取app通知开关是否打开
        boolean enable = NotificationUtil.notificationEnable(this);
        LogUtil.d(TAG, enable ? "应用的通知栏开关状态：已打开" : "应用的通知栏开关状态：已关闭");
        download();
    }

    /**
     * 获取下载管理者
     */
    private synchronized void download() {
        if (downloading) {
            LogUtil.e(TAG, "download: 当前正在下载，请务重复下载！");
            return;
        }
        BaseHttpDownloadManager manager = DownloadManager.getInstance().getConfiguration().getHttpManager();
        //使用自己的下载
        if (manager == null) {
            manager = new HttpDownloadManager(this, downloadPath);
            DownloadManager.getInstance().getConfiguration().setHttpManager(manager);
        }
        //如果用户自己定义了下载过程
        manager.download(apkUrl, apkName, this);
        downloading = true;
    }


    @Override
    public void start() {
        if (showNotification) {
            if (showBgdToast) {
                handler.sendEmptyMessage(0);
            }
            String startDownload = getResources().getString(R.string.start_download);
            String startDownloadHint = getResources().getString(R.string.start_download_hint);
            NotificationUtil.showNotification(this, smallIcon, startDownload, startDownloadHint);
        }
        if (listener != null) {
            listener.start();
        }
    }

    @Override
    public void downloading(int max, int progress) {
        LogUtil.i(TAG, "max: " + max + " --- progress: " + progress);
        if (showNotification) {
            //优化通知栏更新，减少通知栏更新次数
            int curr = (int) (progress / (double) max * 100.0);
            if (curr != lastProgress) {
                lastProgress = curr;
                String downloading = getResources().getString(R.string.start_downloading);
                NotificationUtil.showProgressNotification(this, smallIcon, downloading, "", max, progress);
            }
        }
        if (listener != null) {
            listener.downloading(max, progress);
        }
    }

    @Override
    public void done(File apk) {
        LogUtil.d(TAG, "done: 文件已下载至" + apk.toString());
        downloading = false;
        if (showNotification) {
            String downloadCompleted = getResources().getString(R.string.download_completed);
            String clickHint = getResources().getString(R.string.click_hint);
            NotificationUtil.showDoneNotification(this, smallIcon, downloadCompleted, clickHint, authorities, apk);
        }
        //如果用户设置了回调 则先处理用户的事件 在执行自己的
        if (listener != null) {
            listener.done(apk);
        }
        if (jumpInstallPage) {
            ApkUtil.installApk(this, authorities, apk);
        }
        releaseResources();
    }

    @Override
    public void cancel() {
        downloading = false;
        if (showNotification) {
            NotificationUtil.cancelNotification(this);
        }
        if (listener != null) {
            listener.cancel();
        }
    }

    @Override
    public void error(Exception e) {
        LogUtil.e(TAG, "error: " + e);
        downloading = false;
        if (showNotification) {
            String msg = e.getMessage();
            String downloadError = getResources().getString(R.string.download_error);
            String conDownloading = getResources().getString(R.string.continue_downloading);
            if (!TextUtils.isEmpty(msg) &&
                    msg.contains("android.content.res.XmlResourceParser")) {
                downloadError = getResources().getString(R.string.error_config);
                conDownloading = getResources().getString(R.string.read_readme);
            }
            NotificationUtil.showErrorNotification(this, smallIcon, downloadError, conDownloading);
        }
        if (listener != null) {
            listener.error(e);
        }
    }

    /**
     * 下载完成释放资源
     */

    private void releaseResources() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        stopSelf();
        DownloadManager.getInstance().release();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(DownloadService.this, R.string.background_downloading, Toast.LENGTH_SHORT).show();

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
