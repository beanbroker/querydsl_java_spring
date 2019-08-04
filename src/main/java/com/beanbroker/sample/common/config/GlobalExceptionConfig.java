package com.beanbroker.sample.common.config;

import com.beanbroker.sample.common.domain.ErrorResponse;
import com.beanbroker.sample.exception.BeanbrokerException;
import com.beanbroker.sample.util.JsonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionConfig {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionConfig.class);
    private static final String UNKNOW_ERROR = "UNKNOW_ERROR";
    private static final String NO_HANDLER_FOUND_EXCEPTION = "존재하지 않는 API 경로입니다.";
    private static final String API_EXCEPTION_DEFAULT = "처리 할 수 없는 요청입니다. 담당자에게 문의해주세요.";
    private static final String METHOD_ARGUMENT_NOT_VALID_EXCEPTION = "유효성 검사에 실패했습니다.";
    private static final String HTTP_MESSAGE_NOT_READABLE_EXCEPTION = "처리 할 수 없는 HTTP 요청입니다.";
    private static final String UNEXPECTED_TYPE_EXCEPTION = "처리 할 수 없는 타입의 데이터가 포함되어 있습니다.";

    private final JsonHelper jsonHelper;

    public GlobalExceptionConfig(JsonHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }


    // 요청 결로없을 경우
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ErrorResponse> handleNoHandler(HttpServletRequest req,
                                                               NoHandlerFoundException exception) {

        return error(exception, HttpStatus.NOT_FOUND, NO_HANDLER_FOUND_EXCEPTION);
    }


    // 벨리데이션 비정상
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorResponse> handleValidation(HttpServletRequest req,
                                                                NoHandlerFoundException exception) {

        return error(exception, HttpStatus.BAD_REQUEST, METHOD_ARGUMENT_NOT_VALID_EXCEPTION);
    }

    // 벨리데이션 비정상 타입
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorResponse> handleValidationType(HttpServletRequest req,
                                                                    NoHandlerFoundException exception) {

        return error(exception, HttpStatus.BAD_REQUEST, UNEXPECTED_TYPE_EXCEPTION);
    }


    // 컨트롤러에 매핑되는 값 중 @PathVariable / @ReqeustParam / @ReqeustHeader에 대한 Validation Constraint 위반 유무에 대한 에러 핸들링
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorResponse> handleConstraintViolation(HttpServletRequest req,
                                                                         NoHandlerFoundException exception) {

        return error(exception, HttpStatus.BAD_REQUEST, METHOD_ARGUMENT_NOT_VALID_EXCEPTION);
    }


    // 일반적인 데이터 바인딩 실패 시에 대한 에러 핸들링
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorResponse> handleBindException(HttpServletRequest req,
                                                                   NoHandlerFoundException exception) {

        return error(exception, HttpStatus.BAD_REQUEST, API_EXCEPTION_DEFAULT);
    }


    // 읽을수 없는 http메시지
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorResponse> handleNotReadable(HttpServletRequest req,
                                                                 NoHandlerFoundException exception) {

        return error(exception, HttpStatus.BAD_REQUEST, HTTP_MESSAGE_NOT_READABLE_EXCEPTION);
    }

    // Spring Security Authorization Denied Handler
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseEntity<ErrorResponse> handleAccessDeniedException(HttpServletRequest req,
                                                                           NoHandlerFoundException exception) {

        return error(exception, HttpStatus.UNAUTHORIZED, "접근 거부");
    }


    // 내부 예측가능한에러
    @ExceptionHandler(BeanbrokerException.class)
    public final ResponseEntity<ErrorResponse> hadleCheckedExceptionError(HttpServletRequest httpRequest,
                                                                  BeanbrokerException exception) {

        HttpStatus httpStatus = HttpStatus.valueOf(exception.getStatusCode());

        int errorCode = exception.getErrorCode();
        String errorMessage = exception.getErrorMessage();
        String detailMessage = exception.getDetailMessage();
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        logger.error("checked Exception handling {} -> {}", path, exception.getClass().getSimpleName());


        ErrorResponse errorResponse = new ErrorResponse(httpStatus.name(), errorMessage, errorCode, path, detailMessage);
        errorResponse.setSuccess(false);
        logger.trace("HTTP Response [{} {}, {}]", httpStatus.value(), httpStatus.name(), jsonHelper.getJsonString(errorResponse));
        return new ResponseEntity<>(errorResponse, httpStatus);
    }


    // 진자 모르는 에러
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ErrorResponse> hadleRunTime(HttpServletRequest req, Exception exception) {

        return error(exception, HttpStatus.INTERNAL_SERVER_ERROR, UNKNOW_ERROR);

    }

    private ResponseEntity<ErrorResponse> error(Exception e, HttpStatus httpStatus, String deatailMsg) {

        return new ResponseEntity<>(new ErrorResponse(e.getClass().getSimpleName(), e.getMessage(), httpStatus.value(), deatailMsg),
                httpStatus);
    }


    private ResponseEntity<ErrorResponse> expectedError(int code, String message, String detailMessage, String path, HttpStatus httpStatus) {

        ErrorResponse errorResponse = new ErrorResponse(httpStatus.name(), message, code, path, detailMessage);
        errorResponse.setSuccess(false);
        logger.trace("HTTP Response [{} {}, {}]", httpStatus.value(), httpStatus.name(), jsonHelper.getJsonString(errorResponse));
        return new ResponseEntity<>(errorResponse, httpStatus);


    }


}
