package carmanagement.cockpit.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;


public interface CarRepository extends JpaRepository<Car, Long> {

    Set<Car> findAllByUserId(Long id);

    @Query(value = "SELECT * FROM cars WHERE id= :car_id", nativeQuery = true)
    Optional<Car> getCarById(Long car_id);

    @Query(value = "UPDATE cars SET user_id=NULL WHERE id=:car_id", nativeQuery = true)
    void deleteUser(Long car_id);
}
