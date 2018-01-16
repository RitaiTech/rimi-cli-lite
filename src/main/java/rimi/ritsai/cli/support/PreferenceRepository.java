package rimi.ritsai.cli.support;

import java.util.Map;
import java.util.Optional;

import rimi.ritsai.cli.core.Preference;

public interface PreferenceRepository {

	void saveValue(Preference preference, String value);

	void saveValue(String key, String value);

	String findValue(String key, String defaultValue);

	Optional<String> findValue(String key);
	
	String findValue(Preference preference);

	Map<Preference, String> findValues(Preference[] preferences);

}
