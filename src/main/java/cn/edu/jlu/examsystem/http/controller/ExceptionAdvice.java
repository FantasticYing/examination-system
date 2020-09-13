package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.biz.excepiton.BaseException;
import cn.edu.jlu.examsystem.http.common.HttpConstants;
import cn.edu.jlu.examsystem.http.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

}
