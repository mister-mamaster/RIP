package org.example.Lab2;

public interface Parser<T, V> {
    T parse(V rawData);
}
