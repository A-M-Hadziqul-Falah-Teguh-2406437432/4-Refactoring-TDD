package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping({"/create", "/createCar"})
    public String createCarPage(Model model) {
        model.addAttribute("car", new Car());
        return "createCar";
    }

    @PostMapping({"/create", "/createCar"})
    public String createCarPost(@ModelAttribute Car car, Model model) {
        carService.create(car);
        return "redirect:/car/list";
    }

    @GetMapping({"", "/list", "/listCar"})
    public String carListPage(Model model) {
        List<Car> allCars = carService.findAll();
        model.addAttribute("cars", allCars);
        return "carList";
    }

    @GetMapping({"/edit/{carId}", "/editCar/{carId}"})
    public String editCarPage(@PathVariable String carId, Model model) {
        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        return "editCar";
    }

    @PostMapping({"/edit", "/editCar"})
    public String editCarPost(@ModelAttribute Car car, Model model) {
        carService.update(car.getCarId(), car);
        return "redirect:/car/list";
    }

    @GetMapping("/delete/{carId}")
    public String deleteCarByPath(@PathVariable("carId") String carId) {
        carService.delete(carId);
        return "redirect:/car/list";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") String carId) {
        carService.delete(carId);
        return "redirect:/car/list";
    }
}
