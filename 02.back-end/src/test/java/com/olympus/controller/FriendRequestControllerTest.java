package com.olympus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olympus.service.IFriendRequestService;
import com.olympus.service.IFriendshipService;
import com.olympus.service.IUserService;
import com.olympus.validator.AppValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FriendRequestControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private AppValidator appValidator;
    @MockBean
    private IFriendRequestService friendRequestService;
    @MockBean
    private IFriendshipService friendshipService;
    @MockBean
    private IUserService userService;

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
    void testGetListRequestReceived() throws Exception {
        //Arrange
        Long userId = 1L;
        when(userService.findIdByUserDetails(any(UserDetails.class))).thenReturn(userId);

        //Act & Assert
        mockMvc.perform(get("/v1/friends/requests/received", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring")
    void testGetListRequestSent() throws Exception {
        //Arrange
        Long userId = 1L;
        when(userService.findIdByUserDetails(any(UserDetails.class))).thenReturn(userId);

        //Act & Assert
        mockMvc.perform(get("/v1/friends/requests/sent", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "spring")
    void testSendFriendRequest_Success() throws Exception {
        //Arrange
        Long userId = 1L;
        Long targetId = 2L;
        when(userService.existByUserId(anyLong())).thenReturn(true);
        when(userService.findIdByUserDetails(any(UserDetails.class))).thenReturn(userId);
        when(appValidator.validateFriendRequestSent(any(UserDetails.class), anyLong())).thenReturn(null);

        //Act & Assert
        mockMvc.perform(post("/v1/friends/requests/sent/{targetUserId}", targetId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}