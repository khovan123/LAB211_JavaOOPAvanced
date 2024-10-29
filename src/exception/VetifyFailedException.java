package exception;

public class VetifyFailedException extends Exception{

    public VetifyFailedException(String message) {
        super(message);
    }
    
    public VetifyFailedException(Throwable cause){
        super(cause);
    }
}
