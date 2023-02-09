package shop.mtcoding.blog1.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blog1.dto.ResponseDto;
import shop.mtcoding.blog1.handler.ex.CustomApiException;
import shop.mtcoding.blog1.handler.ex.CustomException;
import shop.mtcoding.blog1.util.Script;

@RestControllerAdvice
public class CustomExceptionHanlder {

    // NullPointException <- RuntimeException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomException e) {
        String responseBody = Script.back(e.getMessage());
        return new ResponseEntity<>(responseBody, e.getStatus());
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> customApiException(CustomApiException e) {
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), null), e.getStatus());
    }
}
