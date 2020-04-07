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

    public static String shortenNumber(Number number, int places) {
        String string = number.toString();
        String[] split = string.split("\\.");

        if (split.length != 2 || split[1].length() < places)
            return string;

        StringBuilder shortened = new StringBuilder(split[0] + ".");

        for (int i = 0; i < places; i++)
            shortened.append(split[1].charAt(i));

        return shortened.toString();
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
