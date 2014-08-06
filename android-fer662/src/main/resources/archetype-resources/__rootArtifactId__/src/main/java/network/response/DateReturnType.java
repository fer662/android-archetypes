package ${package}.network.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DateReturnType {

	public long id;
	public String name;
	
	@JsonProperty("full_name")
	private String fullName;
}