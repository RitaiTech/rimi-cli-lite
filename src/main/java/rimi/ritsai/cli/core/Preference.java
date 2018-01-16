package rimi.ritsai.cli.core;

public interface Preference {

	String getKey();

	String getDescription();

	String getDefaultValue();

	boolean isRequired();

}
