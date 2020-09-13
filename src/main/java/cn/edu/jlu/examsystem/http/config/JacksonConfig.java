package cn.edu.jlu.examsystem.http.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.*;

/**
 * @author WangZeying 2020/9/11 17:57
 */
@Slf4j
@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer());
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer());
            builder.serializerByType(LocalDate.class, new LocalDateSerializer());
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer());
        };
    }

    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            ZonedDateTime zonedDateTime = localDateTime.atZone(serializerProvider.getTimeZone().toZoneId());
            jsonGenerator.writeNumber(zonedDateTime.toEpochSecond());
        }
    }

    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            long timestamp = jsonParser.getLongValue();
            Instant instant = Instant.ofEpochSecond(timestamp);
            ZoneId zoneId = deserializationContext.getTimeZone().toZoneId();
            return LocalDateTime.ofInstant(instant, zoneId);
        }
    }

    public static class LocalDateSerializer extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(serializerProvider.getTimeZone().toZoneId());
            jsonGenerator.writeNumber(zonedDateTime.toEpochSecond());
        }
    }

    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            long timestamp = jsonParser.getLongValue();
            Instant instant = Instant.ofEpochSecond(timestamp);
            ZoneId zoneId = deserializationContext.getTimeZone().toZoneId();
            return LocalDateTime.ofInstant(instant, zoneId).toLocalDate();
        }
    }

}
