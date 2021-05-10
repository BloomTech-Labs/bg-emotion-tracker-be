package com.lambdaschool.oktafoundation;

import com.lambdaschool.oktafoundation.models.Role;
import com.lambdaschool.oktafoundation.models.User;
import com.lambdaschool.oktafoundation.models.UserRoles;
import com.lambdaschool.oktafoundation.services.RoleService;
import com.lambdaschool.oktafoundation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@ConditionalOnProperty(
    prefix = "command.line.runner",
    value = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Component
public class SeedData
    implements CommandLineRunner
{
    /**
     * Connects the Role Service to this process
     */
    @Autowired
    RoleService roleService;

    /**
     * Connects the user service to this process
     */
    @Autowired
    UserService userService;

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args) throws
                                   Exception
    {
        roleService.deleteAll();
        Role r1 = new Role("ADMIN");
        Role r2 = new Role("YDP");
        Role r3 = new Role("CD");
        Role r4 = new Role("KID");

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);
        r4 = roleService.save(r4);

//        ADMIN
        User u1 = new User("llama001@maildrop.cc");
        u1.getRoles()
            .add(new UserRoles(u1,
                r1));
        userService.save(u1);

//        Youth Development Professional
        User u2 = new User("llama002@maildrop.cc");
        u2.getRoles()
            .add(new UserRoles(u2,
                r2));
        userService.save(u2);

//        Club Director
        User u3 = new User("llama003@maildrop.cc");
        u3.getRoles()
                .add(new UserRoles(u3,
                        r3));
        userService.save(u3);

//        Kid
        User u4 = new User("llama004@maildrop.cc");
        u4.getRoles()
                .add(new UserRoles(u4,
                        r4));
        userService.save(u4);

        User u5 = new User("llama005@maildrop.cc");
        u5.getRoles()
                .add(new UserRoles(u5,
                        r4));
        userService.save(u5);

        User u6 = new User("llama006@maildrop.cc");
        u6.getRoles()
                .add(new UserRoles(u6,
                        r4));
        userService.save(u6);

        User u7 = new User("llama007@maildrop.cc");
        u7.getRoles()
                .add(new UserRoles(u7,
                        r4));
        userService.save(u7);

        User u8 = new User("llama008@maildrop.cc");
        u8.getRoles()
                .add(new UserRoles(u8,
                        r4));
        userService.save(u8);

        // The following is an example user!
        /*
        // admin, data, user
        User u1 = new User("admin",
            "password",
            "admin@lambdaschool.local");
        u1.getRoles()
            .add(new UserRoles(u1,
                r1));
        u1.getRoles()
            .add(new UserRoles(u1,
                r2));
        u1.getRoles()
            .add(new UserRoles(u1,
                r3));
        u1.getUseremails()
            .add(new Useremail(u1,
                "admin@email.local"));
        u1.getUseremails()
            .add(new Useremail(u1,
                "admin@mymail.local"));

        userService.save(u1);
        */
    }
}