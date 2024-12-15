package org.example.Lab2.parsers;

public interface Parser<T, V> {
    T parse(V rawData);
}
