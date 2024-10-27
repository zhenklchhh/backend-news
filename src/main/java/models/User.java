package models;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a user in the system.
 */
@Setter
@Getter
public class User {
    /**
     * User's unique identifier.
     */
    private int id;

    /**
     * User's name.
     */
    private String name;

    /**
     * User's password.
     */
    private String password;

    /**
     * User's email address.
     */
    private String email;

    /**
     * Constructor with name, password, and email.
     *
     * @param userName     User's name.
     * @param userPassword User's password.
     * @param userEmail    User's email address.
     */
    public User(final String userName,
                final String userPassword, final String userEmail) {
        this.name = userName;
        this.password = userPassword;
        this.email = userEmail;
    }

    /**
     * Constructor with all user attributes.
     *
     * @param userID        User's ID.
     * @param userName      User's name.
     * @param userPassword User's password.
     * @param userEmail     User's email address.
     */
    public User(final int userID, final String userName,
                final String userPassword, final String userEmail) {
        this.id = userID;
        this.name = userName;
        this.password = userPassword;
        this.email = userEmail;
    }
}
