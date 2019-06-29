package by.butramyou.todolist.util;

import java.util.Random;

public class FileUtils {

    private static String getLetter() {
        return String.valueOf((char) (new Random().nextInt((122 - 97) + 1) + 97));
    }

    public static String getPath() {
        StringBuilder path = new StringBuilder("/");
        for (int i = 0; i < 10; i++) {
            path.append(getLetter()).append("/");
        }
        return path.toString();
    }
}
