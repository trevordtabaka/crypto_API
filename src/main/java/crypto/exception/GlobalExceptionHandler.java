package crypto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public @ResponseBody ExceptionResponse handle403(AuthenticationException ae) {
        ExceptionResponse error = new ExceptionResponse();
        error.setMessage(ae.getMessage());
        error.setStatus(HttpStatus.UNAUTHORIZED);
        return error;
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public @ResponseBody ExceptionResponse handleSQLConstraint(SQLIntegrityConstraintViolationException sqlCon) {
        ExceptionResponse error = new ExceptionResponse();
        error.setMessage(sqlCon.getMessage());
        error.setStatus(HttpStatus.ALREADY_REPORTED);
        return error;
    }

}
