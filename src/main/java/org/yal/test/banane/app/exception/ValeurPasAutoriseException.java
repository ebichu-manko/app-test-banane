package org.yal.test.banane.app.exception;

/**
 * The Class ValeurPasAutoriseException.
 * 
 * 9 sept. 2020
 *
 * @author stephane_huette
 */
public class ValeurPasAutoriseException extends RuntimeException{

	/**
	 * Instantiates a new tache factory exception.
	 */
	public ValeurPasAutoriseException() {
		super();
	}

	/**
	 * Instantiates a new tache factory exception.
	 *
	 * @param message            the message
	 * @param cause              the cause
	 * @param enableSuppression  the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public ValeurPasAutoriseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Instantiates a new tache factory exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public ValeurPasAutoriseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new tache factory exception.
	 *
	 * @param message the message
	 */
	public ValeurPasAutoriseException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new tache factory exception.
	 *
	 * @param cause the cause
	 */
	public ValeurPasAutoriseException(Throwable cause) {
		super(cause);
	}

}