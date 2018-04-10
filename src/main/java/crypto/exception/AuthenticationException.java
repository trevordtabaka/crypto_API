package crypto.exception;

public class AuthenticationException extends Exception {

    public AuthenticationException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "AuthenticationException{}";
    }
}
