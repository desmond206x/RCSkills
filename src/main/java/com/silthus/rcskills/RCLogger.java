package com.silthus.rcskills;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description Handles the logging of the plugin
 * @author Tagette
 */
public class RCLogger {

    private static Logger log;
    private static String prefix;

    public static void initialize(Logger newLog) {
        RCLogger.log = newLog;
        prefix = "[" + RCSkills.name + "] ";
    }

    public static Logger getLog() {
        return log;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        RCLogger.prefix = prefix;
    }

    public static void info(String message) {
        log.info(prefix + message);
    }

    public static void error(String message) {
        log.severe(prefix + message);
    }

    public static void warning(String message) {
        log.warning(prefix + message);
    }
    // Hallo Silthus
    public static void config(String message) {
        log.config(prefix + message);
        // Hallo Silthus 22
    }

    public static void log(Level level, String message) {
        log.log(level, prefix + message);
    }
}