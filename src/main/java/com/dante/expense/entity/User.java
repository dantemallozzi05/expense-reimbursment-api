package com.dante.expense.entity;

import jakarta.persistence.*;

/**
 * Represents application user (can be employee, manager, or finance user)
 *
 * @invariant id >= 0
 * @invariant name != NULL AND name.length() > 0
 * @invariant email != NULL AND email.length() > 0
 * @invariant passwordHash != NULL AND passwordHash.length() > 0
 * @invariant role != NULL
 *
 */
@Entity
@Table(name="users")
public class User {

    private Long id;
    private String name;
    private String email;
    private String passwordHash;
    private Role role;

    /**
     * Default constructor
     *
     * @pre none
     *
     * @post new User()
     */
    public User() {}

    /**
     * Getter for user Id
     *
     * @return the user id
     *
     * @pre none
     *
     * @post getId = id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Setter for user Id
     *
     * @param id the id to set
     *
     * @pre id != NULL AND id >= 0
     *
     * @post this.id = id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves user's display name
     *
     * @return name of user
     *
     * @pre none
     *
     * @post getName = name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for username
     *
     * @param name the name to set
     *
     * @pre name != NULL AND name.length() > 0
     *
     * @post this.name = name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves user's email address
     *
     * @return the user's email
     *
     * @pre none
     *
     * @post getEmail = email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email address
     *
     * @param email the email address to set
     *
     * @pre email != NULL AND email.length() > 0
     *
     * @post this.email = email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves user's hashed password
     *
     * @return the user's hash password
     *
     * @pre none
     *
     * @post getPasswordHash = passwordHash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Setter for stored password hash
     *
     * @param passwordHash the hash to set
     *
     * @pre passwordHash != NULL AND passwordHash.length() > 0
     *
     * @post this.passwordHash = passwordHash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Retrieves the user's Role
     *
     * @return the user's role
     *
     * @pre none
     *
     * @post getRole = role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Setter for the user's Role
     *
     * @param role the Role to assign to the user
     *
     * @pre role != NULL
     *
     * @post this.role = role
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
