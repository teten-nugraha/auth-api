package id.ten.authapi.exceptions;

import id.ten.authapi.dto.GenericResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(DataNotFoundException.class)
  public GenericResponse<String> handleUserNotFoundException(DataNotFoundException exception) {
    return GenericResponse.error(exception.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public GenericResponse<Map<String, String>> handleInvalidArgument(
      MethodArgumentNotValidException exception) {
    Map<String, String> map = new HashMap<>();
    exception
        .getBindingResult()
        .getFieldErrors()
        .forEach(
            fieldError -> {
              map.put(fieldError.getField(), fieldError.getDefaultMessage());
            });

    return GenericResponse.error(map);
  }
}
