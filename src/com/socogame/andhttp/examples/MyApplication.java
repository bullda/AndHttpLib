package com.socogame.andhttp.examples;

import com.socogame.andhttp.DefaultThreadPool;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

	@Override
	public void onCreate() {

		super.onCreate();
	}

	@Override
	public void onLowMemory() {
		/**
		 * 低内存的时候主动释放所有线程和资源
		 * 
		 * PS:这里不一定每被都调用
		 */
		DefaultThreadPool.shutdown();
		Log.i(MyApplication.class.getName(),
				"MyApplication  onError  onLowMemory");
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		/**
		 * 系统退出的时候主动释放所有线程和资源 PS:这里不一定被都调用
		 */
		DefaultThreadPool.shutdown();
		Log.i(MyApplication.class.getName(),
				"MyApplication  onError  onTerminate");
		super.onTerminate();
	}

}
