package carmanagement.cockpit.dealer;

import carmanagement.cockpit.car.Car;
import carmanagement.cockpit.car.CarService;
import carmanagement.cockpit.car.dto.Position;
import carmanagement.cockpit.dealer.dto.NewDealer;
import carmanagement.cockpit.dealer.dto.RentRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dealer")
@CrossOrigin("*")
public class DealerController {

    @Autowired
    private DealerService dealerService;

    // getAllCars
    @GetMapping("")
    public List<Dealer> getAllDealers(){
        return dealerService.findAll();
    }

    @PostMapping("")
    public Dealer save(@RequestBody NewDealer newDealer) {
        return dealerService.save(newDealer);
    }

    @PostMapping("/price")
    public ResponseEntity<?> price(@RequestBody RentRequest rentRequest){
        return new ResponseEntity<>(dealerService.priceCar(rentRequest), HttpStatus.OK);
    }

    @PostMapping("/rent")
    public ResponseEntity<?> rentCar(@RequestBody RentRequest rentRequest){
        return new ResponseEntity<>(dealerService.rentCar(rentRequest), HttpStatus.OK);
    }

    @GetMapping("/return/{car_id}")
    public ResponseEntity<?> returnCar(@PathVariable("car_id") Long car_id){
        return new ResponseEntity<>(dealerService.returnCar(car_id), HttpStatus.OK);
    }
}
