package com.hsenidlanka.attendancemarkerbackend.controller;

import com.hsenidlanka.attendancemarkerbackend.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("")
public class TestController {

    /**
     * Test add a company
     **/
    @PostMapping("/companies")
    public Company addCompanyTest() {
        Company testCompany = new Company();
        testCompany.setId("1");
        testCompany.setCompanyName("company1");
        testCompany.setCompanyLocation("location1");
        return testCompany;
    }

    /**
     * Test retrieve company
     **/
    @GetMapping("/companies")
    public Company getCompanyTest() {
        Company testCompany = new Company();

        testCompany.setId("2");
        testCompany.setCompanyName("company2");
        testCompany.setCompanyLocation("location2");

        return testCompany;
    }

    /**
     * Test add a user
     **/
    @PostMapping("/users")
    public User addUserTest() {
        User testUser = new User();

        testUser.setId("1");
        testUser.setUsername("user1");
        testUser.setEmail("user1@gmail.com");
        testUser.setPassword("12341234");
        testUser.setCompany(new Company("1","company1","location1"));
        return testUser;
    }

    /**
     * Test retrieve user
     **/
    @GetMapping("/users")
    public User getUserTest() {
        User testUser = new User();
        testUser.setId("2");
        testUser.setUsername("user2");
        testUser.setEmail("user2@gmail.com");
        testUser.setPassword("12341234");
        testUser.setCompany(new Company("2","company2","location2"));
        return testUser;
    }

    /**
     * Test add a attendance
     **/
    @PostMapping("/attendances")
    public Attendance addAttendanceTest() {
        Attendance testAttendance = new Attendance();
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_USER));
        testAttendance.setId("1");
        testAttendance.setDate("2023-05-27");
        testAttendance.setStartTime("08:25");
        testAttendance.setEndTime("17:45");
        testAttendance.setWorkedHours(9);
        testAttendance.setHalfDay(false);
        testAttendance.setUser(new User("1","user1","user1@gmail.com","12341234",roles,new Company("1","company1","location1")));
        return testAttendance;
    }

    /**
     * Test retrieve an attendance
     **/
    @GetMapping("/attendances")
    public Attendance getAttendanceTest() {
        Attendance testAttendance = new Attendance();
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_USER));
        testAttendance.setId("2");
        testAttendance.setDate("2023-05-26");
        testAttendance.setStartTime("08:35");
        testAttendance.setEndTime("10:45");
        testAttendance.setWorkedHours(2);
        testAttendance.setHalfDay(true);
        testAttendance.setUser(new User("2","user2","user2@gmail.com","12341234", roles,new Company("1","company1","location1")));
        return testAttendance;
    }
}
