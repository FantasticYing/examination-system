package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.biz.domain.dto.request.AuthRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.service.AuthService;
import cn.edu.jlu.examsystem.http.common.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

import static cn.edu.jlu.examsystem.http.common.HttpConstants.SESSION_ID;

/**
 * @author WangZeying 2020/9/6 12:12
 */
@Api(tags = "认证")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation(value = "登录", notes = "登录并将session id存入Cookie")
    @PostMapping
    public Response<String> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        String sessionId = authService.createAuth(authRequest);
        Cookie cookie = new Cookie("sessionid", sessionId);
        cookie.setMaxAge(Math.toIntExact(Duration.ofDays(3).getSeconds()));
        response.addCookie(cookie);
        return Response.success(sessionId);
    }

    @GetMapping
    @ApiOperation(value = "获取认证信息", notes = "登录并将session id存入Cookie")
    public Response<CoreUserInfo> getAuthInfo(@ApiIgnore @CookieValue(value = SESSION_ID, required = false) String sessionid) {
        return Response.success(authService.getAuth(sessionid));
    }

    @DeleteMapping
    @ApiOperation(value = "退出，删除凭证")
    public Response<Boolean> logout(@CookieValue(SESSION_ID) String sessionid, HttpServletRequest request) {
        return Response.success(authService.expireAuth(sessionid));
    }

}
