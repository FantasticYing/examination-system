package cn.edu.jlu.examsystem.http.interceptor;

import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.enums.RoleEnum;
import cn.edu.jlu.examsystem.biz.redis.UserAuthRedisClient;
import cn.edu.jlu.examsystem.common.util.JsonUtils;
import cn.edu.jlu.examsystem.http.common.Response;
import cn.edu.jlu.examsystem.http.config.AuthConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static cn.edu.jlu.examsystem.http.common.HttpConstants.SESSION_ID;
import static cn.edu.jlu.examsystem.http.common.HttpConstants.ATTR_USER_AUTH;

/**
 * @author wangzeying
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthConfigProperties authConfigProperties;
    private final UserAuthRedisClient authRedisClient;
    private static final String UNAUTHORIZED_RESPONSE = JsonUtils.toJson(Response.failure(HttpStatus.UNAUTHORIZED));
    private static final String FORBIDDEN_RESPONSE = JsonUtils.toJson(Response.failure(HttpStatus.FORBIDDEN));

    public AuthInterceptor(AuthConfigProperties authConfigProperties, RedisTemplate<String, Object> redisTemplate, UserAuthRedisClient authRedisClient) {
        this.authConfigProperties = authConfigProperties;
        this.authRedisClient = authRedisClient;

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        String sessionId = getSessionId(request);

        CoreUserInfo userInfo = authRedisClient.getAuth(sessionId);
        // Caution!!!
        request.setAttribute(ATTR_USER_AUTH, userInfo);

        List<String> nonLoginPrefixes = authConfigProperties.getNonLoginPrefixes();

        // 无需权限即可访问的接口
        if (allowAccess(uri, nonLoginPrefixes)) {
            return true;
        }

        // 没有登录凭证
        if (sessionId == null) {
            log.warn("request blocked because of missing token");
            setDeniedResponse(response, UNAUTHORIZED_RESPONSE);
            return false;
        }

        // 凭证失效
        if (userInfo == null) {
            log.warn("request blocked because of expired token");
            setDeniedResponse(response, UNAUTHORIZED_RESPONSE);
            return false;
        }

        Short roleId = userInfo.getRoleId();
        Map<RoleEnum, List<String>> roleToPrefixes = authConfigProperties.getRoleToPrefixes();
        RoleEnum roleEnum = RoleEnum.fromId(roleId);
        List<String> allowedPrefixes = roleToPrefixes.get(roleEnum);

        // 无访问权限, 出现这种情况可能是前端有问题
        if (!allowAccess(uri, allowedPrefixes)) {
            log.warn("request blocked because user [{}]'s role id [{}] don't have permission for uri [{}]", userInfo.getRoleId(), userInfo.getUsername(), uri);
            setDeniedResponse(response, FORBIDDEN_RESPONSE);
            return false;
        }

        return true;
    }

    private String getSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        Cookie sessionCookie =
                IterableUtils.find(
                        !Objects.isNull(cookies) ? Arrays.asList(cookies) : Collections.emptyList(),
                        cookie -> Objects.equals(cookie.getName(), SESSION_ID));

        return sessionCookie != null ? sessionCookie.getValue() : null;
    }

    private boolean allowAccess(String uri, List<String> prefixes) {
        return IterableUtils.matchesAny(prefixes, prefix -> StringUtils.startsWith(uri, prefix));
    }


    private void setDeniedResponse(HttpServletResponse response, String content) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().append(UNAUTHORIZED_RESPONSE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
