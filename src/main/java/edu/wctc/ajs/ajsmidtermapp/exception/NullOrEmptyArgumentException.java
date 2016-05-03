package edu.wctc.ajs.ajsmidtermapp.exception;

/**
 * Custom exception class when checking for null or empty arguments
 * 
 * @author Alyson
 */
public class NullOrEmptyArgumentException extends IllegalArgumentException{

    public NullOrEmptyArgumentException() {
    }

    public NullOrEmptyArgumentException(String s) {
        super(s);
    }

    public NullOrEmptyArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullOrEmptyArgumentException(Throwable cause) {
        super(cause);
    }
    
}
