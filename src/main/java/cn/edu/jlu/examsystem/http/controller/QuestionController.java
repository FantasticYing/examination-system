package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.biz.domain.dto.BatchGet;
import cn.edu.jlu.examsystem.biz.domain.dto.request.QuestionCreateRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.request.QuestionQueryRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.domain.dto.response.DescriptionVO;
import cn.edu.jlu.examsystem.biz.domain.dto.response.QuestionVO;
import cn.edu.jlu.examsystem.biz.service.QuestionService;
import cn.edu.jlu.examsystem.http.common.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static cn.edu.jlu.examsystem.http.common.HttpConstants.ATTR_USER_AUTH;

/**
 * @author WangZeying 2020/9/8 18:12
 */
@Api(tags = "题库管理")
@RestController
@RequestMapping("/api/v1")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/teacher/questions")
    @ApiOperation("创建题目")
    public Response<Long> createQuestion(
            @RequestBody @Validated QuestionCreateRequest request,
            @ApiParam(hidden = true) @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(questionService.createQuestion(request, userInfo));
    }

    @GetMapping("/questions/{id}")
    @ApiOperation("获取题目详情（包括答案）")
    public Response<QuestionVO> getOne(@PathVariable Long id) {
        return Response.success(questionService.findById(id));
    }

    @GetMapping("/questions")
    @ApiOperation("条件查询题目详情（包括答案）")
    public Response<BatchGet<QuestionVO>> query(QuestionQueryRequest queryRequest) {
        return Response.success(questionService.query(queryRequest));
    }

    @GetMapping("/questions/description")
    @ApiOperation("获取题目描述（无答案）")
    public Response<Map<Long, DescriptionVO>> findDescription(@RequestParam List<Long> ids) {
        return Response.success(questionService.findDescriptionByIds(ids));
    }
}
