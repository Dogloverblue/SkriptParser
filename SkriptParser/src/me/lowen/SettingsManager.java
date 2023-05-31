package me.lowen;

import java.util.prefs.Preferences;

/**
 * A very simple class just to simplify the gettings of setting values
 * values should be set via the {@link me.lowen.gui.SettingsFrame} class
 * @author lowen
 *
 */
public class SettingsManager {

	private static Preferences prefs = Preferences.userRoot();
	
	public static String getValue(String settingKey, String defaultValue) {
		return prefs.get(settingKey, defaultValue);
	}
	

	
	// Hard coded ones, cause the settings count is low

	public static int getAutoParseMillis() {
		return Integer.parseInt(prefs.get("SkParser_parseIntervalMillis", "250"));
	}
	public static boolean isAutoParsingEnabled() {
		return prefs.getBoolean("SkParser_autoParse", true);
	}
}
