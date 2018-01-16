package rimi.ritsai.cli.exception;

public class SystemError extends SystemException {

	private static final long serialVersionUID = 5492645855173601307L;

	public SystemError(String reason, Object... args) {
		super(reason, args);
	}

}
