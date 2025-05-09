package fr.univrouen.rss25SB.service;

import fr.univrouen.rss25SB.repository.UserRepository;
import org.springframework.stereotype.Service;
import fr.univrouen.rss25SB.model.User;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}