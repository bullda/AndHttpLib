package com.socogame.andhttp.examples;

import java.util.ArrayList;
import java.util.List;

import com.socogame.andhttp.AsyncHttpPost;
import com.socogame.andhttp.DefaultThreadPool;
import com.socogame.andhttp.RequestResultCallback;
import com.socogame.andhttp.exception.RequestException;
import com.socogame.andhttp.utils.RequestParameter;

import android.os.Bundle;
import android.util.Log;

public class HttpPostExample extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		testHttpPost();
	}

	private void testHttpPost() {
		List<RequestParameter> parameterList = new ArrayList<RequestParameter>();
		parameterList.add(new RequestParameter("username", "admin"));
		parameterList.add(new RequestParameter("password", ""));
		parameterList.add(new RequestParameter("is_remember", "1"));
		AsyncHttpPost httpost = new AsyncHttpPost(null,
				"http://daxigua.sinaapp.com/index.php/login/do_login",
				parameterList, new RequestResultCallback() {

					@Override
					public void onSuccess(Object o) {
						Log.i("HttpPostExample",
								"HttpPostExample  request  onSuccess result :"
										+ ((String) o).toString());
					}

					@Override
					public void onFail(Exception e) {
						Log.i("HttpPostExample",
								"HttpPostExample  request   onFail :"
										+ ((RequestException) e).getMessage());
					}

				});

		DefaultThreadPool.getInstance().execute(httpost);

		this.requestList.add(httpost);
	}
}
