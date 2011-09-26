package net.physalis.accesslog.parser;

public class CannotParseException extends Exception {

    public CannotParseException() {
    }

    public CannotParseException(String s) {
        super(s);
    }

    public CannotParseException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CannotParseException(Throwable throwable) {
        super(throwable);
    }
}
