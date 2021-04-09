package carmanagement.cockpit.car;

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

}
