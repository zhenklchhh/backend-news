package validators;

public class PasswordValidator {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;

    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        if (!password.matches(".*[0-9].*")) {
            return false;
        }

        return true;
    }
}