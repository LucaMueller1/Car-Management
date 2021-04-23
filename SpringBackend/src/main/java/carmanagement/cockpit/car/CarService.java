package carmanagement.cockpit.car;

import carmanagement.cockpit.car.dto.NewCar;
import carmanagement.cockpit.car.dto.Position;
import carmanagement.cockpit.dealer.Dealer;
import carmanagement.cockpit.dealer.DealerRepository;
import carmanagement.cockpit.dealer.DealerService;
import carmanagement.cockpit.user.User;
import carmanagement.cockpit.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CarService {
    private static final Logger log = LoggerFactory.getLogger(CarService.class);

    @Autowired
    private CarRepository repository;
    @Autowired
    private DealerRepository dealerRepository;
    @Autowired
    private UserRepository userRepository;

    public Car findById(Long id) {
        Optional<Car> opt = repository.findById(id);
        if(!opt.isPresent()) {
            return null;
        }
        return opt.get();
    }

    public Car saveCar(NewCar newCar) {
        Dealer dealer = null;
        User user = null;
        if (dealerRepository.findById(newCar.getDealer_id()).isPresent()) dealer = dealerRepository.findById(newCar.getDealer_id()).get();
        Car car = new Car(null, newCar.getBrand(), dealer, newCar.getLatitude(), newCar.getLongitude(), newCar.getPrice(), user);
        log.info("saving new car");
        return repository.save(car);
    }

    public List<Car> findAll() {
        return repository.findAll();
    }

    public Set<Car> findAllRentable() {return repository.findAllByUserId(null);}

    public Car updateCarPosition(Position position){
        Car car = repository.findById(position.getCar_id()).get();
        car.setLatitude(position.getLatitude());
        car.setLongitude(position.getLongitude());
        return repository.save(car);
    }

    public Car updateRelativeCarPosition(Position position){
        Car car = repository.findById(position.getCar_id()).get();
        Double latitude = car.getLatitude();
        Double longitude = car.getLongitude();

        car.setLatitude(latitude + position.getLatitude());
        car.setLongitude(longitude + position.getLongitude());
        return repository.save(car);
    }

}
