package com.lambdaschool.oktafoundation;

import com.lambdaschool.oktafoundation.models.*;
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
        User u2 = new User("barnbarn@maildrop.cc");
        u2.getRoles()
            .add(new UserRoles(u2,
                r2));
        userService.save(u2);

//        Club Director
        User u3 = new User("serviceprovider@bngclub.cc");
        u3.getRoles()
                .add(new UserRoles(u3,
                        r3));
        userService.save(u3);

//        Kid
        User u4 = new User("normaluser@bngclub.cc");
        u4.getRoles()
                .add(new UserRoles(u4,
                        r4));
        userService.save(u4);
//        Activities
        Activity a1 = new Activity("Club Attendance");
        Activity a2 = new Activity("Arts & Crafts");
        Activity a3 = new Activity("Archery");
        Activity a4 = new Activity("Basketball");
        Activity a5 = new Activity("Homework Help");
        Activity a6 = new Activity("Music");
        Activity a7 = new Activity("Soccer");

//        Club
        //Anderson has Attendance, Arts, Archery, Basketball, Homework
        Club c1 = new Club("Anderson");
        c1.getActivities()
                .add(new ClubActivities(c1,a1));
        c1.getActivities()
                .add(new ClubActivities(c1,a2));
        c1.getActivities()
                .add(new ClubActivities(c1,a3));
        c1.getActivities()
                .add(new ClubActivities(c1,a4));
        c1.getActivities()
                .add(new ClubActivities(c1,a5));

        //Caitlin has Attendance,Arts, Basketball, Homework,
        Club c2 = new Club("Caitlin");
        c2.getActivities()
                .add(new ClubActivities(c2, a1));
        c2.getActivities()
                .add(new ClubActivities(c2, a2));
        c2.getActivities()
                .add(new ClubActivities(c2, a4));
        c2.getActivities()
                .add(new ClubActivities(c2, a5));

        //Grossman has Attendance, Arts, Basketball, Homework, Music, Soccer
        Club c3 = new Club("Grossman");
        c3.getActivities()
                .add(new ClubActivities(c3, a1));
        c3.getActivities()
                .add(new ClubActivities(c3, a2));
        c3.getActivities()
                .add(new ClubActivities(c3, a4));
        c3.getActivities()
                .add(new ClubActivities(c3, a5));
        c3.getActivities()
                .add(new ClubActivities(c3, a6));
        c3.getActivities()
                .add(new ClubActivities(c3, a7));

        //Johnston has Attendance, Arts, Basketball, Homework, Music, Soccer
        Club c4 = new Club("Johnston");
        c4.getActivities()
                .add(new ClubActivities(c4, a1));
        c4.getActivities()
                .add(new ClubActivities(c4, a2));
        c4.getActivities()
                .add(new ClubActivities(c4, a4));
        c4.getActivities()
                .add(new ClubActivities(c4, a5));
        c4.getActivities()
                .add(new ClubActivities(c4, a6));
        c4.getActivities()
                .add(new ClubActivities(c4, a7));

        //Marley has Attendance, Arts, Basketball, Homework Help, Music, Soccer
        Club c5 = new Club("Marley");
        c5.getActivities()
                .add(new ClubActivities(c5, a1));
        c5.getActivities()
                .add(new ClubActivities(c5, a2));
        c5.getActivities()
                .add(new ClubActivities(c5, a4));
        c5.getActivities()
                .add(new ClubActivities(c5, a5));
        c5.getActivities()
                .add(new ClubActivities(c5, a6));
        c5.getActivities()
                .add(new ClubActivities(c5, a7));

        //Morton has Attendance, Arts, Basketball, Homework, Soccer
        Club c6 = new Club ("Morton");
        c6.getActivities()
                .add(new ClubActivities(c6, a1));
        c6.getActivities()
                .add(new ClubActivities(c6, a2));
        c6.getActivities()
                .add(new ClubActivities(c6, a4));
        c6.getActivities()
                .add(new ClubActivities(c6, a5));
        c6.getActivities()
                .add(new ClubActivities(c6, a7));
        //Notter has Attendance, Arts, Basketball, Homework,
        Club c7 = new Club("Notter");
        c7.getActivities()
                .add(new ClubActivities(c7, a1));
        c7.getActivities()
                .add(new ClubActivities(c7, a2));
        c7.getActivities()
                .add(new ClubActivities(c7, a4));
        c7.getActivities()
                .add(new ClubActivities(c7, a5));

        //Stelle has Attendance, Arts
        Club c8 = new Club("Stelle");
        c8.getActivities()
                .add(new ClubActivities(c8, a1));
        c8.getActivities()
                .add(new ClubActivities(c8, a2));

        //Jefferson has Attendance, Arts, Basketball, Homework, Music, Soccer
        Club c9 = new Club("Jefferson");
        c9.getActivities()
                .add(new ClubActivities(c9, a1));
        c9.getActivities()
                .add(new ClubActivities(c9, a2));
        c9.getActivities()
                .add(new ClubActivities(c9, a4));
        c9.getActivities()
                .add(new ClubActivities(c9, a5));
        c9.getActivities()
                .add(new ClubActivities(c9, a6));
        c9.getActivities()
                .add(new ClubActivities(c9, a7));

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