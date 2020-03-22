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

}
