package com.hsenidlanka.attendancemarkerbackend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hsenidlanka.attendancemarkerbackend.model.Company;
import com.hsenidlanka.attendancemarkerbackend.model.ERole;
import com.hsenidlanka.attendancemarkerbackend.model.Role;
import com.hsenidlanka.attendancemarkerbackend.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestApp extends AttendanceServiceMarkerBackendApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Test add a company
     **/
    @Test
    public void addCompanyTest() throws Exception {
        mockMvc.perform(post("/companies")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("1")).andExpect(jsonPath("$.companyName").value("company1")).andExpect(jsonPath("$.companyLocation").value("location1"));

    }

    /**
     * Test retrieve a company
     **/
    @Test
    public void getCompanyTest() throws Exception {
        mockMvc.perform(get("/companies")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("2")).andExpect(jsonPath("$.companyName").value("company2")).andExpect(jsonPath("$.companyLocation").value("location2"));

    }

    /**
     * Test add a user
     **/
    @Test
    public void addUserTest() throws Exception {
        mockMvc.perform(post("/users")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("1")).andExpect(jsonPath("$.username").value("user1")).andExpect(jsonPath("$.email").value("user1@gmail.com"))
                .andExpect(jsonPath("$.password").value("12341234")).andExpect(jsonPath("$.company").value(new Company("1","company1","location1")));

    }

    /**
     * Test retrieve a user
     **/
    @Test
    public void getUserTest() throws Exception {
        mockMvc.perform(get("/users")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("2")).andExpect(jsonPath("$.username").value("user2")).andExpect(jsonPath("$.email").value("user2@gmail.com"))
                .andExpect(jsonPath("$.password").value("12341234")).andExpect(jsonPath("$.company").value(new Company("2","company2","location2")));

    }

    /**
     * Test add an attendance
     **/
    @Test
    public void addAttendanceTest() throws Exception {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_USER));
        mockMvc.perform(post("/attendances")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("1")).andExpect(jsonPath("$.date").value("2023-05-27")).andExpect(jsonPath("$.startTime").value("08:25"))
                .andExpect(jsonPath("$.endTime").value("17:45")).andExpect(jsonPath("$.workedHours").value(9)).andExpect(jsonPath("$.halfDay").value(false))
                .andExpect(jsonPath("$.user").value(new User("1","user1","user1@gmail.com","12341234",roles,new Company("1","company1","location1"))));

    }

    /**
     * Test retrieve an attendance
     **/
    @Test
    public void getAttendanceTest() throws Exception {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_USER));
        mockMvc.perform(get("/attendances")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("2")).andExpect(jsonPath("$.date").value("2023-05-26")).andExpect(jsonPath("$.startTime").value("08:35"))
                .andExpect(jsonPath("$.endTime").value("10:45")).andExpect(jsonPath("$.workedHours").value(2)).andExpect(jsonPath("$.halfDay").value(true))
                .andExpect(jsonPath("$.user").value(new User("2","user2","user2@gmail.com","12341234",roles,new Company("2","company2","location2"))));

    }

}