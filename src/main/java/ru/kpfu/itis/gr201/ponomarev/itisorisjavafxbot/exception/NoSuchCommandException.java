package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.exception;

public class NoSuchCommandException extends Exception {
    public NoSuchCommandException() {
        super();
    }

    public NoSuchCommandException(String message) {
        super(message);
    }

    public NoSuchCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchCommandException(Throwable cause) {
        super(cause);
    }

    protected NoSuchCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
