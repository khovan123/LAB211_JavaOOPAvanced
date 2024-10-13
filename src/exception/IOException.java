package exception;

public class IOException extends Exception {

    public IOException() {
        super();
    }

    public IOException(String message) {
        super(message);
    }

    public IOException(Throwable cause) {
        super(cause);
    }

    public IOException(String message, Throwable cause) {
        super(message, cause);
    }
}
