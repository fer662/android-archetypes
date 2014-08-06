package ${package}.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class POJOParameterSerializer extends ParametersSerializer {

	private Object mPojo;
	
	private static ObjectMapper sObjectMapper;
	
	protected static synchronized ObjectMapper getObjectMapper() {
		if (sObjectMapper == null) {
			sObjectMapper = new ObjectMapper();
		}
		return sObjectMapper;
	}
	@Override
	public byte[] serialize() {
		try{
			return getObjectMapper().writeValueAsBytes(mPojo);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}
	
	public POJOParameterSerializer(Object pojo) {
		mPojo = pojo;
	}
}
