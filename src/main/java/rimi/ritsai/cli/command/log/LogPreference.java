package rimi.ritsai.cli.command.log;

import org.springframework.beans.factory.annotation.Autowired;

import rimi.ritsai.cli.core.Preference;
import rimi.ritsai.cli.exception.SystemException;
import rimi.ritsai.cli.support.PreferenceRepository;

public enum LogPreference implements Preference {

	LOGS_DIR("log.dir.monitor", "Where log files are", "", true), DIFF_DIR("log.dir.outputs", "Where diff output", "", true),;

	private String key;
	private String description;
	private String defaultValue;
	private boolean required = false;

	@Autowired
	private static PreferenceRepository repository;

	private LogPreference(String key, String description, String defaultValue, boolean required) {
		this.key = key;
		this.description = description;
		this.defaultValue = defaultValue;
		this.required = required;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	public static void assertAll() throws SystemException {
		Preference[] preferences = LogPreference.values();
		for (Preference pref : preferences) {
			if (pref.isRequired()) {
				repository.findValue(pref.getKey()).orElseThrow(() -> {
					throw new SystemException("", pref.getKey());
				});
			}
		}
	}
}
