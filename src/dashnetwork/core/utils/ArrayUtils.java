package dashnetwork.core.utils;

public class ArrayUtils {

    public static <T>boolean contains(T[] array, T contains) {
        for (T object : array)
            if (object.equals(contains))
                return true;
        return false;
    }

}
