package carmanagement.cockpit.dealer;

import carmanagement.cockpit.car.Car;
import carmanagement.cockpit.car.CarService;
import carmanagement.cockpit.car.dto.Position;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Long saveDealer(@RequestBody Dealer dealer) {
        return dealerService.saveDealer(dealer).getId();
    }
}
