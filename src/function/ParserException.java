package function;

public class ParserException extends Exception {
    
    private String message;
    
    public ParserException(String msg) {
        message = msg;
    }
    
    public String getMessage() {
        return message;
    }
    
}
