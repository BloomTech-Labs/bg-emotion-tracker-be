package com.lambdaschool.oktafoundation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.oktafoundation.OktaFoundationApplicationTest;
import com.lambdaschool.oktafoundation.models.*;
import com.lambdaschool.oktafoundation.repository.ClubRepository;
import com.lambdaschool.oktafoundation.repository.RoleRepository;
import com.lambdaschool.oktafoundation.repository.UserRepository;
import com.lambdaschool.oktafoundation.services.ClubService;
import com.lambdaschool.oktafoundation.services.RoleService;
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
public class RolesControllerUnitTestNoDB {

   @Autowired
   private WebApplicationContext webApplicationContext;

   private MockMvc mockMvc;

   @MockBean
   private RoleService roleService;

   @MockBean
   private RoleRepository roleRepository;

   @MockBean
   private UserRepository userRepository;

   private List<Role> roleList;


   private User u1;

   @Before
   public void setUp() throws Exception {

      roleList = new ArrayList<>();
      Role r1 = new Role("admin");
      Role r2 = new Role("user");
      Role r3 = new Role("data");
      r1.setRoleid(1);
      r2.setRoleid(2);
      r3.setRoleid(3);
      roleList.add(r1);
      roleList.add(r2);
      roleList.add(r3);

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


      System.out.println("\n*** Seed Data ***");
      for (Role r : roleList) {
         System.out.println(r.getRoleid() + " " + r.getName());
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
   public void listRoles() throws Exception{
      String apiUrl = "/roles/roles";

      Mockito.when(userRepository.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(roleService.findAll())
              .thenReturn(roleList);

      RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
              .accept(MediaType.APPLICATION_JSON);
      MvcResult r = mockMvc.perform(rb)
              .andReturn(); // this could throw an exception
      String tr = r.getResponse()
              .getContentAsString();

      ObjectMapper mapper = new ObjectMapper();
      String er = mapper.writeValueAsString(roleList);

      System.out.println("Expect: " + er);
      System.out.println("Actual: " + tr);

      assertEquals("Rest API Returns List",
              tr,
              er);
   }

   @Test
   public void getRoleById() throws Exception {
      String apiUrl = "/roles/role/2";

      Mockito.when(userRepository.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(roleService.findRoleById(2L))
              .thenReturn(roleList.get(1));

      RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
              .accept(MediaType.APPLICATION_JSON);
      MvcResult r = mockMvc.perform(rb)
              .andReturn(); // this could throw an exception
      String tr = r.getResponse()
              .getContentAsString();

      ObjectMapper mapper = new ObjectMapper();
      String er = mapper.writeValueAsString(roleList.get(1));

      System.out.println("Expect: " + er);
      System.out.println("Actual: " + tr);

      assertEquals("Rest API returns Club",
              tr,
              er);
   }

   @Test
   public void getRoleByName() throws Exception {
      String apiUrl = "/roles/role/name/user";

      Mockito.when(userRepository.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(roleService.findByName("user"))
              .thenReturn(roleList.get(1));

      RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
              .accept(MediaType.APPLICATION_JSON);
      MvcResult r = mockMvc.perform(rb)
              .andReturn(); // this could throw an exception
      String tr = r.getResponse()
              .getContentAsString();

      ObjectMapper mapper = new ObjectMapper();
      String er = mapper.writeValueAsString(roleList.get(1));

      System.out.println("Expect: " + er);
      System.out.println("Actual: " + tr);

      assertEquals("Rest API returns Club",
              tr,
              er);
   }

   @Test
   public void addNewRole() throws Exception {
      String apiUrl = "/roles/role/";

      Mockito.when(userRepository.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(roleService.save(any(Role.class)))
              .thenReturn(roleList.get(0));

      RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON)
              .content("{\"name\": \"test\"}");

      mockMvc.perform(rb)
              .andExpect(status().isCreated())
              .andDo(MockMvcResultHandlers.print());
   }

   @Test
   public void putUpdateRole() throws Exception {
      String apiUrl = "/roles/role/2";

      Mockito.when(userRepository.findByUsername(u1.getUsername()))
              .thenReturn(u1);


      Mockito.when(roleService.update(any(Long.class),any(Role.class)))
              .thenReturn(roleList.get(1));

      RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl)
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON)
              .content("{\"name\": \"test\"}");

      mockMvc.perform(rb)
              .andExpect(status().is2xxSuccessful())
              .andDo(MockMvcResultHandlers.print());
   }
}