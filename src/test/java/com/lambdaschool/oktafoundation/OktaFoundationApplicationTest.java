package com.lambdaschool.oktafoundation;

import com.lambdaschool.oktafoundation.controllers.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

/**
 * Main class to start the application.
 */
// @EnableJpaAuditing
@SpringBootApplication
@RunWith(Suite.class)
@Suite.SuiteClasses(value = {
        ActivityControllerUnitTestNoDB.class,
        ClubActivityControllerUnitTestNoDB.class,
        ClubControllerUnitTestNoDB.class,
        ClubUsersControllerUnitTestNoDB.class,
        RolesControllerUnitTestNoDB.class,
        UserControllerIntegrationTestWithDB.class,
        UserControllerUnitTestNoDB.class,
        UseremailControllerUnitTestNoDB.class
})
public class OktaFoundationApplicationTest
{
    @Test
    public void contextLoads() {
    }
    /**
     * Main method to start the application.
     *
     * @param args Not used in this application.
     */


//    public static void main(String[] args)
//    {
//        SpringApplication.run(OktaFoundationApplicationTest.class,
//            args);
//    }
}
