package com.dk.rr.controller;

import com.dk.rr.IntegrationTest;
import com.dk.rr.TestUtil;
import com.dk.rr.entity.User;
import com.dk.rr.security.AuthoritiesConstants;
import com.dk.rr.service.UserService;
import com.dk.rr.service.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test User Controller")
@AutoConfigureMockMvc
@IntegrationTest
@Slf4j
class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    public User createUserObj() {
        User user = new User();
        user.setName("Dekai Zhang");
        user.setEmail("a@b.com");
        user.setPassword("123456");
        user.setPerm("user:add");
        user.setActivated(true);

        return user;
    }

    @Nested
    @DisplayName("Test user with admin role")
    class adminRole {

        @Test
        @DisplayName("test only admin user can access")
        @WithMockUser(authorities = {AuthoritiesConstants.ADMIN})
        void testOnlyAdminUserCanAccess() throws Exception {
            mockMvc
                    .perform(get("/api/users"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("non admin user got unauthorized error")
        void nonAdminUserGotUnauthorizedError() throws Exception {
            mockMvc
                    .perform(get("/api/users"))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("Test anonymous user access")
    class anonymousUser {

        @Test
        @DisplayName("createNewUser")
        void createNewUser() throws Exception {
            User userObj = createUserObj();

            MvcResult mvcResult = mockMvc
                    .perform(post("/api/users/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userObj)))
                    .andExpect(status().isCreated())
                    .andReturn();

            log.info("Result string ====> {}", mvcResult.getResponse().getContentAsString());

            UserDTO userDTO = TestUtil.convertJsonStringToObject(mvcResult.getResponse().getContentAsString(), UserDTO.class);

            Optional<User> optionalUser = userService.getUserById(userDTO.getId());

            assertTrue(optionalUser.isPresent(), "User is not created or found");

            User createdUser = optionalUser.get();

            assertAll(
                    ()-> assertEquals(userObj.getName(), createdUser.getName()),
                    ()-> assertEquals(userObj.getEmail(), createdUser.getEmail()),
                    ()-> assertEquals(userObj.getPerm(), createdUser.getPerm())
            );

            log.info("UserDTO =====> {}", userDTO);

        }
    }

}