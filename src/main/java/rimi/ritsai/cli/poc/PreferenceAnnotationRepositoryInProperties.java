package rimi.ritsai.cli.poc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;

import org.springframework.stereotype.Repository;

import rimi.ritsai.cli.util.ResourceUtils;

@Repository
public class PreferenceAnnotationRepositoryInProperties implements PreferenceAnnotationRepository {

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

	@Override
	public String findByName(String key, String defaultValue) {
		return stage.getProperty(key, defaultValue);
	}

	@Override
	public Optional<String> findByName(String key) {
		return Optional.ofNullable(stage.getProperty(key));
	}

	@Override
	public void save(String name, String value) {
		stage.setProperty(name, value);
		saveStage();
	}

	@Override
	public void save(PreferenceAnnotation preference, String value) {
		save(preference.name(), value);
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

}
