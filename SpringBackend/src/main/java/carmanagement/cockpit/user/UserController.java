package carmanagement.cockpit.user;

import carmanagement.cockpit.car.Car;
import carmanagement.cockpit.user.dto.NewUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    // getUserById
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id){
        return userService.findById(id);
    }

    @PostMapping("")
    public User saveUser(@RequestBody NewUser newUser){
        return userService.save(newUser);
    }
}
