package fr.univrouen.rss25SB.controllers;

import fr.univrouen.rss25SB.model.User;
import fr.univrouen.rss25SB.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestParam String name) {
        return userService.save(name);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAll();
    }
}
