package com.redscooter.exceptions.generic;

public class FlowControlException extends RuntimeException {
    public FlowControlException() {
        super("This exception can be ignored as it is thrown manually to control the flow of the code.");
    }
}
