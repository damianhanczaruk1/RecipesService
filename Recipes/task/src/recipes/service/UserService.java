package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.repository.UserRepository;
import recipes.exception.EmailAlreadyExistsException;
import recipes.model.User;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        if (findUserByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException("Email already exists");
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
