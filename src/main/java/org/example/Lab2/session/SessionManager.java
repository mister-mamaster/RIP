package org.example.Lab2.session;

import org.example.Lab2.model.User;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class SessionManager {
    Map<Session, User> sessions = new HashMap<>();
    
    public Session createSession(User user) {
        String key = generateKey();
        while(checkKey(key)) {
            key = generateKey();
        };
        Session session = new Session(key);
        sessions.put(session, user);
        return session;
    }

    public User getUser(String sessionKey) {
        return getUser(getSessionByKey(sessionKey));
    }

    public User getUser(Session session) {
        return sessions.get(session);
    }

    private Session getSessionByKey(String key) {
        return sessions.keySet().stream().filter(s-> Objects.equals(s.getId(), key)).toList().get(0);
    }

    private boolean checkKey(String key) {
        return sessions.keySet().stream().anyMatch((s)->s.checkId(key));
    }
    
    private String generateKey() {
        byte[] array = new byte[32];
        new Random().nextBytes(array);

        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) ((array[i] + 128) * (123 - 97) / (128 + 127) + 97);
        }
        return new String(array, StandardCharsets.UTF_8);
    }

    public void deleteSession(String key) {
        for (int i = 0; i < sessions.size(); i++) {
            if(sessions.keySet().stream().toList().get(i).checkId(key)) {
                this.sessions.remove(i);
                break;
            }
        }
    }
}
