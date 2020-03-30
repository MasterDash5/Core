package dashnetwork.core.utils;

import java.util.Collection;

public class LazyUtils {

    public static boolean anyEquals(Object object, Object... equals) {
        for (Object equalsObject : equals)
            if (object.equals(equalsObject))
                return true;
        return false;
    }

    public static boolean anyEqualsIgnoreCase(String string, String... equals) {
        for (String equalsObject : equals)
            if (string.equalsIgnoreCase(equalsObject))
                return true;
        return false;
    }

    public static boolean anyStartsWith(String string, String... starts) {
        for (String startsObject : starts)
            if (string.startsWith(startsObject))
                return true;
        return false;
    }

    public static boolean anyEndsWith(String string, String... ends) {
        for (String endsObject : ends)
            if (string.endsWith(endsObject))
                return true;
        return false;
    }

    public static boolean anyContains(String string, String... contains) {
        for (String containsObject : contains)
            if (string.contains(containsObject))
                return true;
        return false;
    }

    public static boolean anyContains(Collection<Object> collection, Object... contains) {
        for (Object object : contains)
            if (collection.contains(object))
                return true;
        return false;
    }

}
