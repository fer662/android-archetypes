package ${package}.network;

import java.util.ArrayList;
import java.util.List;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import ${package}.Application;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.text.TextUtils;

public class BaseHTTPClient {
	
	public static final String TAG = HTTPClient.class.getSimpleName();
	
	private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

	public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(Application.sharedApplicationContext());
        }
 
        return mRequestQueue;
    }
	
	public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }
	
	public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
 
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
 
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    
    public String getFullURLForPath(String path, List<BasicNameValuePair> getParameters) {
    	if (getParameters != null) {
    		return Environment.getCurrentEnvironment().getBaseURL() + path + "?" + URLEncodedUtils.format(getParameters, "UTF-8");
    	}
    	else {
    		return Environment.getCurrentEnvironment().getBaseURL() + path;
    	}
    }
    
	public <T> void getObject(String path, List<BasicNameValuePair> getParameters, final Class<T> clazz, final Listener<T> listener) {
		JsonRequest<?> request = JsonRequest.objectRequest(Request.Method.GET,
				getFullURLForPath(path, getParameters),
				null,
				clazz, 
				listener,
				new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error);
					}
				});
		
		getRequestQueue().add(request);
	}
	
	public <T> void getArray(String path, List<BasicNameValuePair> getParameters, final Class<T> clazz, final Listener<ArrayList<T>> listener) {
		JsonRequest<?> request = JsonRequest.arrayRequest(Request.Method.GET,
				getFullURLForPath(path, getParameters),
				null,
				clazz, 
				listener,
				new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error);
					}
				});
		
		getRequestQueue().add(request);
	}
	
	public <T> void postObject(String path, List<BasicNameValuePair> getParameters, ParametersSerializer parameters, final Class<T> clazz, final Listener<T> listener) {
		
		JsonRequest<?> request = JsonRequest.objectRequest(Request.Method.POST,
				getFullURLForPath(path, getParameters),
				parameters,
				clazz,
				listener,
				new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error);
					}
				});
		
		getRequestQueue().add(request);
	}
	
	public <T> void postArray(String path, List<BasicNameValuePair> getParameters, ParametersSerializer parameters, final Class<T> clazz, final Listener<ArrayList<T>> listener) {
		
		JsonRequest<?> request = JsonRequest.arrayRequest(Request.Method.POST,
				getFullURLForPath(path, getParameters),
				parameters,
				clazz,
				listener,
				new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onError(error);
					}
				});
		
		getRequestQueue().add(request);
	}
}
