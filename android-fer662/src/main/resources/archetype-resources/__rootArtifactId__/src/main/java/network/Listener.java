package ${package}.network;

import com.android.volley.VolleyError;

public interface Listener <T> {

	public void onResponse(T response);
	public void onError(VolleyError error);
}
