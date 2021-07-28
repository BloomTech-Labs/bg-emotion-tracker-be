package com.lambdaschool.oktafoundation;

import ch.qos.logback.core.util.TimeUtil;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.ClubMembersRepository;
import com.lambdaschool.oktafoundation.repository.MemberReactionRepository;
import com.lambdaschool.oktafoundation.repository.MemberRepository;
import com.lambdaschool.oktafoundation.repository.ReactionRepository;
import com.lambdaschool.oktafoundation.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@SuppressWarnings("ALL")
@Transactional
@ConditionalOnProperty(
        prefix = "command.line.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
public class SeedData
        implements CommandLineRunner {

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;


    @Autowired
    ClubService clubService;

    @Autowired
    ActivityService activityService;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepo;

    @Autowired
    ClubMemberService clubMembersService;

    @Autowired
    ClubMembersRepository clubMembersRepository;
    @Autowired
    ReactionService reactionService;

    @Autowired
    ReactionRepository reactionRepository;

    @Autowired
    MemberReactionRepository memberReactionRepository;

//    hey
    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data is seeded into our database.
     * This seed removes the previous data, should be avoided in production except for the first startup.
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

        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        //ADMIN
        userService.deleteAll();
        User u1 = new User("llama001@maildrop.cc");
        u1.getRoles().add(new UserRoles(u1, r1));
        userService.save(u1);

        //Youth Development Professional
        User u2 = new User("llama002@maildrop.cc");
        u2.getRoles().add(new UserRoles(u2, r2));
        userService.save(u2);

        //Club Director
        User u3 = new User("llama003@maildrop.cc");
        u3.getRoles().add(new UserRoles(u3, r3));
        userService.save(u3);

        //Activities
        clubService.deleteAll();
        activityService.deleteAll();
        Activity a1 = new Activity("Club Checkin");
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


        // Club
        // All clubs have Checkin anc Checkout
        //Anderson has  Arts, Archery, Basketball, Homework
        Club c1 = new Club("Anderson");
        c1.getActivities().add(new ClubActivities(c1, a1));
        c1.getActivities().add(new ClubActivities(c1, a2));
        c1.getActivities().add(new ClubActivities(c1, a3));
        c1.getActivities().add(new ClubActivities(c1, a4));
        c1.getActivities().add(new ClubActivities(c1, a5));
        c1.getActivities().add(new ClubActivities(c1, a8));

        c1 = clubService.save(c1);
        //Anderson has 3 staff
        c1.getUsers().add(new ClubUsers(c1, userService.findByName("llama001@maildrop.cc")));
        c1.getUsers().add(new ClubUsers(c1, userService.findByName("llama002@maildrop.cc")));
        c1.getUsers().add(new ClubUsers(c1, userService.findByName("llama003@maildrop.cc")));

        //Caitlin has Arts, Basketball, Homework,
        Club c2 = new Club("Catlin");
        c2.getActivities().add(new ClubActivities(c2, a1));
        c2.getActivities().add(new ClubActivities(c2, a2));
        c2.getActivities().add(new ClubActivities(c2, a4));
        c2.getActivities().add(new ClubActivities(c2, a5));
        c2.getActivities().add(new ClubActivities(c2, a8));

        c2 = clubService.save(c2);
        //Caitlin has 1 staff
        c2.getUsers().add(new ClubUsers(c2, userService.findByName("llama001@maildrop.cc")));

        //Grossman has  Arts, Basketball, Homework, Music, Soccer
        Club c3 = new Club("Grossman");
        c3.getActivities().add(new ClubActivities(c3, a1));
        c3.getActivities().add(new ClubActivities(c3, a2));
        c3.getActivities().add(new ClubActivities(c3, a4));
        c3.getActivities().add(new ClubActivities(c3, a5));
        c3.getActivities().add(new ClubActivities(c3, a6));
        c3.getActivities().add(new ClubActivities(c3, a7));
        c3.getActivities().add(new ClubActivities(c3, a8));

        c3 = clubService.save(c3);
        c3.getUsers().add(new ClubUsers(c3, userService.findByName("llama002@maildrop.cc")));


        //Johnston has  Arts, Basketball, Homework, Music, Soccer
        Club c4 = new Club("Johnston");
        c4.getActivities().add(new ClubActivities(c4, a1));
        c4.getActivities().add(new ClubActivities(c4, a2));
        c4.getActivities().add(new ClubActivities(c4, a4));
        c4.getActivities().add(new ClubActivities(c4, a5));
        c4.getActivities().add(new ClubActivities(c4, a6));
        c4.getActivities().add(new ClubActivities(c4, a7));
        c4.getActivities().add(new ClubActivities(c4, a8));

        c4 = clubService.save(c4);
        c4.getUsers().add(new ClubUsers(c4, userService.findByName("llama003@maildrop.cc")));

        //Marley has  Arts, Basketball, Homework Help, Music, Soccer
        Club c5 = new Club("Marley");
        c5.getActivities().add(new ClubActivities(c5, a1));
        c5.getActivities().add(new ClubActivities(c5, a2));
        c5.getActivities().add(new ClubActivities(c5, a4));
        c5.getActivities().add(new ClubActivities(c5, a5));
        c5.getActivities().add(new ClubActivities(c5, a6));
        c5.getActivities().add(new ClubActivities(c5, a7));
        c5.getActivities().add(new ClubActivities(c5, a8));
        c5 = clubService.save(c5);
        c5.getUsers().add(new ClubUsers(c5, userService.findByName("llama001@maildrop.cc")));
        c5.getUsers().add(new ClubUsers(c5, userService.findByName("llama003@maildrop.cc")));


        //Morton has  Arts, Basketball, Homework, Soccer
        Club c6 = new Club("Morton");
        c6.getActivities().add(new ClubActivities(c6, a1));
        c6.getActivities().add(new ClubActivities(c6, a2));
        c6.getActivities().add(new ClubActivities(c6, a4));
        c6.getActivities().add(new ClubActivities(c6, a5));
        c6.getActivities().add(new ClubActivities(c6, a7));
        c6.getActivities().add(new ClubActivities(c6, a8));
        c6 = clubService.save(c6);

        c6.getUsers().add(new ClubUsers(c6, userService.findByName("llama001@maildrop.cc")));
        c6.getUsers().add(new ClubUsers(c6, userService.findByName("llama003@maildrop.cc")));

        //Notter has  Arts, Basketball, Homework,
        Club c7 = new Club("Notter");
        c7.getActivities().add(new ClubActivities(c7, a1));
        c7.getActivities().add(new ClubActivities(c7, a2));
        c7.getActivities().add(new ClubActivities(c7, a4));
        c7.getActivities().add(new ClubActivities(c7, a5));
        c7.getActivities().add(new ClubActivities(c7, a8));
        c7 = clubService.save(c7);


        c7.getUsers().add(new ClubUsers(c7, userService.findByName("llama001@maildrop.cc")));
        c7.getUsers().add(new ClubUsers(c7, userService.findByName("llama003@maildrop.cc")));

        //Stelle has  Arts
        Club c8 = new Club("Stelle");
        c8.getActivities().add(new ClubActivities(c8, a1));
        c8.getActivities().add(new ClubActivities(c8, a2));
        c8.getActivities().add(new ClubActivities(c8, a8));
        c8 = clubService.save(c8);


        c8.getUsers().add(new ClubUsers(c8, userService.findByName("llama001@maildrop.cc")));
        c8.getUsers().add(new ClubUsers(c8, userService.findByName("llama003@maildrop.cc")));


        //Jefferson has  Arts, Basketball, Homework, Music, Soccer
        Club c9 = new Club("Jefferson");
        c9.getActivities().add(new ClubActivities(c9, a1));
        c9.getActivities().add(new ClubActivities(c9, a2));
        c9.getActivities().add(new ClubActivities(c9, a4));
        c9.getActivities().add(new ClubActivities(c9, a5));
        c9.getActivities().add(new ClubActivities(c9, a6));
        c9.getActivities().add(new ClubActivities(c9, a7));
        c9.getActivities().add(new ClubActivities(c9, a8));
        c9 = clubService.save(c9);

        c9.getUsers().add(new ClubUsers(c9, userService.findByName("llama001@maildrop.cc")));
        c9.getUsers().add(new ClubUsers(c9, userService.findByName("llama003@maildrop.cc")));


        memberService.deleteAll();
        Member m1 = new Member("testmember1");
        Member m2 = new Member("testmember2");
        Member m3 = new Member("testmember3");
        Member m4 = new Member("testmember4");

        memberService.save(m1);
        memberService.save(m2);
        memberService.save(m3);
        memberService.save(m4);

        // All emoji values (updated 6-8-21)
        String[] reactionValue = {
                // Activity emojis ints
            // Checkin/checkout emojis all
                "1F603", //5 😃
                "1F60E", //4.5 😎
                "1F642",//4 🙂
                "1F974",//3.5 🥴
                "1F610", // 3😐
                "1F634", //2.5 😴
                "1F624", //2 😤
                "1F915", //1.5 🤕
                "1F622", //1 😢
                "1F971", //0.5 🥱


        };
        // Initializing Reactions to values between 1-5
        Double intValue = 5.0;
        for (var emote : reactionValue) {
            Reaction rx1 = new Reaction();
            rx1.setReactionvalue(emote);
            rx1.setReactionint(intValue);
            reactionService.save(rx1);
            intValue -= 0.5;
        }


//        var normalReactionsList = new ArrayList<Reaction>();
//        normalReactionsList.add(reactionRepository.findReactionByReactionvalue("1F601").orElseThrow());
//        normalReactionsList.add(reactionRepository.findReactionByReactionvalue("1F642").orElseThrow());
//        normalReactionsList.add(reactionRepository.findReactionByReactionvalue("1F610").orElseThrow());
//        normalReactionsList.add(reactionRepository.findReactionByReactionvalue("1F641").orElseThrow());
//        normalReactionsList.add(reactionRepository.findReactionByReactionvalue("1F61E").orElseThrow());

        var normalReactionsList = new ArrayList<Reaction>();
        normalReactionsList.add(reactionRepository.findReactionByReactionvalue("1F603").orElseThrow());
        normalReactionsList.add(reactionRepository.findReactionByReactionvalue("1F642").orElseThrow());
        normalReactionsList.add(reactionRepository.findReactionByReactionvalue("1F610").orElseThrow());
        normalReactionsList.add(reactionRepository.findReactionByReactionvalue("1F624").orElseThrow());
        normalReactionsList.add(reactionRepository.findReactionByReactionvalue("1F622").orElseThrow());

        /**
         * We need to set up these reaction so that they are generating a random reaction
         * First for check in
         * Next, generate a random number of activities not exceeding 4
         *      and assign random reactions to them
         * Finally we need to generate another random response for checkout
         * Last, we save all those reactions
         * */
        // Generating 300 memberReactions
        clubMembersService.deleteAll();
        ClubMembers cm1 = new ClubMembers();
        cm1.setClub(c1);    //  id = 15
        cm1.setMember(m1);
        clubMembersService.save(cm1);
        ClubMembers cm2 = new ClubMembers();
        cm2.setClub(c2);    //  id = 16
        cm2.setMember(m1);
        clubMembersService.save(cm2);
        ClubMembers cm3 = new ClubMembers();
        cm3.setClub(c3);    //  id = 17
        cm3.setMember(m1);
        clubMembersService.save(cm3);

        ClubMembers cm4 = new ClubMembers();
        cm4.setClub(c4);    //  id = 16
        cm4.setMember(m2);
        clubMembersService.save(cm4);
        ClubMembers cm5 = new ClubMembers();
        cm5.setClub(c5);    //  id = 17
        cm5.setMember(m2);
        clubMembersService.save(cm5);
        ClubMembers cm6 = new ClubMembers();
        cm6.setClub(c6);    //  id = 18
        cm6.setMember(m2);
        clubMembersService.save(cm6);

        ClubMembers cm7 = new ClubMembers();
        cm7.setClub(c7);
        cm7.setMember(m3);
        clubMembersService.save(cm7);
        ClubMembers cm8 = new ClubMembers();
        cm8.setClub(c8);
        cm8.setMember(m3);
        clubMembersService.save(cm8);
        ClubMembers cm9 = new ClubMembers();
        cm9.setClub(c9);
        cm9.setMember(m3);
        clubMembersService.save(cm9);

        ClubMembers cm10 = new ClubMembers();
        cm10.setClub(c1);
        cm10.setMember(m4);
        clubMembersService.save(cm10);

        m1.getClubs().add(cm1);
        m1.getClubs().add(cm2);
        m1.getClubs().add(cm3);
        memberService.save(m1);

        m2.getClubs().add(cm4);
        m2.getClubs().add(cm5);
        m2.getClubs().add(cm6);
        memberService.save(m2);

        m3.getClubs().add(cm7);
        m3.getClubs().add(cm8);
        m3.getClubs().add(cm9);
        memberService.save(m3);

        m4.getClubs().add(cm10);
        memberService.save(m4);


        ArrayList<Club> clubArrayList = new ArrayList<>();
        var rand = new Random();
        List<Member> allmembers = memberService.findAll();
        ArrayList<Reaction> allreactions = new ArrayList<>();
        reactionRepository.findAll().iterator().forEachRemaining(allreactions::add);

        for (int i = 0; i < 300; i++) {
            //  Gets a random member from a list of all members called currentRandMember
            Member currentRandMember = allmembers.get(rand.nextInt(allmembers.size()));
            //  Grab the list of clubs attached to the user and pick a random club called randClub
            Set<ClubMembers> memberClubs = currentRandMember.getClubs();
            ArrayList<ClubMembers> memberClubsArrayList = new ArrayList<>();
            memberClubs.iterator().forEachRemaining(memberClubsArrayList::add);
            Integer randClubIndex = rand.nextInt(memberClubsArrayList.size());
            ClubMembers randClub = memberClubsArrayList.get(randClubIndex);


            //  Three different reactions to store the checkin/checkout reactions and one to store the possibly multiple regular activity reactions
            MemberReactions checkinReaction;
            MemberReactions memberReaction;
            MemberReactions checkoutReaction;

            //  This was a suggestion from IntelliJz
            final ClubActivities[] checkinActivity = new ClubActivities[1];
            final ClubActivities[] checkoutActivity = new ClubActivities[1];
            ArrayList<ClubActivities> actualActivities = new ArrayList<>();

            // From randClub get a random Activity out of the list of activities at that club called randActivity
            Set<ClubActivities> activities = randClub.getClub().getActivities();
            ArrayList<ClubActivities> randomClubActivitiesArrayList = new ArrayList<>();
            activities.iterator().forEachRemaining(randomClubActivitiesArrayList::add);
            //  This iterates over the activities for this club and stores the
            //  checkin/checkout activities then removes them from the list of activities
            activities.iterator().forEachRemaining(clubActivity -> {
                String activityName = clubActivity.getActivity().getActivityname();
                if(activityName.equals("Club Checkin")){
                    checkinActivity[0] = clubActivity;

                } else if(activityName.equals("Club Checkout")) {
                    checkoutActivity[0] = clubActivity;
                }
                else {
                    actualActivities.add(clubActivity);
                }
            });
            Integer randActivityIndex = rand.nextInt(actualActivities.size());
            var randActivity = actualActivities.get(randActivityIndex);
            //  This creates a random reaction and saves it as a checkin reaction for the current random member
            checkinReaction = memberReactionRepository.save(new MemberReactions(
                    currentRandMember,
                    allreactions.get(rand.nextInt(allreactions.size())),
                    checkinActivity[0]   //  checkin activity
            ));
            currentRandMember.getReactions().add(checkinReaction);

            // wait
            for(long wait = 10000000; wait > 0; wait--){
            }

            //  gets a random integer between 0 - 4
            //  This is how many activities besides checkin/checkout there will be stored
            Integer randActivityCount = rand.nextInt(3);
            for(int j = 0; j < randActivityCount + 1; j++) {
                //  Save the random reaction to the random activity
                    memberReaction = memberReactionRepository.save(new MemberReactions(
                         currentRandMember,
                           normalReactionsList.get(rand.nextInt(normalReactionsList.size())),
                            randActivity
                 ));
                currentRandMember.getReactions().add(memberReaction);

                // Generate a new random activity for the next iteration
                randActivityIndex = rand.nextInt(actualActivities.size());
                randActivity = actualActivities.get(randActivityIndex);

                // wait here too
//                Thread.sleep(100);
                for(long wait = 10000000; wait > 0; wait--) {
                }

            }
            // This creates a random reaction and saves it as a checkout reaction for the current random member
            checkoutReaction = memberReactionRepository.save(new MemberReactions(
                    currentRandMember,
                    allreactions.get(rand.nextInt(allreactions.size())),
                    checkoutActivity[0]   //  checkout activity
            ));
            currentRandMember.getReactions().add(checkoutReaction);


            memberService.save(currentRandMember);
        }
    }
}
