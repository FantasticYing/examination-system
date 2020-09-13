package cn.edu.jlu.examsystem.database.repository;

import cn.edu.jlu.examsystem.common.util.JsonUtils;
import cn.edu.jlu.examsystem.database.entity.CoreUserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WangZeying 2020/9/7 11:42
 */
@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    CoreUserEntity userEntity = CoreUserEntity.builder()
            .id(null)
            .username("WWWWWWWW")
            .password("666666")
            .mobile("13344455666")
            .email("what@jlu.edu.cn")
            .roleId((short) 1)
            .isDel((short) 0)
            .build();

    @Test
    void findByUsernameAndPasswordAndIsDel() {
        userRepository.save(userEntity);
        userEntity.setId(null);
        userRepository.save(userEntity);
        userEntity.setId(null);
        userRepository.save(userEntity);
        System.out.println(JsonUtils.toJson(userRepository.findAll()));
        assertEquals(userRepository.count(), 3);
    }
}