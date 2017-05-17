package fi.thakki.depemp.type;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class DepartmentIdConverters {

    public static class Serializer extends JsonSerializer<DepartmentId> {

        @Override
        public void serialize(
                DepartmentId value,
                JsonGenerator generator,
                SerializerProvider serializers) throws IOException, JsonProcessingException {
            generator.writeNumber(value.longValue());
        }
    }

    public static class Deserializer extends JsonDeserializer<DepartmentId> {

        @Override
        public DepartmentId deserialize(
                JsonParser parser,
                DeserializationContext context) throws IOException, JsonProcessingException {
            return DepartmentId.valueOf(parser.readValueAs(Long.class));
        }
    }
}
