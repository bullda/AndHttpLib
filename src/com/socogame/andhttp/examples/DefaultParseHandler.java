package com.socogame.andhttp.examples;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.socogame.andhttp.ParseHandler;

public class DefaultParseHandler implements ParseHandler {

	@Override
	public Object handle(String str) {
		ArrayList<HashMap<String, Integer>> list = null;
		/*
		 * here we just parse a json object .
		 */
		try {
			HashMap<String, Integer> m = null;
			JSONObject o = new JSONObject(str);
			Log.d(DefaultParseHandler.class.getName(),
					"DefaultParseHandler  handler string  :" + str + " !");
			if (o != null) {
				list = new ArrayList<HashMap<String, Integer>>();
				JSONArray array = o.getJSONArray("items");
				int count = array.length();
				for (int i = 0; i < count; i++) {
					JSONObject item = array.getJSONObject(i);
					if (item != null) {
						m = new HashMap<String, Integer>();
						m.put("code", item.has("code") ? item.getInt("code")
								: 0);
						list.add(m);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
