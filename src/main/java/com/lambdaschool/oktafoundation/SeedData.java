package com.lambdaschool.oktafoundation;

import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.MemberReactionRepository;
import com.lambdaschool.oktafoundation.repository.ReactionRepository;
import com.lambdaschool.oktafoundation.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;

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
        implements CommandLineRunner {
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


    @Autowired
    ClubService clubService;

    @Autowired
    ActivityService activityService;

    @Autowired
    MemberService memberService;

    @Autowired
    ReactionService reactionService;

    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    MemberReactionRepository memberReactionRepository;


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
            Exception {

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
        userService.deleteAll();
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


//        Activities
        clubService.deleteAll();
        activityService.deleteAll();
        Activity a1 = new Activity("Club Attendance");
        Activity a2 = new Activity("Arts & Crafts");
        Activity a3 = new Activity("Archery");
        Activity a4 = new Activity("Basketball");
        Activity a5 = new Activity("Homework Help");
        Activity a6 = new Activity("Music");
        Activity a7 = new Activity("Soccer");
        Activity a8 = new Activity("Club Checkout");
        activityService.save(a1);
        activityService.save(a2);
        activityService.save(a3);
        activityService.save(a4);
        activityService.save(a5);
        activityService.save(a6);
        activityService.save(a7);
        activityService.save(a8);


//       Club
        //Anderson has Attendance, Arts, Archery, Basketball, Homework
        Club c1 = new Club("Anderson");
        c1.getActivities()
                .add(new ClubActivities(c1, a1));
        c1.getActivities()
                .add(new ClubActivities(c1, a2));
        c1.getActivities()
                .add(new ClubActivities(c1, a3));
        c1.getActivities()
                .add(new ClubActivities(c1, a4));
        c1.getActivities()
                .add(new ClubActivities(c1, a5));
        c1.getActivities().add(new ClubActivities(c1, a8));

        c1 = clubService.save(c1);
        //Anderson has 3 staff
        c1.getUsers().add(new ClubUsers(c1, userService.findByName("llama001@maildrop.cc")));
        c1.getUsers().add(new ClubUsers(c1, userService.findByName("llama002@maildrop.cc")));
        c1.getUsers().add(new ClubUsers(c1, userService.findByName("llama003@maildrop.cc")));

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
        c2.getActivities().add(new ClubActivities(c2, a8));

        c2 = clubService.save(c2);
        //Caitlin has 1 staff
        c2.getUsers().add(new ClubUsers(c2, userService.findByName("llama001@maildrop.cc")));

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
        c3.getActivities().add(new ClubActivities(c3, a8));

        clubService.save(c3);

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
        c4.getActivities().add(new ClubActivities(c4, a8));

        clubService.save(c4);

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
        c5.getActivities().add(new ClubActivities(c5, a8));
        clubService.save(c5);

        //Morton has Attendance, Arts, Basketball, Homework, Soccer
        Club c6 = new Club("Morton");
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
        c6.getActivities().add(new ClubActivities(c6, a8));
        clubService.save(c6);
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
        c7.getActivities().add(new ClubActivities(c7, a8));
        clubService.save(c7);

        //Stelle has Attendance, Arts
        Club c8 = new Club("Stelle");
        c8.getActivities()
                .add(new ClubActivities(c8, a1));
        c8.getActivities()
                .add(new ClubActivities(c8, a2));
        c8.getActivities().add(new ClubActivities(c8, a8));

        clubService.save(c8);
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
        c9.getActivities().add(new ClubActivities(c9, a8));

        clubService.save(c9);


        Member m1 = new Member("testmember1");
        Member m2 = new Member("testmember2");
        Member m3 = new Member("testmember3");
        Member m4 = new Member("testmember4");

        m1 = memberService.save(m1);
        memberService.save(m2);
        memberService.save(m3);
        memberService.save(m4);

        Reaction rx1 = new Reaction();
        rx1.setReactionvalue("1F601"); // +2 😁
        Reaction rx2 = new Reaction();
        rx2.setReactionvalue("1F642"); //+1 🙂
        Reaction rx3 = new Reaction();
        rx3.setReactionvalue("1F610"); //0 😐
        Reaction rx4 = new Reaction();
        rx4.setReactionvalue("1F641"); // -1 🙁
        Reaction rx5 = new Reaction();
        rx5.setReactionvalue("1F61E"); // -2 😞
//        Reaction rx6 = new Reaction();
//        rx6.setReactionvalue("testreaction");

        rx1 = reactionService.save(rx1);
        reactionService.save(rx2);
        reactionService.save(rx3);
        reactionService.save(rx4);
        reactionService.save(rx5);
//        reactionService.save(rx6);

        var emojimap = new HashMap<Integer,String>();
        emojimap.put(4,"1F601");
        emojimap.put(3,"1F642");
        emojimap.put(2,"1F610");
        emojimap.put(1,"1F641");
        emojimap.put(0,"1F61E");



        // Generating 100 memberReactions
        var ran = new Random();
        var allmem = memberService.findAll();
        ArrayList<Reaction> allreactions = new ArrayList<>();
        reactionRepository.findAll().iterator().forEachRemaining(allreactions::add);
        var cas = new ArrayList<>(c1.getActivities());
        for (int i = 0; i<100; i++){
            var curmem = allmem.get(ran.nextInt(allmem.size()));
            var mr = memberReactionRepository.save(new MemberReactions(
                    curmem,
                    allreactions.get(ran.nextInt(allreactions.size())) ,
                    true,
                    cas.get(ran.nextInt(cas.size())
                    )));
            curmem.getReactions().add(mr);
            curmem.getClubs().add(new ClubMembers(c1, curmem));
            memberService.save(curmem);
        }




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