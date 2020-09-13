package cn.edu.jlu.examsystem.biz.domain.dto;

import cn.edu.jlu.examsystem.common.util.JsonUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WangZeying 2020/9/11 15:48
 */
class PaginateTest {

    @Test
    void getLimit() {
        Paginate paginate = new Paginate();
        System.out.println(JsonUtils.toJson(paginate));
    }
}