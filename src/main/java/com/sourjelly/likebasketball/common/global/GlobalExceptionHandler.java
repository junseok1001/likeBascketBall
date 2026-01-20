package com.sourjelly.likebasketball.common.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        ErrorCode errorCode = e.getErrorcode();
        log.info(e.getErrorcode().getMessage());

        log.error("CustomException 발생 : {}", errorCode.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                                            .status(errorCode.getStatus())
                                            .message(errorCode.getMessage())
                                            .build();

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex){

        log.info("유효성검사 오류 발생");

        List<ErrorResponse.FieldErrorDetail> errors = ex.getBindingResult()
                .getFieldErrors() // 여기서 스프링이 가진 에러 리스트를 가져옴
                .stream()
                .map(error -> new ErrorResponse.FieldErrorDetail(
                        error.getField(),          // "userId" 뽑기
                        error.getDefaultMessage()   // "아이디는 필수입니다." 뽑기
                ))
                .collect(Collectors.toList());

        ErrorResponse response = ErrorResponse.builder()
                .status(ErrorCode.INVALID_PARAMETER.getStatus())
                .message(ErrorCode.INVALID_PARAMETER.getMessage())
                .fieldErrors(errors)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // 그 외 예상치 못한 모든 예외(Exception)를 잡는 곳 -> Exception.class는 모든 exception들이 잡혀서 log가 어지러워질수도 있음
    // 기준을 잡아야 됌
//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
////        log.error("handleException 발생 : ", e);
//        log.error("handleException 발생 사요 : {}", e.getMessage());
//
//        ErrorResponse response = ErrorResponse.builder()
//                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
//                .message(e.getMessage())
//                .build();
//
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
