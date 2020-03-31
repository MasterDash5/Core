package dashnetwork.core.utils;

public class ArrayUtils {

    public static <T>boolean contains(T[] array, Object contains) {
        for (Object object : array)
            if (object.equals(contains))
                return true;
        return false;
    }

}
