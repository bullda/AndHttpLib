package com.socogame.andhttp;

import java.io.Serializable;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

public class BaseRequest implements Runnable, Serializable {
	HttpUriRequest request = null;

	private static final long serialVersionUID = 1L;
	protected ParseHandler handler = null;
	protected String url = null;

	protected int connectTimeout = 5000;

	protected int readTimeout = 5000;
	protected RequestResultCallback requestCallback = null;

	@Override
	public void run() {

	}

	protected void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	protected void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public HttpUriRequest getRequest() {
		return request;
	}

}
