package ${package};

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesManager {

	private static final String EQUINOX_SHARED_PREFERENCES = "${artifactId}";
	
	//Keys
	public static final String PREF_CURRENT_ENVIRONMENT = "current_environment";
	
	public static SharedPreferences getSharedPreferences(int mode) {
		return ${artifactId}Application.sharedApplicationContext().
				getSharedPreferences(EQUINOX_SHARED_PREFERENCES, 0);
	}
	
	public static void registerDefaults() {
	
	}
}
