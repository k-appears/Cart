package org.checkout.datatransferobject;

public class ResponsePayment implements Response {
    private final Value value;
    private final String message;

    public ResponsePayment(Value value, String message) {
        this.value = value;
        this.message = message;
    }

    public Value getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public enum Value {
        SUCCESS, FAILURE
    }
}
