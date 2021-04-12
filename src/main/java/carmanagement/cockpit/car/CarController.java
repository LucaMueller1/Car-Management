package carmanagement.cockpit.car;


import carmanagement.cockpit.dealer.DealerRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import javax.persistence.*;

@RestController
@RequestMapping("/api/v1/car")
@CrossOrigin("*")
public class CarController {

    @Autowired
    private final CarService carService;
    @Autowired
    private final DealerRepository dealerRepository;

    @Autowired
    public CarController(CarService carService, DealerRepository dealerRepository){
        this.carService=carService;
        this.dealerRepository = dealerRepository;
    }

    // getAllCars
    @GetMapping("")
    public List<Car> getAllCars(){
        return carService.findAll();
    }
    // getCarById
    @GetMapping("/{id}")
    public Car getCarById(@PathVariable("id") Long id){
        return carService.findById(id);
    }
    // PostCar
    @PostMapping("")
    public Long saveCar(@RequestBody Car car){
        car.setDealer(dealerRepository.findById(car.getDealer().getId()).get());
        return carService.saveCar(car).getId();
    }
}
