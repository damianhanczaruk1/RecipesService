package recipes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

@ControllerAdvice
public class CustomErrorHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException(ConstraintViolationException exception,
                                                   ServletWebRequest webRequest) throws IOException {
        webRequest.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public static class NoParamsException extends RuntimeException {
        public NoParamsException(String cause) {
            super(cause);
        }

    }
}
