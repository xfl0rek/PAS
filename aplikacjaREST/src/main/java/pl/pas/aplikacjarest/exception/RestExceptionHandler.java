package pl.pas.aplikacjarest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

//    @ExceptionHandler(InvalidLoginDataException.class)
//    public ResponseEntity<Map<String, String>> handleInvalidLoginDataException(InvalidLoginDataException ex) {
//        Map<String, String> errorMessages = new HashMap<>();
//
//        // Iterate through the validation errors and add them to the errorMessages map
//        for (ObjectError error : ex.getErrors()) {
//            String fieldName = error.getField();
//            String errorMessage = error.getDefaultMessage();
//            errorMessages.put(fieldName, errorMessage);
//        }
//
//        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
