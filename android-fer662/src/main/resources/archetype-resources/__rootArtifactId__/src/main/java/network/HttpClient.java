package ${package}.network;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.StringEntity;
import org.codehaus.jackson.JsonNode;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.loopj.android.http.*;

import ${package}.${rootArtifactId}Application;
import ${package}.network.protocol.${artifactId}Protocol;
import ${package}.network.protocol.NetworkProtocol;
import ${package}.network.response.SampleResponse;
import ${package}.util.Logging;
import ${package}.util.Serialization;

public class HttpClient {
	private static String baseURL = null;

	private static AsyncHttpClient client = new AsyncHttpClient();
	private static NetworkProtocol networkProtocol = new ${artifactId}Protocol();

	protected static void get(String url, RequestParams parameters, AsyncHttpResponseHandler responseHandler) {
		client.get(${rootArtifactId}Application.sharedApplicationContext(),
				getAbsoluteUrl(url),
				parameters,
				responseHandler);
	}

	protected static void post(String url, HttpEntity parameters, AsyncHttpResponseHandler responseHandler) {
		client.post(${rootArtifactId}Application.sharedApplicationContext(),
				getAbsoluteUrl(url),
				parameters,
				networkProtocol.getContentType(),
				responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		if (baseURL == null) {
			Environment environment = Environment.getCurrentEnvironment();
			baseURL = environment.getBaseURL();
			Log.d(Logging.TAG_NETWORK, "HTTP Client initialized with environment:");
			Log.d(Logging.TAG_NETWORK, "Name:" + environment.getName());
			Log.d(Logging.TAG_NETWORK, "Base URL:" + environment.getBaseURL());
		}
		String url = baseURL + relativeUrl;
		Log.d(Logging.TAG_NETWORK, url);
		return url;
	}
/*
	public static void login(String username, String password, ObjectResponseHandler<LoginResponse> responseHandler) {

		JSONObject jsonParams = new JSONObject();
		
		try {
			jsonParams.put("Username", username);
			jsonParams.put("Password", password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		StringEntity parameters = networkProtocol.getParameters("performLogin", jsonParams);
		
		post("json/ProcessMobileDeviceRequest", parameters, new ObjectAsyncHttpResponseHandler<LoginResponse>(responseHandler, LoginResponse.class));
	}
	*/
	//public static void getClubs(ArrayResponseHandler<Club> responseHandler) {
	//	RequestParams params = new RequestParams();
	//	get("/clubs", params, new ArrayAsyncHttpResponseHandler<Club>(responseHandler, Club.class));
	//}
	
	//public static void getClub(int id, ObjectResponseHandler<Club> responseHandler) {
	//	RequestParams params = new RequestParams();
	//	get("/club/" + id, params, new ObjectAsyncHttpResponseHandler<Club>(responseHandler, Club.class));
	//}
}

class ArrayAsyncHttpResponseHandler <E> extends AsyncHttpResponseHandler {
	
	private ArrayResponseHandler<E> responseHandler;
	private Class<E> clazz;
	
	public ArrayAsyncHttpResponseHandler(ArrayResponseHandler<E> responseHandler, Class<E> clazz) {
		this.responseHandler = responseHandler;
		this.clazz = clazz;
	}

	@Override
	public void onSuccess(String json) {
		JsonNode response = Serialization.deserialize(json);
		if (response != null) {
			JsonNode body = response.get("data");
			if (body != null) {
				ArrayList<E> objects = Serialization.<E>mapArray(body, clazz);
				responseHandler.onSuccess(objects);
			}
			else {
				responseHandler.onFailure(ObjectResponseHandler.PARSING_ERROR, "");
			}
		}
		else {
			responseHandler.onFailure(ObjectResponseHandler.PARSING_ERROR, "");
		}
	}

	@Override
	public void onFailure(Throwable e, String errorString) {
		if (e instanceof HttpResponseException) {
            HttpResponseException exp = (HttpResponseException) e;
            responseHandler.onFailure(exp.getStatusCode(), e.getMessage());
        }
	}
}

class ObjectAsyncHttpResponseHandler <E> extends AsyncHttpResponseHandler {
	
	private ObjectResponseHandler<E> responseHandler;
	private Class<E> clazz;
	
	public ObjectAsyncHttpResponseHandler(ObjectResponseHandler<E> responseHandler, Class<E> clazz) {
		this.responseHandler = responseHandler;
		this.clazz = clazz;
	}
	
	@Override
	public void onSuccess(String json) {
		JsonNode response = Serialization.deserialize(json);
		if (response != null) {
			JsonNode results = response.get("MDSFunctionResults").get(0);
			if (results != null) {
				JsonNode result = results.get("Result");
				boolean success = result.get("Success").asBoolean();
				if (success) {
					E object = Serialization.mapObject(result, this.clazz);
					responseHandler.onSuccess(object);
				}
				else {
					responseHandler.onFailure(ArrayResponseHandler.PARSING_ERROR, "");
				}
			}
			else {
				responseHandler.onFailure(ArrayResponseHandler.PARSING_ERROR, "");
			}
		}
		else {
			responseHandler.onFailure(ArrayResponseHandler.PARSING_ERROR, "");
		}
	}
	
	@Override
	public void onFailure(Throwable e, String errorString) {
		if (e instanceof HttpResponseException) {
            HttpResponseException exp = (HttpResponseException) e;
            responseHandler.onFailure(exp.getStatusCode(), e.getMessage());
        }
		else {
			responseHandler.onFailure(-1, "");
		}
	}
}