package com.goodcare.server.global.config;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalTime;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // JavaTimeModule에 커스텀 Serializer/Deserializer 등록
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer());

        // ObjectMapper에 JavaTimeModule 등록
        objectMapper.registerModule(javaTimeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }

    // 커스텀 LocalTime Deserializer
    static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
        @Override
        public LocalTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            // JSON 객체에서 hour, minute, second, nano 필드를 읽어 LocalTime 생성
            JsonNode node = parser.getCodec().readTree(parser);

            // 필드가 없을 경우 기본값 설정
            int hour = node.has("hour") ? node.get("hour").asInt() : 0;
            int minute = node.has("minute") ? node.get("minute").asInt() : 0;
            int second = node.has("second") ? node.get("second").asInt() : 0;
            int nano = node.has("nano") ? node.get("nano").asInt() : 0;

            // LocalTime 객체 생성
            return LocalTime.of(hour, minute, second, nano);
        }
    }

    // 커스텀 LocalTime Serializer
    static class LocalTimeSerializer extends JsonSerializer<LocalTime> {
        @Override
        public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            // LocalTime을 JSON 객체 형식으로 변환
            gen.writeStartObject();
            gen.writeNumberField("hour", value.getHour());
            gen.writeNumberField("minute", value.getMinute());
            gen.writeNumberField("second", value.getSecond());
            gen.writeNumberField("nano", value.getNano());
            gen.writeEndObject();
        }
    }
}
