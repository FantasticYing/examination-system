package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.biz.domain.dto.request.PaperCreateRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.request.PaperPreviewRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.request.PaperQueryRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.domain.paper.Paper;
import cn.edu.jlu.examsystem.biz.domain.paper.PaperPreview;
import cn.edu.jlu.examsystem.biz.service.PaperService;
import cn.edu.jlu.examsystem.http.common.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import static cn.edu.jlu.examsystem.http.common.HttpConstants.ATTR_USER_AUTH;

/**
 * @author WangZeying 2020/9/17 18:22
 */
@Api(tags = "06-试卷")
@RestController
@RequestMapping("/api/v1")
public class PaperController {
    
    private final PaperService paperService;
    
    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }
    
    @PostMapping("/teacher/papers/preview")
    @ApiOperation("预览试卷")
    public Response<PaperPreview> preview(@RequestBody PaperPreviewRequest request) {
        return Response.success(paperService.preview(request));
    }
    
    @PostMapping("/teacher/papers")
    @ApiOperation("创建试卷")
    public Response<Long> create(
            @RequestBody PaperCreateRequest request,
            @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(paperService.create(request, userInfo));
    }
    
    @GetMapping("/teacher/papers/{id}")
    @ApiOperation("打开试卷")
    public Response<Paper> getOne(@PathVariable Long id) {
        return Response.success(paperService.getById(id));
    }
    
    @GetMapping("/student/plans/{planId}/paper")
    @ApiOperation(value = "学生打开次计划的试卷", notes = "学生打开就开始计时（注意id是计划的！！）")
    public Response<Paper> getAndStart(@PathVariable Long planId,
                                       @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(paperService.getAndStart(planId, userInfo));
    }
    
    @GetMapping("/teacher/plans/{planId}/student/{studentId}/paper")
    @ApiOperation(value = "老师获取判题的试卷")
    public Response<Paper> getForJudge(@PathVariable Long planId, @PathVariable Long studentId,
                                       @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(paperService.getForJudge(planId, studentId));
    }

    @GetMapping("/teacher/paper")
    @ApiOperation(value = "查询某科目下的试卷，用于创建考试（计划）")
    public Response<List<Paper>> query(PaperQueryRequest request,
                                      @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(paperService.query(request, userInfo));
    }
}
