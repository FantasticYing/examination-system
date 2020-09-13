package cn.edu.jlu.examsystem.http.controller;

import cn.edu.jlu.examsystem.common.util.JsonUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @author WangZeying 2020/9/11 16:18
 */
class DemoControllerTest {

    @Test
    void now() {
        String json = JsonUtils.toJson(LocalDateTime.now());
        System.out.println(json);
    }
}