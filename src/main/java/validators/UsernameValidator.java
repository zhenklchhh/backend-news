package validators;

public class UsernameValidator {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;
    private static final String ALLOWED_CHARS = "[a-zA-Z0-9_.-]";

    public static boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        if (username.length() < MIN_LENGTH || username.length() > MAX_LENGTH) {
            return false;
        }

        // Проверка на допустимые символы
        if (!username.matches("^" + ALLOWED_CHARS + "+$")) {
            return false;
        }

        return true;
    }
}