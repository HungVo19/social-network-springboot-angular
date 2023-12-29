package com.olympus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olympus.dto.response.friendship.FriendDTO;
import com.olympus.service.IFriendshipService;
import com.olympus.service.IUserService;
import com.olympus.validator.AppValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FriendshipControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private IFriendshipService friendshipService;
    @MockBean
    private IUserService userService;
    @MockBean
    private AppValidator appValidator;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(value = "spring")
    void testGetFriendsList() throws Exception {
        Long userId = 1L;
        when(userService.findIdByUserDetails(any(UserDetails.class))).thenReturn(userId);
        mockMvc.perform(get("/v1/friendship/friends-list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer yourTokenHere"))
                .andExpect(status().isOk());
    }
}