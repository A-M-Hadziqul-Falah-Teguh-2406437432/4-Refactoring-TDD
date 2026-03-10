package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("car-1");
        car.setCarName("Civic");
        car.setCarColor("Black");
        car.setCarQuantity("5");
    }

    @Test
    void testCarListPageWithRootPath() throws Exception {
        when(carService.findAll()).thenReturn(List.of(car));

        mockMvc.perform(get("/car"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attributeExists("cars"));
    }

    @Test
    void testCarListPageWithAliasPath() throws Exception {
        when(carService.findAll()).thenReturn(List.of(car));

        mockMvc.perform(get("/car/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attributeExists("cars"));
    }

    @Test
    void testCreateCarPageWithAliasPath() throws Exception {
        mockMvc.perform(get("/car/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testCreateCarPostRedirectsToListAlias() throws Exception {
        mockMvc.perform(post("/car/create")
                        .flashAttr("car", car))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/list"));

        verify(carService).create(any(Car.class));
    }

    @Test
    void testEditCarPageWithAliasPath() throws Exception {
        when(carService.findById(car.getCarId())).thenReturn(car);

        mockMvc.perform(get("/car/edit/" + car.getCarId()))
                .andExpect(status().isOk())
                .andExpect(view().name("editCar"))
                .andExpect(model().attribute("car", car));
    }

    @Test
    void testDeleteCarByPathAliasRedirectsToListAlias() throws Exception {
        mockMvc.perform(get("/car/delete/" + car.getCarId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/list"));

        verify(carService).delete(car.getCarId());
    }
}
