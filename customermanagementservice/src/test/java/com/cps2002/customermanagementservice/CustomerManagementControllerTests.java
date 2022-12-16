package com.cps2002.customermanagementservice;

import com.cps2002.customermanagementservice.data.repositories.CustomerRepository;
import com.cps2002.customermanagementservice.services.CustomerManagementService;
import com.cps2002.customermanagementservice.services.models.Customer;
import com.cps2002.customermanagementservice.web.controllers.requests.CreateCustomerRequest;
import com.cps2002.customermanagementservice.web.controllers.requests.UpdateCustomerRequest;
import com.cps2002.customermanagementservice.web.controllers.responses.CreateCustomerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedList;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerManagementControllerTests extends ControllerTests {

    @Autowired
    private CustomerManagementService customerManagementService;

    @Autowired
    private CustomerRepository customerRepo;

    @MockBean
    private RestTemplate rest;

    public CustomerManagementControllerTests(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    @BeforeEach
    public void clearH2Db() {
        customerRepo.deleteAll();
    }

    @Test
    public void testCreateCustomer() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest();

        request.setName("test-user");
        request.setSpecialityPreference("my-preference");

        String json = mockMvc.perform(post("/create")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CreateCustomerResponse response = fromJsonString(json, CreateCustomerResponse.class);

        Customer customer = customerManagementService.getCustomer(response.getUuid()).get();

        assertTrue(request.getName().equals(customer.getName()));
        assertTrue(request.getSpecialityPreference().equals(customer.getSpecialityPreference()));
    }

    @Test
    public void testCreateCustomerNoPreference() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest();

        request.setName("test-user");
        request.setSpecialityPreference(null);

        String json = mockMvc.perform(post("/create")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CreateCustomerResponse response = fromJsonString(json, CreateCustomerResponse.class);

        Customer customer = customerManagementService.getCustomer(response.getUuid()).get();

        assertTrue(customer.getName().equals(request.getName()));
        assertTrue(customer.getSpecialityPreference().equals("No Preference"));
    }

    @Test
    public void testCreateCustomerEmptyName() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest();

        request.setName(null);
        request.setSpecialityPreference("my-preference");

        mockMvc.perform(post("/create")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testGetCustomer() throws Exception {
        Customer customer = new Customer();

        customer.setName("test-user");
        customer.setSpecialityPreference("my-preference");

        String customerUuid = customerManagementService.createCustomer(customer).get();

        mockMvc.perform(get("/get?uuid=" + customerUuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(customerUuid))
                .andExpect(jsonPath("$.name").value(customer.getName()))
                .andExpect(jsonPath("$.specialityPreference").value(customer.getSpecialityPreference()));
    }

    @Test
    public void testGetCustomerInvalidUuid() throws Exception {
        Customer customer = new Customer();

        customer.setName("test-user");
        customer.setSpecialityPreference("my-preference");

        String customerUuid = customerManagementService.createCustomer(customer).get();

        mockMvc.perform(get("/get?uuid=bogus-uuid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testGetAll() throws Exception {
        LinkedList<Customer> customers = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();

            customer.setName("test-user");
            customer.setSpecialityPreference("my-preference");

            String customerUuid = customerManagementService.createCustomer(customer).get();

            customer.setUuid(customerUuid);

            customers.add(customer);
        }

        mockMvc.perform(get("/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(10)));
    }


    @Test
    public void testGetAllByPreference() throws Exception {
        LinkedList<Customer> customers = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            Customer customer = new Customer();

            customer.setName("test-user");
            customer.setSpecialityPreference("preference-keyword-preference");

            String customerUuid = customerManagementService.createCustomer(customer).get();

            customer.setUuid(customerUuid);

            customers.add(customer);
        }

        mockMvc.perform(get("/get-all-by-preference?specialityPreference=keyword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        Customer customer = new Customer();

        customer.setName("test-user");
        customer.setSpecialityPreference("my-preference");

        String customerUuid = customerManagementService.createCustomer(customer).get();

        mockMvc.perform(delete("/delete?uuid=" + customerUuid))
                .andExpect(status().isOk());

        assertFalse(customerManagementService.getCustomer(customerUuid).isPresent());
    }

    @Test
    public void testDeleteCustomerInvalidUuid() throws Exception {
        Customer customer = new Customer();

        customer.setName("test-user");
        customer.setSpecialityPreference("my-preference");

        customerManagementService.createCustomer(customer);

        mockMvc.perform(delete("/delete?uuid=bogus-uuid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer customer = new Customer();

        customer.setName("test-user");
        customer.setSpecialityPreference("my-preference");

        String customerUuid = customerManagementService.createCustomer(customer).get();

        UpdateCustomerRequest request = new UpdateCustomerRequest();

        request.setUuid(customerUuid);
        request.setName("other-test-user");
        request.setSpecialityPreference("other-my-preference");

        mockMvc.perform(put("/update")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk());

        customer = customerManagementService.getCustomer(customerUuid).get();

        assertTrue(customer.getUuid().equals(request.getUuid()));
        assertTrue(customer.getName().equals(request.getName()));
        assertTrue(customer.getSpecialityPreference().equals(request.getSpecialityPreference()));
    }

    @Test
    public void testUpdateCustomerNoPreference() throws Exception {
        Customer customer = new Customer();

        customer.setName("test-user");
        customer.setSpecialityPreference("my-preference");

        String customerUuid = customerManagementService.createCustomer(customer).get();

        UpdateCustomerRequest request = new UpdateCustomerRequest();

        request.setUuid(customerUuid);
        request.setName("other-test-user");
        request.setSpecialityPreference(null);

        mockMvc.perform(put("/update")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk());

        customer = customerManagementService.getCustomer(customerUuid).get();

        assertTrue(customer.getUuid().equals(request.getUuid()));
        assertTrue(customer.getName().equals(request.getName()));
        assertTrue(customer.getSpecialityPreference().equals("No Preference"));
    }


    @Test
    public void testUpdateCustomerInvalidName() throws Exception {
        Customer customer = new Customer();

        customer.setName("test-user");
        customer.setSpecialityPreference("my-preference");

        String customerUuid = customerManagementService.createCustomer(customer).get();

        UpdateCustomerRequest request = new UpdateCustomerRequest();

        request.setUuid(customerUuid);
        request.setName(null);
        request.setSpecialityPreference(null);

        mockMvc.perform(put("/update")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }


    @Test
    public void testUpdateCustomerInvalidUuid() throws Exception {
        Customer customer = new Customer();

        customer.setName("test-user");
        customer.setSpecialityPreference("my-preference");

        String customerUuid = customerManagementService.createCustomer(customer).get();

        UpdateCustomerRequest request = new UpdateCustomerRequest();

        request.setUuid("bogus-uuid");
        request.setName("other-test-user");
        request.setSpecialityPreference("other-my-preference");

        mockMvc.perform(put("/update")
                        .content(toJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
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
