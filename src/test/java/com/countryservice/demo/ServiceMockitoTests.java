package com.countryservice.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.repositories.CountryRepository;
import com.countryservice.demo.services.CountryService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceMockitoTests {

    @Mock
    private CountryRepository countryrep;

    @InjectMocks
    private CountryService countryService;

    @Test
    @Order(1)
    public void test_getAllCountries() {
        List<Country> list = new ArrayList<>();
        list.add(new Country(1, "India", "Delhi"));
        list.add(new Country(2, "USA", "Washington"));

        when(countryrep.findAll()).thenReturn(list);

        assertEquals(2, countryService.getAllCountries().size());
    }

    @Test
    @Order(2)
    public void test_getCountryByID() {
        List<Country> list = new ArrayList<>();
        list.add(new Country(1, "India", "Delhi"));
        list.add(new Country(2, "USA", "Washington"));

        when(countryrep.findAll()).thenReturn(list);

        assertEquals(1, countryService.getCountryById(1).getIdCountry());
    }

    @Test
    @Order(3)
    public void test_getCountryByName() {
        List<Country> list = new ArrayList<>();
        list.add(new Country(1, "India", "Delhi"));
        list.add(new Country(2, "USA", "Washington"));

        when(countryrep.findAll()).thenReturn(list);

        assertEquals("USA", countryService.getCountryByName("USA").getName());
    }

    @Test
    @Order(4)
    public void test_addCountry() {
        Country c = new Country(3, "France", "Paris");

        when(countryrep.save(c)).thenReturn(c);

        assertEquals(c, countryService.addCountry(c));
    }

    @Test
    @Order(5)
    public void test_updateCountry() {
        Country c = new Country(3, "Germany", "Berlin");

        when(countryrep.save(c)).thenReturn(c);

        assertEquals(c, countryService.updateCountry(c));
    }

    @Test
    @Order(6)
    public void test_deleteCountry() {
        Country c = new Country(3, "Germany", "Berlin");

        countryService.deleteCountry(c);

        verify(countryrep, times(1)).delete(c);
    }
}
