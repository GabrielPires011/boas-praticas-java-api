package br.com.alura.adopet.api.util;

import java.util.*;

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
            if (obj1 instanceof Object[] && obj2 instanceof Object[]) {
                return Arrays.deepEquals((Object[]) obj1, (Object[]) obj2);
            }
            if (obj1 instanceof int[] && obj2 instanceof int[]) {
                return Arrays.equals((int[]) obj1, (int[]) obj2);
            }
            if (obj1 instanceof long[] && obj2 instanceof long[]) {
                return Arrays.equals((long[]) obj1, (long[]) obj2);
            }
            if (obj1 instanceof short[] && obj2 instanceof short[]) {
                return Arrays.equals((short[]) obj1, (short[]) obj2);
            }
            if (obj1 instanceof byte[] && obj2 instanceof byte[]) {
                return Arrays.equals((byte[]) obj1, (byte[]) obj2);
            }
            if (obj1 instanceof double[] && obj2 instanceof double[]) {
                return Arrays.equals((double[]) obj1, (double[]) obj2);
            }
            if (obj1 instanceof float[] && obj2 instanceof float[]) {
                return Arrays.equals((float[]) obj1, (float[]) obj2);
            }
            if (obj1 instanceof char[] && obj2 instanceof char[]) {
                return Arrays.equals((char[]) obj1, (char[]) obj2);
            }
            if (obj1 instanceof boolean[] && obj2 instanceof boolean[]) {
                return Arrays.equals((boolean[]) obj1, (boolean[]) obj2);
            }
        }

        return obj1.equals(obj2);
    }

    public static boolean isParsableLong(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
