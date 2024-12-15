package org.example.Lab2.parsers;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.Lab2.RegistrationData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RegistrationParser implements Parser<RegistrationData, InputStream> {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

    @Override
    public RegistrationData parse(InputStream rawData) {
        try {
            var data = new RegistrationData();
            JsonNode root = mapper.readTree(new InputStreamReader(rawData));
            data.login = root.get("loginUser").asText();
            data.password = root.get("password").asText();
            data.username = root.get("username").asText();
            data.email = root.get("email").asText();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
