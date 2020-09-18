package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.biz.domain.dto.BatchGet;
import cn.edu.jlu.examsystem.biz.domain.dto.IdAndName;
import cn.edu.jlu.examsystem.biz.domain.dto.Pagination;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.database.annotation.TargetDataSource;
import cn.edu.jlu.examsystem.database.entity.SubjectEntity;
import cn.edu.jlu.examsystem.database.repository.SubjectRepository;
import cn.edu.jlu.examsystem.http.common.Response;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_ONLY;
import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_WRITE;
import static cn.edu.jlu.examsystem.http.common.HttpConstants.ATTR_USER_AUTH;

/**
 * @author WangZeying 2020/9/12 23:39
 */
@Api(tags = "03-科目")
@RestController
@RequestMapping("/api/v1")
public class SubjectController {
    
    private final SubjectRepository subjectRepository;
    
    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
    
    @GetMapping({
            "/subjects",
            "/student/subjects",
            "/teacher/subjects"
    })
    @TargetDataSource(EXAM_READ_ONLY)
    public Response<BatchGet<SubjectEntity>> findSubjects(
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer perPage,
            @RequestParam(required = false, defaultValue = "false") boolean personal,
            @RequestParam(required = false) String name,
            @ApiIgnore @RequestAttribute(ATTR_USER_AUTH) CoreUserInfo userInfo) {
        List<SubjectEntity> entities =
                Lists.newArrayList(subjectRepository.findAll())
                        .stream()
                        .filter(entity -> !personal || userInfo.getId().equals(entity.getCreatorId()))
                        .filter(entity -> name == null || entity.getName().contains(name))
                        .sorted((o1, o2) -> -o1.getId().compareTo(o2.getId()))
                        .collect(Collectors.toList());
        pageNum = MoreObjects.firstNonNull(pageNum, 1);
        perPage = MoreObjects.firstNonNull(perPage, entities.size());
        Pagination.PagedRows<SubjectEntity> pagedRows = Pagination.fromFull(entities, perPage, pageNum);
        return Response.success(BatchGet.of(pagedRows.getRows(), pagedRows.getPagination()));
    }
    
    
    @PostMapping("/teacher/subjects")
    @TargetDataSource(EXAM_READ_WRITE)
    public Response<Long> create(
            @Validated
            @RequestBody IdAndName<Long> subject,
            @ApiIgnore @RequestAttribute(name = ATTR_USER_AUTH) CoreUserInfo userInfo) {
        SubjectEntity entity = new SubjectEntity();
        entity.setName(subject.getName());
        entity.setCreatorId(userInfo.getId());
        entity.setCreatorName(userInfo.getNickname());
        return Response.success(subjectRepository.save(entity).getId());
    }
}
