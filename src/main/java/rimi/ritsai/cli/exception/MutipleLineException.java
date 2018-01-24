package rimi.ritsai.cli.exception;

import java.util.ArrayList;
import java.util.List;

public class MutipleLineException extends SystemException {

	private static final long serialVersionUID = 7471062354944350657L;

	public static MutipleLineException of(String title, Object... args) {
		return new MutipleLineException(title, args);
	}

	private List<String> errors = new ArrayList<String>();

	private MutipleLineException(String title, Object... args) {
		super(title, args);
	}

	public MutipleLineException add(String reason, Object... args) {
		errors.add(i18n.getMessage(reason, args));
		return this;
	}

	public MutipleLineException add(Throwable cause, String reason, Object... args) {
		String rootCause = getRootCause(cause);
		errors.add(i18n.getMessage(reason, args) + rootCause);
		return this;
	}

	public MutipleLineException add(Throwable cause) {
		errors.add(getRootCause(cause));
		return this;
	}

	public void throwIfHasError() throws MutipleLineException {
		if (!errors.isEmpty()) {
			throw this;
		}
	}

	@Override
	public String getMessage() {
		StringBuilder builder = new StringBuilder();
		errors.forEach(message -> {
			builder.append(message).append('\n');
		});
		return builder.toString();
	}

}
