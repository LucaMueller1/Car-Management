package carmanagement.cockpit.user;

import carmanagement.cockpit.car.Car;
import carmanagement.cockpit.dealer.DealerService;
import carmanagement.cockpit.user.dto.NewUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin("*")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllUsers(){
        log.info("Cockpit: getAllUsers");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    // getUserById
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id){
        log.info("Cockpit: getUserById");
        return userService.findById(id);
    }

    @PostMapping("")
    public User saveUser(@RequestBody NewUser newUser){
        log.info("Cockpit: saveUser");
        return userService.save(newUser);
    }

    @DeleteMapping("/{id}")
    public Long deleteUser(@PathVariable("id") Long id){
        log.info("Cockpit: deleteUser");
        userRepository.deleteById(id);
        return id;
    }
}
