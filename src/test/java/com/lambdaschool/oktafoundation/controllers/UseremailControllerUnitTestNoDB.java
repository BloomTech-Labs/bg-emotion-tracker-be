package com.lambdaschool.oktafoundation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.oktafoundation.OktaFoundationApplicationTest;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.UserRepository;
import com.lambdaschool.oktafoundation.repository.UseremailRepository;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.UserService;
import com.lambdaschool.oktafoundation.services.UseremailService;
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
public class UseremailControllerUnitTestNoDB {

   @Autowired
   private WebApplicationContext webApplicationContext;

   private MockMvc mockMvc;

   @MockBean
   private UseremailService useremailService;

   @MockBean
   private UseremailRepository useremailRepository;

   @MockBean
   private UserRepository userrepos;

   private List<Useremail> useremailList;

   private User u1; // special as needed for security

   @Before
   public void setUp()
   {
      useremailList = new ArrayList<>();

      Role r1 = new Role("admin");
      u1 = new User("admin");
      r1.setRoleid(1);
      u1.getRoles()
              .add(new UserRoles(u1,
                      r1));
      u1.setUserid(100L);

      Useremail em1 = new Useremail(u1,"test@email.com");
      em1.setUseremailid(201L);
      useremailList.add(em1);
      u1.getUseremails().add(em1);
      Useremail em2 = new Useremail(u1,"test2@mymail.com");
      em2.setUseremailid(202L);
      useremailList.add(em2);
      u1.getUseremails().add(em2);


      System.out.println("\n*** Seed Data ***");
      for (Useremail u : useremailList)
      {
         System.out.println(u.getUseremailid() + " " + u.getUseremail());
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
   public void listAllUseremails() throws Exception{
      String apiUrl = "/useremails/useremails";

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(useremailService.findAll())
              .thenReturn(useremailList);

      RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
              .accept(MediaType.APPLICATION_JSON);
      MvcResult r = mockMvc.perform(rb)
              .andReturn(); // this could throw an exception
      String tr = r.getResponse()
              .getContentAsString();

      ObjectMapper mapper = new ObjectMapper();
      String er = mapper.writeValueAsString(useremailList);

      System.out.println("Expect: " + er);
      System.out.println("Actual: " + tr);

      assertEquals("Rest API Returns List",
              tr,
              er);

   }

   @Test
   public void getUserEmailById() throws Exception{
      String apiUrl = "/useremails/useremail/202";

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(useremailService.findUseremailById(202L))
              .thenReturn(useremailList.get(1));

      RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
              .accept(MediaType.APPLICATION_JSON);
      MvcResult r = mockMvc.perform(rb)
              .andReturn(); // this could throw an exception
      String tr = r.getResponse()
              .getContentAsString();

      ObjectMapper mapper = new ObjectMapper();
      String er = mapper.writeValueAsString(useremailList.get(1));

      System.out.println("Expect: " + er);
      System.out.println("Actual: " + tr);

      assertEquals("Rest API returns Club",
              tr,
              er);
   }

   @Test
   public void deleteUserEmailById() throws Exception {
      String apiUrl = "/useremails/useremail/{clubid}";

      RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl,
              201L)
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON);

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      mockMvc.perform(rb)
              .andExpect(status().is2xxSuccessful())
              .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void updateUserEmail() throws Exception {
      String apiUrl = "/useremails/useremail/201/email/test0@test.com";

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(useremailService.update(any(Long.class),any(String.class)))
              .thenReturn(useremailList.get(0));

      RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl);

      mockMvc.perform(rb)
              .andExpect(status().is2xxSuccessful())
              .andDo(MockMvcResultHandlers.print());
   }


   @Test
   public void addNewUserEmail() throws Exception {

      String apiUrl = "/useremails/user/100/email/test3@email.com";

      Mockito.when(userrepos.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(useremailService.save(any(Long.class),any(String.class)))
              .thenReturn(useremailList.get(0));

      RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl);

      mockMvc.perform(rb)
              .andExpect(status().isCreated())
              .andDo(MockMvcResultHandlers.print());
   }


}