package br.com.alura.adopet.api.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class Util {

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            return ((String) obj).isEmpty();
        }

        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        if (obj instanceof Optional) {
            return ((Optional<?>) obj).isEmpty();
        }

        if (obj.getClass().isArray()) {
            return ((Object[]) obj).length == 0;
        }

        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isFalse(Boolean obj) {
        return !obj;
    }

    public static boolean isTrue(Boolean obj) {
        return obj;
    }

    public static boolean isEquals(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            return true;
        }
        if (obj1 == null || obj2 == null) {
            return false;
        }

        if (obj1 instanceof String && obj2 instanceof String) {
            return obj1.equals(obj2);
        }

        if (obj1 instanceof Collection && obj2 instanceof Collection) {
            return obj1.equals(obj2);
        }

        if (obj1 instanceof Map && obj2 instanceof Map) {
            return obj1.equals(obj2);
        }

        if (obj1 instanceof Optional && obj2 instanceof Optional) {
            return obj1.equals(obj2);
        }

        if (obj1.getClass().isArray() && obj2.getClass().isArray()) {
            return Arrays.deepEquals((Object[]) obj1, (Object[]) obj2);
        }

        return obj1.equals(obj2);
    }
}
