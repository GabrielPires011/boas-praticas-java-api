package br.com.alura.adopet.api.util;

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
}
