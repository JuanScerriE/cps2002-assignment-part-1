package com.cps2002.customermanagementservice;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EchoControllerTests extends ControllerTests {

    public EchoControllerTests(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

//    @Test
//    public void testEcho() throws Exception {
//        EchoRequest request = new EchoRequest();
//
//        request.setValue("Aw Dinja!");
//
//        mockMvc.perform(post("/echo")
//                        .content(toJsonString(request))
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.uuid").exists())
//                .andExpect(jsonPath("$.value").value("Aw Dinja!"));
//    }
//
//    @Test
//    public void testEchoSaving() throws Exception {
//        EchoRequest request = new EchoRequest();
//
//        request.setValue("Aw dinja!");
//
//        String stringResponse = mockMvc.perform(post("/echo")
//                        .content(toJsonString(request))
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        EchoResponse response = fromJsonString(stringResponse, EchoResponse.class);
//
//        mockMvc.perform(get("/get-echo")
//                        .queryParam("uuid", response.getUuid()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.uuid").value(response.getUuid()));
//    }

}
