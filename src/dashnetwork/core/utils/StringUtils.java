package dashnetwork.core.utils;

public class StringUtils {

    public static String unsplit(String[] strings, String split) {
        String result = "";

        for (String string : strings) {
            if (!result.isEmpty())
                result += split;
            result += string;
        }

        return result;
    }

    public static String clear(String string, String clear) {
        while (string.contains(clear))
            string = string.replace(clear, "");
        return string;
    }

    public static boolean startsWithIgnoreCase(String string, String starts) {
        return string.toLowerCase().startsWith(starts.toLowerCase());
    }

    public static boolean endsWithIgnoreCase(String string, String ends) {
        return string.toLowerCase().endsWith(ends.toLowerCase());
    }

}
