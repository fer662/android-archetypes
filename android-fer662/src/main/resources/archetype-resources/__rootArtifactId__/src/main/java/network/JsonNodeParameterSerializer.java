package ${package}.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonNodeParameterSerializer extends ParametersSerializer {

	private JsonNode mJsonNode;
	
	private static ObjectMapper sObjectMapper;
	
	protected static synchronized ObjectMapper getObjectMapper() {
		if (sObjectMapper == null) {
			sObjectMapper = new ObjectMapper();
		}
		return sObjectMapper;
	}
	
	public JsonNodeParameterSerializer(JsonNode jsonNode) {
		mJsonNode = jsonNode;
	}
	
	@Override
	public byte[] serialize() {
		try {
			return getObjectMapper().writeValueAsBytes(mJsonNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}
}
