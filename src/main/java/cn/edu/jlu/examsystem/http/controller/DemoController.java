package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.http.common.Response;
import cn.edu.jlu.examsystem.biz.excepiton.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author wangzeying
 */
@Api("测试")
@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @ApiOperation("健康测试")
    @RequestMapping(value = "/healthCheck", method = RequestMethod.GET)
    public Response<?> healthCheck() {
        return Response.success(null);
    }

    @ApiOperation("异常测试")
    @RequestMapping(value = "/exceptionCheck", method = RequestMethod.GET)
    public Response<?> exceptionCheck() {
        throw new BaseException(0, "测试");
    }

    @ApiOperation("时间测试")
    @RequestMapping(value = "/now", method = RequestMethod.GET)
    public Response<LocalDateTime> now() {
        return Response.success(LocalDateTime.now());
    }

}
