package ${package}.network;

import java.util.ArrayList;
import android.content.Context;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import ${package}.Application;
import ${package}.PreferencesManager;
import ${package}.R;

public class Environment {

	private String name;
	private String baseURL;
	
	private static ArrayList<Environment> allEnvironments;
	
	private Environment(String name, String baseURL) {
		this.name = name;
		this.baseURL = baseURL;
	}
	
	public static synchronized ArrayList<Environment> allEnvironments() {
		if (allEnvironments == null) {
			allEnvironments = new ArrayList<Environment>();
			
			Context ctx = Application.sharedApplicationContext();
			
			String[] environmentNames = ctx.getResources().getStringArray(R.array.environment_name);
			String[] environmentBaseURLs = ctx.getResources().getStringArray(R.array.environment_base_url);
			
			for (int i = 0; i < environmentNames.length; i++) {
				String name = environmentNames[i];
				String baseURL = environmentBaseURLs[i];
				
				allEnvironments.add(new Environment(name, baseURL));
			}
		}
		
		return allEnvironments;
	}

	public static Environment getEnvironment(final String name) {
		return Iterables.find(allEnvironments(), new Predicate<Environment>() {
			public boolean apply(Environment environment) {
				return environment.name.equals(name);
			}
		});
	}
	
	public static Environment getCurrentEnvironment() {
		String name = PreferencesManager.getSharedPreferences(0).
				getString(PreferencesManager.PREF_CURRENT_ENVIRONMENT, "Staging");
		return getEnvironment(name);
	}

	public String getBaseURL() {
		return baseURL;
	}

	public String getName() {
		return name;
	}
}
