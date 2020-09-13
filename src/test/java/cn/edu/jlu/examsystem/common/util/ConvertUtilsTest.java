package cn.edu.jlu.examsystem.common.util;

import cn.edu.jlu.examsystem.database.entity.CoreUserEntity;
import org.apache.catalina.SessionIdGenerator;
import org.apache.catalina.util.StandardSessionIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author WangZeying 2020/9/5 21:37
 */
class ConvertUtilsTest {

    List<CoreUserEntity> userEntityList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        CoreUserEntity userEntity = CoreUserEntity.builder()
                .id(1L)
                .username("WWWWWWWW")
                .password("666666")
                .mobile("13344455666")
                .email("what@jlu.edu.cn")
                .roleId((short) 1)
                .isDel((short) 0)
                .build();
        userEntityList.add(userEntity);
        userEntityList.add(userEntity);
        userEntityList.add(userEntity);

    }

    @Test
    void testExtractList() {
        List<Long> idList = ConvertUtils.extractList(userEntityList, CoreUserEntity::getId);
        assertEquals(Arrays.asList(1L, 1L, 1L), idList);
    }

    @Test
    void testExtrassctList() {
        SessionIdGenerator sessionIdGenerator = new StandardSessionIdGenerator();
        System.out.println(sessionIdGenerator.getSessionIdLength());
        System.out.println(sessionIdGenerator.generateSessionId());
    }
}