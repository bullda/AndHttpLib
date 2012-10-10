package com.socogame.andhttp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.socogame.andhttp.exception.RequestException;
import com.socogame.andhttp.utils.RequestParameter;
import com.socogame.andhttp.utils.Utils;


public class AsyncHttpGet extends BaseRequest {
	private static final long serialVersionUID = 2L;
	DefaultHttpClient httpClient;
	List<RequestParameter> parameter;

	public AsyncHttpGet(ParseHandler handler, String url,
			List<RequestParameter> parameter,
			RequestResultCallback requestCallback) {
		this.handler = handler;
		this.url = url;
		this.parameter = parameter;
		this.requestCallback = requestCallback;
		if (httpClient == null)
			httpClient = new DefaultHttpClient();
	}

	@Override
	public void run() {
		try {
			if (parameter != null && parameter.size() > 0) {
				StringBuilder bulider = new StringBuilder();
				for (RequestParameter p : parameter) {
					if (bulider.length() != 0) {
						bulider.append("&");
					}

					bulider.append(Utils.encode(p.getName()));
					bulider.append("=");
					bulider.append(Utils.encode(p.getValue()));
				}
				url += "?" + bulider.toString();
			}
			request = new HttpGet(url);
			HttpResponse response = httpClient.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				ByteArrayOutputStream content = new ByteArrayOutputStream();
				response.getEntity().writeTo(content);
				String ret = new String(content.toByteArray()).trim();
				content.close();
				Object Object = null;
				if (AsyncHttpGet.this.handler != null) {
					Object = AsyncHttpGet.this.handler.handle(ret);
					if (AsyncHttpGet.this.requestCallback != null
							&& Object != null) {
						AsyncHttpGet.this.requestCallback.onSuccess(Object);
						return;
					}
					if (Object == null || "".equals(Object.toString())) {
						RequestException exception = new RequestException(
								RequestException.IO_EXCEPTION,
								"read data error.");
						AsyncHttpGet.this.requestCallback.onFail(exception);
					}
				} else {
					AsyncHttpGet.this.requestCallback.onSuccess(ret);
				}
			} else {
				RequestException exception = new RequestException(
						RequestException.IO_EXCEPTION, "read data error.");
				AsyncHttpGet.this.requestCallback.onFail(exception);
			}
		} catch (java.lang.IllegalArgumentException e) {
			RequestException exception = new RequestException(
					RequestException.IO_EXCEPTION, "connect error.");
			AsyncHttpGet.this.requestCallback.onFail(exception);
		} catch (org.apache.http.conn.ConnectTimeoutException e) {
			RequestException exception = new RequestException(
					RequestException.SOCKET_TIMEOUT_EXCEPTION,
					"connect time out.");
			AsyncHttpGet.this.requestCallback.onFail(exception);
		} catch (java.net.SocketTimeoutException e) {
			RequestException exception = new RequestException(
					RequestException.SOCKET_TIMEOUT_EXCEPTION, "read time out.");
			AsyncHttpGet.this.requestCallback.onFail(exception);
		} catch (UnsupportedEncodingException e) {
			RequestException exception = new RequestException(
					RequestException.UNSUPPORTED_ENCODEING_EXCEPTION,
					"encode error.");
			AsyncHttpGet.this.requestCallback.onFail(exception);
		} catch (org.apache.http.conn.HttpHostConnectException e) {
			RequestException exception = new RequestException(
					RequestException.CONNECT_EXCEPTION, "connect error.");
			AsyncHttpGet.this.requestCallback.onFail(exception);
		} catch (ClientProtocolException e) {
			RequestException exception = new RequestException(
					RequestException.CLIENT_PROTOL_EXCEPTION,
					"client protol error.");
			AsyncHttpGet.this.requestCallback.onFail(exception);
			e.printStackTrace();
		} catch (IOException e) {
			RequestException exception = new RequestException(
					RequestException.IO_EXCEPTION, "data read error.");
			AsyncHttpGet.this.requestCallback.onFail(exception);
			e.printStackTrace();
		} finally {
			// request.
		}
		super.run();
	}
}
