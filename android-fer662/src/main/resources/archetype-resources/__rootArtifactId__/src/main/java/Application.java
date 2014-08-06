package ${package};

import android.content.Context;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import com.crittercism.app.Crittercism;

public class Application extends android.app.Application {

	private static Context applicationContext;
	private static Application application;
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
 
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		applicationContext = this.getApplicationContext();
		
		//PreferenceManager.setDefaultValues(applicationContext, R.xml.preference, false);

		//Crittercism.initialize(getApplicationContext(), getResources().getString(R.string.crittercism_app_id));
	}
 
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}
 
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	public static Application sharedApplication() {
		return application;
	}
	
	public static Context sharedApplicationContext() {
		return applicationContext;
	}
}
