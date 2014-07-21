package ${package}.util;

import java.util.ArrayList;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

public class Serialization {

	private static ObjectMapper objectMapper = new ObjectMapper();
	private static TypeFactory typeFactory = TypeFactory.defaultInstance();
	
	public static <E> E mapObject(JsonNode rootNode, Class<E> c) {
		try {
			return objectMapper.readValue(rootNode, c);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static <E> ArrayList<E> mapArray(JsonNode rootNode, Class<E> c) {
		ArrayList<E> arrayList = null;
		try {
			arrayList = objectMapper.readValue(rootNode, typeFactory.constructCollectionType(ArrayList.class, c));
		} catch (Exception e) {
		}
		return arrayList;
	}
	
	public static JsonNode deserialize(String s) {
		try {
			return objectMapper.readValue(s, JsonNode.class);
		} catch (Exception e) {
			return null;
		}
	}
}
