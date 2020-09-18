package cn.edu.jlu.examsystem.biz.service;

import cn.edu.jlu.examsystem.biz.domain.dto.BatchGet;
import cn.edu.jlu.examsystem.biz.domain.dto.IdAndName;
import cn.edu.jlu.examsystem.biz.domain.dto.request.CourseCreateRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.request.CourseQueryRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CourseDetail;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CourseSummary;
import cn.edu.jlu.examsystem.biz.excepiton.BaseException;
import cn.edu.jlu.examsystem.common.util.JsonUtils;
import cn.edu.jlu.examsystem.database.entity.CoreUserEntity;
import cn.edu.jlu.examsystem.database.entity.RelationUserEntity;
import cn.edu.jlu.examsystem.database.entity.SubjectEntity;
import cn.edu.jlu.examsystem.database.mapper.RelationUserMapper;
import cn.edu.jlu.examsystem.database.repository.SubjectRepository;
import cn.edu.jlu.examsystem.database.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WangZeying 2020/9/14 19:39
 */
@SpringBootTest
@ActiveProfiles("test")
class CourseServiceTest {

    @Autowired
    CourseService courseService;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RelationUserMapper relationUserMapper;


    @BeforeEach
    void setUp() {
    }

    @Test
    void createCourse() {
        CoreUserEntity userEntity = CoreUserEntity
                .builder()
                .username("one")
                .password("xxx")
                .nickname("测试1")
                .mobile("xx")
                .email("xx@xx.com")
                .roleId((short) 3)
                .build();
        userRepository.save(userEntity);
        userEntity.setId(null);
        userEntity.setUsername("two");
        userEntity.setNickname("测试2");
        userRepository.save(userEntity);
        userEntity.setId(null);
        userEntity.setUsername("three");
        userEntity.setNickname("测试3");
        userRepository.save(userEntity);
        userEntity.setId(null);
        userEntity.setUsername("four");
        userEntity.setNickname("测试4");
        userRepository.save(userEntity);

        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setName("测试科目");
        subjectEntity.setCreatorId(1L);
        subjectEntity.setCreatorName("xxx");

        subjectRepository.save(subjectEntity);

        CourseCreateRequest request = CourseCreateRequest.builder()
                .subjectId(subjectEntity.getId())
                .subtitle("2020第666次开课")
                .endTime(LocalDateTime.now().plusMonths(1))
                .startTime(LocalDateTime.now())
                .teacherIds(Arrays.asList(3L, 4L, 2L))
                .build();
        CoreUserInfo userInfo = CoreUserInfo.builder()
                .id(1L)
                .nickname("测试教师")
                .roleId((short) 1)
                .build();

        subjectEntity.setId(null);
        subjectEntity.setName("测试科目2");
        subjectRepository.save(subjectEntity);

        Long id = courseService.createCourse(request, userInfo);
        System.out.println(JsonUtils.toJson(request));
        CourseDetail detail = courseService.getDetailById(id);
        System.out.println(JsonUtils.toJson(detail));
        List<IdAndName<Long>> teachers = detail.getTeachers();
        List<Long> except = Arrays.asList(3L, 4L, 2L, 1L);
        List<Long> actual = teachers.stream().map(IdAndName::getId).collect(Collectors.toList());
        System.out.println(actual);
        assertTrue(actual.containsAll(except));

        request.setSubjectId(2L);
        id = courseService.createCourse(request, userInfo);
        request.setTeacherIds(Arrays.asList(3L, 2L));

        userInfo.setId(4L);
        id = courseService.createCourse(request, userInfo);
        id = courseService.createCourse(request, userInfo);

        RelationUserEntity student = RelationUserEntity.builder().userId(4L).userName("选课测试").build();
        relationUserMapper.batchInsertStudents(id, Collections.singletonList(student));

        userInfo.setId(2L);
        id = courseService.createCourse(request, userInfo);
        userInfo.setId(4L);

        CourseQueryRequest query = new CourseQueryRequest();
        BatchGet<CourseSummary> batchGet = courseService.query(query, userInfo);
        System.out.println(JsonUtils.toJson(batchGet));
        assertEquals(5, batchGet.getPagination().getRowCount());

        query.setSubjectId(1L);
        batchGet = courseService.query(query, userInfo);
        System.out.println(JsonUtils.toJson(batchGet));
        assertEquals(1, batchGet.getPagination().getRowCount());

        query.setMyCreated(true);
        batchGet = courseService.query(query, userInfo);
        assertEquals(0, batchGet.getPagination().getRowCount());

        query.setSubjectId(2L);
        batchGet = courseService.query(query, userInfo);
        assertEquals(2, batchGet.getPagination().getRowCount());

        query.setSubjectId(2L);
        query.setMyCreated(false);
        query.setMyChosen(true);
        batchGet = courseService.query(query, userInfo);
        assertEquals(1, batchGet.getPagination().getRowCount());

        userInfo.setId(3L);
        assertThrows(BaseException.class, () -> courseService.deleteCourse(4L, userInfo));

        userInfo.setId(4L);
        courseService.deleteCourse(4L, userInfo);
        batchGet = courseService.query(query, userInfo);
        assertEquals(0, batchGet.getPagination().getRowCount());

    }
}