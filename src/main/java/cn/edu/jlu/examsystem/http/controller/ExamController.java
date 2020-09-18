package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.biz.domain.dto.request.CheckRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.request.DraftUpdateRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.service.ExamService;
import cn.edu.jlu.examsystem.http.common.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

import static cn.edu.jlu.examsystem.http.common.HttpConstants.ATTR_USER_AUTH;

/**
 * @author WangZeying 2020/9/18 15:45
 */
@Api(tags = "08-答卷、判题")
@RestController
@RequestMapping("/api/v1")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/student/exam/draft")
    @ApiOperation("保存草稿")
    public Response<Integer> saveDraft(@RequestBody @Validated DraftUpdateRequest request,
                                       @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(examService.saveDraft(request, userInfo));
    }

    @PostMapping("/student/exam")
    @ApiOperation("交卷")
    public Response<Integer> submit(@RequestBody @Validated DraftUpdateRequest request,
                                       @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(examService.submit(request, userInfo));
    }

    @PostMapping("/teacher/records")
    @ApiOperation("判卷")
    public Response<Integer> judge(@RequestBody CheckRequest request,
                                   @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(examService.judge(request, userInfo));
    }

    @GetMapping("/teacher/plans/{id}/stats")
    @ApiOperation("判卷")
    public Response<Map<String,Integer>> stats(@PathVariable Long id,
                               @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(examService.getStats(id));
    }

}
