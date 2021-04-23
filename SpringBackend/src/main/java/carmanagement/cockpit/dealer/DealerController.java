package carmanagement.cockpit.dealer;

import carmanagement.cockpit.car.Car;
import carmanagement.cockpit.car.CarService;
import carmanagement.cockpit.car.dto.Position;
import carmanagement.cockpit.dealer.dto.NewDealer;
import carmanagement.cockpit.dealer.dto.RentRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dealer")
@CrossOrigin("*")
public class DealerController {
    private static final Logger log = LoggerFactory.getLogger(DealerController.class);

    @Autowired
    private DealerService dealerService;
    @Autowired
    private DealerRepository dealerRepository;

    // getAllCars
    @GetMapping("")
    public List<Dealer> getAllDealers(){
        log.info("Cockpit: getAllDealers");
        return dealerService.findAll();
    }

    @PostMapping("")
    public Dealer save(@RequestBody NewDealer newDealer) {
        log.info("Cockpit: saveDealer");
        return dealerService.save(newDealer);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable("id") Long id){
        log.info("Cockpit: delete Dealer {}", id);
        dealerRepository.deleteById(id);
        return id;
    }

    @PostMapping("/price")
    public ResponseEntity<?> price(@RequestBody RentRequest rentRequest){
        log.info("Cockpit: price calculation");
        return new ResponseEntity<>(dealerService.priceCar(rentRequest), HttpStatus.OK);
    }

    @PostMapping("/rent")
    public ResponseEntity<?> rentCar(@RequestBody RentRequest rentRequest){
        log.info("Cockpit: rent car");
        return new ResponseEntity<>(dealerService.rentCar(rentRequest), HttpStatus.OK);
    }

    @GetMapping("/return/{car_id}")
    public ResponseEntity<?> returnCar(@PathVariable("car_id") Long car_id){
        log.info("Cockpit: return Car");
        return new ResponseEntity<>(dealerService.returnCar(car_id), HttpStatus.OK);
    }
}
