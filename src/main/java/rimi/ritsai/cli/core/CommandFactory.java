package rimi.ritsai.cli.core;

import rimi.ritsai.cli.exception.CommandNotFoundException;

public interface CommandFactory {

	ConsoleCommand findCommonad(String... arguments) throws CommandNotFoundException;

}
