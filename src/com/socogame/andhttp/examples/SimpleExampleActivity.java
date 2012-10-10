package com.socogame.andhttp.examples;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.socogame.andhttp.AsyncHttpGet;
import com.socogame.andhttp.DefaultThreadPool;
import com.socogame.andhttp.R;
import com.socogame.andhttp.RequestResultCallback;

public class SimpleExampleActivity extends BaseActivity {
	AsyncHttpGet httpget1 = null;
	ListView list = null;
	ArrayList<HashMap<String, Integer>> listData = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		list = (ListView) findViewById(R.id.list);

		httpget1 = new AsyncHttpGet(new DefaultParseHandler(),
				"http://files.cnblogs.com/meiyitian/netlib.css", null,
				new RequestResultCallback() {

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(Object o) {
						try {
							SimpleExampleActivity.this.listData = (ArrayList<HashMap<String, Integer>>) o;
							SimpleExampleActivity.this.mHandler
									.sendEmptyMessage(0);
							Log.d(SimpleExampleActivity.class.getName(),
									"MainActivity  onSuccess");
						} catch (Exception e) {
							Log.d(SimpleExampleActivity.class.getName(),
									"MainActivity   onSuccess Exception ,"
											+ e.getMessage());
							e.printStackTrace();
						}
					}

					@Override
					public void onFail(Exception e) {

					}
				});
		Log.i(SimpleExampleActivity.class.getName(), "MainActivity");
		DefaultThreadPool.getInstance().execute(httpget1);
		this.requestList.add(httpget1);
	}

	private class ListAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return listData.size();
		}

		@Override
		public Object getItem(int position) {

			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tvName = new TextView(SimpleExampleActivity.this);
			tvName.setText("code : " + listData.get(position).get("code"));
			return tvName;
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				list.setAdapter(new ListAdapter());
			}
		};
	};
}
