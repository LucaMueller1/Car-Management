package carmanagement.cockpit.car;


import carmanagement.cockpit.car.dto.NewCar;
import carmanagement.cockpit.car.dto.Position;
import carmanagement.cockpit.dealer.DealerRepository;
import org.hibernate.tool.schema.SchemaToolingLogging;
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

    // getAllCars
    @GetMapping("/rentable")
    public Set<Car> getAllCarsRentable(){
        return carService.findAllRentable();
    }

    // getCarById
    @GetMapping("/{id}")
    public Car getCarById(@PathVariable("id") Long id){
        return carService.findById(id);
    }
    // PostCar
    @PostMapping("")
    public Car saveCar(@RequestBody NewCar newCar){
        return carService.saveCar(newCar);
    }

    //updateCarPosition
    @PostMapping("/update/position")
    public ResponseEntity<?> updateCarPosition(@RequestBody Position position){
        return new ResponseEntity<>(carService.updateCarPosition(position), HttpStatus.OK);
    }
    @PostMapping("/update/positionRelative")
    public ResponseEntity<?> updateCarPositionRelative(@RequestBody Position position){
        return new ResponseEntity<>(carService.updateRelativeCarPosition(position), HttpStatus.OK);
    }
}
