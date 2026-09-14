package dev.omercanbasboga.hollowmereliveops.exception;

public class ConfigNotFoundException extends RuntimeException {

    public ConfigNotFoundException(String levelId) {
        super("No config for level '" + levelId + "'");
    }
}
