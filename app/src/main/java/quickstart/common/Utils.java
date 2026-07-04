package quickstart.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class Utils {
    public static Map<String, Object> parseArgs(String[] args) {
        Map<String, Object> result = new LinkedHashMap<>();

        for (String arg : args) {
            if (!arg.startsWith("--")) {
                throw new IllegalArgumentException("Invalid argument: " + arg);
            }

            String[] parts = arg.substring(2).split("=", 2);
            String key = parts[0];

            if (key.isBlank()) {
                throw new IllegalArgumentException("Argument name cannot be empty");
            }

            Object value = parts.length == 1
                    ? true
                    : parseValue(parts[1]);

            result.put(key, value);
        }

        return result;
    }

    private static Object parseValue(String value) {
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }

        if ("false".equalsIgnoreCase(value)) {
            return false;
        }

        return value;
    }
}
