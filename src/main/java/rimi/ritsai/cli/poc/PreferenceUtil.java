package rimi.ritsai.cli.poc;

import java.util.Map;

import org.assertj.core.api.SoftAssertions;

import rimi.ritsai.cli.exception.SystemException;

public final class PreferenceUtil {

	public static void validate(Map<PreferenceAnnotation, String> peferences) throws SystemException {
		SoftAssertions asserts = new SoftAssertions();
		peferences.forEach((preference, value) -> {
			if (preference.required()) {
				asserts.assertThat(value).as(preference.name()).isNotBlank();
			}
		});
		try {
			asserts.assertAll();
		} catch (Exception e) {
			throw new SystemException(e);
		}

	}

}
