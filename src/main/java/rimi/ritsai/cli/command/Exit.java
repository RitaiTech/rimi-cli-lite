package rimi.ritsai.cli.command;

import rimi.ritsai.cli.core.Command;
import rimi.ritsai.cli.core.Console;
import rimi.ritsai.cli.core.ConsoleCommand;
import rimi.ritsai.cli.exception.SystemError;
import rimi.ritsai.cli.exception.SystemException;

@Command(name = "exit", description = "Exit the shell.")
public class Exit implements ConsoleCommand {

	@Override
	public void run(Console console, String... argument) throws IllegalArgumentException, SystemException {
		throw new SystemError("Exiting shell ...");
	}

}
