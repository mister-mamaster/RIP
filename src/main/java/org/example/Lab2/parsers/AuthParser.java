package org.example.Lab2.parsers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Lab2.RegistrationData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AuthParser implements Parser<RegistrationData, InputStream>{

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public RegistrationData parse(InputStream rawData) {
        try {
            var data = new RegistrationData();
            JsonNode root = mapper.readTree(new InputStreamReader(rawData));
            data.login = root.get("loginUser").asText();
            data.password = root.get("password").asText();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
