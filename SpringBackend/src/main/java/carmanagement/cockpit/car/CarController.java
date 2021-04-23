package carmanagement.cockpit.car;


import carmanagement.cockpit.car.dto.NewCar;
import carmanagement.cockpit.car.dto.Position;
import carmanagement.cockpit.dealer.DealerController;
import carmanagement.cockpit.dealer.DealerRepository;
import org.hibernate.tool.schema.SchemaToolingLogging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


import javax.persistence.*;

@RestController
@RequestMapping("/api/v1/car")
@CrossOrigin("*")
public class CarController {
    private static final Logger log = LoggerFactory.getLogger(CarController.class);


    @Autowired
    private CarService carService;
    @Autowired
    private DealerRepository dealerRepository;
    @Autowired
    private CarRepository carRepository;

    @Autowired
    public CarController(CarService carService, DealerRepository dealerRepository){
        this.carService=carService;
        this.dealerRepository = dealerRepository;
    }

    // getAllCars
    @GetMapping("")
    public List<Car> getAllCars(){
        log.info("Cockpit: getAllCars");
        return carService.findAll();
    }

    // getAllCars
    @GetMapping("/rentable")
    public Set<Car> getAllCarsRentable(){
        log.info("Cockpit: getCarsRentable");
        return carService.findAllRentable();
    }

    // getCarById
    @GetMapping("/{id}")
    public Car getCarById(@PathVariable("id") Long id){
        log.info("Cockpit: getCarById");
        return carService.findById(id);
    }
    // PostCar
    @PostMapping("")
    public Car saveCar(@RequestBody NewCar newCar){
        log.info("Cockpit: saveCar");
        return carService.saveCar(newCar);
    }

    @DeleteMapping("/{id}")
    public Long deleteCar(@PathVariable("id") Long id){
        carRepository.deleteById(id);
        return id;
    }

    //updateCarPosition
    @PostMapping("/update/position")
    public ResponseEntity<?> updateCarPosition(@RequestBody Position position){
        log.info("Cockpit: updating absolute Position");
        return new ResponseEntity<>(carService.updateCarPosition(position), HttpStatus.OK);
    }
    @PostMapping("/update/positionRelative")
    public ResponseEntity<?> updateCarPositionRelative(@RequestBody Position position){
        log.info("Cockpit: updating relative Position");
        return new ResponseEntity<>(carService.updateRelativeCarPosition(position), HttpStatus.OK);
    }
}
