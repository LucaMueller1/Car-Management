package carmanagement.cockpit.dealer;

import carmanagement.cockpit.car.Car;
import carmanagement.cockpit.car.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealerService {

    @Autowired
    private DealerRepository repository;

    public Dealer saveDealer(Dealer dealer) {
        return repository.save(dealer);
    }

    public List<Dealer> findAll() {
        return repository.findAll();
    }
}
