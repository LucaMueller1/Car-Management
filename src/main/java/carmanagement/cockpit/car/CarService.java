package carmanagement.cockpit.car;

import carmanagement.cockpit.car.dto.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository repository;

    public Car findById(Long id) {
        Optional<Car> opt = repository.findById(id);
        if(!opt.isPresent()) {
            return null;
        }
        return opt.get();
    }

    public Car saveCar(Car car) {
        return repository.save(car);
    }

    public List<Car> findAll() {
        return repository.findAll();
    }

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
