package it.reply.fantabosco.utils;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 153909672002783945L;

	public ValidationException(String validationErrorMessage) {
		super(validationErrorMessage);
	}

}
