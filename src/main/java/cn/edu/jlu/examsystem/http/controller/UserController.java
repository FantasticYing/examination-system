package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.biz.domain.dto.BatchGet;
import cn.edu.jlu.examsystem.biz.domain.dto.request.UserQueryRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.request.UserRegisterRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.service.UserManageService;
import cn.edu.jlu.examsystem.http.common.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author WangZeying 2020/9/7 0:40
 */
@Api(tags = "02-用户")
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
    
    @ApiOperation(value = "搜索用户", notes = "支持模糊搜索")
    @GetMapping
    public Response<BatchGet<CoreUserInfo>> find(UserQueryRequest request) {
        return Response.success(userService.query(request));
    }
    
    
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{id}")
    public Response<Boolean> delete(@PathVariable Long id) {
        return Response.success(userService.delete(id));
    }
    
}
