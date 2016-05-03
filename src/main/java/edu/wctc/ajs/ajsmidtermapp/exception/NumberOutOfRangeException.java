package edu.wctc.ajs.ajsmidtermapp.exception;

/**
 * Number out of range exception class uses index out of bounds exception
 * when a number is outside of the range intended. 
 * @author Alyson
 */
public class NumberOutOfRangeException extends IndexOutOfBoundsException {

    public NumberOutOfRangeException() {
    }

    public NumberOutOfRangeException(String s) {
        super(s);
    }
    
    
    
}
