package rimi.ritsai.cli.core;

public interface Console {

	String prompt(String message, Object... args);

	void println(String message, Object... args);

}
