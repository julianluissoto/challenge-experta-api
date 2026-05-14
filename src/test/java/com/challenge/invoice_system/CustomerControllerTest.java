package com.challenge.invoice_system;

import com.challenge.invoice_system.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Tests de Integración: Gestión de Clientes")
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Debe registrar un cliente exitosamente cuando los datos son válidos")
    public void shouldRegisterCustomerSuccessfully() throws Exception {
        Customer customer = new Customer();
        customer.setName("Juan Perez");
        customer.setEmail("juan@example.com");
        customer.setAddress("Calle test 123");

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Perez"));
    }

    @Test
    @DisplayName("Debe retornar error 400 cuando el nombre del cliente es nulo o vacío")
    public void shouldReturnBadRequestWhenCustomerNameIsMissing() throws Exception {
        Customer customer = new Customer();
        customer.setName("");
        customer.setEmail("error@test.com");
        customer.setAddress("Direccion");

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debe obtener la lista completa de clientes registrados")
    public void shouldReturnListOfAllCustomers() throws Exception {
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}