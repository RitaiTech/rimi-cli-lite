package rimi.ritsai.cli.poc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface PreferenceAnnotationRepository {

	void save(PreferenceAnnotation preference, String value);

	void save(String anme, String value);

	String findByName(String name, String defaultValue);

	Optional<String> findByName(String name);

	default Map<PreferenceAnnotation, String> findAll(Class<?> beanClass) {
		Map<PreferenceAnnotation, String> properties = new HashMap<PreferenceAnnotation, String>();
		PreferenceAnnotation[] preferences = beanClass.getAnnotationsByType(PreferenceAnnotation.class);
		for (PreferenceAnnotation preference : preferences) {
			String value = findByName(preference.name(), preference.defaultValue());
			properties.put(preference, value);
		}
		return properties;
	}

}
