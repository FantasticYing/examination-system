package cn.edu.jlu.examsystem.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

/**
 * @author WangZeying 2020/9/5 20:46
 */
@Slf4j
@Component
public class JsonUtils {

    private JsonUtils() {
    }

    private static ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper){
        OBJECT_MAPPER = objectMapper;
        log.info("JsonUtils装载ObjectMapper完成");
    }

    public static <T> String toJson(T o) {
        try {
            return OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clz) {
        try {
            return OBJECT_MAPPER.readValue(json, clz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
