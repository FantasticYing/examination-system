package cn.edu.jlu.examsystem.http.interceptor;

import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.http.common.HttpConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static cn.edu.jlu.examsystem.http.common.HttpConstants.ATTR_USER_AUTH;
import static cn.edu.jlu.examsystem.http.common.HttpConstants.HEADER_LOG_ID;

/**
 * @author wangzeying
 */
@Slf4j
@Component
public class AccessLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("start_time", System.currentTimeMillis());
        request.setAttribute("start_time", System.currentTimeMillis());
        CoreUserInfo userInfo = (CoreUserInfo) request.getAttribute(ATTR_USER_AUTH);
        String username = userInfo != null ? userInfo.getUsername() : "[UNKNOWN]";
        String ip = request.getRemoteAddr();
        String uuid = UUID.randomUUID().toString();
        response.setHeader(HEADER_LOG_ID, uuid);
        log.info("{} {} url={}, method={}, param={}, user={}", uuid, ip,  request.getRequestURI(), request.getMethod(), request.getQueryString(), username);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long cost = System.currentTimeMillis() - (long) request.getAttribute("start_time");
        String ip = request.getRemoteAddr();
        Object logId = response.getHeader(HEADER_LOG_ID);
        CoreUserInfo userInfo = (CoreUserInfo) request.getAttribute(ATTR_USER_AUTH);
        String username = userInfo != null ? userInfo.getUsername() : "[UNKNOWN]";
        String code = response.getHeader(HttpConstants.HEADER_STATUS_CODE);
        if (code == null) {
            log.info("{} {} request completed with status code [{}] in {}ms for user {}", logId, ip, response.getStatus(), cost, username);
        } else {
            log.warn("{} {} request failed    with status code [{}] in {}ms for user {}", logId, ip, response.getStatus(), cost, username);
        }
    }
}
