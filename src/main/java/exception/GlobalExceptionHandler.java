package exception;

import business.dto.responses.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Genel Runtime hatalarını (Veri bulunamadı, yetki yok vb.) yakalar
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
        ErrorResponse error = new ErrorResponse(
                exception.getMessage(), 
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Süresi dolmuş token hatalarını yakalar
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException exception) {
        ErrorResponse error = new ErrorResponse(
                "Oturum süreniz dolmuş. Lütfen tekrar giriş yapın.", 
                HttpStatus.UNAUTHORIZED.value()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> handleValidationExceptions(
        org.springframework.web.bind.MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((org.springframework.validation.FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
    });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
}
}