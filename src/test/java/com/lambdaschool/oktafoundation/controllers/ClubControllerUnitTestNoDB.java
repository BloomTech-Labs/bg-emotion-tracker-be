package com.lambdaschool.oktafoundation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.oktafoundation.OktaFoundationApplicationTest;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.UserRepository;
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
public class ClubControllerUnitTestNoDB {


   @Autowired
   private WebApplicationContext webApplicationContext;

   private MockMvc mockMvc;

   @MockBean
   private UserRepository userrepos;


   @MockBean
   private ClubService clubService;

   @MockBean
   private ClubRepository clubRepository;


   private List<Club> clubList;

   private User u1;


   @Before
   public void setUp() {
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
      clubList = new ArrayList<>();

      Club c1 = new Club("Anderson");
      c1.setClubid(201);
      clubList.add(c1);

      Club c2 = new Club("Caitlin");
      c2.setClubid(202);
      clubList.add(c2);
      Club c3 = new Club("Grossman");
      c3.setClubid(203);
      clubList.add(c3);

      System.out.println("\n*** Seed Data ***");
      for (Club c : clubList) {
         System.out.println(c.getClubid() + " " + c.getClubname());
      }
      System.out.println("*** Seed Data ***\n");

      RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

      mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
              .apply(SecurityMockMvcConfigurers.springSecurity())
              .build();
   }

   @After
   public void tearDown() {
   }

   @Test
   public void listAllClubs() throws Exception {
      String apiUrl = "/clubs/clubs";

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(clubService.findAll())
              .thenReturn(clubList);

      RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
              .accept(MediaType.APPLICATION_JSON);
      MvcResult r = mockMvc.perform(rb)
              .andReturn(); // this could throw an exception
      String tr = r.getResponse()
              .getContentAsString();

      ObjectMapper mapper = new ObjectMapper();
      String er = mapper.writeValueAsString(clubList);

      System.out.println("Expect: " + er);
      System.out.println("Actual: " + tr);

      assertEquals("Rest API Returns List",
              tr,
              er);
   }

   @Test
   public void getClubById() throws Exception {

      String apiUrl = "/clubs/club/202";

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(clubService.findClubById(202L))
              .thenReturn(clubList.get(1));

      RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
              .accept(MediaType.APPLICATION_JSON);
      MvcResult r = mockMvc.perform(rb)
              .andReturn(); // this could throw an exception
      String tr = r.getResponse()
              .getContentAsString();

      ObjectMapper mapper = new ObjectMapper();
      String er = mapper.writeValueAsString(clubList.get(1));

      System.out.println("Expect: " + er);
      System.out.println("Actual: " + tr);

      assertEquals("Rest API returns Club",
              tr,
              er);
   }

   @Test
   public void addNewClub() throws Exception {
      String apiUrl = "/clubs/club/newClub";

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(clubService.save(any(Club.class)))
              .thenReturn(clubList.get(0));

      RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON)
              .content("{\"clubname\": \"Anderson\"}");

      mockMvc.perform(rb)
              .andExpect(status().isCreated())
              .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void updateClub() throws Exception {
      String apiUrl = "/clubs/club/201";

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(clubService.update(any(Club.class),any(Long.class)))
              .thenReturn(clubList.get(0));

      RequestBuilder rb = MockMvcRequestBuilders.patch(apiUrl,201L)
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON)
              .content("{\"clubname\": \"Sanderson\"}");

      mockMvc.perform(rb)
              .andExpect(status().is2xxSuccessful())
              .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void deleteClubById() throws Exception {
      String apiUrl = "/clubs/club/{clubid}";

      RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl,
              "203")
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON);

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      mockMvc.perform(rb)
              .andExpect(status().is2xxSuccessful())
              .andDo(MockMvcResultHandlers.print());
   }

}