package rimi.ritsai.cli.exception;

import rimi.ritsai.cli.util.I18nMessage;

public class SystemException extends RuntimeException {

	private static final long serialVersionUID = 6512055024812191579L;

	private String detailMessage;
	protected I18nMessage i18n = I18nMessage.instance("META-INF/shell/i18n/errors.properties");

	public SystemException(Throwable cause) {
		super(cause);
		this.detailMessage = getLocalizedRootReason();
	}

	public SystemException(String reason, Object... args) {
		super();
		this.detailMessage = i18n.getMessage(reason, args);
	}

	@Override
	public String getMessage() {
		return detailMessage;
	}

	public final String getLocalizedRootReason() {
		return getRootCause(this.getCause());
	}

	public final String getRootCause(Throwable cause) {
		StringBuilder builder = new StringBuilder();
		if (null != cause) {
			String causedBy = i18n.getMessage("Caused by");
			String pattern = String.format("%s : %s - %s", causedBy, cause.getClass().getName(), cause.getLocalizedMessage());
			// TODO: filter and extract the major messages in the StackTrace with the root case class name
			builder.append(String.format(pattern));
		}
		return builder.toString();
	}
}
