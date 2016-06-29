package com.tchip.autorecord.receiver;

import com.tchip.autorecord.MyApp;
import com.tchip.autorecord.util.MyLog;
import com.tchip.autorecord.util.StorageUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CardEjectReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		MyLog.i("CardEjectReceiver.action:" + action);
		if (action.equals(Intent.ACTION_MEDIA_EJECT)
				|| action.equals(Intent.ACTION_MEDIA_BAD_REMOVAL)
				|| action.equals(Intent.ACTION_MEDIA_UNMOUNTED)) {
			if ("/storage/sdcard2".equals(intent.getData().getPath())) {
				MyApp.isVideoCardEject = true;
			}
		} else if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
			if ("/storage/sdcard2".equals(intent.getData().getPath())) { // 插入录像卡自动录像
				StorageUtil.createRecordDirectory();
				if (MyApp.isAccOn) {
					if (!MyApp.isFrontRecording) {
						MyApp.shouldMountRecordFront = true;
					}
					if (!MyApp.isBackRecording) {
						MyApp.shouldMountRecordBack = true;
					}
				}
				MyApp.isVideoCardEject = false;
				MyApp.isVideoCardFormat = false;
			}
		}
	}

}
