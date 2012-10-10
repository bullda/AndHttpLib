package com.socogame.andhttp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import com.socogame.andhttp.exception.RequestException;
import com.socogame.andhttp.utils.RequestParameter;


public class AsyncHttpPost extends BaseRequest {
	private static final long serialVersionUID = 2L;
	DefaultHttpClient httpClient;
	List<RequestParameter> parameter = null;

	public AsyncHttpPost(ParseHandler handler, String url,
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
			request = new HttpPost(url);
			request.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, connectTimeout);
			request.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					readTimeout);
			if (parameter != null && parameter.size() > 0) {
				List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
				for (RequestParameter p : parameter) {
					list.add(new BasicNameValuePair(p.getName(), p.getValue()));
				}
				((HttpPost) request).setEntity(new UrlEncodedFormEntity(list,
						HTTP.UTF_8));
			}
			HttpResponse response = httpClient.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				ByteArrayOutputStream content = new ByteArrayOutputStream();
				response.getEntity().writeTo(content);
				String ret = new String(content.toByteArray()).trim();
				content.close();
				Object Object = null;
				if (AsyncHttpPost.this.handler != null) {
					Object = AsyncHttpPost.this.handler.handle(ret);
					if (AsyncHttpPost.this.requestCallback != null
							&& Object != null) {
						AsyncHttpPost.this.requestCallback.onSuccess(Object);
						return;
					}
					if (Object == null || "".equals(Object.toString())) {
						RequestException exception = new RequestException(
								RequestException.IO_EXCEPTION, "数据读取异常");
						AsyncHttpPost.this.requestCallback.onFail(exception);
					}
				} else {
					AsyncHttpPost.this.requestCallback.onSuccess(ret);
				}
			} else {
				RequestException exception = new RequestException(
						RequestException.IO_EXCEPTION, "respond code error : "
								+ statusCode);
				AsyncHttpPost.this.requestCallback.onFail(exception);
			}

		} catch (java.lang.IllegalArgumentException e) {
			RequestException exception = new RequestException(
					RequestException.IO_EXCEPTION, "connect error.");
			AsyncHttpPost.this.requestCallback.onFail(exception);
		} catch (org.apache.http.conn.ConnectTimeoutException e) {
			RequestException exception = new RequestException(
					RequestException.SOCKET_TIMEOUT_EXCEPTION,
					"connect time out.");
			AsyncHttpPost.this.requestCallback.onFail(exception);
		} catch (java.net.SocketTimeoutException e) {
			RequestException exception = new RequestException(
					RequestException.SOCKET_TIMEOUT_EXCEPTION, "read time out.");
			AsyncHttpPost.this.requestCallback.onFail(exception);
		} catch (UnsupportedEncodingException e) {
			RequestException exception = new RequestException(
					RequestException.UNSUPPORTED_ENCODEING_EXCEPTION,
					"encode error.");
			AsyncHttpPost.this.requestCallback.onFail(exception);
		} catch (org.apache.http.conn.HttpHostConnectException e) {
			RequestException exception = new RequestException(
					RequestException.CONNECT_EXCEPTION, "connect error.");
			AsyncHttpPost.this.requestCallback.onFail(exception);
		} catch (ClientProtocolException e) {
			RequestException exception = new RequestException(
					RequestException.CLIENT_PROTOL_EXCEPTION,
					"client protol error.");
			AsyncHttpPost.this.requestCallback.onFail(exception);
			e.printStackTrace();
		} catch (IOException e) {
			RequestException exception = new RequestException(
					RequestException.IO_EXCEPTION, "data read error.");
			AsyncHttpPost.this.requestCallback.onFail(exception);
			e.printStackTrace();
		} finally {
			// request.
		}
		super.run();
	}
}
