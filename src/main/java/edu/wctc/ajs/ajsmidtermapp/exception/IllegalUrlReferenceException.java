package edu.wctc.ajs.ajsmidtermapp.exception;

/**
 * Illegal URL reference exception class is for when a url reference does not
 * fit the criteria for a url specified. 
 * @author Alyson
 */
public class IllegalUrlReferenceException extends IllegalArgumentException{

    public IllegalUrlReferenceException() {
    }

    public IllegalUrlReferenceException(String s) {
        super(s);
    }

    public IllegalUrlReferenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalUrlReferenceException(Throwable cause) {
        super(cause);
    }
    
}
