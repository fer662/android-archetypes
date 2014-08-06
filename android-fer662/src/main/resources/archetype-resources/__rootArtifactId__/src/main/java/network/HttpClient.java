package ${package}.network;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.message.BasicNameValuePair;

import ${package}.network.response.*;

public class HTTPClient extends BaseHTTPClient {

	private static HTTPClient instance = null;

	protected HTTPClient() {
	}

	public static HTTPClient getInstance() {
		if (instance == null) {
			instance = new HTTPClient();
		}
		return instance;
	}
	
	public void getRepos(Listener<ArrayList<DateReturnType>> listener) {
		getArray("/users/fer662/repos", 
				Arrays.asList(
						new BasicNameValuePair("para1", "value1"),
						new BasicNameValuePair("para2", "value2")),
				DateReturnType.class, listener);
	}
}
