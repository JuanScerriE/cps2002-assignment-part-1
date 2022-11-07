package com.cps2002.consultancyservice;

import com.cps2002.consultancyservice.web.controllers.requests.EchoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EchoControllerTests extends Tests {

    public EchoControllerTests(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    @Test
    public void testEcho() throws Exception {
        EchoRequest request = new EchoRequest();

        request.setValue("Aw Dinja!");

        mockMvc.perform(post("/echo")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value("Aw Dinja!"));
    }

}
