package cn.edu.jlu.examsystem.database.mapper;

import cn.edu.jlu.examsystem.common.util.JsonUtils;
import cn.edu.jlu.examsystem.database.entity.CourseEntity;
import cn.edu.jlu.examsystem.database.entity.RelationUserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WangZeying 2020/9/13 19:29
 */
@SpringBootTest
@ActiveProfiles("test")
class CourseMapperTest {

    @Autowired
    CourseMapper courseMapper;
    @Autowired
    RelationUserMapper relationUserMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void insert() {
    }

    @Test
    void selectByPrimaryKey() {
        CourseEntity courseEntity = CourseEntity.builder()
                .subjectId(2L)
                .subjectName("测试科目")
                .creatorId(2L)
                .creatorName("测试用户")
                .subtitle("2020上学期，第一次课")
                .startTime(LocalDateTime.now().minusDays(10L))
                .endTime(LocalDateTime.now().plusMonths(2))
                .build();
        int insert = courseMapper.insert(courseEntity);
        System.out.println(JsonUtils.toJson(courseEntity));
        CourseEntity newCourseEntity = courseMapper.selectByPrimaryKey(courseEntity.getId());
        System.out.println(JsonUtils.toJson(newCourseEntity));

        RelationUserEntity userEntity1 = RelationUserEntity.builder()
                .courseId(courseEntity.getId())
                .userId(1L)
                .userName("老师1")
                .build();

        RelationUserEntity userEntity2 = RelationUserEntity.builder()
                .courseId(courseEntity.getId())
                .userId(2L)
                .userName("老师2")
                .build();

        RelationUserEntity userEntity3 = RelationUserEntity.builder()
                .courseId(courseEntity.getId())
                .userId(3L)
                .userName("老师3")
                .build();

        relationUserMapper.batchInsertTeachers(Arrays.asList(userEntity1, userEntity2, userEntity3));

        newCourseEntity = courseMapper.selectByPrimaryKey(courseEntity.getId());
        System.out.println(JsonUtils.toJson(newCourseEntity));
        assertEquals(3, newCourseEntity.getTeachers().size());
    }
}