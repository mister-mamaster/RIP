package org.example.Lab2;

import java.io.IOException;
import java.io.OutputStream;

public class TextMessage implements TextResponse {

    private String message;

    @Override
    public void getResponse(OutputStream out) throws IOException {
        out.write(message.getBytes());
    }

    @Override
    public String getTextOfResponse() {
        return message;
    }

    @Override
    public void formResponse(String text) {
        this.message = text;
    }
}
