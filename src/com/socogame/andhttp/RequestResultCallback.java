package com.socogame.andhttp;

public interface RequestResultCallback {
	public void onSuccess(Object o);

	public void onFail(Exception e);

}
