package cn.edu.jlu.examsystem.common.util;

import cn.edu.jlu.examsystem.database.entity.CoreUserEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WangZeying 2020/9/5 21:11
 */
class JsonUtilsTest {

    CoreUserEntity userEntity = CoreUserEntity.builder()
            .id(666L)
            .username("WWWWWWWW")
            .password("666666")
            .mobile("13344455666")
            .email("what@jlu.edu.cn")
            .roleId((short) 1)
            .isDel((short) 0)
            .build();

    String jsonTemp = "{\"id\":666,\"username\":\"WWWWWWWW\",\"email\":\"what@jlu.edu.cn\",\"mobile\":\"13344455666\",\"password\":\"666666\",\"role\":1,\"isDel\":0}";

    @Test
    void toJson() {
        String json = JsonUtils.toJson(userEntity);
        assertEquals(jsonTemp, json);
    }

    @Test
    void fromJson() {
        CoreUserEntity coreUserEntity = JsonUtils.fromJson(jsonTemp, userEntity.getClass());
        List<CoreUserEntity> coreUserEntities = JsonUtils.fromJson("[" + jsonTemp + "]", new TypeReference<List<CoreUserEntity>>() {
        });
        assertEquals(userEntity, coreUserEntity);
        assertEquals(userEntity, coreUserEntities.get(0));
    }
}