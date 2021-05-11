package com.lambdaschool.oktafoundation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.oktafoundation.OktaFoundationApplicationTest;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.ActivityRepository;
import com.lambdaschool.oktafoundation.repository.ClubActivityRepository;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.UserRepository;
import com.lambdaschool.oktafoundation.services.ActivityService;
import com.lambdaschool.oktafoundation.services.ClubActivityService;
import com.lambdaschool.oktafoundation.services.ClubService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = OktaFoundationApplicationTest.class,
        properties = {
                "command.line.runner.enabled=false"})
@AutoConfigureMockMvc
@WithMockUser(username = "admin",
        roles = {"USER", "ADMIN"})
public class ClubActivityControllerUnitTestNoDB {


   @Autowired
   private WebApplicationContext webApplicationContext;

   private MockMvc mockMvc;



   @MockBean
   private UserRepository userrepos;


   @MockBean
   private ClubService clubService;

   @MockBean
   private ClubRepository clubRepository;

   @MockBean
   private ActivityService activityService;

   @MockBean
   private ActivityRepository activityRepository;

   @MockBean
   private ClubActivityService clubActivityService;

   @MockBean
   private ClubActivityRepository clubActivityRepository;


   private List<Club> clubList;
   private List<Activity> activityList;
   private List<ClubActivities> clubActivitiesList;

   private User u1;



   @Before
   public void setUp() throws Exception {

      Role r1 = new Role("admin");
      r1.setRoleid(1);
      Role r2 = new Role("user");
      r2.setRoleid(2);
      Role r3 = new Role("data");
      r3.setRoleid(3);
      u1 = new User("admin");
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
              .get(0)
              .setUseremailid(10);

      u1.getUseremails()
              .add(new Useremail(u1,
                      "admin@mymail.local"));
      u1.getUseremails()
              .get(1)
              .setUseremailid(11);

      u1.setUserid(101);

      activityList = new ArrayList<>();
      clubList = new ArrayList<>();
      clubActivitiesList = new ArrayList<>();

      Activity a1 = new Activity("Club Attendance");
      Activity a2 = new Activity("Arts & Crafts");
      Activity a3 = new Activity("Archery");
      Activity a4 = new Activity("Basketball");
      Activity a5 = new Activity("Homework Help");
      a1.setActivityid(101L);
      a2.setActivityid(102L);
      a3.setActivityid(103L);
      a4.setActivityid(104L);
      a5.setActivityid(105L);
      activityList.add(a1);
      activityList.add(a2);
      activityList.add(a3);
      activityList.add(a4);
      activityList.add(a5);

      Club c1 = new Club("Anderson");
      c1.setClubid(201L);
      clubList.add(c1);
      ClubActivities ca1 = new ClubActivities(clubList.get(0),activityList.get(0));
      clubActivitiesList.add(ca1);
      clubList.get(0).getActivities()
              .add(ca1);
      ClubActivities ca2 = new ClubActivities(clubList.get(0),activityList.get(1));
      clubActivitiesList.add(ca2);
      clubList.get(0).getActivities()
              .add(ca2);
      ClubActivities ca3 = new ClubActivities(clubList.get(0),activityList.get(2));
      clubActivitiesList.add(ca3);
      clubList.get(0).getActivities()
              .add(ca3);
      ClubActivities ca4 = new ClubActivities(clubList.get(0),activityList.get(3));
      clubActivitiesList.add(ca4);
      clubList.get(0).getActivities()
              .add(ca4);
      ClubActivities ca5 = new ClubActivities(clubList.get(0),activityList.get(4));
      clubActivitiesList.add(ca5);
      clubList.get(0).getActivities()
              .add(ca5);


      System.out.println("\n*** Seed Data ***");
      for (ClubActivities ca : clubActivitiesList) {
         System.out.println(ca.getClub().getClubname() + " " +ca.getActivity().getActivityname());
      }
      System.out.println("*** Seed Data ***\n");

      RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
              .apply(SecurityMockMvcConfigurers.springSecurity())
              .build();

   }

   @After
   public void tearDown() throws Exception {
   }

   @Test
   public void listAllClubActivities() throws Exception{
      String apiUrl = "/clubactivities/clubactivities";

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);
      System.out.println(clubActivitiesList);

      Mockito.when(clubActivityService.findAll())
              .thenReturn(clubActivitiesList);

      RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
              .accept(MediaType.APPLICATION_JSON);
      MvcResult r = mockMvc.perform(rb)
              .andReturn(); // this could throw an exception
      String tr = r.getResponse()
              .getContentAsString();

      ObjectMapper mapper = new ObjectMapper();
      String er = mapper.writeValueAsString(clubActivitiesList);

      System.out.println("Expect: " + er);
      System.out.println("Actual: " + tr);

      assertEquals("Rest API Returns List",
              er,
              tr) ;


   }

   @Test
   public void getClubActivityById() throws Exception {
      // Not tested, this method should not be used. (composite ID)
   }
}