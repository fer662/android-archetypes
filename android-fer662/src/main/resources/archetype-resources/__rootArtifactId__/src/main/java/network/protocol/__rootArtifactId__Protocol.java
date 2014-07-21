package ${package}.network.protocol;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

public class ${rootArtifactId}Protocol implements NetworkProtocol {

	public StringEntity getParameters(String functionName, JSONObject parameters) {
		
		try {
			JSONObject jsonParams = new JSONObject();

			JSONObject function = new JSONObject();

			function.put("Name", functionName);
			function.put("Parameters", parameters);

			JSONArray functions = new JSONArray();
			functions.put(function);

			jsonParams.put("Functions", functions);

			return new StringEntity(jsonParams.toString());
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public String getContentType() {
		return "application/json";
	}
}
