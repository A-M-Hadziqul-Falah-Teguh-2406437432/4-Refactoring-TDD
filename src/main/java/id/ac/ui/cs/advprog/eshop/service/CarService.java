package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;

public interface CarService extends CrudReadService<Car, String>, CrudWriteService<Car, String> {
    void update(String carId, Car car);
}
