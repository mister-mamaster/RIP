package org.example.Lab2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.math.NumberUtils;

public class ParamsParser {
    public static Map<String, Object> parse(String rawString) {
        String[] params = rawString.split("&");
        Map<String, Object> paramsMap = new HashMap<>();
        Arrays.stream(params).forEach((param) -> {
            var paramPair = param.split("=");
            paramsMap.put(paramPair[0], NumberUtils.isDigits(paramPair[1])? Integer.valueOf(NumberUtils.toInt(paramPair[1])) : paramPair[1]);
        });
        return paramsMap;
    }
}
