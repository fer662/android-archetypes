package ${package};

import android.content.SharedPreferences;

public class PreferencesManager {

	private static final String SHARED_PREFERENCES = "${artifactId}";
	
	//Keys
	public static final String PREF_CURRENT_ENVIRONMENT = "current_environment";
	
	public static SharedPreferences getSharedPreferences(int mode) {
		return Application.sharedApplicationContext().
				getSharedPreferences(SHARED_PREFERENCES, 0);
	}
	
	public static void registerDefaults() {
	
	}
}
