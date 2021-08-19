package dev.panda.combofly.utilities;

import org.apache.commons.lang.time.DurationFormatUtils;

public class Time {

    public static String formatIntMin(int time) {
        return String.format("%02d:%02d", time / 60, time % 60);
    }

    public static String formatLongMin(long time) {
        long totalSecs = time / 1000L;
        return String.format("%02d:%02d", totalSecs / 60L, totalSecs % 60L);
    }

    public static String formatDuration(long input) {
        return DurationFormatUtils.formatDurationWords(input, true, true);
    }

    public static int format(String input) {
        if (input == null || input.isEmpty()) {
            return -1;
        }
        int result = 0;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                String str;
                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convert(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }
        return result;
    }

    private static int convert(int value, char unit) {
        switch (unit) {
            case 'h': {
                return value * 60 * 60;
            }
            case 'm': {
                return value * 60;
            }
            case 's': {
                return value;
            }
            default: {
                return -1;
            }
        }
    }
}
