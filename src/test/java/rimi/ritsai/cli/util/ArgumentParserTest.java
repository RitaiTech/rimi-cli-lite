package rimi.ritsai.cli.util;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

public class ArgumentParserTest {

	@Test
	public void givenANormalCommandLineThenParseParametersCorrectly() {
		SoftAssertions assertion = new SoftAssertions();
		String commandLine = "log snap A BeforeSend";
		String[] arguments = ArgumentParser.parse(commandLine);
		assertion.assertThat(arguments.length).as("arguments number").isEqualTo(4);
		assertion.assertThat(arguments[0]).as("Command name").isEqualTo("log");
		assertion.assertThat(arguments[1]).as("Sub command").isEqualTo("snap");
		assertion.assertThat(arguments[2]).as("Parameter 1").isEqualTo("A");
		assertion.assertThat(arguments[3]).as("Parameter 2").isEqualTo("BeforeSend");
		assertion.assertAll();
	}

	@Test
	public void givenACommandLineWithDoubleQuoteThenParseThenAsOneParameter() {
		SoftAssertions assertion = new SoftAssertions();
		String commandLine = "log snap A \"Before send message\"";
		String[] arguments = ArgumentParser.parse(commandLine);
		assertion.assertThat(arguments.length).as("arguments number").isEqualTo(4);
		assertion.assertThat(arguments[0]).as("Command name").isEqualTo("log");
		assertion.assertThat(arguments[1]).as("Sub command").isEqualTo("snap");
		assertion.assertThat(arguments[2]).as("Parameter 1").isEqualTo("A");
		assertion.assertThat(arguments[3]).as("Parameter 2").isEqualTo("Before send message");
		assertion.assertAll();
	}

	@Test
	public void givenACommandLineWithSingleQuoteThenParseThenAsOneParameter() {
		SoftAssertions assertion = new SoftAssertions();
		String commandLine = "log snap A \'Before send \"message'";
		String[] arguments = ArgumentParser.parse(commandLine);
		assertion.assertThat(arguments.length).as("arguments number").isEqualTo(4);
		assertion.assertThat(arguments[0]).as("Command name").isEqualTo("log");
		assertion.assertThat(arguments[1]).as("Sub command").isEqualTo("snap");
		assertion.assertThat(arguments[2]).as("Parameter 1").isEqualTo("A");
		assertion.assertThat(arguments[3]).as("Parameter 2").isEqualTo("Before send \"message");
		assertion.assertAll();
	}

	@Test
	public void givenACommandLineWithMultipleQuotesThenParseParametersCorrectly() {
		SoftAssertions assertion = new SoftAssertions();
		String commandLine = "log snap A \"Before send message\" \"This is parameter 3 \"";
		String[] arguments = ArgumentParser.parse(commandLine);
		assertion.assertThat(arguments.length).as("arguments number").isEqualTo(5);
		assertion.assertThat(arguments[0]).as("Command name").isEqualTo("log");
		assertion.assertThat(arguments[1]).as("Sub command").isEqualTo("snap");
		assertion.assertThat(arguments[2]).as("Parameter 1").isEqualTo("A");
		assertion.assertThat(arguments[3]).as("Parameter 2").isEqualTo("Before send message");
		assertion.assertThat(arguments[4]).as("Parameter 3").isEqualTo("This is parameter 3 ");
		assertion.assertAll();
	}
}
