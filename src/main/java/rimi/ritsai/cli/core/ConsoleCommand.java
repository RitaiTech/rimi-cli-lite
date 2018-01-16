package rimi.ritsai.cli.core;

import rimi.ritsai.cli.exception.SystemException;

public interface ConsoleCommand {

	void run(Console console, String... arguments) throws SystemException;

}
