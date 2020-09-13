package cn.edu.jlu.examsystem.database.repository;

import cn.edu.jlu.examsystem.common.util.JsonUtils;
import cn.edu.jlu.examsystem.database.entity.SubjectEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WangZeying 2020/9/12 23:50
 */
@SpringBootTest
@ActiveProfiles("test")
class SubjectRepositoryTest {

    @Autowired
    SubjectRepository subjectRepository;

    @BeforeAll
    static void setUp() {
    }

    @Test
    void findAll() {
        SubjectEntity entity = new SubjectEntity();
        entity.setCreatorId(1L);
        entity.setCreatorName("TEST");
        entity.setName("测试");

        subjectRepository.save(entity);
        entity.setId(null);
        subjectRepository.save(entity);
        entity.setId(null);
        subjectRepository.save(entity);
        entity.setId(null);
        subjectRepository.save(entity);

        Iterable<SubjectEntity> entities = subjectRepository.findAll();
        System.out.println("====>>>>"+JsonUtils.toJson(entities));
        assertEquals(4, subjectRepository.count());
    }
}