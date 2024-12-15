package org.example.Lab2.session;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SessionManager {
    List<? extends Session> sessions = new ArrayList<>();
    
    public Session createSession() {
        String key = generateKey();
        while(checkKey(key)) {
            key = generateKey();
        };
        return new Session(key);
    }

    private boolean checkKey(String key) {
        return sessions.stream().anyMatch((s)->s.checkId(key));
    }
    
    private String generateKey() {
        byte[] array = new byte[32];
        new Random().nextBytes(array);

        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) ((array[i] + 128) * (123 - 97) / (128 + 127) + 97);
        }
        return new String(array, StandardCharsets.UTF_8);
    }
}
