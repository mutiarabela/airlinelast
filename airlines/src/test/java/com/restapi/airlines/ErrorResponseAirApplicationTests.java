package com.restapi.airlines;

import com.restapi.airlines.controller.AirlineController;
import com.restapi.airlines.controller.UserController;
import com.restapi.airlines.service.AirlineService;
import com.restapi.airlines.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({UserController.class, AirlineController.class})
@Slf4j
public class ErrorResponseAirApplicationTests extends AbstractJUnit4SpringContextTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AirlineService airlineService;

    @Test
    public void createUser() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/Users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"idUser\": \"7\",\n" +
                        "\"firstNameUser\": \"M\",\n" +
                        "\"lastNameUser\": \"B\",\n" +
                        "\"phoneNumUser\": \"0\",\n" +
                        "\"userNameUser\": \"m\",\n" +
                        "\"emailUser\": \"b\",\n" +
                        "\"passwordUser\": \"1\"\n" +
                        "}"))
                .andExpect(status().is4xxClientError())
                .andDo(print()).andReturn();

        String content = result.getResponse().getContentAsString();
        log.info(content);
    }

    @Test
    public void getUser() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/Users/7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print()).andReturn();

        String content = result.getResponse().getContentAsString();
        log.info(content);
    }

    @Test
    public void getAirline() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/Airlines/7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print()).andReturn();

        String content = result.getResponse().getContentAsString();
        log.info(content);
    }
}
