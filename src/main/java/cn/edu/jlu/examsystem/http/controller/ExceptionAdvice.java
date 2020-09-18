package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.biz.excepiton.BaseException;
import cn.edu.jlu.examsystem.common.util.ConvertUtils;
import cn.edu.jlu.examsystem.common.util.JsonUtils;
import cn.edu.jlu.examsystem.http.common.HttpConstants;
import cn.edu.jlu.examsystem.http.common.Response;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author WangZeying 2020/9/6 18:01
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    
    @ExceptionHandler(RuntimeException.class)
    public Response<?> handleRuntimeException(RuntimeException e, HttpServletRequest request, HttpServletResponse response) {
        Object logId = request.getAttribute("log_id");
        log.info("{} Exception     ============>>", logId, e);
        response.setIntHeader(HttpConstants.HEADER_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return Response.failure(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(BaseException.class)
    public Response<?> handleBaseException(BaseException e, HttpServletRequest request, HttpServletResponse response) {
        Object logId = request.getAttribute("log_id");
        log.info("{} BaseException ============>>", logId, e);
        response.setIntHeader(HttpConstants.HEADER_STATUS_CODE, e.getCode());
        return Response.failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public Response<?> handleHttpMessageConversionException(HttpMessageConversionException e, HttpServletRequest request, HttpServletResponse response) {
        Object logId = request.getAttribute("log_id");
        log.info("{} BaseException ============>>", logId, e);
        response.setIntHeader(HttpConstants.HEADER_STATUS_CODE, HttpStatus.BAD_REQUEST.value());
        return Response.failure(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(MismatchedInputException.class)
    public Response<?> handleMismatchedInputException(MismatchedInputException e, HttpServletRequest request, HttpServletResponse response) {
        Object logId = request.getAttribute("log_id");
        log.info("{} BaseException ============>>", logId, e);
        response.setIntHeader(HttpConstants.HEADER_STATUS_CODE, HttpStatus.BAD_REQUEST.value());
        return Response.failure(HttpStatus.BAD_REQUEST.value(), "请求格式错误");
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) {
        Object logId = request.getAttribute("log_id");
        log.info("{} NotValidException ============>>", logId, e);
        response.setIntHeader(HttpConstants.HEADER_STATUS_CODE, HttpStatus.BAD_REQUEST.value());
        BindingResult result = e.getBindingResult();
        Map<String, String> errorFieldAndMsg = ConvertUtils.extractMap(
                result.getFieldErrors(),
                FieldError::getField,
                DefaultMessageSourceResolvable::getDefaultMessage);
        return Response.failure(HttpStatus.BAD_REQUEST.value(), null, JsonUtils.toJson(errorFieldAndMsg));
    }
    
}
