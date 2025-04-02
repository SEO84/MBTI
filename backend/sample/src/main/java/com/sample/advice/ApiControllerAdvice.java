package com.sample.advice;

import com.sample.advice.error.DefaultAuthenticationException;
import com.sample.advice.error.DefaultException;
import com.sample.advice.error.DefaultNullPointerException;
import com.sample.advice.error.InvalidParameterException;
import com.sample.advice.payload.ErrorCode;
import com.sample.advice.payload.ErrorResponse;
import com.sample.payload.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ApiControllerAdvice {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .code("HTTP_METHOD_NOT_ALLOWED")
                .clazz(e.getMethod())
                .message(e.getMessage())
                .build();
        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code("VALIDATION_ERROR")
                .clazz(e.getBindingResult().getObjectName())
                .message("입력값 검증에 실패하였습니다.")
                .fieldErrors(e.getFieldErrors())
                .build();
        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<?> handleInvalidParameterException(InvalidParameterException e) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code("INVALID_PARAMETER")
                .clazz(e.getErrors().getObjectName())
                .message(e.getMessage())
                .fieldErrors(e.getFieldErrors())
                .build();
        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DefaultException.class)
    protected ResponseEntity<?> handleDefaultException(DefaultException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(e.getMessage())
                .build();
        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception e) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code("INTERNAL_SERVER_ERROR")
                .message(e.getMessage())
                .build();
        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .code("AUTHENTICATION_ERROR")
                .message(e.getMessage())
                .build();
        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DefaultAuthenticationException.class)
    protected ResponseEntity<?> handleCustomAuthenticationException(DefaultAuthenticationException e) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .code("AUTHENTICATION_ERROR")
                .message(e.getMessage())
                .build();
        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DefaultNullPointerException.class)
    protected ResponseEntity<?> handleNullPointerException(DefaultNullPointerException e) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code("NULL_POINTER")
                .message(e.getMessage())
                .build();
        ApiResponse apiResponse = ApiResponse.builder().check(false).information(response).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
