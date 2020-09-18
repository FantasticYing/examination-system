package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.biz.common.Validations.*;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

import static cn.edu.jlu.examsystem.http.common.HttpConstants.ATTR_USER_AUTH;

/**
 * @author WangZeying 2020/9/8 18:12
 */
@Api(tags = "05-题目")
@RestController
@RequestMapping("/api/v1")
public class QuestionController {
    
    private final QuestionService questionService;
    
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
    
    @PostMapping("/teacher/questions/choice")
    @ApiOperation("创建单选题目")
    public Response<Long> createQuestion1(
            @RequestBody @Validated(ValidateChoice.class) QuestionCreateRequest request,
            @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(questionService.createQuestion(request, userInfo));
    }
    
    @PostMapping("/teacher/questions/multiChoice")
    @ApiOperation("创建多选题目")
    public Response<Long> createQuestion2(
            @RequestBody @Validated(ValidateMultiAnswerChoice.class) QuestionCreateRequest request,
            @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(questionService.createQuestion(request, userInfo));
    }
    
    @PostMapping("/teacher/questions/tf")
    @ApiOperation("创建判断题目")
    public Response<Long> createQuestion3(
            @RequestBody @Validated(ValidateTF.class) QuestionCreateRequest request,
            @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(questionService.createQuestion(request, userInfo));
    }
    
    @PostMapping("/teacher/questions/shortAnswer")
    @ApiOperation("创建简答题目")
    public Response<Long> createQuestion5(
            @RequestBody @Validated(ValidateShortAnswer.class) QuestionCreateRequest request,
            @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        return Response.success(questionService.createQuestion(request, userInfo));
    }
    
    @GetMapping("/teacher/questions/{id}")
    @ApiOperation("获取题目详情（包括答案）")
    public Response<QuestionVO> getOne(@PathVariable Long id) {
        return Response.success(questionService.findById(id));
    }
    
    @DeleteMapping("/teacher/questions/{id}")
    @ApiOperation("删除")
    public Response<Integer> delete(@PathVariable Long id) {
        return Response.success(questionService.deleteById(id));
    }
    
    @GetMapping("/teacher/questions")
    @ApiOperation("查询题目详情（包括答案）")
    public Response<BatchGet<QuestionVO>> query(QuestionQueryRequest queryRequest) {
        return Response.success(questionService.query(queryRequest));
    }
    
    @GetMapping({
            "/teacher/questions/description",
            "/student/questions/description",
            "/questions/description"
    })
    @ApiOperation("获取题目描述（无答案）")
    public Response<Map<Long, DescriptionVO>> findDescription(@RequestParam List<Long> ids) {
        return Response.success(questionService.findDescriptionByIds(ids));
    }
}
