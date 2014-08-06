package ${package}.network;

import java.io.IOException;
import java.util.ArrayList;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import ${package}.network.Listener;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonRequest<T> extends StringRequest {

	private static ObjectMapper sObjectMapper;
	
	private ParametersSerializer mBodyParametersSerializer;
	
	protected static synchronized ObjectMapper getObjectMapper() {
		if (sObjectMapper == null) {
			sObjectMapper = new ObjectMapper();
		}
		return sObjectMapper;
	}
	
	public JsonRequest(int method, String url, ParametersSerializer parametersSerializer, Response.Listener<String> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		
		mBodyParametersSerializer = parametersSerializer;
	}
	
	public static <T> T fromJSON(final TypeReference<T> type,
		      final String jsonPacket) {
		   T data = null;

		   try {
		      data = new ObjectMapper().readValue(jsonPacket, type);
		   } catch (Exception e) {
		      // Handle the problem
		   }
		   return data;
		}
	
	public static <T> JsonRequest<T> arrayRequest(int method,
			String url,
			ParametersSerializer parametersSerializer,
			final Class<T> clazz,
			final Listener<ArrayList<T>> listener,
			ErrorListener errorListener) {
		
		return new JsonRequest<T>(method, url, parametersSerializer, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					ArrayList<T> items = getObjectMapper().readValue(response, getObjectMapper().getTypeFactory().constructCollectionType(ArrayList.class, clazz));
					listener.onResponse(items);
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}, errorListener);
	}
	
	public static <T> JsonRequest<T> objectRequest(int method,
			String url,
			ParametersSerializer parametersSerializer,
			final Class<T> clazz,
			final Listener<T> listener,
			ErrorListener errorListener) {
		
		return new JsonRequest<T>(method, url, parametersSerializer, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					listener.onResponse(getObjectMapper().readValue(response, clazz));
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}, errorListener);
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		if (mBodyParametersSerializer != null) {
			return mBodyParametersSerializer.serialize();
		}
		else {
			return null;
		}
	}
	
	@Override
	public String getBodyContentType() {
		return "application/json; charset=" + getParamsEncoding();
	}
	
}
