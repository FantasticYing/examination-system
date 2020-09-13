package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.biz.domain.dto.request.UserRegisterRequest;
import cn.edu.jlu.examsystem.biz.service.UserManageService;
import cn.edu.jlu.examsystem.http.common.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangZeying 2020/9/7 0:40
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserManageService userService;

    public UserController(UserManageService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "注册用户", notes = "需要邀请码")
    @PostMapping
    public Response<Long> registerUser(@RequestBody @Validated UserRegisterRequest registerRequest) {
        return Response.success(userService.registerUser(registerRequest));
    }

}
