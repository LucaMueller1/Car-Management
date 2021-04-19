package carmanagement.cockpit.dealer;

import carmanagement.cockpit.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerRepository extends JpaRepository<Dealer, Long> {

}
