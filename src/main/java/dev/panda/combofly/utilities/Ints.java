package dev.panda.combofly.utilities;

public class Ints {

    public static Integer tryParse(String string) {
        try {
            return Integer.parseInt(string);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
