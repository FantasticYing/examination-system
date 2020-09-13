package cn.edu.jlu.examsystem.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * @author WangZeying 2020/9/11 21:17
 */
@Component
public class JsonHelper {

    private final ObjectMapper objectMapper;

    public JsonHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> String toJson(T o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(String json, Class<T> clz) {
        try {
            return objectMapper.readValue(json, clz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
