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

    public User save(String name) {
        return userRepository.save(new User(name));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}