package pl.pas.aplikacjamvc.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(AppException.class)
    public String handleException(AppException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("statusCode", ex.getStatusCode());
        return "error";
    }
}
