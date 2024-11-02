package validators;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

public class EmailValidator {
    public static boolean isValidEmail(String email) {
        try {
            InternetAddress address = new InternetAddress(email);
            address.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }
}