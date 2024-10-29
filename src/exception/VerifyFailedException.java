package exception;

public class VerifyFailedException extends Exception{

    public VerifyFailedException(String message) {
        super(message);
    }
    
    public VerifyFailedException(Throwable cause){
        super(cause);
    }
}
