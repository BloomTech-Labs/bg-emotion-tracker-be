package com.lambdaschool.oktafoundation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.oktafoundation.OktaFoundationApplicationTest;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.ActivityRepository;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.UserRepository;
import com.lambdaschool.oktafoundation.services.ActivityService;
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
@WithMockUser(username = "admin",
        roles = {"USER", "ADMIN"})
@AutoConfigureMockMvc
public class ActivityControllerUnitTestNoDB {

   @Autowired
   private WebApplicationContext webApplicationContext;

   private MockMvc mockMvc;

   @MockBean
   private UserRepository userrepos;


   @MockBean
   private ActivityService activityService;

   @MockBean
   private ActivityRepository activityRepository;


   private List<Activity> activityList;

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

      //Activities
      activityList = new ArrayList<>();
      Activity a1 = new Activity("Club Attendance");
      a1.setActivityid(201);
      activityList.add(a1);
      Activity a2 = new Activity("Arts & Crafts");
      a2.setActivityid(202);
      activityList.add(a2);
      Activity a3 = new Activity("Archery");
      a3.setActivityid(203);
      activityList.add(a3);


      System.out.println("\n*** Seed Data ***");
      for (Activity c : activityList) {
         System.out.println(c.getActivityid() + " " + c.getActivityname());
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
   public void listAllActivities() throws Exception{
      String apiUrl = "/activities/activities";

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(activityService.findAll())
              .thenReturn(activityList);

      RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
              .accept(MediaType.APPLICATION_JSON);
      MvcResult r = mockMvc.perform(rb)
              .andReturn(); // this could throw an exception
      String tr = r.getResponse()
              .getContentAsString();

      ObjectMapper mapper = new ObjectMapper();

      String er = mapper.writeValueAsString(activityList);

      System.out.println("Expect: " + er);
      System.out.println("Actual: " + tr);

      assertEquals("Rest API Returns List",
              er,
              tr);

   }

   @Test
   public void getActivityById() throws Exception{
      String apiUrl = "/activities/activity/202";
      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(activityService.findActivityById(202L))
              .thenReturn(activityList.get(1));

      RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
              .accept(MediaType.APPLICATION_JSON);
      MvcResult r = mockMvc.perform(rb)
              .andReturn(); // this could throw an exception
      String tr = r.getResponse()
              .getContentAsString();

      ObjectMapper mapper = new ObjectMapper();

      String er = mapper.writeValueAsString(activityList.get(1));

      System.out.println("Expect: " + er);
      System.out.println("Actual: " + tr);

      assertEquals("Rest API Returns List",
              er,
              tr);


   }

   @Test
   public void addNewActivity() throws Exception{

      String apiUrl = "/activities/activity/";

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(activityService.save(any(Activity.class)))
              .thenReturn(activityList.get(0));

      RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON)
              .content("{\"activityname\": \"Test\"}");

      mockMvc.perform(rb)
              .andExpect(status().isCreated())
              .andDo(MockMvcResultHandlers.print());

   }

   @Test
   public void updateActivity() throws Exception{
      String apiUrl = "/activities/activity/203";

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(activityService.update(any(Activity.class),any(Long.class)))
              .thenReturn(activityList.get(2));

      RequestBuilder rb = MockMvcRequestBuilders.patch(apiUrl,201L)
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON)
              .content("{\"activityname\": \"Fencing\"}");

      mockMvc.perform(rb)
              .andExpect(status().is2xxSuccessful())
              .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void deleteActivityById() throws Exception{
      String apiUrl = "/activities/activity/202";

      RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl,
              "202")
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON);

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      mockMvc.perform(rb)
              .andExpect(status().is2xxSuccessful())
              .andDo(MockMvcResultHandlers.print());
   }

}