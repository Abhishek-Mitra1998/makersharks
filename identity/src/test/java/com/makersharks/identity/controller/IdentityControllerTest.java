package com.makersharks.identity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makersharks.identity.constants.ApiResponse;
import com.makersharks.identity.exception.CustomException;
import com.makersharks.identity.service.UserService;
import com.makersharks.identity.util.UserServiceHelper;
import com.makersharks.identity.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = {IdentityController.class})
public class IdentityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserUtil userUtil;

    @Test
    public void whenRegister_thenHappyFlow() throws Exception {
        Mockito.when(userService.register(Mockito.any())).thenReturn(
                ApiResponse.<String>builder()
                        .success(true)
                        .data("USER CREATED 12345")
                        .build()
        );

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/register")
                        .content(new ObjectMapper().writeValueAsString(UserServiceHelper.getUserDaoMock()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());


        Mockito.verify(this.userService, Mockito.times(1)).register(Mockito.any());
    }

    @Test
    public void whenLogin_thenHappyFlow() throws Exception {
        Mockito.when(userService.register(Mockito.any())).thenReturn(
                ApiResponse.<String>builder()
                        .success(true)
                        .data("Mock Token Created")
                        .build()
        );

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/user/login")
                                .content(new ObjectMapper().writeValueAsString(UserServiceHelper.getLoginDaoMock()))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());


        Mockito.verify(this.userService, Mockito.times(1)).login(Mockito.any());
    }

}
