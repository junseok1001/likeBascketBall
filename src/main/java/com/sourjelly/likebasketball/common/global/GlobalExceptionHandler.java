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

//        List<ErrorResponse.FieldErrorDetail> errors = ex.getBindingResult()
//                .getFieldErrors() // 여기서 스프링이 가진 에러 리스트를 가져옴
//                .stream() // 데이터 스트림 형식으로 데이터 넣어 쓸준비
//                .map(error -> new ErrorResponse.FieldErrorDetail(
//                        error.getField(),          // "userId" 뽑기
//                        error.getDefaultMessage()   // "아이디는 필수입니다." 뽑기
//                ))
//                .collect(Collectors.toList());
//        // 400뜨우고 내가 정한 message로 가져오기
//        // 더 알아보기 편하게는 어떻게 하지?? log를 어디다가 찍어야될까?
//
//
//        ErrorResponse response = ErrorResponse.builder()
//                .status(ErrorCode.INVALID_PARAMETER.getStatus())
//                .message(ErrorCode.INVALID_PARAMETER.getMessage())
//                .fieldErrors(errors)
//                .build();

        // 1. 모든 에러 메시지를 하나의 문자열로 합칩니다.
        // 예: "locationName: 공백일 수 없습니다, userId: 필수 입력값입니다"
        String combinedErrorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("\n "));

        // 2. ErrorResponse 생성 시 message에 합쳐진 문자열을 넣습니다.
        // fieldErrors 리스트는 필요 없다면 builder에서 제외하거나 null로 보낼 수 있습니다.
        ErrorResponse response = ErrorResponse.builder()
                .status(ErrorCode.INVALID_PARAMETER.getStatus())
                .message(combinedErrorMessage) // '잘못된 요청...' 대신 실제 에러 내용을 전달
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
