package susussg.pengreenlive.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import java.io.IOException;

public class CustomCsrfTokenDeserializer extends StdDeserializer<DefaultCsrfToken> {

    public CustomCsrfTokenDeserializer() {
        super(DefaultCsrfToken.class);
    }

    @Override
    public DefaultCsrfToken deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String token = node.get("token").asText();
        String parameterName = node.get("parameterName").asText();
        String headerName = node.get("headerName").asText();
        return new DefaultCsrfToken(headerName, parameterName, token);
    }
}