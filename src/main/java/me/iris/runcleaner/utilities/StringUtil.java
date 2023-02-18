package me.iris.runcleaner.utilities;

public class StringUtil {
    public static String removeLetters(final String str) {
        final StringBuilder filteredString = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c))
                filteredString.append(c);
        }

        return filteredString.toString();
    }
}
