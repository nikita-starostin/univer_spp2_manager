package main.application;

public class Logger {
    private static int defaultPriority = 1;
    public static String lastFatalError;

    public static void log(String message) {
        log(message, defaultPriority);
    }

    public static void log(String message, int priority) {
        System.out.println(message);
    }

    public static void fatalError(String message) {
        log(message, defaultPriority);
        lastFatalError = message;
    }
}
