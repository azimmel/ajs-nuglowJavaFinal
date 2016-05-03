package edu.wctc.ajs.ajsmidtermapp.exception;

/**
 * Custom exception class to handle multiple data access exceptions.
 * Can combine multiple exceptions into one overview exception of data
 * access exceptions when dealing with database information. 
 * ***Side note: Jim, I just used the class name, I promise I generated the
 * constructors myself. Created the class, extended Exception, right clicked, 
 * went to constructors, then checked all of them. Ok? ok!
 * @author Alyson
 */
public class DataAccessException extends Exception{

    public DataAccessException() {
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }

    public DataAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
}
