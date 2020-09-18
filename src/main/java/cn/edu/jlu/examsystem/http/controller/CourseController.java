package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.biz.domain.dto.BatchGet;
import cn.edu.jlu.examsystem.biz.domain.dto.request.CourseCreateRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.request.CourseQueryRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CourseDetail;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CourseSummary;
import cn.edu.jlu.examsystem.biz.service.CourseService;
import cn.edu.jlu.examsystem.http.common.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static cn.edu.jlu.examsystem.http.common.HttpConstants.ATTR_USER_AUTH;

/**
 * @author WangZeying 2020/9/13 13:44
 */
@Api(tags = "04-课程")
@RestController
@RequestMapping("/api/v1")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/teacher/courses")
    @ApiOperation("创建课程")
    public Response<Long> createCourse(
            @Validated @RequestBody CourseCreateRequest request,
            @ApiIgnore @RequestAttribute(ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(courseService.createCourse(request, userInfo));
    }

    @GetMapping("/courses")
    @ApiOperation("查询课程")
    public Response<BatchGet<CourseSummary>> queryCourse(
            CourseQueryRequest request,
            @ApiIgnore  @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(courseService.query(request, userInfo));
    }

    @GetMapping("/courses/{id}")
    public Response<CourseDetail> getMyCourse(@PathVariable Long id) {
        return Response.success(courseService.getDetailById(id));
    }

    @DeleteMapping("/teacher/courses/{id}")
    public Response<Boolean> deleteCourse(
            @PathVariable Long id,
            @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(courseService.deleteCourse(id, userInfo));
    }

    @PostMapping("/student/courses")
    @ApiOperation("选课")
    public Response<Integer> chooseCourse(
            @RequestParam Long courseId,
            @ApiIgnore @RequestAttribute(ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(courseService.chooseCourse(courseId, userInfo));
    }

    @DeleteMapping("/student/courses")
    @ApiOperation("退课")
    public Response<Integer> dropCourse(
            @RequestParam Long courseId,
            @ApiIgnore @RequestAttribute(ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(courseService.dropStudentCourse(courseId, userInfo));
    }

}
