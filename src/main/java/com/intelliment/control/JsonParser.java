package com.intelliment.control;

import java.util.Collection;

public interface JsonParser<T> {
     String parse(T type);
     String parse(Collection<T> type);
     T parse(String type);
     Collection<T> parseEntries(String types);
}
