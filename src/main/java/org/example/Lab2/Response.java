package org.example.Lab2;

import java.io.IOException;
import java.io.OutputStream;

public interface Response {
    void writeResponse(OutputStream out) throws IOException;
}
