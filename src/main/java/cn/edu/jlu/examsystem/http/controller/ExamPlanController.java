package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.biz.domain.dto.request.ExamPlanCreateRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.domain.dto.response.ExamPlan;
import cn.edu.jlu.examsystem.biz.domain.dto.response.ExamPlanDetail;
import cn.edu.jlu.examsystem.biz.service.ExamPlanService;
import cn.edu.jlu.examsystem.http.common.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static cn.edu.jlu.examsystem.http.common.HttpConstants.ATTR_USER_AUTH;

/**
 * @author WangZeying 2020/9/18 9:14
 */
@Api(tags = "07-考试计划")
@RestController
@RequestMapping("/api/v1")
public class ExamPlanController {

    private final ExamPlanService examPlanService;

    public ExamPlanController(ExamPlanService examPlanService) {
        this.examPlanService = examPlanService;
    }

    @PostMapping("/teacher/plans")
    @ApiOperation("创建计划")
    public Response<Long> create(@RequestBody @Validated ExamPlanCreateRequest request,
                                 @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(examPlanService.create(request, userInfo));
    }

    @GetMapping("/teacher/plans/{id}")
    @ApiOperation("获取计划详情计划")
    public Response<ExamPlanDetail> getById(@PathVariable Long id) {
        return Response.success(examPlanService.selectByPrimaryKey(id));
    }

    @DeleteMapping("/teacher/plans/{id}")
    @ApiOperation("删除")
    public Response<Boolean> delete(
            @PathVariable Long id,
            @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(examPlanService.deleteById(id, userInfo));
    }

    @GetMapping("/student/plans")
    @ApiOperation("学生选的课的考试")
    public Response<List<ExamPlan>> findByStudentId(
            @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(examPlanService.findByStudentId(userInfo.getId()));
    }

    @GetMapping("/teacher/plans")
    @ApiOperation("老师教的课的考试")
    public Response<List<ExamPlan>> findByTeacherId(
            @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(examPlanService.findByTeacherId(userInfo.getId()));

    }

    @GetMapping("/teacher/records")
    @ApiOperation("需要判的卷子")
    public Response<List<ExamPlan.SimpleExamRecord>> findRecordByTeacherId(
            @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(examPlanService.findRecordByTeacherId(userInfo.getId()));

    }

    @GetMapping("/teacher/course/{courseId}/plans")
    @ApiOperation("某个课程下的考试")
    public Response<List<ExamPlan>> findByCourseId(@PathVariable Long courseId) {
        return Response.success(examPlanService.findByCourseId(courseId));
    }

    @GetMapping("/student/course/{courseId}/plans")
    @ApiOperation("某个课程下的考试")
    public Response<List<ExamPlan>> findRecordByCourseId(
            @PathVariable Long courseId,
            @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(examPlanService.findByCourseId(courseId, userInfo.getId()));
    }
}
