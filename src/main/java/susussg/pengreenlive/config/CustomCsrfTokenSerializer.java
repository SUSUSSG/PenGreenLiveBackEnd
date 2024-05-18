package susussg.pengreenlive.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import java.io.IOException;

public class CustomCsrfTokenSerializer extends StdSerializer<DefaultCsrfToken> {

    public CustomCsrfTokenSerializer() {
        super(DefaultCsrfToken.class);
    }

    @Override
    public void serialize(DefaultCsrfToken value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("token", value.getToken());
        gen.writeStringField("parameterName", value.getParameterName());
        gen.writeStringField("headerName", value.getHeaderName());
        gen.writeEndObject();
    }
}
