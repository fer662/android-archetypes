package ${package}.network.protocol;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

public interface NetworkProtocol {
	
	public StringEntity getParameters(String functionName, JSONObject parameters);
	public String getContentType();
}
