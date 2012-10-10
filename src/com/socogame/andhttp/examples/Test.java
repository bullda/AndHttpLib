package com.socogame.andhttp.examples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class Test extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout root = new LinearLayout(this);
		root.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams rootparams = new LinearLayout.LayoutParams(
				-1, -1);
		LinearLayout.LayoutParams childparams = new LinearLayout.LayoutParams(
				-1, -2);
		root.setLayoutParams(rootparams);

		Button btnSimple = new Button(this);
		btnSimple.setText("简单测试");
		btnSimple.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent inent = new Intent();
				inent.setClass(Test.this, SimpleExampleActivity.class);
				Test.this.startActivity(inent);

			}
		});
		btnSimple.setLayoutParams(childparams);
		root.addView(btnSimple);

		Button btnHttpGet = new Button(this);
		btnHttpGet.setText("测试HttpGet");
		btnHttpGet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent inent = new Intent();
				inent.setClass(Test.this, HttpGetExample.class);
				Test.this.startActivity(inent);

			}
		});
		btnHttpGet.setLayoutParams(childparams);
		root.addView(btnHttpGet);

		Button btnPost = new Button(this);
		btnPost.setText("测试HttpPost");
		btnPost.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent inent = new Intent();
				inent.setClass(Test.this, HttpPostExample.class);
				Test.this.startActivity(inent);

			}
		});
		btnPost.setLayoutParams(childparams);
		root.addView(btnPost);

		this.setContentView(root);

	}
}
