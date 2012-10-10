package com.socogame.andhttp.examples;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.socogame.andhttp.AsyncHttpGet;
import com.socogame.andhttp.DefaultThreadPool;
import com.socogame.andhttp.RequestResultCallback;
import com.socogame.andhttp.exception.RequestException;
import com.socogame.andhttp.utils.RequestParameter;

public class HttpGetExample extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		testHttpGet();
	}

	private void testHttpGet() {
		Log.i("HttpGetExample", "begin testHttpGet()");
		List<RequestParameter> parameterList = new ArrayList<RequestParameter>();
		parameterList.add(new RequestParameter("bs", "测试"));
		parameterList.add(new RequestParameter("wd", "测试"));
		AsyncHttpGet httpget = new AsyncHttpGet(null, "http://www.baidu.com",
				parameterList, new RequestResultCallback() {

					@Override
					public void onSuccess(Object o) {
						Log.i("HttpGetExample",
								"HttpGetExample  request  onSuccess result :"
										+ ((String) o).toString());
					}

					@Override
					public void onFail(Exception e) {
						Log.i("HttpGetExample",
								"HttpGetExample  request   onFail :"
										+ ((RequestException) e).getMessage());
					}

				});

		DefaultThreadPool.getInstance().execute(httpget);

		this.requestList.add(httpget);
		Log.i("HttpGetExample", "end testHttpGet()");
	}
}
