package rimi.ritsai.cli.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import rimi.ritsai.cli.core.Preference;
import rimi.ritsai.cli.exception.SystemException;
import rimi.ritsai.cli.util.ResourceUtils;

@Repository
public class PreferenceRepositoryInProperties implements PreferenceRepository {

	private static final String PREF_FILE = "data/preference.properties";
	private static Properties stage = new Properties();

	static {
		InputStream inputSteam = null;
		try {
			inputSteam = ResourceUtils.loadResource(PREF_FILE, Locale.getDefault());
			stage.load(inputSteam);
		} catch (Exception e) {
		} finally {
			ResourceUtils.close(inputSteam);
		}
	}

	private void saveStage() {
		OutputStream out = null;
		try {
			File propFile = new File(PREF_FILE);
			if (!propFile.getParentFile().exists()) {
				propFile.getParentFile().mkdirs();
			}
			out = new FileOutputStream(propFile);
			stage.store(out, "");
			out.flush();
		} catch (IOException e) {
		} finally {
			ResourceUtils.close(out);
		}

	}

	@Override
	public void saveValue(Preference preference, String value) {
		saveValue(preference.getKey(), value);
	}

	@Override
	public void saveValue(String key, String value) {
		stage.setProperty(key, value);
		saveStage();
	}

	@Override
	public String findValue(String key, String defaultValue) {
		return findValue(key).orElse(defaultValue);
	}

	@Override
	public Optional<String> findValue(String key) {
		return Optional.ofNullable(stage.getProperty(key));
	}

	@Override
	public String findValue(Preference preference) {
		Optional<String> value = findValue(preference.getKey());
		if (preference.isRequired() && !value.isPresent()) {
			throw new SystemException("Preference '%s' is not configured yet", preference.getKey());
		}
		return findValue(preference.getKey(), preference.getDefaultValue());
	}

	@Override
	public Map<Preference, String> findValues(Preference[] preferences) {
		List<String> errors = new ArrayList<String>();
		Map<Preference, String> values = new HashMap<Preference, String>();
		for (Preference pref : preferences) {
			try {
				String value = findValue(pref);
				values.put(pref, value);
			} catch (SystemException e) {
				errors.add(e.getLocalizedMessage());
			}
		}
		if (!errors.isEmpty()) {
			throw new SystemException(errors.stream().collect(Collectors.joining("\n")));
		}
		return values;
	}

}
