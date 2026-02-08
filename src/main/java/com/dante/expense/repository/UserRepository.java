package com.dante.expense.repository;

import com.dante.expense.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for persisting / retrieving User obj
 *
 * @invariant this != NULL
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user given their email
     *
     * @param email email adr of user
     *
     * @return an Optional containing IFF a user exists with that email adr, OW empty
     *
     * @pre email != NULL AND email.length() > 0
     *
     * @post return != NULL
     */
    Optional<User> findByEmail(String email);
}
