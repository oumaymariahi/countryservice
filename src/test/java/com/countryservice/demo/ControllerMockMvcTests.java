package com.countryservice.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CountryController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerMockMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @Test
    @Order(1)
    public void test_getAllCountries() throws Exception {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1,"India","Delhi"));
        countries.add(new Country(2,"USA","Washington"));

        when(countryService.getAllCountries()).thenReturn(countries);

        mockMvc.perform(get("/getcountries"))
            .andExpect(status().isFound());
    }

    @Test
    @Order(2)
    public void test_getCountryByID() throws Exception {
        Country country = new Country(1,"India","Delhi");

        when(countryService.getCountryById(1)).thenReturn(country);

        mockMvc.perform(get("/getcountries/1"))
            .andExpect(status().isFound())
            .andExpect(jsonPath("$.idCountry").value(1))
            .andExpect(jsonPath("$.name").value("India"))
            .andExpect(jsonPath("$.capital").value("Delhi"));
    }

    @Test
    @Order(3)
    public void test_addCountry() throws Exception {
        Country country = new Country(3,"Germany","Berlin");

        when(countryService.addCountry(country)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(
                post("/addcountry")
                .content(mapper.writeValueAsString(country))
                .contentType("application/json"))
            .andExpect(status().isCreated());
    }
}
