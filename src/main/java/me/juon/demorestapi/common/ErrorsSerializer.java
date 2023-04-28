package me.juon.demorestapi.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;

import java.io.IOException;

/**
 * @author Kim Juon
 */
@JsonComponent  // ObjectMapper에 등록
public class ErrorsSerializer extends JsonSerializer<Errors> {
    @Override
    public void serialize(Errors errors, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        errors.getFieldErrors().forEach(e -> {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("field", e.getField());
                jsonGenerator.writeStringField("objectName", e.getObjectName());
                jsonGenerator.writeStringField("code", e.getCode());
                jsonGenerator.writeStringField("defaultMessage", e.getDefaultMessage());
                Object rejectedValue = e.getRejectedValue();
                if (!ObjectUtils.isEmpty(rejectedValue)) {
                    jsonGenerator.writeStringField("rejectedValue", rejectedValue.toString());
                }
                jsonGenerator.writeEndObject();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        errors.getGlobalErrors().forEach(e -> {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("objectName", e.getObjectName());
                jsonGenerator.writeStringField("code", e.getCode());
                jsonGenerator.writeStringField("defaultMessage", e.getDefaultMessage());
                jsonGenerator.writeEndObject();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        jsonGenerator.writeEndArray();
    }
}
