package rimi.ritsai.cli.exception;

public class CommandNotFoundException extends SystemException {

	private static final long serialVersionUID = 1598974302070622562L;

	public CommandNotFoundException(String name) {
		super("Unkown command : %s", name);
	}

}
