package com.dante.expense.config;

import com.dante.expense.entity.Role;
import com.dante.expense.entity.User;
import com.dante.expense.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * A startup seeder that inserts demo users for testing
 *
 * @invariant userRepo != NULL
 */
public class DataSeeder {
    private final UserRepository userRepo;

    /**
     * constructs DataSeeder
     *
     * @param userRepo the repository for user persistence
     *
     * @pre userRepo != NULL
     *
     * @post this.userRepo = userRepo
     */
    public DataSeeder(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Seeds users if none exist
     *
     * @param args command line arguments
     *
     * @pre none
     *
     * @post userRepo.count() >= 1
     */
    public void run(String... args) {
        if (userRepo.count() > 0) return;

        User emp = new User();
        emp.setName("Employee 1");
        emp.setEmail("emp@demo.com");
        emp.setPasswordHash("not-real");
        emp.setRole(Role.EMPLOYEE);
        userRepo.save(emp);

        User mgr = new User();
        mgr.setName("Manager 1");
        mgr.setEmail("mgr@demo.com");
        mgr.setPasswordHash("not-real");
        mgr.setRole(Role.MANAGER);
        userRepo.save(mgr);

        User fin = new User();
        fin.setName("Finance 1");
        fin.setEmail("fin@demo.com");
        fin.setPasswordHash("not-real");
        fin.setRole(Role.FINANCE);
        userRepo.save(fin);
    }

}
