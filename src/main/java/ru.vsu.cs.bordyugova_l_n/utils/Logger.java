package ru.vsu.cs.bordyugova_l_n.utils;


import java.time.LocalDateTime;

public class Logger {
    public static void logInfo(String message) {
        System.out.println("INFO [" + LocalDateTime.now() + "]: " + message);
    }

    public static void logError(String message) {
        System.err.println("ERROR [" + LocalDateTime.now() + "]: " + message);
    }
}