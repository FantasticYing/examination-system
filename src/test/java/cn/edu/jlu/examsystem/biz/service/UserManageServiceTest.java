package cn.edu.jlu.examsystem.biz.service;

import cn.edu.jlu.examsystem.biz.bean.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author WangZeying 2020/9/11 13:49
 */
@SpringBootTest
class UserManageServiceTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
    }

    @Test
    void registerUser() {
    }

    @Test
    void sha() {
        String passwd = passwordEncoder.encoding("qwertyuiop");
        System.out.println(passwd);
    }
}