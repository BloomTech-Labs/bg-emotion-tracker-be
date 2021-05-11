package com.lambdaschool.oktafoundation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.oktafoundation.OktaFoundationApplicationTest;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.ClubUsersRepository;
import com.lambdaschool.oktafoundation.repository.UserRepository;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.ClubUsersService;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = OktaFoundationApplicationTest.class,
        properties = {
                "command.line.runner.enabled=false"})
@AutoConfigureMockMvc
@WithMockUser(username = "admin",
        roles = {"USER", "ADMIN"})
public class ClubUsersControllerUnitTestNoDB {



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
   private ClubUsersService clubUsersService;

   @MockBean
   private ClubUsersRepository clubUsersRepository;


   private List<Club> clubList;

   private List<ClubUsers> clubUsersList;

   private User u1;

   private List<User> userList;


   @Before
   public void setUp() throws Exception {
      userList = new ArrayList<>();
      clubList = new ArrayList<>();
      clubUsersList = new ArrayList<>();

      Role r1 = new Role("admin");
      r1.setRoleid(1);
      Role r2 = new Role("user");
      r2.setRoleid(2);
      Role r3 = new Role("data");
      r3.setRoleid(3);

      // admin, data, user
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
      userList.add(u1);

      // data, user
      User u2 = new User("cinnamon");
      u1.getRoles()
              .add(new UserRoles(u2,
                      r2));
      u1.getRoles()
              .add(new UserRoles(u2,
                      r3));

      u2.getUseremails()
              .add(new Useremail(u2,
                      "cinnamon@mymail.local"));
      u2.getUseremails()
              .get(0)
              .setUseremailid(20);

      u2.getUseremails()
              .add(new Useremail(u2,
                      "hops@mymail.local"));
      u2.getUseremails()
              .get(1)
              .setUseremailid(21);

      u2.getUseremails()
              .add(new Useremail(u2,
                      "bunny@email.local"));
      u2.getUseremails()
              .get(2)
              .setUseremailid(22);

      u2.setUserid(102);
      userList.add(u2);

      // user
      User u3 = new User("testingbarn");
      u3.getRoles()
              .add(new UserRoles(u3,
                      r1));

      u3.getUseremails()
              .add(new Useremail(u3,
                      "barnbarn@email.local"));
      u3.getUseremails()
              .get(0)
              .setUseremailid(30);

      u3.setUserid(103);
      userList.add(u3);

      User u4 = new User("testingcat");
      u4.getRoles()
              .add(new UserRoles(u4,
                      r2));

      u4.setUserid(104);
      userList.add(u4);

      User u5 = new User("testingdog");
      u4.getRoles()
              .add(new UserRoles(u5,
                      r2));

      u5.setUserid(105);
      userList.add(u5);


      Club c1 = new Club("test1");
      c1.setClubid(101L);

      ClubUsers cu1 = new ClubUsers(u1,c1);
      clubUsersList.add(cu1);


      Club c2 = new Club("test2");
      c2.setClubid(102L);
      ClubUsers cu2 = new ClubUsers(u1,c2);
      clubUsersList.add(cu2);

      System.out.println("\n*** Seed Data ***");
      for (ClubUsers c : clubUsersList) {
         System.out.println(c.getUser().getUsername() + " " + c.getClub().getClubname());
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
   public void listAllClubUsers() throws Exception{
      String apiUrl = "/clubusers/clubusers";

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(clubUsersService.findAll())
              .thenReturn(clubUsersList);

      RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
              .accept(MediaType.APPLICATION_JSON);
      MvcResult r = mockMvc.perform(rb)
              .andReturn(); // this could throw an exception
      String tr = r.getResponse()
              .getContentAsString();

      ObjectMapper mapper = new ObjectMapper();
      String er = mapper.writeValueAsString(clubUsersList);

      System.out.println("Expect: " + er);
      System.out.println("Actual: " + tr);

      assertEquals("Rest API Returns List",
              tr,
              er);
   }

   @Test
   public void getClubUserById() {
      // composite ID, not tested
   }
}