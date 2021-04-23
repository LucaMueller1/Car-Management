package carmanagement.cockpit.user;

import carmanagement.cockpit.car.Car;
import carmanagement.cockpit.user.dto.NewUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(NewUser newUser){
        User user = new User(null, newUser.getName(), newUser.getLatitude(), newUser.getLongitude());
       return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        Optional<User> opt = userRepository.findById(id);
        if(!opt.isPresent()) {
            return null;
        }
        return opt.get();
    }
}
