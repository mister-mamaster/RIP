package org.example.Lab2.session;

import java.util.Objects;

public class Session {
    private String content;

    private final String id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Session(String content, String id) {
        this.content = content;
        this.id = id;
    }

    public Session(String id) {
        this.content = "";
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean checkId(String id) {
        return Objects.equals(this.id, id);
    }

    public void addContent(String content) {
        this.content += content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return id.equals(session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
